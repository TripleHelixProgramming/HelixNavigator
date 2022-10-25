package org.team2363.helixnavigator.ui.document;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HAutoRoutine;
import org.team2363.helixnavigator.document.HSelectionModel;
import org.team2363.helixnavigator.document.timeline.waypoint.HHolonomicWaypoint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;

public class WaypointListView extends ListView<HHolonomicWaypoint> {

    private final ObservableList<HHolonomicWaypoint> blankItems = FXCollections.<HHolonomicWaypoint>observableArrayList();
    private final MultipleSelectionModel<HHolonomicWaypoint> blankSelecitonModel = new HSelectionModel<>(blankItems);

    private final DocumentManager documentManager;

    private final ContextMenu noneSelectedContextMenu = new ContextMenu();
    private final MenuItem newSoftWaypointMenuItem = new MenuItem("New soft waypoint");
    private final MenuItem newHardWaypointMenuItem = new MenuItem("New hard waypoint");
    private final MenuItem newCustomWaypointMenuItem = new MenuItem("New custom waypoint");
    private final MenuItem newInitialGuessWaypointMenuItem = new MenuItem("New initial guess waypoint");

    private final Label placeholder = new Label("RIGHT-CLICK TO CREATE A WAYPOINT");

    private final ChangeListener<? super HAutoRoutine> onSelectedPathChanged = this::selectedPathChanged;

    public WaypointListView(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setEditable(true);
        placeholder.setPadding(new Insets(0, 10, 0, 10));
        setPlaceholder(placeholder);
        setItems(blankItems);
        setSelectionModel(blankSelecitonModel);
        setContextMenu(null);
        setCellFactory(TimelineElementListCell.WAYPOINT_CELL_FACTORY);
        noneSelectedContextMenu.getItems().addAll(newSoftWaypointMenuItem, newHardWaypointMenuItem,
                newCustomWaypointMenuItem, newInitialGuessWaypointMenuItem);
        newSoftWaypointMenuItem.setOnAction(this::newSoftWaypoint);
        newHardWaypointMenuItem.setOnAction(this::newHardWaypoint);
        newCustomWaypointMenuItem.setOnAction(this::newCustomWaypoint);
        newInitialGuessWaypointMenuItem.setOnAction(this::newInitialGuessWaypoint);
        noneSelectedContextMenu.setAutoHide(true);

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);
    }

    private void newSoftWaypoint(ActionEvent event) {
        documentManager.actions().newSoftWaypoint();
    }
    private void newHardWaypoint(ActionEvent event) {
        documentManager.actions().newHardWaypoint();
    }
    private void newCustomWaypoint(ActionEvent event) {
        documentManager.actions().newCustomWaypoint();
    }
    private void newInitialGuessWaypoint(ActionEvent event) {
        documentManager.actions().newInitialGuessWaypoint();
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            unloadSelectedPath(oldDocument.getSelectedAutoRoutine());
            oldDocument.selectedPathProperty().removeListener(onSelectedPathChanged);
        }
    }
    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            loadSelectedPath(newDocument.getSelectedAutoRoutine());
            newDocument.selectedPathProperty().addListener(onSelectedPathChanged);
        }
    }
    private void selectedPathChanged(ObservableValue<? extends HAutoRoutine> currentPath, HAutoRoutine oldPath, HAutoRoutine newPath) {
        unloadSelectedPath(oldPath);
        loadSelectedPath(newPath);
    }
    private void unloadSelectedPath(HAutoRoutine oldPath) {
        if (oldPath != null) {
            setItems(blankItems);
            setSelectionModel(blankSelecitonModel);
            setContextMenu(null);
        }
    }
    private void loadSelectedPath(HAutoRoutine newPath) {
        if (newPath != null) {
            setItems(newPath.getTimeline());
            setSelectionModel(newPath.getTimelineSelectionModel());
            setContextMenu(noneSelectedContextMenu);
        }
    }
}