package seedu.coursepilot.ui;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.coursepilot.model.tutorial.Tutorial;

/**
 * A UI component that displays a list of tutorials in the application.
 */
public class TutorialCodeListPanel extends UiPart<Region> {

    private static final String FXML = "TutorialCodeListPanel.fxml";
    private static final String SELECTED_STYLE_CLASS = "tutorial-selected";
    private static final String SELECTED_INDICATOR = " ●";
    private static final String CELL_PADDING = "  ";

    /**
     * The {@code TableView} UI element that displays the list of tutorials.
     */
    @FXML
    private TableView<Tutorial> tutorialCodeListView;

    /**
     * The column that displays tutorial codes.
     */
    @FXML
    private TableColumn<Tutorial, String> tutorialCodeColumn;

    /**
     * Creates a {@code TutorialCodeListPanel} and populates the list view
     * with tutorial data.
     */
    public TutorialCodeListPanel(ObservableList<Tutorial> tutorials, ObjectProperty<Tutorial> currentTutorial) {
        super(FXML);
        setupTableView(tutorials);
        setupCellFactory(currentTutorial);
        setupTutorialListener(currentTutorial);
    }

    private void setupTableView(ObservableList<Tutorial> tutorials) {
        tutorialCodeListView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tutorialCodeListView.setItems(tutorials);
        tutorialCodeListView.setSelectionModel(new NoOpTableSelectionModel<>(tutorialCodeListView));
        tutorialCodeListView.addEventFilter(KeyEvent.KEY_PRESSED, this::consumeNavigationKeys);
    }

    private void consumeNavigationKeys(KeyEvent event) {
        KeyCode key = event.getCode();
        if (key == KeyCode.UP || key == KeyCode.DOWN || key == KeyCode.PAGE_UP
                || key == KeyCode.PAGE_DOWN || key == KeyCode.HOME || key == KeyCode.END) {
            event.consume();
        }
    }

    private void setupCellFactory(ObjectProperty<Tutorial> currentTutorial) {
        tutorialCodeColumn.setCellFactory(col -> new TableCell<Tutorial, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                TableRow<Tutorial> row = getTableRow();
                Tutorial tutorial = row == null ? null : row.getItem();
                if (empty || tutorial == null) {
                    setText(null);
                    setStyle("");
                    return;
                }
                updateCellDisplay(tutorial, currentTutorial);
            }

            private void updateCellDisplay(Tutorial tutorial, ObjectProperty<Tutorial> currentTutorial) {
                Tutorial current = currentTutorial == null ? null : currentTutorial.get();
                boolean isSelected = current != null && current.isSameTutorial(tutorial);
                setText(CELL_PADDING + tutorial.getTutorialCode() + (isSelected ? SELECTED_INDICATOR : ""));
                if (isSelected) {
                    getStyleClass().add(SELECTED_STYLE_CLASS);
                } else {
                    getStyleClass().remove(SELECTED_STYLE_CLASS);
                }
            }
        });
    }

    private void setupTutorialListener(ObjectProperty<Tutorial> currentTutorial) {
        if (currentTutorial != null) {
            currentTutorial.addListener((obs, oldVal, newVal) -> {
                tutorialCodeListView.refresh();
                tutorialCodeListView.scrollTo(newVal);
            });
        }
    }

    /**
     * Returns the {@code TableView} displaying tutorial codes.
     */
    public TableView<Tutorial> getTableView() {
        return tutorialCodeListView;
    }
}
