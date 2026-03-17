package seedu.coursepilot.ui;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";
    private static final String CURRENT_TUTORIAL_PREFIX = "Current operating on tutorial: ";
    private static final String NO_CURRENT_TUTORIAL_TEXT = "No currently operating tutorial";

    @FXML
    private Label currentTutorialStatus;

    /**
     * Creates a {@code StatusBarFooter} that reflects the current operating tutorial.
     */
    public StatusBarFooter(ObjectProperty<Tutorial> currentOperatingTutorialProperty) {
        super(FXML);
        requireNonNull(currentOperatingTutorialProperty);

        this.updateFooterText(currentOperatingTutorialProperty.get());
        currentOperatingTutorialProperty.addListener((
            observable, oldValue, newValue) -> updateFooterText(newValue)
        );
    }

    private void updateFooterText(Tutorial tutorial) {
        if (tutorial == null) {
            this.currentTutorialStatus.setText(NO_CURRENT_TUTORIAL_TEXT);
            return;
        }

        this.currentTutorialStatus.setText(CURRENT_TUTORIAL_PREFIX + tutorial.getTutorialCode());
    }
}
