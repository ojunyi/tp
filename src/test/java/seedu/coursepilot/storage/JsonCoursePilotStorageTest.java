package seedu.coursepilot.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.coursepilot.testutil.Assert.assertThrows;
import static seedu.coursepilot.testutil.TypicalStudents.ALICE;
import static seedu.coursepilot.testutil.TypicalStudents.HOON;
import static seedu.coursepilot.testutil.TypicalStudents.IDA;
import static seedu.coursepilot.testutil.TypicalStudents.getTypicalCoursePilot;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.coursepilot.commons.exceptions.DataLoadingException;
import seedu.coursepilot.model.CoursePilot;
import seedu.coursepilot.model.ReadOnlyCoursePilot;
import seedu.coursepilot.model.tutorial.Tutorial;

public class JsonCoursePilotStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCoursePilotStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCoursePilot_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCoursePilot(null));
    }

    private java.util.Optional<ReadOnlyCoursePilot> readCoursePilot(String filePath) throws Exception {
        return new JsonCoursePilotStorage(Paths.get(filePath)).readCoursePilot(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCoursePilot("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readCoursePilot("notJsonFormatCoursePilot.json"));
    }

    @Test
    public void readCoursePilot_invalidStudentCoursePilot_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readCoursePilot("invalidStudentCoursePilot.json"));
    }

    @Test
    public void readCoursePilot_invalidAndValidStudentCoursePilot_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readCoursePilot("invalidAndValidStudentCoursePilot.json"));
    }

    @Test
    public void readCoursePilot_duplicateStudentContactCoursePilot_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readCoursePilot("duplicateStudentContactCoursePilot.json"));
    }

    @Test
    public void readCoursePilot_studentWithoutTutorialCoursePilot_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readCoursePilot("studentWithoutTutorialCoursePilot.json"));
    }

    @Test
    public void readAndSaveCoursePilot_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCoursePilot.json");
        CoursePilot original = getTypicalCoursePilot();
        JsonCoursePilotStorage jsonCoursePilotStorage = new JsonCoursePilotStorage(filePath);
        Tutorial tutorial = original.getTutorialList().get(0);

        // Save in new file and read back
        jsonCoursePilotStorage.saveCoursePilot(original, filePath);
        ReadOnlyCoursePilot readBack = jsonCoursePilotStorage.readCoursePilot(filePath).get();
        assertEquals(original, new CoursePilot(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addStudent(HOON);
        tutorial.addStudent(HOON);
        tutorial.removeStudent(ALICE);
        original.removeStudent(ALICE);
        jsonCoursePilotStorage.saveCoursePilot(original, filePath);
        readBack = jsonCoursePilotStorage.readCoursePilot(filePath).get();
        assertEquals(original, new CoursePilot(readBack));

        // Save and read without specifying file path
        original.addStudent(IDA);
        tutorial.addStudent(IDA);
        jsonCoursePilotStorage.saveCoursePilot(original); // file path not specified
        readBack = jsonCoursePilotStorage.readCoursePilot().get(); // file path not specified
        assertEquals(original, new CoursePilot(readBack));

    }

    @Test
    public void saveCoursePilot_nullCoursePilot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCoursePilot(null, "SomeFile.json"));
    }

    /**
     * Saves {@code coursePilot} at the specified {@code filePath}.
     */
    private void saveCoursePilot(ReadOnlyCoursePilot coursePilot, String filePath) {
        try {
            new JsonCoursePilotStorage(Paths.get(filePath))
                    .saveCoursePilot(coursePilot, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCoursePilot_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCoursePilot(new CoursePilot(), null));
    }
}
