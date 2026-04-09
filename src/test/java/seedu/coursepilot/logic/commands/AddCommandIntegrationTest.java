package seedu.coursepilot.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.coursepilot.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.coursepilot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.coursepilot.testutil.TypicalStudents.getTypicalCoursePilot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.coursepilot.logic.Messages;
import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.ModelManager;
import seedu.coursepilot.model.UserPrefs;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tutorial.Capacity;
import seedu.coursepilot.model.tutorial.Day;
import seedu.coursepilot.model.tutorial.TimeSlot;
import seedu.coursepilot.model.tutorial.Tutorial;
import seedu.coursepilot.model.tutorial.TutorialCode;
import seedu.coursepilot.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCoursePilot(), new UserPrefs());
        if (model.getFilteredTutorialList().isEmpty()) {
            Tutorial currentTutorial = new Tutorial(new TutorialCode("CS2103T-W13"), new Day("Wed"),
                    new TimeSlot("13:00-14:00"), new Capacity(10));
            model.addTutorial(currentTutorial);
            model.setCurrentOperatingTutorial(currentTutorial);
        } else {
            model.setCurrentOperatingTutorial(model.getFilteredTutorialList().get(0));
        }
    }

    @Test
    public void execute_newStudent_success() {
        Student validStudent = new StudentBuilder().build();

        Model expectedModel = new ModelManager(model.getCoursePilot(), new UserPrefs());
        Tutorial currentTutorial = model.getCurrentOperatingTutorial().get();
        // expectedModel already has currentTutorial because it's in model.getCoursePilot()
        expectedModel.setCurrentOperatingTutorial(currentTutorial);
        expectedModel.addStudent(validStudent);

        assertCommandSuccess(new AddCommand(validStudent), model,
                String.format(AddCommand.MESSAGE_SUCCESS_STUDENT, Messages.format(validStudent)),
                expectedModel);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student validStudent = new StudentBuilder().build();
        Student duplicateStudent = new StudentBuilder().altContactBuild();
        model.addStudent(validStudent);
        model.getCurrentOperatingTutorial().get().addStudent(validStudent);

        assertCommandFailure(new AddCommand(duplicateStudent), model,
                AddCommand.MESSAGE_DUPLICATE_STUDENT);
    }

    @Test
    public void execute_tutorialAtCapacity_throwsCommandExceptionAndStateUnchanged() {
        Tutorial fullTutorial = new Tutorial(new TutorialCode("CS2103T-FULL"), new Day("Thu"),
                new TimeSlot("14:00-15:00"), new Capacity(1));
        model.addTutorial(fullTutorial);
        model.setCurrentOperatingTutorial(fullTutorial);

        Student existingStudent = new StudentBuilder().withName("Existing Student")
                .withPhone("90000001")
                .withEmail("existing@example.com")
                .withMatriculationNumber("A123450")
                .build();
        model.addStudent(existingStudent);
        fullTutorial.addStudent(existingStudent);

        Student rejectedStudent = new StudentBuilder().withName("Rejected Student")
                .withPhone("90000002")
                .withEmail("rejected@example.com")
                .withMatriculationNumber("A123451")
                .build();

        int globalStudentCountBefore = model.getCoursePilot().getStudentList().size();

        assertCommandFailure(new AddCommand(rejectedStudent), model,
                AddCommand.MESSAGE_TUTORIAL_FULL);
        assertEquals(globalStudentCountBefore, model.getCoursePilot().getStudentList().size());
        assertFalse(model.hasStudent(rejectedStudent));
        assertFalse(fullTutorial.hasStudent(rejectedStudent));
    }

}
