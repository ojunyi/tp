package seedu.coursepilot.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.coursepilot.logic.commands.CommandResult.PanelSwitch;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));
        assertTrue(commandResult.equals(new CommandResult("feedback", PanelSwitch.NO_CHANGE)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", false, true)));

        // different panelSwitch value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", PanelSwitch.SHOW_STUDENT_LIST)));

        // different panelSwitch value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", PanelSwitch.SHOW_TUTORIAL_DETAILS)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult(
                "feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult(
                "feedback", false, true).hashCode());

        // different panelSwitch value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult(
                "feedback", PanelSwitch.SHOW_STUDENT_LIST).hashCode());

        // different panelSwitch value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult(
                "feedback", PanelSwitch.SHOW_TUTORIAL_DETAILS).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", panelSwitch=" + commandResult.getPanelSwitch() + "}";
        assertEquals(expected, commandResult.toString());
    }
}
