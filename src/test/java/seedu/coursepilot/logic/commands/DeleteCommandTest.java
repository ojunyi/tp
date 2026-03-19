package seedu.coursepilot.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.coursepilot.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.coursepilot.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.coursepilot.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.coursepilot.testutil.TypicalStudents.getTypicalCoursePilot;

import org.junit.jupiter.api.Test;

import seedu.coursepilot.commons.core.index.Index;
import seedu.coursepilot.logic.Messages;
import seedu.coursepilot.logic.commands.exceptions.CommandException;
import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.ModelManager;
import seedu.coursepilot.model.UserPrefs;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalCoursePilot(), new UserPrefs());

    @Test
    public void execute_noCurrentOperatingTutorial_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_STUDENT, "student");
        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_NO_CURRENT_OPERATING_TUTORIAL);
    }

    @Test
    public void execute_validIndexInTutorial_success() throws CommandException {
        Tutorial tutorial = model.getFilteredTutorialList().get(0);
        model.setCurrentOperatingTutorial(tutorial);

        Student studentToDelete = tutorial.getStudents().get(INDEX_FIRST_STUDENT.getZeroBased());

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_STUDENT, "student");
        CommandResult result = deleteCommand.execute(model);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(studentToDelete));
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Student should be removed from the tutorial
        assertFalse(tutorial.hasStudent(studentToDelete));
        // Student was only in one tutorial, so should be removed from main list
        assertFalse(model.hasStudent(studentToDelete));
    }

    @Test
    public void execute_invalidIndexInTutorial_throwsCommandException() {
        Tutorial tutorial = model.getFilteredTutorialList().get(0);
        model.setCurrentOperatingTutorial(tutorial);

        Index outOfBoundIndex = Index.fromOneBased(tutorial.getStudents().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, "student");

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_studentInMultipleTutorials_notRemovedFromMainList() throws CommandException {
        Tutorial tutorial1 = model.getFilteredTutorialList().get(0);
        model.setCurrentOperatingTutorial(tutorial1);

        Student studentToDelete = tutorial1.getStudents().get(INDEX_FIRST_STUDENT.getZeroBased());

        // Create second tutorial and add the same student
        Tutorial tutorial2 = new Tutorial("CS2103T-T01", "Thu", "2pm-3pm", 10);
        tutorial2.addStudent(studentToDelete);
        model.addTutorial(tutorial2);

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_STUDENT, "student");
        CommandResult result = deleteCommand.execute(model);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(studentToDelete));
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Student should be removed from current tutorial
        assertFalse(tutorial1.hasStudent(studentToDelete));
        // Student is still in tutorial2, so should remain in main list
        assertTrue(model.hasStudent(studentToDelete));
        assertTrue(tutorial2.hasStudent(studentToDelete));
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_STUDENT, "student");
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_STUDENT, "student");

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_STUDENT, "student");
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex, "student");
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}
