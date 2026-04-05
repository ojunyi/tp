package seedu.coursepilot.ui;

import static java.util.Objects.requireNonNull;

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
        super(requireNonNull(tableView));
    }

    /**
     * Returns an empty list as no cell selection is maintained.
     */
    @Override
    public ObservableList<TablePosition> getSelectedCells() {
        return FXCollections.emptyObservableList();
    }

    /**
     * Returns {@code false} as no selection is maintained.
     */
    @Override
    public boolean isSelected(int row, TableColumn<T, ?> column) {
        return false;
    }

    /**
     * Returns {@code false} as no selection is maintained.
     */
    @Override
    public boolean isSelected(int index) {
        return false;
    }

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void select(int row, TableColumn<T, ?> column) {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void select(int index) {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void select(T obj) {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void clearAndSelect(int row, TableColumn<T, ?> column) {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void clearAndSelect(int index) {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void clearSelection(int row, TableColumn<T, ?> column) {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void clearSelection(int index) {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void clearSelection() {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void selectLeftCell() {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void selectRightCell() {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void selectAboveCell() {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void selectBelowCell() {}

    /**
     * Returns an empty list as no selection is maintained.
     */
    @Override
    public ObservableList<Integer> getSelectedIndices() {
        return FXCollections.emptyObservableList();
    }

    /**
     * Returns an empty list as no selection is maintained.
     */
    @Override
    public ObservableList<T> getSelectedItems() {
        return FXCollections.emptyObservableList();
    }

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void selectIndices(int index, int... indices) {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void selectAll() {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void selectFirst() {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void selectLast() {}

    /**
     * Returns {@code true} as this model maintains no selections.
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void selectPrevious() {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void selectNext() {}
}
