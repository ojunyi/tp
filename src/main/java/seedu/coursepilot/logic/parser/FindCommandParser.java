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
 * Parses input arguments and creates a new {@code FindCommand} object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final int KEYWORD_START_INDEX = 1;
    private static final int MIN_TOKENS_WITH_FLAG = 2;
    private static final String FLAG_PREFIX = "/";

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

        String[] tokens = trimmedArgs.split("\\s+");

        if (tokens[0].startsWith(FLAG_PREFIX)) {
            FindCommand.Flag flag = FindCommand.Flag.fromString(tokens[0]);
            if (flag == null) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT_FLAG, FindCommand.MESSAGE_USAGE_FLAG));
            }

            if (tokens.length < MIN_TOKENS_WITH_FLAG) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE_FLAG));
            }

            String[] keywords = Arrays.copyOfRange(tokens, KEYWORD_START_INDEX, tokens.length);

            switch (flag) {
            case PHONE:
                return new FindCommand(new PhoneStartsWithKeywordsPredicate(Arrays.asList(keywords)));
            case EMAIL:
                return new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));
            case MATRIC:
                return new FindCommand(new MatricNumberStartsWithKeywordsPredicate(Arrays.asList(keywords)));
            default:
                // Default case only occurs if you added a flag into FindCommand.Flag but did not add the case here
                // Otherwise, it should be impossible to reach here
                throw new AssertionError("Unhandled flag: " + flag);
            }
        }

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(tokens)));
    }
}
