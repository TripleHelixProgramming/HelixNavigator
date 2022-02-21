package org.team2363.helixnavigator.ui.document;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.HSelectionModel;
import org.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.document.waypoint.HSoftWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;

public class WaypointListView extends ListView<HWaypoint> {

    private final ObservableList<HWaypoint> blankItems = FXCollections.<HWaypoint>observableArrayList();
    private final MultipleSelectionModel<HWaypoint> blankSelecitonModel = new HSelectionModel<>(blankItems);

    private final DocumentManager documentManager;

    private final ContextMenu noneSelectedContextMenu = new ContextMenu();
    private final MenuItem newSoftWaypointMenuItem = new MenuItem("New soft waypoint");
    private final MenuItem newHardWaypointMenuItem = new MenuItem("New hard waypoint");

    private final ChangeListener<? super HPath> onSelectedPathChanged = this::selectedPathChanged;

    public WaypointListView(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setEditable(true);
        setItems(blankItems);
        setSelectionModel(blankSelecitonModel);
        setContextMenu(null);
        setCellFactory(WaypointListCell.WAYPOINT_CELL_FACTORY);
        noneSelectedContextMenu.getItems().addAll(newSoftWaypointMenuItem, newHardWaypointMenuItem);
        newSoftWaypointMenuItem.setOnAction(this::newSoftWaypoint);
        newHardWaypointMenuItem.setOnAction(this::newHardWaypoint);
        noneSelectedContextMenu.setAutoHide(true);

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);
    }

    private void newSoftWaypoint(ActionEvent event) {
        HWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName(String.valueOf(getItems().size()));
        getItems().add(newWaypoint);
    }
    private void newHardWaypoint(ActionEvent event) {
        HWaypoint newWaypoint = new HHardWaypoint();
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
            setItems(blankItems);
            setSelectionModel(blankSelecitonModel);
            setContextMenu(null);
        }
    }
    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            setItems(newPath.getWaypoints());
            setSelectionModel(newPath.getWaypointsSelectionModel());
            setContextMenu(noneSelectedContextMenu);
        }
    }
}