package seedu.coursepilot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.coursepilot.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /**
     * Represents the panel switch behaviour after a command is executed.
     * {@code SHOW_STUDENT_LIST} switches the center panel to the student list.
     * {@code SHOW_TUTORIAL_DETAILS} switches the center panel to the tutorial details.
     * {@code NO_CHANGE} leaves the center panel as-is.
     */
    public enum PanelSwitch {
        SHOW_STUDENT_LIST,
        SHOW_TUTORIAL_DETAILS,
        NO_CHANGE
    }

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** Specifies whether a command triggers a panel switch */
    private final PanelSwitch panelSwitch;

    /**
     * Primary constructor that all other constructors will use.
     */
    private CommandResult(String feedbackToUser, boolean showHelp, boolean exit, PanelSwitch panelSwitch) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.panelSwitch = panelSwitch;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, PanelSwitch.NO_CHANGE);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, PanelSwitch.NO_CHANGE);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code panelSwitch},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, PanelSwitch panelSwitch) {
        this(feedbackToUser, false, false, panelSwitch);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public PanelSwitch getPanelSwitch() {
        return panelSwitch;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && panelSwitch == otherCommandResult.panelSwitch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, panelSwitch);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("panelSwitch", panelSwitch)
                .toString();
    }

}
