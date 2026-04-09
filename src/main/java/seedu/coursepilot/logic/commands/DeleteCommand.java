package seedu.coursepilot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.coursepilot.commons.core.index.Index;
import seedu.coursepilot.commons.util.ToStringBuilder;
import seedu.coursepilot.logic.Messages;
import seedu.coursepilot.logic.commands.exceptions.CommandException;
import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tutorial.Tutorial;


/**
 * Deletes a student or tutorial identified using it's displayed index from CoursePilot.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a student or tutorial by index.\n"
            + "Modes: -student, -tutorial\n"
            + "Parameters: MODE INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " -student 1\n"
            + "Example: " + COMMAND_WORD + " -tutorial 1";

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted student: %1$s";
    public static final String MESSAGE_DELETE_TUTORIAL_SUCCESS = "Deleted tutorial: %1$s";
    public static final String MESSAGE_NO_CURRENT_OPERATING_TUTORIAL =
            "No tutorial selected. Please select a tutorial to operate on first.";
    public static final String MESSAGE_STUDENT_NOT_IN_TUTORIAL =
            "This student is not in the current tutorial.";

    private final Index targetIndex;
    private String type;

    /**
     * Creates an DeleteCommand to delete the specified entry
     */
    public DeleteCommand(Index targetIndex, String type) {

        this.targetIndex = targetIndex;
        this.type = type;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.type.equals("student")) {
            Optional<Tutorial> currentTutorialOpt = model.getCurrentOperatingTutorial();
            if (currentTutorialOpt.isEmpty()) {
                throw new CommandException(MESSAGE_NO_CURRENT_OPERATING_TUTORIAL);
            }

            Tutorial currentTutorial = currentTutorialOpt.get();
            List<Student> tutorialStudents = currentTutorial.getStudents();

            if (targetIndex.getZeroBased() >= tutorialStudents.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }

            Student studentToDelete = tutorialStudents.get(targetIndex.getZeroBased());

            if (!currentTutorial.hasStudent(studentToDelete)) {
                throw new CommandException(MESSAGE_STUDENT_NOT_IN_TUTORIAL);
            }

            // Remove from current tutorial
            currentTutorial.removeStudent(studentToDelete);

            // Check if student exists in any other tutorial
            boolean inOtherTutorial = model.getFilteredTutorialList().stream()
                    .filter(t -> !t.isSameTutorial(currentTutorial))
                    .anyMatch(t -> t.hasStudent(studentToDelete));

            // If student is not in any other tutorial, remove from main list
            if (!inOtherTutorial) {
                model.deleteStudent(studentToDelete);
            }

            return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS,
                    Messages.format(studentToDelete)));
        } else if (this.type.equals("tutorial")) {
            List<Tutorial> lastShownList = model.getFilteredTutorialList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
            }

            Tutorial tutorialToDelete = lastShownList.get(targetIndex.getZeroBased());
            List<Student> studentsInTutorial = tutorialToDelete.getStudents();
            for (Student student : studentsInTutorial) {
                boolean inOtherTutorial = model.getFilteredTutorialList().stream()
                        .filter(t -> !t.isSameTutorial(tutorialToDelete))
                        .anyMatch(t -> t.hasStudent(student));
                if (!inOtherTutorial && model.hasStudent(student)) {
                    model.deleteStudent(student);
                }
            }
            String deletedCode = tutorialToDelete.getTutorialCode().toString();
            Optional<Tutorial> currentTutorialOpt = model.getCurrentOperatingTutorial();
            if (!currentTutorialOpt.isEmpty()
                    && currentTutorialOpt.get().getTutorialCode().toString().equals(deletedCode)) {
                model.clearCurrentOperatingTutorial();
                model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
            }
            model.deleteTutorial(tutorialToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_TUTORIAL_SUCCESS, Messages.format(tutorialToDelete)));
        }
        throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex)
                && Objects.equals(type, otherDeleteCommand.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex, type);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("type", type)
                .toString();
    }
}
