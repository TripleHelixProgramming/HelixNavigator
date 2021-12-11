package com.team2363.helixnavigator.ui.document;

import java.util.List;

import com.team2363.helixnavigator.document.waypoint.HAbstractWaypoint;
import com.team2363.helixnavigator.document.waypoint.HSoftWaypoint;
import com.team2363.lib.json.JSONSerializer;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

// remember to credit code
public class WaypointListCell extends ListCell<HAbstractWaypoint> {

    @Override
    protected void updateItem(HAbstractWaypoint item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
            setText("");
        } else {
            String waypointTypeString = item instanceof HSoftWaypoint ? "Soft Waypoint" : "Hard Waypoint";
            String name = item.getName();
            setText(waypointTypeString + " " + name);
        }
    }

    // try lambda
    public static final Callback<ListView<HAbstractWaypoint>, ListCell<HAbstractWaypoint>> waypointCellFactory = new Callback<ListView<HAbstractWaypoint>, ListCell<HAbstractWaypoint>>() {
        @Override
        public ListCell<HAbstractWaypoint> call(ListView<HAbstractWaypoint> listView) {
            return new WaypointListCell();
        }
    };

    public WaypointListCell() {
        setOnDragDetected(event -> {
            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent clipboard = new ClipboardContent();
            List<HAbstractWaypoint> waypoints = getListView().getSelectionModel().getSelectedItems();
            String jsonString = JSONSerializer.serializeJSON(waypoints)
            clipboard.putString(JSONSerializer.serializeJSON
        });
    }
}