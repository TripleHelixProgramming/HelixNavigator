package org.team2363.lib.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class OrderableListCell<E> extends ListCell<E> {

    private static final Border NO_BORDER = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0.0, 0.0, 0.0, 0.0)));
    private static final Border TOP_BORDER = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 0.0, 0.0, 0.0)));
    private static final Border BOTTOM_BORDER = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0.0, 0.0, 1.0, 0.0)));

    private boolean dropAtTop;

    private final BooleanProperty orderable = new SimpleBooleanProperty(this, "orderable", true);

    public OrderableListCell() {
        setOnDragDetected(event -> {
            if (getItem() == null) {
                return;
            }
            Dragboard dragboard = startDragAndDrop(TransferMode.COPY_OR_MOVE); //TODO: experiment with this
            List<E> selectedItems = getListView().getSelectionModel().getSelectedItems();
            dragboard.setDragView(dragView(selectedItems.size()));
            dragboard.setDragViewOffsetX(50.0);
            dragboard.setDragViewOffsetY(50.0);
            ClipboardContent clipboard = new ClipboardContent();
            File tempFile;
            try {
                Path tempDirectoryPath = Files.createTempDirectory("items");
                tempFile = new File(tempDirectoryPath.toFile(), fileName());
                tempFile.createNewFile();
            } catch (IOException e) {
                tempFile = null;
            }
            String fileString = fileString();
            try {
                PrintWriter writer = new PrintWriter(tempFile);
                writer.print(fileString);
                writer.close();
            } catch (FileNotFoundException e) {
            }
            clipboard.putString(fileString);
            clipboard.putFiles(List.of(tempFile));
            dragboard.setContent(clipboard);

            event.consume();
        });
        setOnDragOver(event -> {
            if (event.getDragboard().hasString() || event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            if (event.getGestureSource() != this) {
                double mouseY = event.getY();
                double minY = getBoundsInLocal().getMinY();
                double maxY = getBoundsInLocal().getMaxY();
                if (mouseY - minY > maxY - mouseY) {
                    setBottomBorder();
                } else {
                    setTopBorder();
                }
            }
            event.consume();
        });
        setOnDragEntered(event -> {
        });
        setOnDragExited(event -> {
            setNoBorder();
        });
        setOnDragDropped(event -> {
            Object gestureSource = event.getGestureSource();
            if (gestureSource == this) {
                return;
            }
            Dragboard dragboard = event.getDragboard();
            ObservableList<E> items = getListView().getItems();
            boolean inThisListView = false;
            if (gestureSource instanceof ListCell<?>) {
                ListCell<?> listCell = (ListCell<?>) gestureSource;
                inThisListView = listCell.getListView() == this.getListView();
            }
            int index = dropAtTop ? getIndex() : getIndex() + 1;
            if (index > items.size()) {
                index = items.size();
            }
            boolean success;
            dropAttempt: {
                if (inThisListView) {
                    if (isOrderable()) {
                        ObservableList<Integer> selectedIndicies = getListView().getSelectionModel().getSelectedIndices();
                        List<E> selectedItems = new ArrayList<>(); // have to use this to avoid automatic updating
                        selectedItems.addAll(getListView().getSelectionModel().getSelectedItems());
                        int numSelectedLessThanIndex = 0;
                        for (int selectedIndex : selectedIndicies) {
                            if (selectedIndex < index) {
                                numSelectedLessThanIndex++;
                            }
                        }
                        // I think this may fix some bugs--clear the selection
                        // before modifying the list so that ListView doesn't
                        // do it automatically. This should make the behavior
                        // more predictable
                        getListView().getSelectionModel().clearSelection();
                        items.removeAll(selectedItems);
                        index -= numSelectedLessThanIndex;
                        items.addAll(index, selectedItems);
                        getListView().getSelectionModel().selectRange(index, index + selectedItems.size());
                        success = true;
                    } else {
                        success = false;
                    }
                } else {
                    String string;
                    if (dragboard.hasString()) {
                        string = dragboard.getString();
                    } else if (dragboard.hasFiles()) {
                        File file = dragboard.getFiles().get(0);
                        try {
                            string = Files.readString(file.toPath());
                        } catch (IOException e) {
                            success = false;
                            break dropAttempt;
                        }
                    } else {
                        success = false;
                        break dropAttempt;
                    }
                    try {
                        List<E> newItems = newItems(string);
                        items.addAll(index, newItems);
                        getListView().getSelectionModel().clearSelection();
                        getListView().getSelectionModel().selectRange(index, index + newItems.size());
                        success = true;
                    } catch (IllegalArgumentException e) {
                        success = false;
                        break dropAttempt;
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private final void setNoBorder() {
        setBorder(NO_BORDER);
    }

    private final void setTopBorder() {
        dropAtTop = true;
        setBorder(TOP_BORDER);
    }

    private final void setBottomBorder() {
        dropAtTop = false;
        setBorder(BOTTOM_BORDER);
    }

    public final BooleanProperty orderableProperty() {
        return orderable;
    }

    public final void setOrderable(boolean value) {
        orderable.set(value);
    }

    public final boolean isOrderable() {
        return orderable.get();
    }
    protected abstract Image dragView(int selectionSize);
    protected abstract String fileName();
    protected abstract String fileString();
    protected abstract List<E> newItems(String fileString) throws IllegalArgumentException;
}