package org.team2363.helixnavigator.ui.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.document.waypoint.HSoftWaypoint;

import org.team2363.helixnavigator.document.waypoint.HWaypoint;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.ContextMenuEvent;

public class WaypointListView extends ListView<HWaypoint> {

    private final DocumentManager documentManager;

    private final ContextMenu noneSelectedContextMenu = new ContextMenu();
    private final ContextMenu singleSelectedContextMenu = new ContextMenu();
    private final ContextMenu multipleSelectedContextMenu = new ContextMenu();

    private final MenuItem newSoftWaypointContextMenuItem = new MenuItem("New soft waypoint");
    private final MenuItem newHardWaypointContextMenuItem = new MenuItem("New hard waypoint");
    private final MenuItem insertNewSoftWaypointBeforeMenuItem = new MenuItem("Insert new soft waypoint before");
    private final MenuItem insertNewHardWaypointBeforeMenuItem = new MenuItem("Insert new hard waypoint before");
    private final MenuItem insertNewSoftWaypointAfterMenuItem = new MenuItem("Insert new soft waypoint after");
    private final MenuItem insertNewHardWaypointAfterMenuItem = new MenuItem("Insert new hard waypoint after");
    private final MenuItem deleteSingleWaypointMenuItem = new MenuItem("Delete waypoint");
    private final MenuItem deleteMultipleWaypointMenuItem = new MenuItem("Delete waypoints");

    public WaypointListView(DocumentManager documentManager) {
        this.documentManager = documentManager;
        this.documentManager.documentProperty().addListener((currentVal, oldVal, newVal) -> refreshDocument());

        newSoftWaypointContextMenuItem.setOnAction(this::newSoftWaypoint);
        newHardWaypointContextMenuItem.setOnAction(this::newHardWaypoint);
        insertNewSoftWaypointBeforeMenuItem.setOnAction(this::insertNewSoftWaypointBefore);
        insertNewHardWaypointBeforeMenuItem.setOnAction(this::insertNewHardWaypointBefore);
        insertNewSoftWaypointAfterMenuItem.setOnAction(this::insertNewSoftWaypointAfter);
        insertNewHardWaypointAfterMenuItem.setOnAction(this::insertNewHardWaypointAfter);
        deleteSingleWaypointMenuItem.setOnAction(this::deleteSingleWaypoint);
        deleteMultipleWaypointMenuItem.setOnAction(this::deleteMultipleWaypoint);

        noneSelectedContextMenu.getItems().addAll(newSoftWaypointContextMenuItem, newHardWaypointContextMenuItem);
        singleSelectedContextMenu.getItems().addAll(insertNewSoftWaypointBeforeMenuItem, insertNewHardWaypointBeforeMenuItem, insertNewSoftWaypointAfterMenuItem, insertNewHardWaypointAfterMenuItem, deleteSingleWaypointMenuItem);
        multipleSelectedContextMenu.getItems().addAll(deleteMultipleWaypointMenuItem);

        noneSelectedContextMenu.setAutoHide(true);
        singleSelectedContextMenu.setAutoHide(true);
        multipleSelectedContextMenu.setAutoHide(true);

        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getSelectionModel().getSelectedIndices().addListener(this::selectedIndicesChanged);
        setCellFactory(WaypointListCell.waypointCellFactory);
        setOnContextMenuRequested(this::contextMenuRequested);
    }

    private void selectedIndicesChanged(ListChangeListener.Change<? extends Integer> change) {
        // check if there is a path to deselect or select waypointso on
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            while (change.next()) {
                if (change.wasAdded()) {
                    List<? extends Integer> list = change.getAddedSubList();
                    documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().selectIndices(list);
                } // don't use else if it doesn't work for replacements -- it will crash the program
                if (change.wasRemoved()) {
                    List<? extends Integer> list = change.getRemoved();
                    documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().deselectIndices(list);
                }
            }
        }
    }

    private void contextMenuRequested(ContextMenuEvent event) {
        ObservableList<Integer> selectedIndices = getSelectionModel().getSelectedIndices();
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().getSelectedPathIndex() != -1) {
            if (selectedIndices.size() == 0) {
                noneSelectedContextMenu.show(this.getScene().getWindow(), event.getScreenX(), event.getScreenY());
            } else if (selectedIndices.size() == 1) {
                singleSelectedContextMenu.show(this.getScene().getWindow(), event.getScreenX(), event.getScreenY());
            } else {
                multipleSelectedContextMenu.show(this.getScene().getWindow(), event.getScreenX(), event.getScreenY());
            }
        }
    }
    private void newSoftWaypoint(ActionEvent event) {
        HSoftWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName(String.valueOf(documentManager.getDocument().getSelectedPath().getWaypoints().size()));
        documentManager.getDocument().getSelectedPath().getWaypoints().add(newWaypoint);
    }
    private void newHardWaypoint(ActionEvent event) {
        HHardWaypoint newWaypoint = new HHardWaypoint();
        newWaypoint.setName(String.valueOf(documentManager.getDocument().getSelectedPath().getWaypoints().size()));
        documentManager.getDocument().getSelectedPath().getWaypoints().add(newWaypoint);
    }
    private void insertNewSoftWaypointBefore(ActionEvent event) {
        int selectedWaypointIndex = getSelectionModel().getSelectedIndex();
        HSoftWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName(String.valueOf(selectedWaypointIndex));
        documentManager.getDocument().getSelectedPath().getWaypoints().add(selectedWaypointIndex, newWaypoint);
    }
    private void insertNewHardWaypointBefore(ActionEvent event) {
        int selectedWaypointIndex = getSelectionModel().getSelectedIndex();
        HHardWaypoint newWaypoint = new HHardWaypoint();
        newWaypoint.setName(String.valueOf(selectedWaypointIndex));
        documentManager.getDocument().getSelectedPath().getWaypoints().add(selectedWaypointIndex, newWaypoint);
    }
    private void insertNewSoftWaypointAfter(ActionEvent event) {
        int selectedWaypointIndex = getSelectionModel().getSelectedIndex();
        HSoftWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName(String.valueOf(selectedWaypointIndex + 1));
        documentManager.getDocument().getSelectedPath().getWaypoints().add(selectedWaypointIndex + 1, newWaypoint);
    }
    private void insertNewHardWaypointAfter(ActionEvent event) {
        int selectedWaypointIndex = getSelectionModel().getSelectedIndex();
        HHardWaypoint newWaypoint = new HHardWaypoint();
        newWaypoint.setName(String.valueOf(selectedWaypointIndex + 1));
        documentManager.getDocument().getSelectedPath().getWaypoints().add(selectedWaypointIndex + 1, newWaypoint);
    }
    private void deleteSingleWaypoint(ActionEvent event) {
        int selectedWaypointIndex = getSelectionModel().getSelectedIndex();
        documentManager.getDocument().getSelectedPath().getWaypoints().remove(selectedWaypointIndex);
    }
    private void deleteMultipleWaypoint(ActionEvent event) {
        ObservableList<Integer> selectedIndices = getSelectionModel().getSelectedIndices();
        Integer[] selectedIndicesArray = selectedIndices.<Integer>toArray(new Integer[0]);
        Arrays.<Integer>sort(selectedIndicesArray, (a, b) -> b - a);
        for (Integer index : selectedIndicesArray) {
            documentManager.getDocument().getSelectedPath().getWaypoints().remove(index.intValue()); // have to use intValue() to remove ambiguity
        }
    }
    private void refreshDocument() {
        System.out.println("WaypointListView: Refreshing document.");
        if (documentManager.getIsDocumentOpen()) {
            if (documentManager.getDocument().getSelectedPathIndex() >= 0) {
                setItems(documentManager.getDocument().getSelectedPath().getWaypoints()); // initial set, then check for changes
            }
            documentManager.getDocument().selectedPathIndexProperty().addListener((currentVal, oldVal, newVal) -> {
                System.out.println("WaypointListView: Selected Path changed.");
                if (newVal.intValue() >= 0) {
                    setItems(documentManager.getDocument().getSelectedPath().getWaypoints());
                }
            });
        }
    }
}
