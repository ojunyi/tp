package seedu.coursepilot.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.coursepilot.model.ReadOnlyCoursePilot;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tag.Tag;
import seedu.coursepilot.model.tutorial.Tutorial;

public class SampleDataUtilTest {

    @Test
    public void constructor_createInstance_success() {
        SampleDataUtil sampleDataUtil = new SampleDataUtil();
        assertNotNull(sampleDataUtil);
    }

    @Test
    public void getSampleStudents_groupsContainExpectedStudents() {
        Student[] studentsGroupOne = SampleDataUtil.getSampleStudents1();
        Student[] studentsGroupTwo = SampleDataUtil.getSampleStudents2();
        Student[] studentsGroupThree = SampleDataUtil.getSampleStudents3();

        assertEquals(2, studentsGroupOne.length);
        assertEquals(2, studentsGroupTwo.length);
        assertEquals(2, studentsGroupThree.length);

        assertEquals("A000000", studentsGroupOne[0].getMatriculationNumber().toString());
        assertEquals("A000001", studentsGroupOne[1].getMatriculationNumber().toString());
        assertEquals("A000002", studentsGroupTwo[0].getMatriculationNumber().toString());
        assertEquals("A000003", studentsGroupTwo[1].getMatriculationNumber().toString());
        assertEquals("A000004", studentsGroupThree[0].getMatriculationNumber().toString());
        assertEquals("A000005", studentsGroupThree[1].getMatriculationNumber().toString());
    }

    @Test
    public void getSampleTutorials_returnsExpectedTutorials() {
        List<Tutorial> tutorials = SampleDataUtil.getSampleTutorials();

        assertEquals(3, tutorials.size());
        assertEquals("CS2103T-W13", tutorials.get(0).getTutorialCode());
        assertEquals("Mon", tutorials.get(0).getDay());
        assertEquals("10:00-11:00", tutorials.get(0).getTimeSlot());
        assertEquals(20, tutorials.get(0).getCapacity());

        assertEquals("CS2103T-W14", tutorials.get(1).getTutorialCode());
        assertEquals("CS2103T-W15", tutorials.get(2).getTutorialCode());
    }

    @Test
    public void getSampleCoursePilot_studentsAssignedToTutorials() {
        ReadOnlyCoursePilot sampleCoursePilot = SampleDataUtil.getSampleCoursePilot();

        assertEquals(6, sampleCoursePilot.getStudentList().size());
        assertEquals(3, sampleCoursePilot.getTutorialList().size());

        for (Tutorial tutorial : sampleCoursePilot.getTutorialList()) {
            assertEquals(2, tutorial.getStudents().size());
        }

        Set<String> allStudentMatricNumbers = sampleCoursePilot.getStudentList().stream()
                .map(student -> student.getMatriculationNumber().toString())
                .collect(Collectors.toSet());
        Set<String> assignedStudentMatricNumbers = sampleCoursePilot.getTutorialList().stream()
                .flatMap(tutorial -> tutorial.getStudents().stream())
                .map(student -> student.getMatriculationNumber().toString())
                .collect(Collectors.toSet());

        assertEquals(allStudentMatricNumbers, assignedStudentMatricNumbers);
    }

    @Test
    public void getTagSet_duplicateTags_returnsUniqueTags() {
        Set<Tag> tags = SampleDataUtil.getTagSet("DeanList", "DeanList", "HonorRoll");

        assertEquals(2, tags.size());
        assertTrue(tags.contains(new Tag("DeanList")));
        assertTrue(tags.contains(new Tag("HonorRoll")));
    }
}
