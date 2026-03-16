package seedu.coursepilot.logic.parser;

import static seedu.coursepilot.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.coursepilot.commons.core.index.Index;
import seedu.coursepilot.logic.commands.DeleteCommand;
import seedu.coursepilot.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public DeleteCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            String[] splitArgs = trimmedArgs.split("\\s+", 2);
            String firstArg = splitArgs[0];
            String remainingArgs = splitArgs.length > 1 ? splitArgs[1].trim() : "";
            if ("-student".equals(firstArg)) {
                Index index = ParserUtil.parseIndex(remainingArgs);
                // delete student to be done here (deletes from main list and all tutorials)
                return new DeleteCommand(index, "student");
            } else if ("-tutorial".equals(firstArg)) {
                Index index = ParserUtil.parseIndex(remainingArgs);
                return new DeleteCommand(index, "tutorial");
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
