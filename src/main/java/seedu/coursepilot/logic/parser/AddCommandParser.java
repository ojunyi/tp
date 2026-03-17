package seedu.coursepilot.logic.parser;

import static seedu.coursepilot.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_MATRICNUMBER;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_TIMESLOT;
import static seedu.coursepilot.logic.parser.CliSyntax.PREFIX_TUTORIALCODE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.coursepilot.logic.commands.AddCommand;
import seedu.coursepilot.logic.parser.exceptions.ParseException;
import seedu.coursepilot.model.person.Email;
import seedu.coursepilot.model.person.MatricNumber;
import seedu.coursepilot.model.person.Name;
import seedu.coursepilot.model.person.Phone;
import seedu.coursepilot.model.person.Student;
import seedu.coursepilot.model.tag.Tag;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        String[] splitArgs = trimmedArgs.split("\\s+", 2);
        String firstArg = splitArgs[0];
        String remainingArgs = splitArgs.length > 1 ? " " + splitArgs[1].trim() : "";

        if ("-student".equals(firstArg)) {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(remainingArgs, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                            PREFIX_MATRICNUMBER, PREFIX_TAG);

            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_MATRICNUMBER);

            if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MATRICNUMBER, PREFIX_PHONE, PREFIX_EMAIL)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
            MatricNumber matriculationNumber =
                    ParserUtil.parseMatricNumber(argMultimap.getValue(PREFIX_MATRICNUMBER).get());
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Student student = new Student(name, phone, email, matriculationNumber, tagList);

            return new AddCommand(student);
        } else if ("-tutorial".equals(firstArg)) {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(remainingArgs,
                            PREFIX_TUTORIALCODE, PREFIX_DAY, PREFIX_TIMESLOT, PREFIX_CAPACITY);

            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIALCODE, PREFIX_DAY, PREFIX_TIMESLOT, PREFIX_CAPACITY);

            if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIALCODE, PREFIX_DAY, PREFIX_TIMESLOT, PREFIX_CAPACITY)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
            String tutorialCode = argMultimap.getValue(PREFIX_TUTORIALCODE).get();
            String day = argMultimap.getValue(PREFIX_DAY).get();
            String timeslot = argMultimap.getValue(PREFIX_TIMESLOT).get();
            int capacity = Integer.parseInt(argMultimap.getValue(PREFIX_CAPACITY).get());

            Tutorial tutorial = new Tutorial(tutorialCode, day, timeslot, capacity);
            return new AddCommand(tutorial);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }


    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
