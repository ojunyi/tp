package seedu.coursepilot.logic.parser;

import static seedu.coursepilot.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.coursepilot.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT_FLAG;

import java.util.Arrays;

import seedu.coursepilot.logic.commands.FindCommand;
import seedu.coursepilot.logic.parser.exceptions.ParseException;
import seedu.coursepilot.model.student.EmailContainsKeywordsPredicate;
import seedu.coursepilot.model.student.MatricNumberStartsWithKeywordsPredicate;
import seedu.coursepilot.model.student.NameContainsKeywordsPredicate;
import seedu.coursepilot.model.student.PhoneStartsWithKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        assert nameKeywords.length > 0;

        if (nameKeywords[0].startsWith("/")) {
            FindCommand.Flag flag = FindCommand.Flag.fromString(nameKeywords[0]);
            if (flag == null) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT_FLAG, FindCommand.MESSAGE_USAGE_FLAG));
            }

            if (nameKeywords.length < 2) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE_FLAG));
            }

            switch (flag) {
            case PHONE:
                return new FindCommand(new PhoneStartsWithKeywordsPredicate(Arrays.asList(nameKeywords)));
            case EMAIL:
                return new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
            case MATRIC:
                return new FindCommand(new MatricNumberStartsWithKeywordsPredicate(Arrays.asList(nameKeywords)));
            default:
                // Default case only occurs if you added a flag into FindCommand.Flag but did not add the case here
                // Otherwise, it should be impossible to reach here
                // Typically, parser files do not have logging, but I am adding one here as it seems appropriate
                throw new AssertionError("Unhandled flag: " + flag);
            }
        }

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
