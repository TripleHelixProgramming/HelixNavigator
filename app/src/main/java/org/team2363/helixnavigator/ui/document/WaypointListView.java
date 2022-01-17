package org.team2363.helixnavigator.ui.document;

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
    private final ListChangeListener<? super Integer> onPathSelectedWaypointsIndicesChanged = this::pathSelectedWaypointsIndicesChanged;

    public WaypointListView(DocumentManager documentManager) {
        this.documentManager = documentManager;
        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);

        setEditable(true);
        setItems(BLANK);
        setContextMenu(null);
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getSelectionModel().getSelectedIndices().addListener(onListViewSelectedIndicesChanged);
        setCellFactory(WaypointListCell.waypointCellFactory);
        noneSelectedContextMenu.getItems().addAll(newSoftWaypointMenuItem, newHardWaypointMenuItem);
        newSoftWaypointMenuItem.setOnAction(this::newSoftWaypoint);
        newHardWaypointMenuItem.setOnAction(this::newHardWaypoint);
        noneSelectedContextMenu.setAutoHide(true);
    }

    private void listViewSelectedIndicesChanged(ListChangeListener.Change<? extends Integer> change) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().getSelectedIndices().removeListener(onPathSelectedWaypointsIndicesChanged);
            documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().clear();
            documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().selectIndices(getSelectionModel().getSelectedIndices());
            documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().getSelectedIndices().addListener(onPathSelectedWaypointsIndicesChanged);
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
            unloadSelectedPath(oldDocument.getSelectedPath());
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
            setContextMenu(null);
            getSelectionModel().getSelectedIndices().addListener(onListViewSelectedIndicesChanged);
            oldPath.getWaypointsSelectionModel().getSelectedIndices().removeListener(onPathSelectedWaypointsIndicesChanged);
        }
    }
    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            setItems(newPath.getWaypoints());
            setContextMenu(noneSelectedContextMenu);
            for (int index : documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().getSelectedIndices()) {
                getSelectionModel().select(index);
            }
            newPath.getWaypointsSelectionModel().getSelectedIndices().addListener(onPathSelectedWaypointsIndicesChanged);
        }
    }
    private void pathSelectedWaypointsIndicesChanged(ListChangeListener.Change<? extends Integer> change) {
        getSelectionModel().getSelectedIndices().removeListener(onListViewSelectedIndicesChanged);
        getSelectionModel().clearSelection();
        for (int index : change.getList()) {
            getSelectionModel().select(index);
        }
        getSelectionModel().getSelectedIndices().addListener(onListViewSelectedIndicesChanged);
    }
}
