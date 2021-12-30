package org.team2363.helixnavigator.ui.document;

import java.io.InputStream;
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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class WaypointListCell extends OrderableListCell<HWaypoint> {

    private static final Image SOFT;
    private static final Image HARD;
    private static final Image ONE_DRAGGED;
    private static final Image TWO_DRAGGED;
    private static final Image THREE_DRAGGED;
    private static final Image FOUR_DRAGGED;

    public static final Callback<ListView<HWaypoint>, ListCell<HWaypoint>> waypointCellFactory = new Callback<ListView<HWaypoint>, ListCell<HWaypoint>>() {
        @Override
        public ListCell<HWaypoint> call(ListView<HWaypoint> listView) {
            return new WaypointListCell();
        }
    };

    static {
        InputStream streamSoft = WaypointListCell.class.getResourceAsStream("/waypoint_images/soft_waypoint_100px.png");
        SOFT = new Image(streamSoft);
        InputStream streamHard = WaypointListCell.class.getResourceAsStream("/waypoint_images/hard_waypoint_100px.png");
        HARD = new Image(streamHard);
        InputStream stream1 = WaypointListCell.class.getResourceAsStream("/waypoint_images/waypoints_dragged_1.png");
        ONE_DRAGGED = new Image(stream1);
        InputStream stream2 = WaypointListCell.class.getResourceAsStream("/waypoint_images/waypoints_dragged_2.png");
        TWO_DRAGGED = new Image(stream2);
        InputStream stream3 = WaypointListCell.class.getResourceAsStream("/waypoint_images/waypoints_dragged_3.png");
        THREE_DRAGGED = new Image(stream3);
        InputStream stream4 = WaypointListCell.class.getResourceAsStream("/waypoint_images/waypoints_dragged_4.png");
        FOUR_DRAGGED = new Image(stream4);
    }

    private final ImageView softView = new ImageView(SOFT);
    private final ImageView hardView = new ImageView(HARD);
    private final TextField textField = new TextField();
    private final HBox graphicBox = new HBox();

    public WaypointListCell() {
        softView.setPreserveRatio(true);
        softView.setFitHeight(20);
        hardView.setPreserveRatio(true);
        hardView.setFitHeight(20);
        graphicBox.getChildren().add(softView); // have to use one image here
        setEditable(true);
        textField.setOnAction(event -> {
            if (isEditing()) {
                cancelEdit();
            }
            event.consume();
        });
        textField.focusedProperty().addListener((val, oldVal, newVal) -> {
            if (isEditing() && newVal == false) {
                cancelEdit();
            }
        });
    }

    @Override
    public void updateItem(HWaypoint item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
            textProperty().unbind();
            setText("");
            setGraphic(null);
        } else {
            textProperty().bind(item.nameProperty());
            setGraphic(graphicBox);
            switch (item.getWaypointType()) {
                case SOFT:
                    graphicBox.getChildren().set(0, softView);
                    break;
                case HARD:
                    graphicBox.getChildren().set(0, hardView);
                    break;
            }
        }
    }

    @Override
    public void startEdit() {
        System.out.println("Starting edit...");
        super.startEdit();
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        graphicBox.getChildren().add(1, textField);
        textField.setText(getItem().getName());
        textField.requestFocus();
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        System.out.println("Cancelling edit...");
        super.cancelEdit();
        setContentDisplay(ContentDisplay.LEFT);
        System.out.println("Removing text field...");
        System.out.println(graphicBox.getChildren().remove(1) instanceof TextField);
        String newName = textField.getText();
        getItem().setName(newName);
    }

    @Override
    protected Image dragView(int selectionSize) {
        switch (selectionSize) {
            case 1:
                return ONE_DRAGGED;
            case 2:
                return TWO_DRAGGED;
            case 3:
                return THREE_DRAGGED;
            default:
                return FOUR_DRAGGED;
        }
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
            JSONEntry jsonEntry = JSONParser.parseJSONEntry(fileString);
            if (jsonEntry.isArray()) {
                return JSONDeserializer.deserialize(jsonEntry, new TypeMarker<List<HWaypoint>>() {});
            } else {
                return List.of(JSONDeserializer.deserialize(jsonEntry, HWaypoint.class));
            }
        } catch (JSONParserException | JSONDeserializerException e) {
            throw new IllegalArgumentException();
        }
    }
    
}