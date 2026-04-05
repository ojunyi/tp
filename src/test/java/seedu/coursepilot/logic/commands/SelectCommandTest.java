package seedu.coursepilot.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.coursepilot.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.coursepilot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.coursepilot.testutil.TypicalStudents.getTypicalCoursePilot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.ModelManager;
import seedu.coursepilot.model.UserPrefs;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SelectCommand.
 */
public class SelectCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCoursePilot(), new UserPrefs());
        expectedModel = new ModelManager(model.getCoursePilot(), new UserPrefs());
    }

    @Test
    public void execute_validTutorialCode_selectsCorrectTutorial() {
        Tutorial tutorialToSelect = model.getFilteredTutorialList().get(0);
        String tutorialCode = tutorialToSelect.getTutorialCode().toString();

        SelectCommand selectCommand = new SelectCommand(tutorialCode);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SUCCESS, tutorialCode);

        expectedModel.setCurrentOperatingTutorial(tutorialToSelect);
        assertCommandSuccess(selectCommand, model, expectedMessage, expectedModel);
        assertEquals(tutorialToSelect, model.getCurrentOperatingTutorial().orElse(null));
    }

    @Test
    public void execute_validTutorialCodeCaseInsensitive_selectsCorrectTutorial() {
        Tutorial tutorialToSelect = model.getFilteredTutorialList().get(0);
        String tutorialCode = tutorialToSelect.getTutorialCode().toString();
        String lowerCaseTutorialCode = tutorialCode.toLowerCase();

        SelectCommand selectCommand = new SelectCommand(lowerCaseTutorialCode);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SUCCESS, tutorialCode);

        expectedModel.setCurrentOperatingTutorial(tutorialToSelect);
        assertCommandSuccess(selectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTutorialCodeUpperCase_selectsCorrectTutorial() {
        Tutorial tutorialToSelect = model.getFilteredTutorialList().get(0);
        String tutorialCode = tutorialToSelect.getTutorialCode().toString();
        String upperCaseTutorialCode = tutorialCode.toUpperCase();

        SelectCommand selectCommand = new SelectCommand(upperCaseTutorialCode);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SUCCESS, tutorialCode);

        expectedModel.setCurrentOperatingTutorial(tutorialToSelect);
        assertCommandSuccess(selectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTutorialCodeMixedCase_selectsCorrectTutorial() {
        Tutorial tutorialToSelect = model.getFilteredTutorialList().get(0);
        String tutorialCode = tutorialToSelect.getTutorialCode().toString();

        // Alternate upper and lower case characters
        StringBuilder mixedCase = new StringBuilder();
        for (int i = 0; i < tutorialCode.length(); i++) {
            char c = tutorialCode.charAt(i);
            mixedCase.append(i % 2 == 0 ? Character.toUpperCase(c) : Character.toLowerCase(c));
        }

        SelectCommand selectCommand = new SelectCommand(mixedCase.toString());
        String expectedMessage = String.format(SelectCommand.MESSAGE_SUCCESS, tutorialCode);

        expectedModel.setCurrentOperatingTutorial(tutorialToSelect);
        assertCommandSuccess(selectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTutorialCode_returnsNotFoundMessage() {
        String invalidCode = "INVALID999";
        SelectCommand selectCommand = new SelectCommand(invalidCode);
        String expectedMessage = String.format(SelectCommand.MESSAGE_TUTORIAL_NOT_FOUND, invalidCode);

        assertCommandFailure(selectCommand, model, expectedMessage);
        assertTrue(model.getCurrentOperatingTutorial().isEmpty());
    }

    @Test
    public void execute_clearKeyword_clearsCurrentOperatingTutorial() {
        // Set a tutorial first, then clear it
        Tutorial tutorialToSelect = model.getFilteredTutorialList().get(0);
        model.setCurrentOperatingTutorial(tutorialToSelect);

        SelectCommand clearCommand = new SelectCommand(SelectCommand.CLEAR_KEYWORD);
        String expectedMessage = SelectCommand.MESSAGE_CLEAR_TUTORIAL;

        assertCommandSuccess(clearCommand, model, expectedMessage, expectedModel);
        assertTrue(model.getCurrentOperatingTutorial().isEmpty());
    }

    @Test
    public void execute_clearKeywordCaseInsensitive_clearsCurrentOperatingTutorial() {
        Tutorial tutorialToSelect = model.getFilteredTutorialList().get(0);
        model.setCurrentOperatingTutorial(tutorialToSelect);

        SelectCommand clearCommand = new SelectCommand("NONE");
        String expectedMessage = SelectCommand.MESSAGE_CLEAR_TUTORIAL;

        assertCommandSuccess(clearCommand, model, expectedMessage, expectedModel);
        assertTrue(model.getCurrentOperatingTutorial().isEmpty());
    }

    @Test
    public void execute_clearKeywordMixedCase_clearsCurrentOperatingTutorial() {
        Tutorial tutorialToSelect = model.getFilteredTutorialList().get(0);
        model.setCurrentOperatingTutorial(tutorialToSelect);

        SelectCommand clearCommand = new SelectCommand("NonE");
        String expectedMessage = SelectCommand.MESSAGE_CLEAR_TUTORIAL;

        assertCommandSuccess(clearCommand, model, expectedMessage, expectedModel);
        assertTrue(model.getCurrentOperatingTutorial().isEmpty());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        SelectCommand selectCommand = new SelectCommand("T01");
        assertTrue(selectCommand.equals(selectCommand));
    }

    @Test
    public void equals_sameTutorialKeyword_returnsTrue() {
        SelectCommand selectCommand1 = new SelectCommand("T01");
        SelectCommand selectCommand2 = new SelectCommand("T01");
        assertTrue(selectCommand1.equals(selectCommand2));
    }

    @Test
    public void equals_sameTutorialKeywordDifferentCase_returnsTrue() {
        SelectCommand selectCommandUpper = new SelectCommand("T01");
        SelectCommand selectCommandLower = new SelectCommand("t01");
        assertTrue(selectCommandUpper.equals(selectCommandLower));
    }

    @Test
    public void equals_differentTutorialKeyword_returnsFalse() {
        SelectCommand selectCommand1 = new SelectCommand("T01");
        SelectCommand selectCommand2 = new SelectCommand("T02");
        assertFalse(selectCommand1.equals(selectCommand2));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        SelectCommand selectCommand = new SelectCommand("T01");
        assertFalse(selectCommand.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        SelectCommand selectCommand = new SelectCommand("T01");
        assertFalse(selectCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        SelectCommand selectCommand = new SelectCommand("T01");
        String expected = SelectCommand.class.getCanonicalName() + "{tutorialKeyword=T01}";
        assertEquals(expected, selectCommand.toString());
    }
}
