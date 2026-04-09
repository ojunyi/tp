package seedu.coursepilot.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

/**
 * A no-op selection model for {@code ListView} that prevents any selection behaviour
 * while allowing JavaFX internals to call selection methods without crashing.
 */
public class NoOpListSelectionModel<T> extends MultipleSelectionModel<T> {

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

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void clearAndSelect(int index) {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void select(int index) {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void select(T obj) {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void clearSelection(int index) {}

    /** Does nothing. Selection is intentionally disabled. */
    @Override
    public void clearSelection() {}

    /**
     * Returns {@code false} as no selection is maintained.
     */
    @Override
    public boolean isSelected(int index) {
        return false;
    }

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
