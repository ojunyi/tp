package seedu.coursepilot.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.coursepilot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.coursepilot.testutil.TypicalStudents.getTypicalCoursePilot;

import org.junit.jupiter.api.Test;

import seedu.coursepilot.model.CoursePilot;
import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.ModelManager;
import seedu.coursepilot.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyCoursePilot_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCoursePilot_success() {
        Model model = new ModelManager(getTypicalCoursePilot(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCoursePilot(), new UserPrefs());
        expectedModel.setCoursePilot(new CoursePilot());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withSelectedTutorial_selectionCleared() {
        Model model = new ModelManager(getTypicalCoursePilot(), new UserPrefs());
        model.setCurrentOperatingTutorial(model.getFilteredTutorialList().get(0));

        new ClearCommand().execute(model);

        assertTrue(model.getCurrentOperatingTutorial().isEmpty());
    }

}
