package org.team2363.helixnavigator.ui.document;

import java.util.Arrays;
import java.util.List;

import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONEntry;
import com.jlbabilino.json.JSONParser;
import com.jlbabilino.json.JSONParserException;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.TypeMarker;

import org.team2363.helixnavigator.document.obstacle.HCircleObstacle;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HObstacle.ObstacleType;
import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.helixnavigator.ui.prompts.obstacle.ObstacleEditDialog;
import org.team2363.lib.ui.OrderableListCell;
import org.team2363.lib.ui.validation.FilteredTextField;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ObstacleListCell extends OrderableListCell<HObstacle> {

    private static final Image CIRCLE;
    private static final Image POLYGON;
    private static final Image RECTANGLE;
    private static final Image ANY_DRAGGED;

    public static final Callback<ListView<HObstacle>, ListCell<HObstacle>> obstacleCellFactory =
            new Callback<ListView<HObstacle>, ListCell<HObstacle>>() {
        @Override
        public ListCell<HObstacle> call(ListView<HObstacle> listView) {
            return new ObstacleListCell();
        }
    };

    static {
        CIRCLE = null;
        POLYGON = null;
        RECTANGLE = null;
        ANY_DRAGGED = null;
    }

    private final ImageView circleView = new ImageView(CIRCLE);
    private final ImageView polygonView = new ImageView(POLYGON);
    private final ImageView rectangleView = new ImageView(RECTANGLE);
    private final TextField textField = new FilteredTextField(Standards.MAX_NAME_LENGTH, Standards.VALID_NAME);
    private final HBox graphicBox = new HBox();

    private final ContextMenu noneSelectedContextMenu = new ContextMenu();
    private final MenuItem newCircleObstacleMenuItem = new MenuItem("New circle obstacle");
    private final MenuItem newPolygonObstacleMenuItem = new MenuItem("New polygon obstacle");

    private final ContextMenu singleSelectedContextMenu = new ContextMenu();
    private final MenuItem editMenuItem = new MenuItem("Edit...");
    private final Menu insertMenu = new Menu("Insert");
    private final MenuItem insertNewCircleObstacleBeforeMenuItem = new MenuItem("Insert new circle obstacle before");
    private final MenuItem insertNewPolygonObstacleBeforeMenuItem = new MenuItem("Insert new polygon obstacle before");
    private final MenuItem insertNewCircleObstacleAfterMenuItem = new MenuItem("Insert new circle obstacle after");
    private final MenuItem insertNewPolygonObstacleAfterMenuItem = new MenuItem("Insert new polygon obstacle after");
    private final MenuItem renameMenuItem = new MenuItem("Rename");
    private final MenuItem deleteSingleMenuItem = new MenuItem("Delete");

    private final ContextMenu multipleSelectedContextMenu = new ContextMenu();
    private final MenuItem deleteMultipleMenuItem = new MenuItem("Delete");

    public ObstacleListCell() {
        circleView.setPreserveRatio(true);
        circleView.setFitHeight(20);
        polygonView.setPreserveRatio(true);
        polygonView.setFitHeight(20);
        graphicBox.getChildren().add(circleView); // have to use one image here even though we don't know which it is yet
        setEditable(true);
        setOrderable(true);
        // cancel edit if ENTER is pressed:
        textField.setOnAction(event -> {
            if (isEditing()) {
                cancelEdit();
            }
            event.consume();
        });
        // cancel edit if enter is pressed
        textField.focusedProperty().addListener((val, oldVal, newVal) -> {
            if (isEditing() && newVal == false) {
                cancelEdit();
            }
        });

        editMenuItem.setOnAction(this::edit);
        newCircleObstacleMenuItem.setOnAction(this::newCircleObstacle);
        newPolygonObstacleMenuItem.setOnAction(this::newPolygonObstacle);
        insertNewCircleObstacleBeforeMenuItem.setOnAction(this::insertNewCircleObstacleBefore);
        insertNewPolygonObstacleBeforeMenuItem.setOnAction(this::insertNewPolygonObstacleBefore);
        insertNewCircleObstacleAfterMenuItem.setOnAction(this::insertNewCircleObstacleAfter);
        insertNewPolygonObstacleAfterMenuItem.setOnAction(this::insertNewPolygonObstacleAfter);
        renameMenuItem.setOnAction(this::renameObstacle);
        deleteSingleMenuItem.setOnAction(this::deleteSelectedObstacles);
        deleteMultipleMenuItem.setOnAction(this::deleteSelectedObstacles);

        insertMenu.getItems().addAll(insertNewCircleObstacleBeforeMenuItem, insertNewPolygonObstacleBeforeMenuItem, insertNewCircleObstacleAfterMenuItem, insertNewPolygonObstacleAfterMenuItem);

        noneSelectedContextMenu.getItems().addAll(newCircleObstacleMenuItem, newPolygonObstacleMenuItem);
        singleSelectedContextMenu.getItems().addAll(editMenuItem, insertMenu, renameMenuItem, deleteSingleMenuItem);
        multipleSelectedContextMenu.getItems().addAll(deleteMultipleMenuItem);

        noneSelectedContextMenu.setAutoHide(true);
        singleSelectedContextMenu.setAutoHide(true);
        multipleSelectedContextMenu.setAutoHide(true);

        setOnContextMenuRequested(this::contextMenuRequested);
    }

    @Override
    public void updateItem(HObstacle item, boolean empty) {
        super.updateItem(item, empty);
        textProperty().unbind();
        if (empty || item == null) {
            setText("");
            setGraphic(null);
        } else {
            updateObstacleType(item.getObstacleType());
            textProperty().bind(item.nameProperty());
            setGraphic(graphicBox);
        }
    }

    private void updateObstacleType(ObstacleType newType) {
        switch (newType) {
            case CIRCLE:
                graphicBox.getChildren().set(0, circleView);
                break;
            case POLYGON:
                graphicBox.getChildren().set(0, polygonView);
                break;
            case RECTANGLE:
                graphicBox.getChildren().set(0, rectangleView);
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
        return ANY_DRAGGED;
    }

    @Override
    protected String fileName() {
        return getItem().getName() + ".json";
    }

    @Override
    protected String fileString() {
        ObservableList<HObstacle> selectedItems = getListView().getSelectionModel().getSelectedItems();
        if (selectedItems.size() == 1) {
            return JSONSerializer.serializeString(getListView().getSelectionModel().getSelectedItem());
        } else if (selectedItems.size() > 1) {
            return JSONSerializer.serializeString(getListView().getSelectionModel().getSelectedItems());
        } else {
            return "";
        }
    }

    @Override
    protected List<HObstacle> newItems(String fileString) throws IllegalArgumentException {
        try {
            JSONEntry jsonEntry = JSONParser.parseJSONEntry(fileString);
            if (jsonEntry.isArray()) {
                return JSONDeserializer.deserialize(jsonEntry, new TypeMarker<List<HObstacle>>() {});
            } else {
                return List.of(JSONDeserializer.deserialize(jsonEntry, HObstacle.class));
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
        ObstacleEditDialog dialog = ObstacleEditDialog.dialog(getItem());
        dialog.show();
    }
    private void newCircleObstacle(ActionEvent event) {
        HObstacle newObstacle = new HCircleObstacle();
        newObstacle.setName(String.valueOf(getListView().getItems().size()));
        getListView().getItems().add(newObstacle);
    }
    private void newPolygonObstacle(ActionEvent event) {
        HObstacle newObstacle = new HPolygonObstacle();
        newObstacle.setName(String.valueOf(getListView().getItems().size()));
        getListView().getItems().add(newObstacle);
    }
    private void insertNewCircleObstacleBefore(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex();
        HObstacle newObstacle = new HCircleObstacle();
        newObstacle.setName(String.valueOf(getListView().getItems().size()));
        getListView().getItems().add(insertIndex, newObstacle);
    }
    private void insertNewPolygonObstacleBefore(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex();
        HObstacle newObstacle = new HPolygonObstacle();
        newObstacle.setName(String.valueOf(getListView().getItems().size()));
        getListView().getItems().add(insertIndex, newObstacle);
    }
    private void insertNewCircleObstacleAfter(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex() + 1;
        HObstacle newObstacle = new HCircleObstacle();
        newObstacle.setName(String.valueOf(getListView().getItems().size()));
        getListView().getItems().add(insertIndex, newObstacle);
    }
    private void insertNewPolygonObstacleAfter(ActionEvent event) {
        int insertIndex = getListView().getSelectionModel().getSelectedIndex() + 1;
        HObstacle newObstacle = new HPolygonObstacle();
        newObstacle.setName(String.valueOf(getListView().getItems().size()));
        getListView().getItems().add(insertIndex, newObstacle);
    }
    private void renameObstacle(ActionEvent event) {
        startEdit();
    }
    private void deleteSelectedObstacles(ActionEvent event) {
        ObservableList<Integer> selectedIndices = getListView().getSelectionModel().getSelectedIndices();
        Integer[] selectedIndicesArray = selectedIndices.<Integer>toArray(new Integer[0]);
        Arrays.<Integer>sort(selectedIndicesArray, (a, b) -> b - a);
        for (Integer index : selectedIndicesArray) {
            getListView().getItems().remove(index.intValue()); // have to use intValue() to remove ambiguity
        }
    }
}