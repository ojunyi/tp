package seedu.coursepilot.logic.commands;

import static seedu.coursepilot.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.coursepilot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.coursepilot.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.coursepilot.logic.Messages;
import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.ModelManager;
import seedu.coursepilot.model.UserPrefs;
import seedu.coursepilot.model.person.Student;
import seedu.coursepilot.model.tutorial.Tutorial;
import seedu.coursepilot.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        if (model.getFilteredTutorialList().isEmpty()) {
            Tutorial currentTutorial = new Tutorial("CS2103T-W13", "Wed", "1pm-2pm", 10);
            model.addTutorial(currentTutorial);
            model.setCurrentOperatingTutorial(currentTutorial);
        } else {
            model.setCurrentOperatingTutorial(model.getFilteredTutorialList().get(0));
        }
    }

    @Test
    public void execute_newPerson_success() {
        Student validStudent = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Tutorial currentTutorial = model.getCurrentOperatingTutorial().get();
        // expectedModel already has currentTutorial because it's in model.getAddressBook()
        expectedModel.setCurrentOperatingTutorial(currentTutorial);
        expectedModel.addPerson(validStudent);

        assertCommandSuccess(new AddCommand(validStudent), model,
                String.format(AddCommand.MESSAGE_SUCCESS_STUDENT, Messages.format(validStudent)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Student validStudent = new PersonBuilder().build();
        model.addPerson(validStudent);
        model.getCurrentOperatingTutorial().get().addStudent(validStudent);

        assertCommandFailure(new AddCommand(validStudent), model,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
