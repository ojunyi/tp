package seedu.coursepilot.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.coursepilot.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s2-cs2103t-w13-4.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    public static final String COMMAND_SUMMARY = String.join("\n",
            "  select TUTORIAL_CODE        Set the current operating tutorial",
            "  select NONE                 Unselect current operating tutorial",
            "  list -tutorial              List all tutorial details",
            "  list -student               List students in the current operating tutorial",
            "  add -tutorial /code CODE /day DAY /timeslot TIMESLOT /capacity CAP",
            "  add -student /name NAME /phone PHONE /email EMAIL /matric MATRIC [/tag TAG]...",
            "  edit INDEX [/name NAME] [/phone PHONE] [/email EMAIL] [/matric MATRIC] [/tag TAG]...",
            "  delete -tutorial INDEX      Delete tutorial at INDEX in the list",
            "  delete -student INDEX       Delete student at INDEX from the tutorial",
            "  find KEYWORD                Search by name (case-insensitive, substring match)",
            "  find /phone KEYWORD         Search by phone number prefix",
            "  find /email KEYWORD         Search by email (case-insensitive)",
            "  find /matric KEYWORD        Search by matric number prefix",
            "  clear                       Delete all students and tutorials",
            "  help                        Show this help window",
            "  exit                        Exit the application",
            "",
            "TIPS",
            "  - Parameters can be in any order",
            "  - Use TAB or ENTER to accept autocomplete suggestions, ESC to dismiss"
    );

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private Label commandReference;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        commandReference.setText(COMMAND_SUMMARY);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
