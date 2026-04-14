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
                AddCommand.MESSAGE_DUPLICATE_STUDENT_MATRIC);
    }

    @Test
    public void execute_addOverlappingTutorial_throwsCommandException() {
        Tutorial existing = model.getCurrentOperatingTutorial().get();
        // Same day + strictly overlapping window -> must be rejected.
        Tutorial overlapping = new Tutorial(new TutorialCode("CS2103T-CLASH"),
                new Day(existing.getDay()),
                new TimeSlot(overlappingTimeSlot(existing.getTimeSlot())),
                new Capacity(10));
        String expected = String.format(AddCommand.MESSAGE_TUTORIAL_OVERLAP, existing.getTutorialCode());
        assertCommandFailure(new AddCommand(overlapping), model, expected);
    }

    @Test
    public void execute_addBackToBackTutorial_success() {
        Tutorial existing = model.getCurrentOperatingTutorial().get();
        // Touching boundary but not overlapping (end == start) -> allowed.
        Tutorial backToBack = new Tutorial(new TutorialCode("CS2103T-BTB"),
                new Day(existing.getDay()),
                new TimeSlot(backToBackTimeSlot(existing.getTimeSlot())),
                new Capacity(10));
        Model expectedModel = new ModelManager(model.getCoursePilot(), new UserPrefs());
        expectedModel.setCurrentOperatingTutorial(expectedModel.getFilteredTutorialList().get(0));
        expectedModel.addTutorial(backToBack);
        assertCommandSuccess(new AddCommand(backToBack), model,
                String.format(AddCommand.MESSAGE_SUCCESS_TUTORIAL, Messages.format(backToBack)),
                expectedModel);
    }

    private static String overlappingTimeSlot(String existing) {
        // "13:00-14:00" -> "13:30-14:30"
        String[] parts = existing.split("-");
        int startHour = Integer.parseInt(parts[0].substring(0, 2));
        return String.format("%02d:30-%02d:30", startHour, startHour + 1);
    }

    private static String backToBackTimeSlot(String existing) {
        // "13:00-14:00" -> "14:00-15:00"
        String[] parts = existing.split("-");
        int endHour = Integer.parseInt(parts[1].substring(0, 2));
        return String.format("%02d:00-%02d:00", endHour, endHour + 1);
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
