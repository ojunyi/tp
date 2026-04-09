package seedu.coursepilot.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.coursepilot.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.coursepilot.commons.exceptions.IllegalValueException;
import seedu.coursepilot.commons.util.JsonUtil;
import seedu.coursepilot.model.CoursePilot;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tutorial.Capacity;
import seedu.coursepilot.model.tutorial.Day;
import seedu.coursepilot.model.tutorial.TimeSlot;
import seedu.coursepilot.model.tutorial.Tutorial;
import seedu.coursepilot.model.tutorial.TutorialCode;
import seedu.coursepilot.testutil.StudentBuilder;
import seedu.coursepilot.testutil.TypicalStudents;

public class JsonSerializableCoursePilotTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCoursePilotTest");
    private static final Path TYPICAL_STUDENTS_FILE = TEST_DATA_FOLDER.resolve("typicalStudentsCoursePilot.json");
    private static final Path INVALID_STUDENT_FILE = TEST_DATA_FOLDER.resolve("invalidStudentCoursePilot.json");
    private static final Path DUPLICATE_STUDENT_FILE = TEST_DATA_FOLDER.resolve("duplicateStudentCoursePilot.json");
    private static final Path DUPLICATE_STUDENT_CONTACT_FILE =
            TEST_DATA_FOLDER.resolve("duplicateStudentContactCoursePilot.json");
    private static final Path STUDENT_WITHOUT_TUTORIAL_FILE =
            TEST_DATA_FOLDER.resolve("studentWithoutTutorialCoursePilot.json");

    @Test
    public void toModelType_typicalStudentsFile_success() throws Exception {
        JsonSerializableCoursePilot dataFromFile = JsonUtil.readJsonFile(TYPICAL_STUDENTS_FILE,
                JsonSerializableCoursePilot.class).get();
        CoursePilot coursePilotFromFile = dataFromFile.toModelType();
        CoursePilot typicalStudentsCoursePilot = TypicalStudents.getTypicalCoursePilot();
        assertEquals(coursePilotFromFile, typicalStudentsCoursePilot);
    }

    @Test
    public void toModelType_invalidStudentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCoursePilot dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_FILE,
                JsonSerializableCoursePilot.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudents_throwsIllegalValueException() throws Exception {
        JsonSerializableCoursePilot dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_FILE,
                JsonSerializableCoursePilot.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCoursePilot.MESSAGE_DUPLICATE_STUDENT,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudentContact_throwsIllegalValueException() throws Exception {
        JsonSerializableCoursePilot dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_CONTACT_FILE,
                JsonSerializableCoursePilot.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCoursePilot.MESSAGE_DUPLICATE_STUDENT_CONTACT,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_studentWithoutTutorial_throwsIllegalValueException() throws Exception {
        JsonSerializableCoursePilot dataFromFile = JsonUtil.readJsonFile(STUDENT_WITHOUT_TUTORIAL_FILE,
                JsonSerializableCoursePilot.class).get();
        assertThrows(IllegalValueException.class,
                JsonSerializableCoursePilot.MESSAGE_STUDENT_NOT_ASSIGNED_TO_TUTORIAL,
                dataFromFile::toModelType);
    }

    @Test
    public void constructor_readOnlyCoursePilotSource_roundTripSuccess() throws Exception {
        CoursePilot source = TypicalStudents.getTypicalCoursePilot();
        JsonSerializableCoursePilot serialized = new JsonSerializableCoursePilot(source);

        assertEquals(source, serialized.toModelType());
    }

    @Test
    public void toModelType_nullStudentsAndTutorials_success() throws Exception {
        JsonSerializableCoursePilot data = new JsonSerializableCoursePilot(null, null);
        CoursePilot coursePilot = data.toModelType();

        assertEquals(0, coursePilot.getStudentList().size());
        assertEquals(0, coursePilot.getTutorialList().size());
    }

    @Test
    public void toModelType_duplicateStudentEmailOnly_throwsIllegalValueException() {
        Student studentOne = new StudentBuilder().withName("Email One")
                .withPhone("90001001")
                .withEmail("same@example.com")
                .withMatriculationNumber("A910001")
                .build();
        Student studentTwo = new StudentBuilder().withName("Email Two")
                .withPhone("90001002")
                .withEmail("same@example.com")
                .withMatriculationNumber("A910002")
                .build();

        JsonSerializableCoursePilot data = new JsonSerializableCoursePilot(
                List.of(new JsonAdaptedStudent(studentOne), new JsonAdaptedStudent(studentTwo)),
                List.of());

        assertThrows(IllegalValueException.class,
                JsonSerializableCoursePilot.MESSAGE_DUPLICATE_STUDENT_CONTACT,
                data::toModelType);
    }

    @Test
    public void toModelType_duplicateTutorials_throwsIllegalValueException() {
        Student student = new StudentBuilder().withName("Tutorial Duplicate")
                .withPhone("90002001")
                .withEmail("dup@example.com")
                .withMatriculationNumber("A920001")
                .build();

        Tutorial tutorialOne = new Tutorial(new TutorialCode("CS2103T-W99"), new Day("Mon"),
                new TimeSlot("09:00-10:00"), new Capacity(10));
        Tutorial tutorialTwo = new Tutorial(new TutorialCode("CS2103T-W99"), new Day("Tue"),
                new TimeSlot("10:00-11:00"), new Capacity(10));
        tutorialOne.addStudent(student);
        tutorialTwo.addStudent(student);

        JsonSerializableCoursePilot data = new JsonSerializableCoursePilot(
                List.of(new JsonAdaptedStudent(student)),
                List.of(new JsonAdaptedTutorial(tutorialOne), new JsonAdaptedTutorial(tutorialTwo)));

        assertThrows(IllegalValueException.class,
                JsonSerializableCoursePilot.MESSAGE_DUPLICATE_TUTORIAL,
                data::toModelType);
    }

    @Test
    public void toModelType_duplicateTutorialStudent_throwsIllegalValueException() {
        Student student = new StudentBuilder().withName("Repeated Student")
                .withPhone("90003001")
                .withEmail("repeat@example.com")
                .withMatriculationNumber("A930001")
                .build();

        Tutorial tutorial = new Tutorial(new TutorialCode("CS2103T-W98"), new Day("Mon"),
                new TimeSlot("11:00-12:00"), new Capacity(10));
        tutorial.addStudent(student);
        tutorial.addStudent(student);

        JsonSerializableCoursePilot data = new JsonSerializableCoursePilot(
                List.of(new JsonAdaptedStudent(student)),
                List.of(new JsonAdaptedTutorial(tutorial)));

        assertThrows(IllegalValueException.class,
                JsonSerializableCoursePilot.MESSAGE_DUPLICATE_TUTORIAL_STUDENT,
                data::toModelType);
    }

    @Test
    public void toModelType_tutorialStudentNotInStudentList_throwsIllegalValueException() {
        Student listedStudent = new StudentBuilder().withName("Listed Student")
                .withPhone("90004001")
                .withEmail("listed@example.com")
                .withMatriculationNumber("A940001")
                .build();
        Student tutorialOnlyStudent = new StudentBuilder().withName("Tutorial Only")
                .withPhone("90004002")
                .withEmail("only@example.com")
                .withMatriculationNumber("A940002")
                .build();

        Tutorial tutorial = new Tutorial(new TutorialCode("CS2103T-W97"), new Day("Wed"),
                new TimeSlot("13:00-14:00"), new Capacity(10));
        tutorial.addStudent(tutorialOnlyStudent);

        JsonSerializableCoursePilot data = new JsonSerializableCoursePilot(
                List.of(new JsonAdaptedStudent(listedStudent)),
                List.of(new JsonAdaptedTutorial(tutorial)));

        assertThrows(IllegalValueException.class,
                JsonSerializableCoursePilot.MESSAGE_TUTORIAL_STUDENT_NOT_IN_STUDENT_LIST,
                data::toModelType);
    }

    @Test
    public void toModelType_tutorialExceedsCapacity_throwsIllegalValueException() {
        Student studentOne = new StudentBuilder().withName("Capacity One")
                .withPhone("90006001")
                .withEmail("capacity.one@example.com")
                .withMatriculationNumber("A960001")
                .build();
        Student studentTwo = new StudentBuilder().withName("Capacity Two")
                .withPhone("90006002")
                .withEmail("capacity.two@example.com")
                .withMatriculationNumber("A960002")
                .build();

        JsonAdaptedTutorial overCapacityTutorial = new JsonAdaptedTutorial(
                "CS2103T-W95",
                "Fri",
                "09:00-10:00",
                1,
                List.of(new JsonAdaptedStudent(studentOne), new JsonAdaptedStudent(studentTwo)));

        JsonSerializableCoursePilot data = new JsonSerializableCoursePilot(
                List.of(new JsonAdaptedStudent(studentOne), new JsonAdaptedStudent(studentTwo)),
                List.of(overCapacityTutorial));

        assertThrows(IllegalValueException.class,
                JsonAdaptedTutorial.MESSAGE_TUTORIAL_EXCEEDS_CAPACITY,
                data::toModelType);
    }

    @Test
    public void toModelType_tutorialStudentDetailsMismatch_throwsIllegalValueException() {
        Student listedStudent = new StudentBuilder().withName("Stored Student")
                .withPhone("90005001")
                .withEmail("stored@example.com")
                .withMatriculationNumber("A950001")
                .build();
        Student mismatchedStudent = new StudentBuilder(listedStudent)
                .withPhone("90005002")
                .build();

        Tutorial tutorial = new Tutorial(new TutorialCode("CS2103T-W96"), new Day("Thu"),
                new TimeSlot("15:00-16:00"), new Capacity(10));
        tutorial.addStudent(mismatchedStudent);

        JsonSerializableCoursePilot data = new JsonSerializableCoursePilot(
                List.of(new JsonAdaptedStudent(listedStudent)),
                List.of(new JsonAdaptedTutorial(tutorial)));

        assertThrows(IllegalValueException.class,
                JsonSerializableCoursePilot.MESSAGE_TUTORIAL_STUDENT_DETAILS_MISMATCH,
                data::toModelType);
    }

}
