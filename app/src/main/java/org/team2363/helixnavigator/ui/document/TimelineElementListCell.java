package org.team2363.helixnavigator.ui.document;

import java.util.Arrays;
import java.util.List;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.timeline.HInitialGuessPoint;
import org.team2363.helixnavigator.document.timeline.HTimelineElement;
import org.team2363.helixnavigator.document.timeline.HWaypoint;
import org.team2363.helixnavigator.document.timeline.HWaypoint.WaypointType;
import org.team2363.helixnavigator.document.timeline.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.document.timeline.waypoint.HSoftWaypoint;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.helixnavigator.ui.editor.waypoint.CustomWaypointView;
import org.team2363.helixnavigator.ui.editor.waypoint.HardWaypointView;
import org.team2363.helixnavigator.ui.editor.waypoint.InitialGuessWaypointView;
import org.team2363.helixnavigator.ui.editor.waypoint.SoftWaypointView;
import org.team2363.helixnavigator.ui.prompts.waypoint.WaypointEditDialog;
import org.team2363.lib.ui.OrderableListCell;
import org.team2363.lib.ui.validation.FilteredTextField;

import com.jlbabilino.json.InvalidJSONTranslationConfiguration;
import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONEntry;
import com.jlbabilino.json.JSONParser;
import com.jlbabilino.json.JSONParserException;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.JSONSerializerException;
import com.jlbabilino.json.TypeMarker;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

public class TimelineElementListCell extends OrderableListCell<HTimelineElement> {

    public static final Callback<ListView<HTimelineElement>, ListCell<HTimelineElement>> timelineElementCellFactory(DocumentManager documentManager) {
        return new Callback<ListView<HTimelineElement>, ListCell<HTimelineElement>>() {
            @Override
            public ListCell<HTimelineElement> call(ListView<HTimelineElement> listView) {
                return new TimelineElementListCell(documentManager);
            }
        };
    }

    private final DocumentManager documentManager;

    private final SoftWaypointView softView = new SoftWaypointView(new HSoftWaypoint());
    private final HardWaypointView hardView = new HardWaypointView(new HHardWaypoint());
    private final CustomWaypointView customView = new CustomWaypointView(new HWaypoint());
    private final InitialGuessWaypointView initialGuessView = new InitialGuessWaypointView(new HInitialGuessPoint());
    private final TextField textField = new FilteredTextField(Standards.MAX_NAME_LENGTH, Standards.VALID_NAME);
    private final HBox graphicBox = new HBox();

    private final ContextMenu noneSelectedContextMenu = new ContextMenu();
    private final MenuItem newSoftWaypointMenuItem = new MenuItem("New soft waypoint");
    private final MenuItem newHardWaypointMenuItem = new MenuItem("New hard waypoint");
    private final MenuItem newCustomWaypointMenuItem = new MenuItem("New custom waypoint");
    private final MenuItem newInitialGuessWaypointMenuItem = new MenuItem("New initial guess waypoint");

    private final ContextMenu singleSelectedContextMenu = new ContextMenu();
    private final MenuItem editMenuItem = new MenuItem("Edit...");
    private final Menu insertMenu = new Menu("Insert");
    private final MenuItem insertNewSoftWaypointBeforeMenuItem = new MenuItem("Insert new soft waypoint before");
    private final MenuItem insertNewHardWaypointBeforeMenuItem = new MenuItem("Insert new hard waypoint before");
    private final MenuItem insertNewCustomWaypointBeforeMenuItem = new MenuItem("Insert new custom waypoint before");
    private final MenuItem insertNewInitialGuessWaypointBeforeMenuItem = new MenuItem("Insert new initial guess waypoint before");
    private final MenuItem insertNewSoftWaypointAfterMenuItem = new MenuItem("Insert new soft waypoint after");
    private final MenuItem insertNewHardWaypointAfterMenuItem = new MenuItem("Insert new hard waypoint after");
    private final MenuItem insertNewCustomWaypointAfterMenuItem = new MenuItem("Insert new custom waypoint after");
    private final MenuItem insertNewInitialGuessWaypointAfterMenuItem = new MenuItem("Insert new initial guess waypoint after");
    private final MenuItem renameMenuItem = new MenuItem("Rename");
    private final MenuItem deleteSingleMenuItem = new MenuItem("Delete");

    private final ContextMenu multipleSelectedContextMenu = new ContextMenu();
    private final MenuItem deleteMultipleMenuItem = new MenuItem("Delete");

    public TimelineElementListCell(DocumentManager documentManager) {
        this.documentManager = documentManager;

        double xt = softView.getWaypointView().getBoundsInLocal().getWidth() / 2;
        double yt = softView.getWaypointView().getBoundsInLocal().getHeight() / 2;
        softView.getWaypointView().setTranslateX(xt);
        softView.getWaypointView().setTranslateY(yt);
        hardView.getWaypointView().setTranslateX(xt);
        hardView.getWaypointView().setTranslateY(yt);
        customView.getWaypointView().setTranslateX(xt);
        customView.getWaypointView().setTranslateY(yt);
        initialGuessView.getWaypointView().setTranslateX(xt);
        initialGuessView.getWaypointView().setTranslateY(yt);
        softView.getWaypointView().setClip(new Circle(10));
        hardView.getWaypointView().setClip(new Circle(10)); // these circles make snapshots of cells correct
        customView.getWaypointView().setClip(new Circle(10));
        initialGuessView.getWaypointView().setClip(new Circle(10));
        // (see OrderableListCell)

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
        newCustomWaypointMenuItem.setOnAction(this::newCustomWaypoint);
        newInitialGuessWaypointMenuItem.setOnAction(this::newInitialGuessWaypoint);
        insertNewSoftWaypointBeforeMenuItem.setOnAction(this::insertNewSoftWaypointBefore);
        insertNewHardWaypointBeforeMenuItem.setOnAction(this::insertNewHardWaypointBefore);
        insertNewCustomWaypointBeforeMenuItem.setOnAction(this::insertNewCustomWaypointBefore);
        insertNewInitialGuessWaypointBeforeMenuItem.setOnAction(this::insertNewInitialGuessWaypointBefore);
        insertNewSoftWaypointAfterMenuItem.setOnAction(this::insertNewSoftWaypointAfter);
        insertNewHardWaypointAfterMenuItem.setOnAction(this::insertNewHardWaypointAfter);
        insertNewCustomWaypointAfterMenuItem.setOnAction(this::insertNewCustomWaypointAfter);
        insertNewInitialGuessWaypointAfterMenuItem.setOnAction(this::insertNewInitialGuessWaypointAfter);
        renameMenuItem.setOnAction(this::renameWaypoint);
        deleteSingleMenuItem.setOnAction(this::deleteSelectedWaypoints);
        deleteMultipleMenuItem.setOnAction(this::deleteSelectedWaypoints);

        insertMenu.getItems().addAll(
                insertNewSoftWaypointBeforeMenuItem, insertNewHardWaypointBeforeMenuItem,
                insertNewCustomWaypointBeforeMenuItem, insertNewInitialGuessWaypointBeforeMenuItem,
                insertNewSoftWaypointAfterMenuItem, insertNewHardWaypointAfterMenuItem,
                insertNewCustomWaypointAfterMenuItem, insertNewInitialGuessWaypointAfterMenuItem);

        noneSelectedContextMenu.getItems().addAll(newSoftWaypointMenuItem, newHardWaypointMenuItem, newCustomWaypointMenuItem, newInitialGuessWaypointMenuItem);
        singleSelectedContextMenu.getItems().addAll(editMenuItem, insertMenu, renameMenuItem, deleteSingleMenuItem);
        multipleSelectedContextMenu.getItems().addAll(deleteMultipleMenuItem);

        noneSelectedContextMenu.setAutoHide(true);
        singleSelectedContextMenu.setAutoHide(true);
        multipleSelectedContextMenu.setAutoHide(true);

        setOnContextMenuRequested(this::contextMenuRequested);
    }

    @Override
    public void updateItem(HTimelineElement item, boolean empty) {
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
            case CUSTOM:
                graphicBox.getChildren().set(0, customView.getWaypointView());
                break;
            case INITIAL_GUESS:
                graphicBox.getChildren().set(0, initialGuessView.getWaypointView());
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
    protected String fileName() {
        return getItem().getName() + ".json";
    }

    @Override
    protected String fileString() {
        ObservableList<HWaypoint> selectedItems = getListView().getSelectionModel().getSelectedItems();
        try {
            if (selectedItems.size() == 1) {
                return JSONSerializer.serializeString(getListView().getSelectionModel().getSelectedItem());
            } else if (selectedItems.size() > 1) {
                return JSONSerializer.serializeString(getListView().getSelectionModel().getSelectedItems());
            } else {
                return "";
            }
        } catch (InvalidJSONTranslationConfiguration | JSONSerializerException e) {
            // TODO: error log
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
        } catch (JSONParserException | InvalidJSONTranslationConfiguration | JSONDeserializerException e) {
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
        WaypointEditDialog dialog = WaypointEditDialog.dialog(getItem());
        dialog.show();
    }
    private void newSoftWaypoint(ActionEvent event) {
        HWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName("Soft Waypoint " + getListView().getItems().size());
        getListView().getItems().add(newWaypoint);
    }
    private void newHardWaypoint(ActionEvent event) {
        HWaypoint newWaypoint = new HHardWaypoint();
        newWaypoint.setName("Hard Waypoint " + getListView().getItems().size());
        getListView().getItems().add(newWaypoint);
    }
    private void newCustomWaypoint(ActionEvent event) {
        HWaypoint newWaypoint = new HWaypoint();
        newWaypoint.setName("Custom Waypoint " + getListView().getItems().size());
        getListView().getItems().add(newWaypoint);
    }
    private void newInitialGuessWaypoint(ActionEvent event) {
        HWaypoint newWaypoint = new HInitialGuessPoint();
        newWaypoint.setName("Initial Guess Waypoint " + getListView().getItems().size());
        getListView().getItems().add(newWaypoint);
    }
    private void insertNewSoftWaypointBefore(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex();
        HWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName("Soft Waypoint " + getListView().getItems().size());
        getListView().getItems().add(insertIndex, newWaypoint);
    }
    private void insertNewHardWaypointBefore(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex();
        HWaypoint newWaypoint = new HHardWaypoint();
        newWaypoint.setName("Hard Waypoint " + getListView().getItems().size());
        getListView().getItems().add(insertIndex, newWaypoint);
    }
    private void insertNewCustomWaypointBefore(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex();
        HWaypoint newWaypoint = new HWaypoint();
        newWaypoint.setName("Custom Waypoint " + getListView().getItems().size());
        getListView().getItems().add(insertIndex, newWaypoint);
    }
    private void insertNewInitialGuessWaypointBefore(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex();
        HWaypoint newWaypoint = new HInitialGuessPoint();
        newWaypoint.setName("Initial Guess Waypoint " + getListView().getItems().size());
        getListView().getItems().add(insertIndex, newWaypoint);
    }
    private void insertNewSoftWaypointAfter(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex() + 1;
        HWaypoint newWaypoint = new HSoftWaypoint();
        newWaypoint.setName("Soft Waypoint " + getListView().getItems().size());
        getListView().getItems().add(insertIndex, newWaypoint);
    }
    private void insertNewHardWaypointAfter(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex() + 1;
        HWaypoint newWaypoint = new HHardWaypoint();
        newWaypoint.setName("Hard Waypoint " + getListView().getItems().size());
        getListView().getItems().add(insertIndex, newWaypoint);
    }
    private void insertNewCustomWaypointAfter(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex() + 1;
        HWaypoint newWaypoint = new HWaypoint();
        newWaypoint.setName("Custom Waypoint " + getListView().getItems().size());
        getListView().getItems().add(insertIndex, newWaypoint);
    }
    private void insertNewInitialGuessWaypointAfter(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex() + 1;
        HWaypoint newWaypoint = new HInitialGuessPoint();
        newWaypoint.setName("Initial Guess Waypoint " + getListView().getItems().size());
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