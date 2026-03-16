package seedu.coursepilot.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.coursepilot.commons.core.GuiSettings;
import seedu.coursepilot.model.person.Student;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Student student);
    boolean hasTutorial(Tutorial tutorial);
    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Student target);
    void deleteTutorial(Tutorial target);
    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Student student);
    void addTutorial(Tutorial tutorial);
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Student target, Student editedStudent);
    void setTutorial(Tutorial target, Tutorial editedTutorial);
    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Student> getFilteredPersonList();
    ObservableList<Tutorial> getFilteredTutorialList();
    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Student> predicate);
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
