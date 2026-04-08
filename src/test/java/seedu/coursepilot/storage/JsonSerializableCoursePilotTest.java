package seedu.coursepilot.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.coursepilot.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.coursepilot.commons.exceptions.IllegalValueException;
import seedu.coursepilot.commons.util.JsonUtil;
import seedu.coursepilot.model.CoursePilot;
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

}
