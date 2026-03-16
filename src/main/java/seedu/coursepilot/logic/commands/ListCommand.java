package seedu.coursepilot.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.coursepilot.logic.commands.exceptions.CommandException;
import seedu.coursepilot.model.Model;

/**
 * Lists all students or tutorials based on the argument.
 */
public class ListCommand extends Command {

    /**
     * Specifies the target of the list command.
     */
    public enum ListTarget {
        STUDENT,
        TUTORIAL
    }

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + "Lists tutorial details or students in the current operating tutorial.\n"
        + "Parameters: -student | -tutorial\n"
        + "Examples: " + COMMAND_WORD + " -student\n"
        + "          " + COMMAND_WORD + " -tutorial";

    public static final String MESSAGE_SUCCESS_STUDENT =
        "Listed students in the current operating tutorial";

    public static final String MESSAGE_SUCCESS_TUTORIAL =
        "Listed tutorial details";

    public static final String MESSAGE_NO_CURRENT_OPERATING_TUTORIAL =
        "No current operating tutorial selected. Use select first.";

    private final ListTarget listTarget;

    /**
     * Creates a {@code ListCommand} with the specified listing target.
     *
     * @param listTarget The type of entity to list (student or tutorial).
     */
    public ListCommand(ListTarget listTarget) {
        requireNonNull(listTarget);
        this.listTarget = listTarget;
    }

    /**
     * Executes the list command.
     * Lists tutorials or filters students based on the current operating tutorial.
     *
     * @throws CommandException if no current operating tutorial is selected when listing students
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (listTarget == ListTarget.TUTORIAL) {
            model.clearCurrentOperatingTutorial();
            return new CommandResult(MESSAGE_SUCCESS_TUTORIAL);
        }

        if (model.getCurrentOperatingTutorial().isEmpty()) {
            throw new CommandException(MESSAGE_NO_CURRENT_OPERATING_TUTORIAL);
        }

        return new CommandResult(MESSAGE_SUCCESS_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherCommand = (ListCommand) other;
        return listTarget == otherCommand.listTarget;
    }

}
