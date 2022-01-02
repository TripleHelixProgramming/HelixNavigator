package org.team2363.helixnavigator.ui.document;

import java.util.List;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint.WaypointType;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;

public class WaypointListView extends ListView<HWaypoint> {

    private static final ObservableList<HWaypoint> BLANK = FXCollections.<HWaypoint>observableArrayList();

    private final DocumentManager documentManager;

    private final ContextMenu noneSelectedContextMenu = new ContextMenu();
    private final MenuItem newSoftWaypointMenuItem = new MenuItem("New soft waypoint");
    private final MenuItem newHardWaypointMenuItem = new MenuItem("New hard waypoint");

    private final ListChangeListener<? super Integer> onListViewSelectedIndicesChanged = this::listViewSelectedIndicesChanged;
    private final ChangeListener<? super HPath> onSelectedPathChanged = this::selectedPathChanged;
    private final ListChangeListener<? super Integer> onPathSelectedWaypointsIndiciesChanged = this::pathSelectedWaypointsIndiciesChanged;

    public WaypointListView(DocumentManager documentManager) {
        this.documentManager = documentManager;
        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);

        setEditable(true);
        setMinHeight(USE_COMPUTED_SIZE);
        setItems(BLANK);
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getSelectionModel().getSelectedIndices().addListener(onListViewSelectedIndicesChanged);
        setCellFactory(WaypointListCell.waypointCellFactory);
        noneSelectedContextMenu.getItems().addAll(newSoftWaypointMenuItem, newHardWaypointMenuItem);
        newSoftWaypointMenuItem.setOnAction(this::newSoftWaypoint);
        newHardWaypointMenuItem.setOnAction(this::newHardWaypoint);
        noneSelectedContextMenu.setAutoHide(true);
        setContextMenu(noneSelectedContextMenu);
    }

    private void listViewSelectedIndicesChanged(ListChangeListener.Change<? extends Integer> change) {
        // check if there is a path to deselect or select waypoints on
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            while (change.next()) {
                if (change.wasAdded()) {
                    List<? extends Integer> list = change.getAddedSubList();
                    documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().selectIndices(list);
                } // don't use else if it doesn't work for replacements -- it will crash the program
                if (change.wasRemoved()) {
                    List<? extends Integer> list = change.getRemoved();
                    documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().deselectIndices(list);
                } // TODO: consider changing this to just setting the selected waypoints to the listview selected waypoints
            }
        }
    }

    private void newSoftWaypoint(ActionEvent event) {
        HWaypoint newWaypoint = new HWaypoint(WaypointType.SOFT);
        newWaypoint.setName(String.valueOf(getItems().size()));
        getItems().add(newWaypoint);
    }
    private void newHardWaypoint(ActionEvent event) {
        HWaypoint newWaypoint = new HWaypoint(WaypointType.HARD);
        newWaypoint.setName(String.valueOf(getItems().size()));
        getItems().add(newWaypoint);
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            setItems(BLANK);
            oldDocument.selectedPathProperty().removeListener(onSelectedPathChanged);
        }
    }
    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            loadSelectedPath(newDocument.getSelectedPath());
            newDocument.selectedPathProperty().addListener(onSelectedPathChanged);
        }
    }
    private void selectedPathChanged(ObservableValue<? extends HPath> currentPath, HPath oldPath, HPath newPath) {
        unloadSelectedPath(oldPath);
        loadSelectedPath(newPath);
    }
    private void unloadSelectedPath(HPath oldPath) {
        if (oldPath != null) {
            getSelectionModel().getSelectedIndices().removeListener(onListViewSelectedIndicesChanged);
            setItems(BLANK);
            getSelectionModel().getSelectedIndices().addListener(onListViewSelectedIndicesChanged);
            oldPath.getWaypointsSelectionModel().getSelectedIndices().removeListener(onPathSelectedWaypointsIndiciesChanged);
        }
    }
    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            setItems(newPath.getWaypoints());
            for (int index : documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().getSelectedIndices()) {
                getSelectionModel().select(index);
            }
            newPath.getWaypointsSelectionModel().getSelectedIndices().addListener(onPathSelectedWaypointsIndiciesChanged);
        }
    }
    private void pathSelectedWaypointsIndiciesChanged(ListChangeListener.Change<? extends Integer> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for (int index : change.getAddedSubList()) {
                    getSelectionModel().select(index);
                }
            }
            if (change.wasRemoved()) {
                for (int index : change.getRemoved()) {
                    getSelectionModel().clearSelection(index);
                }
            }
        }
    }
}
