package seedu.coursepilot.logic.commands;

import static java.util.Objects.requireNonNull;

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
        + ": Lists students or tutorials.\n"
        + "Modes: -student, -tutorial\n"
        + "Parameters: MODE\n"
        + "Example: " + COMMAND_WORD + " -student\n"
        + "Example: " + COMMAND_WORD + " -tutorial";

    public static final String MESSAGE_SUCCESS_STUDENT =
        "Listed students in the current tutorial.";

    public static final String MESSAGE_SUCCESS_TUTORIAL =
        "Listed all tutorial details.";

    public static final String MESSAGE_SUCCESS_ALL_STUDENTS =
        "Listed all students.";

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
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (listTarget == ListTarget.TUTORIAL) {
            return new CommandResult(MESSAGE_SUCCESS_TUTORIAL);
        }

        if (model.getCurrentOperatingTutorial().isEmpty()) {
            model.updateFilteredStudentList(student -> true);
            return new CommandResult(MESSAGE_SUCCESS_ALL_STUDENTS);
        }

        model.updateFilteredStudentList(
            student -> model.getCurrentOperatingTutorial().get().hasStudent(student)
        );
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
