package seedu.coursepilot.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * A UI component that displays a list of tutorials in the application.
 */
public class TutorialListPanel extends UiPart<Region> {
    private static final String FXML = "TutorialListPanel.fxml";

    /**
     * The {@code ListView} UI element that displays the list of tutorials.
     */
    @FXML
    private ListView<Tutorial> tutorialListView;

    /**
     * Creates a {@code TutorialListPanel} and populates the list view
     * with tutorial data.
     */
    public TutorialListPanel(ObservableList<Tutorial> tutorials) {
        super(FXML);
        tutorialListView.setItems(tutorials);
    }
}
