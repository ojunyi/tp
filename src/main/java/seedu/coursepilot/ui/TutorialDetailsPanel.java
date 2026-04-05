package seedu.coursepilot.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * A UI component that displays a table of tutorials and their details.
 */
public class TutorialDetailsPanel extends UiPart<Region> {

    private static final String FXML = "TutorialDetailsPanel.fxml";

    @FXML
    private TableView<Tutorial> tutorialDetailsTable;

    @FXML
    private TableColumn<Tutorial, String> dayColumn;

    @FXML
    private TableColumn<Tutorial, String> timeSlotColumn;

    @FXML
    private TableColumn<Tutorial, Integer> capacityColumn;

    /**
     * Creates a {@code TutorialDetailsPanel} with the given list of tutorials.
     *
     * @param tutorials Observable list of tutorials to be displayed in the table.
     */
    public TutorialDetailsPanel(ObservableList<Tutorial> tutorials) {
        super(FXML);
        dayColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDay()));
        timeSlotColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTimeSlot()));
        capacityColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCapacity()));

        tutorialDetailsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tutorialDetailsTable.setItems(tutorials);
        tutorialDetailsTable.setSelectionModel(new NoOpTableSelectionModel<>(tutorialDetailsTable));
        tutorialDetailsTable.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.UP || key == KeyCode.DOWN || key == KeyCode.PAGE_UP
                || key == KeyCode.PAGE_DOWN || key == KeyCode.HOME || key == KeyCode.END) {
                event.consume();
            }
        });
    }

    /**
     * Returns the {@code TableView} displaying tutorial details.
     */
    public TableView<Tutorial> getTableView() {
        return tutorialDetailsTable;
    }
}
