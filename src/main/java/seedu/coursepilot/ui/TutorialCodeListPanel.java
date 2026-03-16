package seedu.coursepilot.ui;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * A UI component that displays a list of tutorials in the application.
 */
public class TutorialCodeListPanel extends UiPart<Region> {
    private static final String FXML = "TutorialCodeListPanel.fxml";

    /**
     * The {@code TableView} UI element that displays the list of tutorials.
     */
    @FXML
    private TableView<Tutorial> tutorialCodeListView;

    @FXML
    private TableColumn<Tutorial, String> tutorialCodeColumn;

    /**
     * Creates a {@code TutorialListPanel} and populates the list view
     * with tutorial data.
     */
    public TutorialCodeListPanel(ObservableList<Tutorial> tutorials, ObjectProperty<Tutorial> currentTutorial) {
        super(FXML);
        tutorialCodeColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTutorialCode()));
        tutorialCodeListView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tutorialCodeListView.setItems(tutorials);

        if (currentTutorial != null) {
            currentTutorial.addListener((obs, oldVal, newVal) -> {
                tutorialCodeListView.getSelectionModel().select(newVal);
                tutorialCodeListView.scrollTo(newVal);
            });
        }
    }
}
