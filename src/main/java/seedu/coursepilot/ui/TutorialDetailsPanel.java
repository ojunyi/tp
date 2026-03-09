package seedu.coursepilot.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * A UI component that displays details for all tutorials.
 */
public class TutorialDetailsPanel extends UiPart<Region> {
    private static final String FXML = "TutorialDetailsPanel.fxml";

    @FXML
    private TableView<Tutorial> tutorialDetailsTable;

    @FXML
    private TableColumn<Tutorial, String> tutorialCodeColumn;

    @FXML
    private TableColumn<Tutorial, String> dayColumn;

    @FXML
    private TableColumn<Tutorial, String> timeSlotColumn;

    @FXML
    private TableColumn<Tutorial, Integer> capacityColumn;

    public TutorialDetailsPanel(ObservableList<Tutorial> tutorials) {
        super(FXML);
        tutorialCodeColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTutorialCode()));
        dayColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDay()));
        timeSlotColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTimeSlot()));
        capacityColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCapacity()));
        tutorialDetailsTable.setItems(tutorials);
    }
}
