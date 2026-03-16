package seedu.coursepilot.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.coursepilot.commons.util.ToStringBuilder;
import seedu.coursepilot.model.Model;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * Selects a tutorial in CoursePilot, making it the current operating tutorial.
 * Keyword matching is case insensitive.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects a tutorial whose code matches "
            + "the specified keywords (case-insensitive) exactly.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " CS2103T-W12";

    public static final String MESSAGE_SUCCESS = "Selected tutorial: %1$s";
    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "No tutorial found with code: %1$s";

    private final String tutorialKeyword;

    public SelectCommand(String tutorialKeyword) {
        this.tutorialKeyword = tutorialKeyword;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Tutorial tutorial = model.getFilteredTutorialList().stream()
                .filter(tut -> tut.getTutorialCode().equalsIgnoreCase(tutorialKeyword))
                .findFirst()
                .orElse(null);

        if (tutorial == null) {
            return new CommandResult(
                    String.format(MESSAGE_TUTORIAL_NOT_FOUND, tutorialKeyword));
        }

        model.setCurrentOperatingTutorial(tutorial);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, tutorial.getTutorialCode()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SelectCommand)) {
            return false;
        }
        SelectCommand otherSelectCommand = (SelectCommand) other;
        return tutorialKeyword.equals(otherSelectCommand.tutorialKeyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorialKeyword", tutorialKeyword)
                .toString();
    }
}
