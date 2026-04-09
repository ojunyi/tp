package seedu.coursepilot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.coursepilot.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.coursepilot.model.Model.PREDICATE_SHOW_ALL_TUTORIALS;

import seedu.coursepilot.model.CoursePilot;
import seedu.coursepilot.model.Model;

/**
 * Clears CoursePilot.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "CoursePilot has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setCoursePilot(new CoursePilot());
        model.clearCurrentOperatingTutorial();
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
