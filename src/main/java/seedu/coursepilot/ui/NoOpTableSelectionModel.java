package seedu.coursepilot.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;

/**
 * A no-op selection model for {@code TableView} that prevents any selection behaviour
 * while allowing JavaFX internals to call selection methods without crashing.
 */
public class NoOpTableSelectionModel<T> extends TableViewSelectionModel<T> {

    /**
     * Creates a {@code NoOpTableSelectionModel} for the given {@code TableView}.
     *
     * @param tableView the {@code TableView} this selection model is associated with
     */
    public NoOpTableSelectionModel(TableView<T> tableView) {
        super(tableView);
    }

    @Override
    public ObservableList<TablePosition> getSelectedCells() {
        return FXCollections.emptyObservableList();
    }

    @Override
    public boolean isSelected(int row, TableColumn<T, ?> column) {
        return false;
    }

    @Override
    public boolean isSelected(int index) {
        return false;
    }

    @Override
    public void select(int row, TableColumn<T, ?> column) {}

    @Override
    public void select(int index) {}

    @Override
    public void select(T obj) {}

    @Override
    public void clearAndSelect(int row, TableColumn<T, ?> column) {}

    @Override
    public void clearAndSelect(int index) {}

    @Override
    public void clearSelection(int row, TableColumn<T, ?> column) {}

    @Override
    public void clearSelection(int index) {}

    @Override
    public void clearSelection() {}

    @Override
    public void selectLeftCell() {}

    @Override
    public void selectRightCell() {}

    @Override
    public void selectAboveCell() {}

    @Override
    public void selectBelowCell() {}

    @Override
    public ObservableList<Integer> getSelectedIndices() {
        return FXCollections.emptyObservableList();
    }

    @Override
    public ObservableList<T> getSelectedItems() {
        return FXCollections.emptyObservableList();
    }

    @Override
    public void selectIndices(int index, int... indices) {}

    @Override
    public void selectAll() {}

    @Override
    public void selectFirst() {}

    @Override
    public void selectLast() {}

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void selectPrevious() {}

    @Override
    public void selectNext() {}
}
