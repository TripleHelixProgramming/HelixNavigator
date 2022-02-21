package org.team2363.helixnavigator.ui.document;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.obstacle.HCircleObstacle;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;

public class ObstacleListView extends ListView<HObstacle> {

    private static final ObservableList<HObstacle> BLANK = FXCollections.<HObstacle>observableArrayList();

    private final DocumentManager documentManager;

    private final ContextMenu noneSelectedContextMenu = new ContextMenu();
    private final MenuItem newCircleObstacleMenuItem = new MenuItem("New circle obstacle");
    private final MenuItem newPolygonObstacleMenuItem = new MenuItem("New polygon obstacle");

    private final ListChangeListener<? super Integer> onListViewSelectedIndicesChanged = this::listViewSelectedIndicesChanged;
    private final ChangeListener<? super HPath> onSelectedPathChanged = this::selectedPathChanged;
    private final ListChangeListener<? super Integer> onPathSelectedObstaclesIndicesChanged = this::pathSelectedObstaclesIndicesChanged;

    public ObstacleListView(DocumentManager documentManager) {
        this.documentManager = documentManager;
        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);

        setEditable(true);
        setItems(BLANK);
        setContextMenu(null);
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getSelectionModel().getSelectedIndices().addListener(onListViewSelectedIndicesChanged);
        setCellFactory(ObstacleListCell.obstacleCellFactory);
        noneSelectedContextMenu.getItems().addAll(newCircleObstacleMenuItem, newPolygonObstacleMenuItem);
        newCircleObstacleMenuItem.setOnAction(this::newCircleObstacle);
        newPolygonObstacleMenuItem.setOnAction(this::newPolygonObstacle);
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

    private void listViewSelectedIndicesChanged(ListChangeListener.Change<? extends Integer> change) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            documentManager.getDocument().getSelectedPath().getObstaclesSelectionModel().getSelectedIndices().removeListener(onPathSelectedObstaclesIndicesChanged);
            documentManager.getDocument().getSelectedPath().getObstaclesSelectionModel().clearSelection();
            documentManager.getDocument().getSelectedPath().getObstaclesSelectionModel().selectIndices(getSelectionModel().getSelectedIndices());
            documentManager.getDocument().getSelectedPath().getObstaclesSelectionModel().getSelectedIndices().addListener(onPathSelectedObstaclesIndicesChanged);
        }
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            unloadSelectedPath(oldDocument.getSelectedPath());
            oldDocument.selectedPathProperty().removeListener(onSelectedPathChanged);
        }
    }
    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            loadSelectedPath(newDocument.getSelectedPath());
            newDocument.selectedPathProperty().addListener(onSelectedPathChanged);
        }
    }
    private void selectedPathChanged(ObservableValue<? extends HPath> currentPath, HPath oldPath, HPath newPath) {
        unloadSelectedPath(oldPath);
        loadSelectedPath(newPath);
    }
    private void unloadSelectedPath(HPath oldPath) {
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
    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            setItems(newPath.getObstacles());
            setContextMenu(noneSelectedContextMenu);
            for (int index : documentManager.getDocument().getSelectedPath().getObstaclesSelectionModel().getSelectedIndices()) {
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
