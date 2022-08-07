package org.team2363.helixnavigator.ui.document;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HAutoRoutine;
import org.team2363.helixnavigator.document.obstacle.HCircleObstacle;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import org.team2363.helixnavigator.document.obstacle.HRectangleObstacle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;

public class ObstacleListView extends ListView<HObstacle> {

    private static final ObservableList<HObstacle> BLANK = FXCollections.<HObstacle>observableArrayList();

    private final DocumentManager documentManager;

    private final ContextMenu noneSelectedContextMenu = new ContextMenu();
    private final MenuItem newCircleObstacleMenuItem = new MenuItem("New circle obstacle");
    private final MenuItem newPolygonObstacleMenuItem = new MenuItem("New polygon obstacle");
    private final MenuItem newRectangleObstacleMenuItem = new MenuItem("New rectangle obstacle");

    private final Label placeholder = new Label("RIGHT-CLICK TO CREATE AN OBSTACLE");

    private final ListChangeListener<? super Integer> onListViewSelectedIndicesChanged = this::listViewSelectedIndicesChanged;
    private final ChangeListener<? super HAutoRoutine> onSelectedPathChanged = this::selectedPathChanged;
    private final ListChangeListener<? super Integer> onPathSelectedObstaclesIndicesChanged = this::pathSelectedObstaclesIndicesChanged;

    public ObstacleListView(DocumentManager documentManager) {
        this.documentManager = documentManager;
        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);

        setEditable(true);
        placeholder.setPadding(new Insets(0, 10, 0, 10));
        setPlaceholder(placeholder);
        setItems(BLANK);
        setContextMenu(null);
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getSelectionModel().getSelectedIndices().addListener(onListViewSelectedIndicesChanged);
        setCellFactory(ObstacleListCell.obstacleCellFactory(this.documentManager));
        noneSelectedContextMenu.getItems().addAll(newCircleObstacleMenuItem, newPolygonObstacleMenuItem, newRectangleObstacleMenuItem);
        newCircleObstacleMenuItem.setOnAction(this::newCircleObstacle);
        newPolygonObstacleMenuItem.setOnAction(this::newPolygonObstacle);
        newRectangleObstacleMenuItem.setOnAction(this::newRectangleObstacle);
        noneSelectedContextMenu.setAutoHide(true);
    }

    private void newCircleObstacle(ActionEvent event) {
        HObstacle newObstacle = new HCircleObstacle();
        newObstacle.setName(String.valueOf(getItems().size()));
        getItems().add(newObstacle);
    }
    private void newPolygonObstacle(ActionEvent event) {
        HObstacle newObstacle = new HPolygonObstacle();
        newObstacle.setName(String.valueOf(getItems().size()));
        getItems().add(newObstacle);
    }
    private void newRectangleObstacle(ActionEvent event) {
        HObstacle newObstacle = new HRectangleObstacle();
        newObstacle.setName(String.valueOf(getItems().size()));
        getItems().add(newObstacle);
    }

    private void listViewSelectedIndicesChanged(ListChangeListener.Change<? extends Integer> change) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            documentManager.getDocument().getSelectedAutoRoutine().getObstaclesSelectionModel().getSelectedIndices().removeListener(onPathSelectedObstaclesIndicesChanged);
            documentManager.getDocument().getSelectedAutoRoutine().getObstaclesSelectionModel().clearSelection();
            documentManager.getDocument().getSelectedAutoRoutine().getObstaclesSelectionModel().selectIndices(getSelectionModel().getSelectedIndices());
            documentManager.getDocument().getSelectedAutoRoutine().getObstaclesSelectionModel().getSelectedIndices().addListener(onPathSelectedObstaclesIndicesChanged);
        }
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
            // Must remove the listener -- When you clear items in ListView,
            // the selection model clears selected indices too
            getSelectionModel().getSelectedIndices().removeListener(onListViewSelectedIndicesChanged);
            setItems(BLANK);
            setContextMenu(null);
            getSelectionModel().getSelectedIndices().addListener(onListViewSelectedIndicesChanged);
            oldPath.getObstaclesSelectionModel().getSelectedIndices().removeListener(onPathSelectedObstaclesIndicesChanged);
        }
    }
    private void loadSelectedPath(HAutoRoutine newPath) {
        if (newPath != null) {
            setItems(newPath.getObstacles());
            setContextMenu(noneSelectedContextMenu);
            for (int index : documentManager.getDocument().getSelectedAutoRoutine().getObstaclesSelectionModel().getSelectedIndices()) {
                getSelectionModel().select(index);
            }
            newPath.getObstaclesSelectionModel().getSelectedIndices().addListener(onPathSelectedObstaclesIndicesChanged);
        }
    }
    private void pathSelectedObstaclesIndicesChanged(ListChangeListener.Change<? extends Integer> change) {
        getSelectionModel().getSelectedIndices().removeListener(onListViewSelectedIndicesChanged);
        getSelectionModel().clearSelection();
        for (int index : change.getList()) {
            getSelectionModel().select(index);
        }
        getSelectionModel().getSelectedIndices().addListener(onListViewSelectedIndicesChanged);
    }
}
