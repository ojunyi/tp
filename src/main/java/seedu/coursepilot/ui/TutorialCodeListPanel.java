package seedu.coursepilot.ui;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
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
        tutorialCodeListView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tutorialCodeListView.setItems(tutorials);
        tutorialCodeListView.setSelectionModel(new NoOpTableSelectionModel<>(tutorialCodeListView));
        tutorialCodeListView.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.UP || key == KeyCode.DOWN || key == KeyCode.PAGE_UP
                || key == KeyCode.PAGE_DOWN || key == KeyCode.HOME || key == KeyCode.END) {
                event.consume();
            }
        });

        tutorialCodeColumn.setCellFactory(col -> new TableCell<Tutorial, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                    return;
                }
                Tutorial tutorial = getTableRow().getItem();
                Tutorial current = currentTutorial == null ? null : currentTutorial.get();
                boolean isSelected = current != null && current.isSameTutorial(tutorial);
                setText(isSelected ? "  " + tutorial.getTutorialCode() + " ●" : "  " + tutorial.getTutorialCode());
                setStyle(isSelected ? "-fx-background-color: #3a7bd5; -fx-font-weight: bold;" : "");
            }
        });

        if (currentTutorial != null) {
            currentTutorial.addListener((obs, oldVal, newVal) -> {
                tutorialCodeListView.refresh();
                tutorialCodeListView.scrollTo(newVal);
            });
        }
    }

    public TableView<Tutorial> getTableView() {
        return tutorialCodeListView;
    }
}
