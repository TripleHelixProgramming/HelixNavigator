package org.team2363.helixnavigator.ui.document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.jlbabilino.json.JSON;
import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONEntry;
import com.jlbabilino.json.JSONParser;
import com.jlbabilino.json.JSONParserException;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.TypeMarker;

import org.team2363.helixnavigator.document.waypoint.HSoftWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

// TODO: remember to credit code
public class WaypointListCellOld extends ListCell<HWaypoint> {

    private static final Image DRAG_ICON = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Parentesi_Graffe.svg/150px-Parentesi_Graffe.svg.png");

    @Override
    protected void updateItem(HWaypoint item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
            setText("");
        } else {
            String waypointTypeString = item instanceof HSoftWaypoint ? "Soft Waypoint" : "Hard Waypoint";
            String name = item.getName();
            setText(waypointTypeString + " " + name);
        }
    }

    public static final Callback<ListView<HWaypoint>, ListCell<HWaypoint>> waypointCellFactory = new Callback<ListView<HWaypoint>, ListCell<HWaypoint>>() {
        @Override
        public ListCell<HWaypoint> call(ListView<HWaypoint> listView) {
            return new WaypointListCellOld();
        }
    };

    public WaypointListCellOld() {
        setOnDragDetected(event -> {
            if (getItem() == null) {
                return;
            }
            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            dragboard.setDragView(DRAG_ICON);
            ClipboardContent clipboard = new ClipboardContent();
            List<HWaypoint> selectedWaypoints = getListView().getSelectionModel().getSelectedItems();
            String jsonString;
            if (selectedWaypoints.size() == 1) {
                jsonString = JSONSerializer.serializeJSON(selectedWaypoints.get(0)).exportJSON();
            } else {
                jsonString = JSONSerializer.serializeJSON(selectedWaypoints).exportJSON();
            }
            Path tempFilePath;
            try {
                tempFilePath = Files.createTempFile("waypoint", ".json");
            } catch (IOException e) {
                tempFilePath = null;
            }
            File tempFile = tempFilePath.toFile();
            try {
                PrintWriter writer = new PrintWriter(tempFile);
                writer.print(jsonString);
                writer.close();
            } catch (FileNotFoundException e) {
            }
            clipboard.putString(jsonString);
            clipboard.putFiles(List.of(tempFile));
            getListView().getItems().removeAll(selectedWaypoints);
            dragboard.setContent(clipboard);

            event.consume();
        });
        setOnDragOver(event -> {
            if (event.getDragboard().hasString() || event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });
        setOnDragEntered(event -> {
            if (getItem() != null && (event.getDragboard().hasString() || event.getDragboard().hasFiles())) {
                getListView().getItems().add(getIndex(), null);
            }
        });
        setOnDragExited(event -> {
            System.out.println("WaypointListCell: Drag exited.");
            if (!event.isDropCompleted() && (event.getDragboard().hasString() || event.getDragboard().hasFiles())
                    && getIndex() <= getListView().getItems().size() - 1) {
                getListView().getItems().remove(getIndex());
            }
        });
        setOnDragDropped(event -> {
            System.out.println("WaypointListCell: Drop gesture initiated.");
            Dragboard dragboard = event.getDragboard();
            ObservableList<HWaypoint> items = getListView().getItems();
            int index = getIndex();
            if (index <= items.size() - 1) {
                items.remove(index);
            } else {
                index = items.size();
            }
            boolean success;
            dropAttempt: {
                String jsonString;
                if (dragboard.hasString()) {
                    jsonString = dragboard.getString();
                } else if (dragboard.hasFiles()) {
                    File file = dragboard.getFiles().get(0);
                    try {
                        jsonString = Files.readString(file.toPath());
                    } catch (IOException e) {
                        success = false;
                        break dropAttempt;
                    }
                } else {
                    success = false;
                    break dropAttempt;
                }
                try {
                    JSONEntry jsonEntry = JSONParser.parseStringAsJSONEntry(jsonString);
                    if (jsonEntry.isArray()) {
                        List<HWaypoint> newWaypoints = JSONDeserializer.deserializeJSONEntry(jsonEntry, new TypeMarker<List<HWaypoint>>() {});
                        items.addAll(index, newWaypoints);
                    } else { // should be object
                        HWaypoint newWaypoint = JSONDeserializer.deserializeJSONEntry(jsonEntry, HWaypoint.class);
                        items.add(index, newWaypoint);
                    }
                    System.out.println("WaypointListCell: Successfully dropped new waypoint(s).");
                    success = true;
                } catch (JSONParserException | JSONDeserializerException e) {
                    success = false;
                    break dropAttempt;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
}