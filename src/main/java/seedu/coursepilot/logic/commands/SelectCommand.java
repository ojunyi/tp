package seedu.coursepilot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.coursepilot.commons.util.ToStringBuilder;
import seedu.coursepilot.logic.commands.exceptions.CommandException;
import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * Selects a tutorial in CoursePilot, making it the current operating tutorial.
 * Tutorial code matching is case-insensitive.
 * Use {@code CLEAR_KEYWORD} as the argument to clear the current selection.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String CLEAR_KEYWORD = "none";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects a tutorial as the current operating tutorial, or clears the selection.\n"
            + "Parameters: TUTORIAL_CODE | none\n"
            + "Example: " + COMMAND_WORD + " CS2103T-W13\n"
            + "Example: " + COMMAND_WORD + " none";

    public static final String MESSAGE_SUCCESS = "Selected tutorial: %1$s";
    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "No tutorial found with code: %1$s";
    public static final String MESSAGE_CLEAR_TUTORIAL = "Cleared current tutorial.";

    private final String tutorialKeyword;

    /**
     * Creates a SelectCommand to select the tutorial with the given {@code tutorialKeyword}.
     * Use {@code "none"} to clear the current operating tutorial.
     */
    public SelectCommand(String tutorialKeyword) {
        requireNonNull(tutorialKeyword);
        this.tutorialKeyword = tutorialKeyword;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (tutorialKeyword.equalsIgnoreCase(CLEAR_KEYWORD)) {
            model.clearCurrentOperatingTutorial();
            model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
            return new CommandResult(MESSAGE_CLEAR_TUTORIAL);
        }

        Tutorial tutorial = model.getTutorialByCode(tutorialKeyword)
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_TUTORIAL_NOT_FOUND, tutorialKeyword)));

        model.setCurrentOperatingTutorial(tutorial);
        model.updateFilteredStudentList(tutorial::hasStudent);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, tutorial.getTutorialCode()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SelectCommand)) {
            return false;
        }
        SelectCommand otherSelectCommand = (SelectCommand) other;
        return tutorialKeyword.equalsIgnoreCase(otherSelectCommand.tutorialKeyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorialKeyword", tutorialKeyword)
                .toString();
    }

    @Override
    public int hashCode() {
        return tutorialKeyword.toLowerCase().hashCode();
    }
}
