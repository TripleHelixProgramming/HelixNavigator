/*
 * Copyright (C) 2021 Justin Babilino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.team2363.helixnavigator.ui.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.team2363.helixnavigator.document.CurrentDocument;
import com.team2363.helixnavigator.document.waypoint.HAbstractWaypoint;
import com.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import com.team2363.helixnavigator.document.waypoint.HSoftWaypoint;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.ContextMenuEvent;

/**
 *
 * @author Justin Babilino
 */
public class WaypointListView extends ListView<HAbstractWaypoint> {
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

    public WaypointListView() {
        CurrentDocument.documentProperty().addListener((currentVal, oldVal, newVal) -> refreshDocument());

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
        while (change.next()) {
            if (change.wasAdded()) {
                List<? extends Integer> list = change.getAddedSubList();
                CurrentDocument.getDocument().getSelectedPath().getWaypointsSelectionModel().selectIndices(list);
            } // don't use else if it doesn't work for replacements -- it will crash the program
            if (change.wasRemoved()) {
                List<? extends Integer> list = change.getRemoved();
                CurrentDocument.getDocument().getSelectedPath().getWaypointsSelectionModel().deSelectIndices(list);
            }
        }
    }

    private void contextMenuRequested(ContextMenuEvent event) {
        ObservableList<Integer> selectedIndices = getSelectionModel().getSelectedIndices();
        if (CurrentDocument.isDocumentOpen() && CurrentDocument.getDocument().getSelectedPathIndex() != -1) {
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
        HAbstractWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName(String.valueOf(CurrentDocument.getDocument().getSelectedPath().getWaypoints().size()));
        CurrentDocument.getDocument().getSelectedPath().getWaypoints().add(newWaypoint);
    }
    private void newHardWaypoint(ActionEvent event) {
        HAbstractWaypoint newWaypoint = new HHardWaypoint();
        newWaypoint.setName(String.valueOf(CurrentDocument.getDocument().getSelectedPath().getWaypoints().size()));
        CurrentDocument.getDocument().getSelectedPath().getWaypoints().add(newWaypoint);
    }
    private void insertNewSoftWaypointBefore(ActionEvent event) {
        int selectedWaypointIndex = getSelectionModel().getSelectedIndex();
        HAbstractWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName(String.valueOf(selectedWaypointIndex));
        CurrentDocument.getDocument().getSelectedPath().getWaypoints().add(selectedWaypointIndex, newWaypoint);
    }
    private void insertNewHardWaypointBefore(ActionEvent event) {
        int selectedWaypointIndex = getSelectionModel().getSelectedIndex();
        HAbstractWaypoint newWaypoint = new HHardWaypoint();
        newWaypoint.setName(String.valueOf(selectedWaypointIndex));
        CurrentDocument.getDocument().getSelectedPath().getWaypoints().add(selectedWaypointIndex, newWaypoint);
    }
    private void insertNewSoftWaypointAfter(ActionEvent event) {
        int selectedWaypointIndex = getSelectionModel().getSelectedIndex();
        HAbstractWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName(String.valueOf(selectedWaypointIndex + 1));
        CurrentDocument.getDocument().getSelectedPath().getWaypoints().add(selectedWaypointIndex + 1, newWaypoint);
    }
    private void insertNewHardWaypointAfter(ActionEvent event) {
        int selectedWaypointIndex = getSelectionModel().getSelectedIndex();
        HAbstractWaypoint newWaypoint = new HHardWaypoint();
        newWaypoint.setName(String.valueOf(selectedWaypointIndex + 1));
        CurrentDocument.getDocument().getSelectedPath().getWaypoints().add(selectedWaypointIndex + 1, newWaypoint);
    }
    private void deleteSingleWaypoint(ActionEvent event) {
        int selectedWaypointIndex = getSelectionModel().getSelectedIndex();
        CurrentDocument.getDocument().getSelectedPath().getWaypoints().remove(selectedWaypointIndex);
    }
    private void deleteMultipleWaypoint(ActionEvent event) {
        ObservableList<Integer> selectedIndices = getSelectionModel().getSelectedIndices();
        Integer[] selectedIndicesArray = selectedIndices.<Integer>toArray(new Integer[0]);
        Arrays.<Integer>sort(selectedIndicesArray, (a, b) -> b - a);
        for (Integer index : selectedIndicesArray) {
            CurrentDocument.getDocument().getSelectedPath().getWaypoints().remove(index.intValue()); // have to use intValue() to remove ambiguity
        }
    }
    private void refreshDocument() {
        CurrentDocument.getDocument().selectedPathIndexProperty().addListener((currentVal, oldVal, newVal) -> {
            if (newVal.intValue() != -1) {
                setItems(CurrentDocument.getDocument().getSelectedPath().getWaypoints());
            } else {
                setItems(FXCollections.<HAbstractWaypoint>observableArrayList());
            }
        });
    }
}
