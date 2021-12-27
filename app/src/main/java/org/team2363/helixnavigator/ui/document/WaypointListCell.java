package org.team2363.helixnavigator.ui.document;

import java.util.List;

import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONEntry;
import com.jlbabilino.json.JSONParser;
import com.jlbabilino.json.JSONParserException;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.TypeMarker;

import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.lib.ui.OrderableListCell;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.util.Callback;

public class WaypointListCell extends OrderableListCell<HWaypoint> {

    private static final Image DRAG_ICON = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Parentesi_Graffe.svg/150px-Parentesi_Graffe.svg.png");

    public static final Callback<ListView<HWaypoint>, ListCell<HWaypoint>> waypointCellFactory = new Callback<ListView<HWaypoint>, ListCell<HWaypoint>>() {
        @Override
        public ListCell<HWaypoint> call(ListView<HWaypoint> listView) {
            return new WaypointListCell();
        }
    };

    public WaypointListCell() {
    }

    @Override
    protected void updateItem(HWaypoint item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
            setText("");
        } else {
            setText(item.getWaypointType().toString() + " waypoint " + item.getName());
        }
    }
    @Override
    protected Image dragView(int selectionSize) {
        return DRAG_ICON;
    }

    @Override
    protected String fileName() {
        return getItem().getName() + ".json";
    }

    @Override
    protected String fileString() {
        ObservableList<HWaypoint> selectedItems = getListView().getSelectionModel().getSelectedItems();
        if (selectedItems.size() == 1) {
            return JSONSerializer.serializeJSON(getListView().getSelectionModel().getSelectedItem()).exportJSON();
        } else if (selectedItems.size() > 1) {
            return JSONSerializer.serializeJSON(getListView().getSelectionModel().getSelectedItems()).exportJSON();
        } else {
            return "";
        }
    }

    @Override
    protected List<HWaypoint> newItems(String fileString) throws IllegalArgumentException {
        try {
            JSONEntry jsonEntry = JSONParser.parseStringAsJSONEntry(fileString);
            return JSONDeserializer.deserializeJSONEntry(jsonEntry, new TypeMarker<List<HWaypoint>>() {});
        } catch (JSONParserException | JSONDeserializerException e) {
            throw new IllegalArgumentException();
        }
    }
    
}