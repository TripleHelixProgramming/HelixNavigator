package org.team2363.helixnavigator.document;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class HSelectionModel<E extends HPathElement> {

    private final ObservableList<E> items;

    private final ObservableList<Integer> selectedIndices = FXCollections.<Integer>observableArrayList();
    private final ObservableList<Integer> unmodifiableSelectedIndices = FXCollections.<Integer>unmodifiableObservableList(selectedIndices);
    private final ObservableList<E> selectedItems = FXCollections.<E>observableArrayList();
    private final ObservableList<E> unmodifiableSelectedItems = FXCollections.<E>unmodifiableObservableList(selectedItems);

    public HSelectionModel(ObservableList<E> items) {
        this.items = items;
        items.addListener(this::itemsChanged);
    }

    private void itemsChanged(ListChangeListener.Change<? extends E> change) {
        while (change.next()) {
            if (change.wasRemoved()) {
                for (E item : change.getRemoved()) {
                    int indexOfItem = selectedItems.indexOf(item);
                    if (indexOfItem != -1) { // if item that was removed from list was selected, then remove from
                                             // selectedIndices
                        selectedIndices.remove(indexOfItem); // since indices of selectedIndices and selectedItems are
                                                             // in the same order, we can just assume they are the same
                                                             // and remove that index.
                        selectedItems.remove(indexOfItem);
                    }
                }
            }
        }
    }

    public ObservableList<Integer> getSelectedIndices() {
        return unmodifiableSelectedIndices;
    }

    public ObservableList<E> getSelectedItems() {
        return unmodifiableSelectedItems;
    }

    public void select(int index) {
        if (index >= 0 && index < items.size()) {
            if (selectedIndices.size() == 0) {
                selectedIndices.add(index);
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
                    selectedItems.add(foundIndex, items.get(index));
                }
            }
        }
    }

    public void deselect(int index) {
        if (index >= 0 && index < items.size()) {
            int indexOfItem = selectedIndices.indexOf(index);
            if (indexOfItem != -1) {
                selectedIndices.remove(indexOfItem);
                selectedItems.remove(indexOfItem);
            }
        }
    }

    public void toggle(int index) {
        if (selectedIndices.contains(index)) {
            deselect(index);
        } else {
            select(index);
        }
    }

    public void clear() {
        selectedIndices.clear();
        selectedItems.clear();
    }

    public boolean isSelected(int index) {
        return selectedIndices.contains(index);
    }

    public boolean isSelected(E item) {
        return selectedItems.contains(item);
    }

    public void selectRange(int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            select(i);
        }
    }

    public void selectAll() {
        selectRange(0, items.size());
    }

    public void selectIndices(int... indices) {
        for (int indexInIndices : indices) {
            select(indexInIndices);
        }
    }

    public void selectIndices(Collection<? extends Integer> collection) {
        for (Integer index : collection) {
            select(index);
        }
    }

    public void deselectIndices(int... indices) {
        for (int indexInIndices : indices) {
            deselect(indexInIndices);
        }
    }

    public void deselectIndices(Collection<? extends Integer> collection) {
        for (int index : collection) {
            deselect(index);
        }
    }

    public void select(E item) {
        select(items.indexOf(item)); // note if item is not in items it will return -1, and select(int) will not
                                     // actually select it
    }

    public void selectItems(Collection<? extends E> collection) {
        for (E item : collection) {
            select(item);
        }
    }

    public void deselect(E item) {
        deselect(items.indexOf(item));
    }

    public void toggle(E item) {
        toggle(items.indexOf(item));
    }
}