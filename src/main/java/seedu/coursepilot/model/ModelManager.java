package seedu.coursepilot.model;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.coursepilot.commons.core.GuiSettings;
import seedu.coursepilot.commons.core.LogsCenter;
import seedu.coursepilot.model.student.Student;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * Represents the in-memory model of CoursePilot data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final CoursePilot coursePilot;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Tutorial> filteredTutorials;
    private final ObjectProperty<Tutorial> currentOperatingTutorial = new SimpleObjectProperty<>();


    /**
     * Initializes a ModelManager with the given coursePilot and userPrefs.
     */
    public ModelManager(ReadOnlyCoursePilot coursePilot, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(coursePilot, userPrefs);

        logger.fine("Initializing with CoursePilot: " + coursePilot + " and user prefs " + userPrefs);

        this.coursePilot = new CoursePilot(coursePilot);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredStudents = new FilteredList<>(this.coursePilot.getStudentList());
        this.filteredTutorials = new FilteredList<>(this.coursePilot.getTutorialList());

        currentOperatingTutorial.setValue(null);
    }

    public ModelManager() {
        this(new CoursePilot(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCoursePilotFilePath() {
        return userPrefs.getCoursePilotFilePath();
    }

    @Override
    public void setCoursePilotFilePath(Path coursePilotFilePath) {
        requireNonNull(coursePilotFilePath);
        userPrefs.setCoursePilotFilePath(coursePilotFilePath);
    }

    //=========== CoursePilot ================================================================================

    @Override
    public void setCoursePilot(ReadOnlyCoursePilot coursePilot) {
        this.coursePilot.resetData(coursePilot);
    }

    @Override
    public ReadOnlyCoursePilot getCoursePilot() {
        return coursePilot;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return coursePilot.hasStudent(student);
    }
    @Override
    public boolean hasTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        return coursePilot.hasTutorial(tutorial);
    }

    @Override
    public void deleteStudent(Student target) {
        coursePilot.removeStudent(target);
    }

    @Override
    public void deleteTutorial(Tutorial tutorial) {
        coursePilot.removeTutorial(tutorial);
    }

    @Override
    public void addStudent(Student student) {
        coursePilot.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void addTutorial(Tutorial tutorial) {
        coursePilot.addTutorial(tutorial);
        updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);
    }


    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        coursePilot.setStudent(target, editedStudent);
    }

    @Override
    public void setTutorial(Tutorial target, Tutorial editedTutorial) {
        requireAllNonNull(target, editedTutorial);
        coursePilot.setTutorial(target, editedTutorial);
    }

    //=========== CoursePilot ================================================================================

    @Override
    public Optional<Tutorial> getCurrentOperatingTutorial() {
        return Optional.ofNullable(currentOperatingTutorial.get());
    }

    @Override
    public Optional<Tutorial> getTutorialByCode(String tutorialCode) {
        requireNonNull(tutorialCode);
        return filteredTutorials.stream()
                .filter(tut -> tut.getTutorialCode().equalsIgnoreCase(tutorialCode))
                .findFirst();
    }

    @Override
    public ObjectProperty<Tutorial> getCurrentOperatingTutorialProperty() {
        return currentOperatingTutorial;
    }

    public void setCurrentOperatingTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        currentOperatingTutorial.set(tutorial);
    }

    @Override
    public void clearCurrentOperatingTutorial() {
        currentOperatingTutorial.setValue(null);
    }


    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedCoursePilot}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public boolean isStudentInCurrentTutorial(Student student) {
        requireNonNull(student);
        return getCurrentOperatingTutorial()
                .map(tutorial -> tutorial.hasStudent(student))
                .orElse(false);
    }

    @Override
    public ObservableList<Tutorial> getFilteredTutorialList() {
        return filteredTutorials;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
        requireNonNull(predicate);
        filteredTutorials.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return coursePilot.equals(otherModelManager.coursePilot)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredStudents.equals(otherModelManager.filteredStudents)
                && filteredTutorials.equals(otherModelManager.filteredTutorials)
                && Optional.ofNullable(currentOperatingTutorial.get())
                    .equals(Optional.ofNullable(otherModelManager.currentOperatingTutorial.get()));
    }
}
