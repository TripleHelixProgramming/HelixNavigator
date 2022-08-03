package org.team2363.helixnavigator.ui.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.timeline.HWaypoint;
import org.team2363.helixnavigator.document.timeline.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.lib.ui.MouseEventWrapper;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class BackgroundRectangle extends Pane {

    private final DocumentManager documentManager;

    private final Rectangle backgroundRectangle = new Rectangle();
    private final Rectangle selectionRectangle = new Rectangle();

    private double selectionRectangleX = 0.0;
    private double selectionRectangleY = 0.0;
    private List<Integer> initialSelectedWaypointIndices = null;
    private List<Integer> initialSelectedObstacleIndices = null;
    
    public BackgroundRectangle(DocumentManager documentManager) {
        this.documentManager = documentManager;

        getChildren().add(backgroundRectangle);

        backgroundRectangle.setFill(Standards.BACKGROUND_COLOR);
        selectionRectangle.setFill(Color.valueOf("#00000030"));
        selectionRectangle.setStroke(Color.gray(.05));
        selectionRectangle.setStrokeType(StrokeType.INSIDE);
        selectionRectangle.setStrokeWidth(2);
        selectionRectangle.setOpacity(0.0);
        selectionRectangle.setMouseTransparent(true);

        selectionRectangle.setX(100);
        selectionRectangle.setY(100);
        selectionRectangle.setWidth(1000);
        selectionRectangle.setHeight(600);

        EventHandler<MouseEvent> onMousePressed = event -> {
        };
        EventHandler<MouseEvent> onMouseDragBegin = event -> {
            selectionRectangleX = event.getX();
            selectionRectangleY = event.getY();
            selectionRectangle.setX(selectionRectangleX);
            selectionRectangle.setY(selectionRectangleY);
            selectionRectangle.setOpacity(1.0);
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                if (event.isShortcutDown()) {
                    initialSelectedWaypointIndices = new ArrayList<>(this.documentManager.getDocument().getSelectedPath()
                            .getTimelineSelectionModel().getSelectedIndices());
                    initialSelectedObstacleIndices = new ArrayList<>(this.documentManager.getDocument().getSelectedPath()
                            .getObstaclesSelectionModel().getSelectedIndices());
                } else {
                    initialSelectedWaypointIndices = Collections.emptyList();
                    initialSelectedObstacleIndices = Collections.emptyList();
                    documentManager.actions().clearSelection();
                }
            }
        };
        EventHandler<MouseEvent> onMouseDragged = event -> {
            double x = event.getX();
            double y = event.getY();
            if (x < selectionRectangleX) {
                selectionRectangle.setX(x);
                selectionRectangle.setWidth(selectionRectangleX - x);
                if (selectionRectangle.getStrokeDashArray().isEmpty()) {
                    selectionRectangle.getStrokeDashArray().add(8.0);
                }
            } else {
                selectionRectangle.setX(selectionRectangleX);
                selectionRectangle.setWidth(x - selectionRectangleX);
                if (selectionRectangle.getStrokeDashArray().size() > 0) {
                    selectionRectangle.getStrokeDashArray().remove(0);
                }
            }
            if (y < selectionRectangleY) {
                selectionRectangle.setY(y);
                selectionRectangle.setHeight(selectionRectangleY - y);
            } else {
                selectionRectangle.setY(selectionRectangleY);
                selectionRectangle.setHeight(y - selectionRectangleY);
            }
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                HPath path = this.documentManager.getDocument().getSelectedPath();
                for (int waypointIndex = 0; waypointIndex < path.getTimeline().size(); waypointIndex++) {
                    if (initialSelectedWaypointIndices == null || !initialSelectedWaypointIndices.contains(waypointIndex)) {
                        HWaypoint waypoint = path.getTimeline().get(waypointIndex);
                        Point2D pathAreaCoords = this.documentManager.actions().calculatePathAreaCoordinates(waypoint);
                        // System.out.println(pathAreaCoords.toString());
                        if (selectionRectangle.getX() <= pathAreaCoords.getX() &&
                                pathAreaCoords.getX() <= selectionRectangle.getX() + selectionRectangle.getWidth() &&
                                selectionRectangle.getY() <= pathAreaCoords.getY() &&
                                pathAreaCoords.getY() <= selectionRectangle.getY() + selectionRectangle.getHeight()) {
                            path.getTimelineSelectionModel().select(waypointIndex);
                        } else {
                            path.getTimelineSelectionModel().deselect(waypointIndex);
                        }
                    }
                }
            }
        };
        EventHandler<MouseEvent> onMouseDragEnd = event -> {
            selectionRectangle.setOpacity(0.0);
        };
        EventHandler<MouseEvent> onMouseReleased = event -> {
            if (event.getButton() == MouseButton.PRIMARY
                    && this.documentManager.getIsDocumentOpen()
                    && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.actions().clearSelection();
                if (this.documentManager.actions().getAutoWaypoint()) {
                    HWaypoint newWaypoint;
                    newWaypoint = new HHardWaypoint();
                    double x = (event.getX() - this.documentManager.getPathAreaWidth() / 2 - this.documentManager.getDocument().getZoomTranslateX()) / this.documentManager.getDocument().getZoomScale();
                    double y = -(event.getY() - this.documentManager.getPathAreaHeight() / 2 - this.documentManager.getDocument().getZoomTranslateY()) / this.documentManager.getDocument().getZoomScale();
                    newWaypoint.setX(x);
                    newWaypoint.setY(y);
                    int index = this.documentManager.getDocument().getSelectedPath().getTimeline().size();
                    newWaypoint.setName("Hard Waypoint " + index);
                    this.documentManager.getDocument().getSelectedPath().getTimeline().add(index, newWaypoint);
                    this.documentManager.getDocument().getSelectedPath().getTimelineSelectionModel().select(index);
                }
            }
        };

        MouseEventWrapper eventWrapper = new MouseEventWrapper(onMousePressed,
                onMouseDragBegin, onMouseDragged, onMouseDragEnd, onMouseReleased);
        setOnMousePressed(eventWrapper.getOnMousePressed());
        setOnMouseDragged(eventWrapper.getOnMouseDragged());
        setOnMouseReleased(eventWrapper.getOnMouseReleased());

        backgroundRectangle.widthProperty().bind(this.documentManager.pathAreaWidthProperty());
        backgroundRectangle.heightProperty().bind(this.documentManager.pathAreaHeightProperty());
    }

    public Node getSelectionRectangle() {
        return selectionRectangle;
    }
}