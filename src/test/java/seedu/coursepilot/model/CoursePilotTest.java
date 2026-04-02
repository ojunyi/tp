package seedu.coursepilot.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_MATRIC_BOB;
import static seedu.coursepilot.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.coursepilot.testutil.Assert.assertThrows;
import static seedu.coursepilot.testutil.TypicalStudents.ALICE;
import static seedu.coursepilot.testutil.TypicalStudents.getTypicalCoursePilot;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tutorial.Tutorial;
import seedu.coursepilot.testutil.StudentBuilder;

public class CoursePilotTest {

    private final CoursePilot coursePilot = new CoursePilot();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), coursePilot.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> coursePilot.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyCoursePilot_replacesData() {
        CoursePilot newData = getTypicalCoursePilot();
        coursePilot.resetData(newData);
        assertEquals(newData, coursePilot);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withMatriculationNumber(VALID_MATRIC_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        CoursePilotStub newData = new CoursePilotStub(newStudents);

        coursePilot.resetData(newData);
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> coursePilot.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInCoursePilot_returnsFalse() {
        assertFalse(coursePilot.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInCoursePilot_returnsTrue() {
        coursePilot.addStudent(ALICE);
        assertTrue(coursePilot.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInCoursePilot_returnsTrue() {
        coursePilot.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withMatriculationNumber(VALID_MATRIC_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertFalse(coursePilot.hasStudent(editedAlice));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> coursePilot.getStudentList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = CoursePilot.class.getCanonicalName() + "{students=" + coursePilot.getStudentList()
                + ", tutorials=" + coursePilot.getTutorialList() + "}";
        assertEquals(expected, coursePilot.toString());
    }

    /**
     * A stub ReadOnlyCoursePilot whose students list can violate interface constraints.
     */
    private static class CoursePilotStub implements ReadOnlyCoursePilot {
        private final ObservableList<Student> students = FXCollections.observableArrayList();
        private final ObservableList<Tutorial> tutorials = FXCollections.observableArrayList();

        CoursePilotStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<Tutorial> getTutorialList() {
            return tutorials;
        }
    }

}
