package org.team2363.helixnavigator.ui.document;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONEntry;
import com.jlbabilino.json.JSONParser;
import com.jlbabilino.json.JSONParserException;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.TypeMarker;

import org.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.document.waypoint.HSoftWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint.WaypointType;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.helixnavigator.ui.editor.waypoint.HardWaypointView;
import org.team2363.helixnavigator.ui.editor.waypoint.SoftWaypointView;
import org.team2363.helixnavigator.ui.prompts.waypoint.WaypointEditDialog;
import org.team2363.lib.ui.OrderableListCell;
import org.team2363.lib.ui.prompts.FilteredTextField;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class WaypointListCell extends OrderableListCell<HWaypoint> {

    private static final Image ONE_DRAGGED;
    private static final Image TWO_DRAGGED;
    private static final Image THREE_DRAGGED;
    private static final Image FOUR_DRAGGED;

    public static final Callback<ListView<HWaypoint>, ListCell<HWaypoint>> WAYPOINT_CELL_FACTORY =
            new Callback<ListView<HWaypoint>, ListCell<HWaypoint>>() {
        @Override
        public ListCell<HWaypoint> call(ListView<HWaypoint> listView) {
            return new WaypointListCell();
        }
    };

    static {
        InputStream stream1 = WaypointListCell.class.getResourceAsStream("/waypoint_images/waypoints_dragged_1.png");
        ONE_DRAGGED = new Image(stream1);
        InputStream stream2 = WaypointListCell.class.getResourceAsStream("/waypoint_images/waypoints_dragged_2.png");
        TWO_DRAGGED = new Image(stream2);
        InputStream stream3 = WaypointListCell.class.getResourceAsStream("/waypoint_images/waypoints_dragged_3.png");
        THREE_DRAGGED = new Image(stream3);
        InputStream stream4 = WaypointListCell.class.getResourceAsStream("/waypoint_images/waypoints_dragged_4.png");
        FOUR_DRAGGED = new Image(stream4);
    }

    private final SoftWaypointView softView = new SoftWaypointView(new HSoftWaypoint());
    private final HardWaypointView hardView = new HardWaypointView(new HHardWaypoint());
    private final TextField textField = new FilteredTextField(Standards.MAX_NAME_LENGTH, Standards.VALID_NAME);
    private final HBox graphicBox = new HBox();

    private final ContextMenu noneSelectedContextMenu = new ContextMenu();
    private final MenuItem newSoftWaypointMenuItem = new MenuItem("New soft waypoint");
    private final MenuItem newHardWaypointMenuItem = new MenuItem("New hard waypoint");

    private final ContextMenu singleSelectedContextMenu = new ContextMenu();
    private final MenuItem editMenuItem = new MenuItem("Edit...");
    private final Menu insertMenu = new Menu("Insert");
    private final MenuItem insertNewSoftWaypointBeforeMenuItem = new MenuItem("Insert new soft waypoint before");
    private final MenuItem insertNewHardWaypointBeforeMenuItem = new MenuItem("Insert new hard waypoint before");
    private final MenuItem insertNewSoftWaypointAfterMenuItem = new MenuItem("Insert new soft waypoint after");
    private final MenuItem insertNewHardWaypointAfterMenuItem = new MenuItem("Insert new hard waypoint after");
    private final MenuItem renameMenuItem = new MenuItem("Rename");
    private final MenuItem deleteSingleMenuItem = new MenuItem("Delete");

    private final ContextMenu multipleSelectedContextMenu = new ContextMenu();
    private final MenuItem deleteMultipleMenuItem = new MenuItem("Delete");

    public WaypointListCell() {
        double xt = softView.getWaypointView().getBoundsInLocal().getWidth() / 2;
        double yt = softView.getWaypointView().getBoundsInLocal().getHeight() / 2;
        softView.getWaypointView().setTranslateX(xt);
        softView.getWaypointView().setTranslateY(yt);
        hardView.getWaypointView().setTranslateX(xt);
        hardView.getWaypointView().setTranslateY(yt);

        graphicBox.getChildren().add(softView.getWaypointView()); // have to use one image here even though we don't know which it is yet
        setEditable(true);
        // cancel edit if clicked off of or ENTER is pressed:
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

        editMenuItem.setOnAction(this::edit);
        newSoftWaypointMenuItem.setOnAction(this::newSoftWaypoint);
        newHardWaypointMenuItem.setOnAction(this::newHardWaypoint);
        insertNewSoftWaypointBeforeMenuItem.setOnAction(this::insertNewSoftWaypointBefore);
        insertNewHardWaypointBeforeMenuItem.setOnAction(this::insertNewHardWaypointBefore);
        insertNewSoftWaypointAfterMenuItem.setOnAction(this::insertNewSoftWaypointAfter);
        insertNewHardWaypointAfterMenuItem.setOnAction(this::insertNewHardWaypointAfter);
        renameMenuItem.setOnAction(this::renameWaypoint);
        deleteSingleMenuItem.setOnAction(this::deleteSelectedWaypoints);
        deleteMultipleMenuItem.setOnAction(this::deleteSelectedWaypoints);

        insertMenu.getItems().addAll(insertNewSoftWaypointBeforeMenuItem, insertNewHardWaypointBeforeMenuItem, insertNewSoftWaypointAfterMenuItem, insertNewHardWaypointAfterMenuItem);

        noneSelectedContextMenu.getItems().addAll(newSoftWaypointMenuItem, newHardWaypointMenuItem);
        singleSelectedContextMenu.getItems().addAll(editMenuItem, insertMenu, renameMenuItem, deleteSingleMenuItem);
        multipleSelectedContextMenu.getItems().addAll(deleteMultipleMenuItem);

        noneSelectedContextMenu.setAutoHide(true);
        singleSelectedContextMenu.setAutoHide(true);
        multipleSelectedContextMenu.setAutoHide(true);

        setOnContextMenuRequested(this::contextMenuRequested);
    }

    @Override
    public void updateItem(HWaypoint item, boolean empty) {
        super.updateItem(item, empty);
        textProperty().unbind();
        if (empty || item == null) {
            setText("");
            setGraphic(null);
        } else {
            updateWaypointType(item.getWaypointType());
            textProperty().bind(item.nameProperty());
            setGraphic(graphicBox);
        }
    }

    private void updateWaypointType(WaypointType newType) {
        switch (newType) {
            case SOFT:
                graphicBox.getChildren().set(0, softView.getWaypointView());
                break;
            case HARD:
                graphicBox.getChildren().set(0, hardView.getWaypointView());
                break;
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        graphicBox.getChildren().add(1, textField);
        textField.setText(getItem().getName());
        textField.requestFocus();
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.LEFT); // sets both graphic and text to display
        graphicBox.getChildren().remove(1);
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
            return JSONSerializer.serializeString(getListView().getSelectionModel().getSelectedItem());
        } else if (selectedItems.size() > 1) {
            return JSONSerializer.serializeString(getListView().getSelectionModel().getSelectedItems());
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

    private void contextMenuRequested(ContextMenuEvent event) {
        if (getItem() != null) {
            final ObservableList<Integer> selectedIndices = getListView().getSelectionModel().getSelectedIndices();
            if (selectedIndices.size() == 1) {
                singleSelectedContextMenu.show(this.getScene().getWindow(), event.getScreenX(), event.getScreenY());
            } else {
                multipleSelectedContextMenu.show(this.getScene().getWindow(), event.getScreenX(), event.getScreenY());
            }
        } else {
            noneSelectedContextMenu.show(this.getScene().getWindow(), event.getScreenX(), event.getScreenY());
        }
        event.consume();
    }

    private void edit(ActionEvent event) {
        HWaypoint selectedWaypoint = getListView().getSelectionModel().getSelectedItems().get(0);
        WaypointEditDialog dialog = WaypointEditDialog.dialog(selectedWaypoint);
        dialog.show();
    }
    private void newSoftWaypoint(ActionEvent event) {
        HWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName(String.valueOf(getListView().getItems().size()));
        getListView().getItems().add(newWaypoint);
    }
    private void newHardWaypoint(ActionEvent event) {
        HWaypoint newWaypoint = new HHardWaypoint();
        newWaypoint.setName(String.valueOf(getListView().getItems().size()));
        getListView().getItems().add(newWaypoint);
    }
    private void insertNewSoftWaypointBefore(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex();
        HWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName(String.valueOf(getListView().getItems().size()));
        getListView().getItems().add(insertIndex, newWaypoint);
    }
    private void insertNewHardWaypointBefore(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex();
        HWaypoint newWaypoint = new HHardWaypoint();
        newWaypoint.setName(String.valueOf(getListView().getItems().size()));
        getListView().getItems().add(insertIndex, newWaypoint);
    }
    private void insertNewSoftWaypointAfter(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex() + 1;
        HWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName(String.valueOf(getListView().getItems().size()));
        getListView().getItems().add(insertIndex, newWaypoint);
    }
    private void insertNewHardWaypointAfter(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex() + 1;
        HWaypoint newWaypoint = new HHardWaypoint();
        newWaypoint.setName(String.valueOf(getListView().getItems().size()));
        getListView().getItems().add(insertIndex, newWaypoint);
    }
    private void renameWaypoint(ActionEvent event) {
        startEdit();
    }
    private void deleteSelectedWaypoints(ActionEvent event) {
        ObservableList<Integer> selectedIndices = getListView().getSelectionModel().getSelectedIndices();
        Integer[] selectedIndicesArray = selectedIndices.<Integer>toArray(new Integer[0]);
        Arrays.<Integer>sort(selectedIndicesArray, (a, b) -> b - a);
        for (Integer index : selectedIndicesArray) {
            getListView().getItems().remove(index.intValue()); // have to use intValue() to remove ambiguity
        }
    }
}