package seedu.coursepilot.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.coursepilot.commons.core.GuiSettings;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    Predicate<Tutorial> PREDICATE_SHOW_ALL_TUTORIALS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' CoursePilot file path.
     */
    Path getCoursePilotFilePath();

    /**
     * Sets the user prefs' CoursePilot file path.
     */
    void setCoursePilotFilePath(Path coursePilotFilePath);

    /**
     * Replaces CoursePilot data with the data in {@code coursePilot}.
     */
    void setCoursePilot(ReadOnlyCoursePilot coursePilot);

    /** Returns the CoursePilot */
    ReadOnlyCoursePilot getCoursePilot();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the CoursePilot.
     */
    boolean hasStudent(Student student);
    boolean hasTutorial(Tutorial tutorial);
    /**
     * Deletes the given student.
     * The student must exist in the CoursePilot.
     */
    void deleteStudent(Student target);
    void deleteTutorial(Tutorial target);
    /**
     * Adds the given student.
     * {@code student} must not already exist in the CoursePilot.
     */
    void addStudent(Student student);
    void addTutorial(Tutorial tutorial);
    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the CoursePilot.
     * The student identity of {@code editedStudent} must not be the same as
     * another existing student in the CoursePilot.
     */
    void setStudent(Student target, Student editedStudent);
    void setTutorial(Tutorial target, Tutorial editedTutorial);
    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();
    ObservableList<Tutorial> getFilteredTutorialList();
    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);
    void updateFilteredTutorialList(Predicate<Tutorial> predicate);
    /** Returns the current operating tutorial, if any. */
    Optional<Tutorial> getCurrentOperatingTutorial();

    /** Returns the current operating tutorial, for JavaFX UI */
    ObjectProperty<Tutorial> getCurrentOperatingTutorialProperty();

    /** Sets the current operating tutorial. */
    void setCurrentOperatingTutorial(Tutorial tutorial);

    /** Clears the current operating tutorial. */
    void clearCurrentOperatingTutorial();
}
