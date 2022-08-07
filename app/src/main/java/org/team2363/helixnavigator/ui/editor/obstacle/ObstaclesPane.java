package org.team2363.helixnavigator.ui.editor.obstacle;

import java.util.List;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HAutoRoutine;
import org.team2363.helixnavigator.document.obstacle.HCircleObstacle;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import org.team2363.helixnavigator.document.obstacle.HRectangleObstacle;
import org.team2363.lib.ui.MouseEventWrapper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ObstaclesPane extends Pane {

    private final DocumentManager documentManager;

    private final Pane obstaclesPane = new Pane();
    private final PolygonPointsPane polygonPointsPane;

    private final ChangeListener<? super HAutoRoutine> onSelectedPathChanged = this::selectedPathChanged;
    private final ListChangeListener<? super HObstacle> onObstaclesChanged = this::obstaclesChanged;
    
    public ObstaclesPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        polygonPointsPane = new PolygonPointsPane(this.documentManager);

        obstaclesPane.setPickOnBounds(false);
        getChildren().addAll(obstaclesPane, polygonPointsPane);
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
            obstaclesPane.getChildren().clear();
            oldPath.getObstacles().removeListener(onObstaclesChanged);
        }
    }

    private void loadSelectedPath(HAutoRoutine newPath) {
        if (newPath != null) {
            updateObstacles(newPath.getObstacles());
            newPath.getObstacles().addListener(onObstaclesChanged);
        }
    }

    private void obstaclesChanged(ListChangeListener.Change<? extends HObstacle> change) {
        updateObstacles(change.getList());
    }

    private void updateObstacles(List<? extends HObstacle> list) {
        obstaclesPane.getChildren().clear();
        for (int i = 0; i < list.size(); i++) {
            HObstacle obstacle = list.get(i);
            ObstacleView obstacleView;
            switch (obstacle.getObstacleType()) {
                case CIRCLE:
                    HCircleObstacle circleObstacle = (HCircleObstacle) obstacle;
                    obstacleView = new CircleObstacleView(circleObstacle);
                    break;
                case POLYGON:
                    HPolygonObstacle polygonObstacle = (HPolygonObstacle) obstacle;
                    obstacleView = new PolygonObstacleView(polygonObstacle);
                    break;
                case RECTANGLE:
                    HRectangleObstacle rectangleObstacle = (HRectangleObstacle) obstacle;
                    obstacleView = new RectangleObstacleView(rectangleObstacle);
                    break;
                default:
                    obstacleView = null;
                    break;

            }
            linkObstacleView(i, obstacleView, obstacle);
            obstaclesPane.getChildren().add(i, obstacleView.getView());
        }
    }

    private void linkObstacleView(int index, ObstacleView obstacleView, HObstacle obstacle) {
        obstacleView.zoomScaleProperty().bind(documentManager.getDocument().zoomScaleProperty());

        EventHandler<MouseEvent> onMousePressed = event -> {
        };
        EventHandler<MouseEvent> onMouseDragBegin = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!event.isShortcutDown() && !documentManager.getDocument().getSelectedAutoRoutine().getObstaclesSelectionModel().isSelected(index)) {
                    documentManager.getDocument().getSelectedAutoRoutine().clearSelection();
                }
                documentManager.getDocument().getSelectedAutoRoutine().getObstaclesSelectionModel().select(index);
                documentManager.actions().handleMouseDragBeginAsElementsDragBegin(event);
            }
        };
        EventHandler<MouseEvent> onMouseDragged = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                documentManager.actions().handleMouseDraggedAsElementsDragged(event);
            }
        };
        EventHandler<MouseEvent> onMouseDragEnd = event -> {
        };
        EventHandler<MouseEvent> onMouseReleased = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!event.isShortcutDown()) {
                    boolean selected = documentManager.getDocument().getSelectedAutoRoutine().getObstaclesSelectionModel().isSelected(index);
                    documentManager.getDocument().getSelectedAutoRoutine().clearSelection();
                    documentManager.getDocument().getSelectedAutoRoutine().getObstaclesSelectionModel().setSelected(index, selected);
                }
                documentManager.getDocument().getSelectedAutoRoutine().getObstaclesSelectionModel().toggle(index);
            }
        };

        MouseEventWrapper eventWrapper = new MouseEventWrapper(onMousePressed, onMouseDragBegin, onMouseDragged, onMouseDragEnd, onMouseReleased);
        obstacleView.getView().setOnMousePressed(eventWrapper.getOnMousePressed());
        obstacleView.getView().setOnMouseDragged(eventWrapper.getOnMouseDragged());
        obstacleView.getView().setOnMouseReleased(eventWrapper.getOnMouseReleased());
    }
}