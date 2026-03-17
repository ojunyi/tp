package seedu.coursepilot.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.coursepilot.testutil.Assert.assertThrows;
import static seedu.coursepilot.testutil.TypicalPersons.ALICE;
import static seedu.coursepilot.testutil.TypicalPersons.BOB;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.coursepilot.commons.core.GuiSettings;
import seedu.coursepilot.logic.Messages;
import seedu.coursepilot.logic.commands.exceptions.CommandException;
import seedu.coursepilot.model.AddressBook;
import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.ReadOnlyAddressBook;
import seedu.coursepilot.model.ReadOnlyUserPrefs;
import seedu.coursepilot.model.person.Student;
import seedu.coursepilot.model.tutorial.Tutorial;
import seedu.coursepilot.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand((Student) null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Student validStudent = new PersonBuilder().build();
        Tutorial tutorial = new Tutorial("T01", "Mon", "1-2pm", 20);
        modelStub.setCurrentOperatingTutorial(tutorial);

        CommandResult commandResult = new AddCommand(validStudent).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS_STUDENT, Messages.format(validStudent)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Student validStudent = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validStudent);
        ModelStubWithPerson modelStub = new ModelStubWithPerson(validStudent);
        Tutorial tutorial = new Tutorial("T01", "Mon", "1-2pm", 20);
        tutorial.addStudent(validStudent);
        modelStub.setCurrentOperatingTutorial(tutorial);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateMatricNumber_throwsCommandException() {
        Student alice = new PersonBuilder(ALICE).build();
        Student bobWithAliceMatric = new PersonBuilder(BOB)
                .withMatriculationNumber(ALICE.getMatriculationNumber().matricNumber).build();
        AddCommand addCommand = new AddCommand(bobWithAliceMatric);
        ModelStubWithPerson modelStub = new ModelStubWithPerson(alice);
        Tutorial tutorial = new Tutorial("T01", "Mon", "1-2pm", 20);
        tutorial.addStudent(alice);
        modelStub.setCurrentOperatingTutorial(tutorial);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Student alice = new PersonBuilder().withName("Alice").build();
        Student bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        private Tutorial currentTutorial;
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTutorial(Tutorial target, Tutorial editedTutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Tutorial> getCurrentOperatingTutorial() {
            return Optional.ofNullable(currentTutorial);
        }

        @Override
        public ObjectProperty<Tutorial> getCurrentOperatingTutorialProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentOperatingTutorial(Tutorial tutorial) {
            this.currentTutorial = tutorial;
        }

        @Override
        public void clearCurrentOperatingTutorial() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasTutorial(Tutorial tutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTutorial(Tutorial target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTutorial(Tutorial tutorial) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Student student;

        ModelStubWithPerson(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public boolean hasPerson(Student student) {
            requireNonNull(student);
            return this.student.isSamePerson(student);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Student> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Student student) {
            requireNonNull(student);
            return personsAdded.stream().anyMatch(student::isSamePerson);
        }

        @Override
        public void addPerson(Student student) {
            requireNonNull(student);
            personsAdded.add(student);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
