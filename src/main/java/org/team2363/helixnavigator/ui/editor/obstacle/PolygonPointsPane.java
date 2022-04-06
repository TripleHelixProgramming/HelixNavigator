package org.team2363.helixnavigator.ui.editor.obstacle;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.HSelectionModel;
import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;
import org.team2363.lib.ui.MouseEventWrapper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PolygonPointsPane extends Pane {

    private final DocumentManager documentManager;

    private final ChangeListener<? super HPath> onSelectedPathChanged = this::selectedPathChanged;
    private final ChangeListener<? super HSelectionModel<HPolygonPoint>> onSelectionModelChanged = this::selectionModelChanged;
    private final ListChangeListener<? super HPolygonPoint> onPointsListChanged = this::pointsListChanged;

    public PolygonPointsPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setPickOnBounds(false);

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);
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
            unloadSelectionModel(oldPath.getPolygonPointsSelectionModel());
            oldPath.polygonPointsSelectionModelProperty().removeListener(onSelectionModelChanged);
        }
    }

    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            loadSelectionModel(newPath.getPolygonPointsSelectionModel());
            newPath.polygonPointsSelectionModelProperty().addListener(onSelectionModelChanged);
        }
    }

    private void selectionModelChanged(ObservableValue<? extends HSelectionModel<HPolygonPoint>> currentValue,
            HSelectionModel<HPolygonPoint> oldValue, HSelectionModel<HPolygonPoint> newValue) {
        unloadSelectionModel(oldValue);
        loadSelectionModel(newValue);
    }

    private void unloadSelectionModel(HSelectionModel<HPolygonPoint> oldValue) {
        if (oldValue != null) {
            purgePoints();
            oldValue.getItems().removeListener(onPointsListChanged);
        }
    }
    private void loadSelectionModel(HSelectionModel<HPolygonPoint> newValue) {
        if (newValue != null) {
            updatePoints(newValue.getItems());
            newValue.getItems().addListener(onPointsListChanged);
        }
    }

    private void pointsListChanged(ListChangeListener.Change<? extends HPolygonPoint> change) {
        purgePoints();
        updatePoints(change.getList());
    }

    private void purgePoints() {
        getChildren().clear();
    }
    private void updatePoints(ObservableList<? extends HPolygonPoint> points) {
        for (int i = 0; i < points.size(); i++) {
            PolygonPointView pointView = new PolygonPointView(points.get(i));
            pointView.zoomScaleProperty().bind(documentManager.getDocument().zoomScaleProperty());
            linkPolygonPointView(i, pointView, points.get(i));
            getChildren().add(pointView.getView());
        }
    }
    
    private void linkPolygonPointView(int index, PolygonPointView polygonPointView, HPolygonPoint polygonPoint) {
        polygonPointView.zoomScaleProperty().bind(documentManager.getDocument().zoomScaleProperty());

        EventHandler<MouseEvent> onMousePressed = event -> {
        };
        EventHandler<MouseEvent> onMouseDragBegin = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!event.isShortcutDown() && !documentManager.getDocument().getSelectedPath().getPolygonPointsSelectionModel().isSelected(index)) {
                    documentManager.getDocument().getSelectedPath().clearPolygonPointSelection();
                }
                documentManager.getDocument().getSelectedPath().getPolygonPointsSelectionModel().select(index);
            }
        };
        EventHandler<MouseEvent> onMouseDragged = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                documentManager.actions().handleMouseDraggedAsPolygonPointDragged(event, polygonPoint);
            }
        };
        EventHandler<MouseEvent> onMouseDragEnd = event -> {
        };
        EventHandler<MouseEvent> onMouseReleased = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!event.isShortcutDown()) {
                    boolean selected = documentManager.getDocument().getSelectedPath().getPolygonPointsSelectionModel().isSelected(index);
                    documentManager.getDocument().getSelectedPath().clearPolygonPointSelection();
                    documentManager.getDocument().getSelectedPath().getPolygonPointsSelectionModel().setSelected(index, selected);
                }
                documentManager.getDocument().getSelectedPath().getPolygonPointsSelectionModel().toggle(index);
            }
        };

        MouseEventWrapper eventWrapper = new MouseEventWrapper(onMousePressed, onMouseDragBegin, onMouseDragged, onMouseDragEnd, onMouseReleased);
        polygonPointView.getView().setOnMousePressed(eventWrapper.getOnMousePressed());
        polygonPointView.getView().setOnMouseDragged(eventWrapper.getOnMouseDragged());
        polygonPointView.getView().setOnMouseReleased(eventWrapper.getOnMouseReleased());
    }
}