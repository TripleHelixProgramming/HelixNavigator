package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.lib.ui.MouseEventWrapper;

import javafx.event.EventHandler;
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
                    int index = this.documentManager.getDocument().getSelectedPath().getWaypoints().size();
                    newWaypoint.setName("Hard Waypoint " + index);
                    this.documentManager.getDocument().getSelectedPath().getWaypoints().add(index, newWaypoint);
                    this.documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().select(index);
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