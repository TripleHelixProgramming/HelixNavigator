package org.team2363.helixnavigator.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class HSelectionModel<E extends HSelectableElement> {

    /**
     * The list of items that can be selected. This field is immutable, but the contents of
     * the list can be modified.
     */
    private final ObservableList<E> items;
    /**
     * The list of selected indices, sorted in ascending order. It can be contain indices
     * from {@code 0} to {@code items.size() - 1}.
     */
    private final ObservableList<Integer> selectedIndices = FXCollections.<Integer>observableArrayList();
    /**
     * The list of selected indices, sorted in ascending order. It can be contain indices
     * from {@code 0} to {@code items.size() - 1}. This list is immutable to prevent illegal
     * states where the list is not sorted, or there are indices out of bounds.
     */
    private final ObservableList<Integer> unmodifiableSelectedIndices = FXCollections.<Integer>unmodifiableObservableList(selectedIndices);
    /**
     * The list of selected items, sorted in the same order as the items appear in {@code items}.
     * This list updates along with {@code selectedIndices} when items are selected or deselected.
     */
    private final ObservableList<E> selectedItems = FXCollections.<E>observableArrayList();
    /**
     * The list of selected items, sorted in the same order as the items appear in {@code items}.
     * This list updates along with {@code selectedIndices} when items are selected or deselected.
     * This list is immutable to prevent illegal states where the list is not sorted, or there
     * are items not in the {@code items} list.
     */
    private final ObservableList<E> unmodifiableSelectedItems = FXCollections.<E>unmodifiableObservableList(selectedItems);

    /**
     * Constructs an {@code HSelectionModel} with an observable list of items.
     * This list cannot be set later; {@code HSelectionModel} is an immutable type.
     * 
     * @param items the list of items
     */
    public HSelectionModel(ObservableList<E> items) {
        this.items = items;
        items.addListener(this::itemsChanged);
    }

    /**
     * Called when the list of items changes, this method deletes selected
     * indices that become out of bounds, and it updates {@code selectedItems}
     * if items have been permutated.
     * 
     * @param change the changes made on the list
     */
    private void itemsChanged(ListChangeListener.Change<? extends E> change) {
        for (int i = 0; i < selectedIndices.size(); i++) {
            if (selectedIndices.get(i).intValue() >= items.size()) {
                selectedIndices.remove(i, selectedIndices.size());
                for (int j = i; i < selectedItems.size(); i++) {
                    selectedItems.get(j).setSelected(false);
                }
                selectedItems.remove(i, selectedItems.size());
                break;
            }
        }
        updateSelectedItems();
    }

    /**
     * Sets {@code selectedItems} with the items in {@code items} corresponding to
     * the selected indices.
     */
    private void updateSelectedItems() {
        List<E> newSelectedItems = new ArrayList<>();
        for (E item : selectedItems) {
            item.setSelected(false);
        }
        for (Integer index : selectedIndices) {
            E item = items.get(index);
            item.setSelected(true);
            newSelectedItems.add(item);
        }
        selectedItems.setAll(newSelectedItems);
    }

    /**
     * Returns the unmodifiable list of selected indices.
     * 
     * @return the list of selected indices
     */
    public ObservableList<Integer> getSelectedIndices() {
        return unmodifiableSelectedIndices;
    }

    /**
     * Returns the unmodifiable list of selected items.
     * 
     * @return the list of selected items
     */
    public ObservableList<E> getSelectedItems() {
        return unmodifiableSelectedItems;
    }

    public void setSelected(int index, boolean value) {
        if (value) {
            select(index);
        } else {
            deselect(index);
        }
    }

    public void setSelected(E item, boolean value) {
        if (value) {
            select(item);
        } else {
            deselect(item);
        }
    }

    /**
     * Returns {@code true} if and only if {@code index} is currently selected.
     * This is a convenience method short for {@code getSelectedIndices.contains(index)}.
     * 
     * @param index the index to check
     * @return {@code true} if the index is selected
     */
    public boolean isSelected(int index) {
        return selectedIndices.contains(index);
    }

    /**
     * Returns {@code true} if and only if {@code item} is current selected.
     * This is a convenience method short for {@code getSelectedItems.contains(item)}.
     * 
     * @param item the item to check
     * @return {@code true} if the item is selected
     */
    public boolean isSelected(E item) {
        return selectedItems.contains(item);
    }

    /**
     * Selects an index if it is within the bounds of the list of items.
     * Note: this method does not throw any exceptions if an invalid index
     * is selected.
     * 
     * @param index the index to select
     */
    public void select(int index) {
        if (index >= 0 && index < items.size()) {
            if (selectedIndices.size() == 0) {
                selectedIndices.add(index);
                E item = items.get(index);
                item.setSelected(true);
                selectedItems.add(items.get(index));
            } else {
                boolean found = false;
                int foundIndex = -1;
                int start = 0;
                int end = selectedIndices.size() - 1;
                while (!found) {
                    int testIndex = (start + end) / 2;
                    int valAtTest = selectedIndices.get(testIndex);
                    if (index > valAtTest) {
                        if (end - start <= 0) {
                            foundIndex = end + 1;
                            found = true;
                        } else {
                            start = testIndex + 1;
                        }
                    } else if (index < valAtTest) {
                        if (end - start <= 0) { // the < is for the case when the item you're adding is supposed to go
                                                // at the beginning
                            foundIndex = start;
                            found = true;
                        } else {
                            end = testIndex - 1;
                        }
                    } else {
                        found = true;
                    }
                }
                if (foundIndex != -1) {
                    selectedIndices.add(foundIndex, index);
                    E item = items.get(index);
                    item.setSelected(true);
                    selectedItems.add(foundIndex, item);
                }
            }
        }
    }

    /**
     * Selects an item if and only if it exists in the list of items.
     * 
     * @param item the item to select
     */
    public void select(E item) {
        select(items.indexOf(item));
        // note if item is not in items it will return -1, and
        // select(int) will not actually select it
    }

    /**
     * <p>
     * Deselects an index if it is selected, i.e. if:
     * </p>
     * <pre>
     * isSelected(index) == true
     * </pre>
     * <p>
     * Otherwise, does nothing.
     * </p>
     * 
     * @param index the index to deselect
     */
    public void deselect(int index) {
        if (index >= 0 && index < items.size()) {
            int indexOfItem = selectedIndices.indexOf(index);
            if (indexOfItem != -1) {
                selectedIndices.remove(indexOfItem);
                E item = items.get(index);
                item.setSelected(false);
                selectedItems.remove(indexOfItem);
            }
        }
    }

    /**
     * Deselects an item if and only if it exists in the list of items.
     * 
     * @param item the item to deselect
     */
    public void deselect(E item) {
        deselect(items.indexOf(item));
    }

    /**
     * Selects an index if it is not already selected, and deselects an index if it is
     * selected.
     * 
     * @param index the index to toggle.
     */
    public void toggle(int index) {
        if (isSelected(index)) {
            deselect(index);
        } else {
            select(index);
        }
    }

    /**
     * Toggles an item if and only if it exists in the list of items.
     * 
     * @param item the item to toggle
     */
    public void toggle(E item) {
        toggle(items.indexOf(item));
    }

    /**
     * Selects a range of indices.
     * 
     * @param startIndex the begin index (inclusive)
     * @param endIndex the end index (exclusive)
     */
    public void selectRange(int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            select(i);
        }
    }

    /**
     * Deselects a range of indices.
     * 
     * @param startIndex the begin index (inclusive)
     * @param endIndex the end index (exclusive)
     */
    public void deselectRange(int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            deselect(i);
        }
    }

    /**
     * Toggles a range of indices.
     * 
     * @param startIndex the begin index (inclusive)
     * @param endIndex the end index (exclusive)
     */
    public void toggleRange(int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            toggle(i);
        }
    }

    /**
     * Clears the selection by removing all selected indices and selected items.
     */
    public void clear() {
        selectedIndices.clear();
        for (E item : selectedItems) {
            item.setSelected(false);
        }
        selectedItems.clear();
    }

    /**
     * Selects all possible indices. After this method is called, it is
     * guaranteed that {@code items.equals(getSelectedItems()) == true}
     */
    public void selectAll() {
        selectRange(0, items.size());
    }

    /**
     * Selects an array of indices. This method iterates over the array of indices
     * and selects each.
     * 
     * @param indices the indices to select
     */
    public void selectIndices(int... indices) {
        for (int index : indices) {
            select(index);
        }
    }

    /**
     * Deselects an array of indices. This method iterates over the array of
     * indices and deselects each.
     * 
     * @param indices the indices to deselect
     */
    public void deselectIndices(int... indices) {
        for (int index : indices) {
            deselect(index);
        }
    }

    /**
     * Selects a collection of indices. This method iterates over the collection of
     * indices and selects each.
     * 
     * @param indices the indices to select
     */
    public void selectIndices(Collection<? extends Integer> indices) {
        for (Integer index : indices) {
            select(index);
        }
    }

    /**
     * Selects a collection of items. This method iterates over the collection of
     * indices and selects each.
     * 
     * @param items the items to select
     */
    public void selectItems(Collection<? extends E> items) {
        for (E item : items) {
            select(item);
        }
    }

    /**
     * Deselects a collection of indices. This method iterates over the collection of
     * indices and deselects each.
     * 
     * @param indices the indices to deselect
     */
    public void deselectIndices(Collection<? extends Integer> indices) {
        for (int index : indices) {
            deselect(index);
        }
    }

    /**
     * Deselects a collection of items. This method iterates over the collection of
     * indices and selects each.
     * 
     * @param items the items to deselect
     */
    public void deselectItems(Collection<? extends E> items) {
        for (E item : items) {
            deselect(item);
        }
    }
}