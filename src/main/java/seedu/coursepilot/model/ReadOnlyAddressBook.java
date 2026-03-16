package seedu.coursepilot.model;

import javafx.collections.ObservableList;
import seedu.coursepilot.model.person.Student;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Student> getPersonList();
    ObservableList<Tutorial> getTutorialList();
}
