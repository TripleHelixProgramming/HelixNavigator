package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.ui.editor.field.FieldImageLayer;
import org.team2363.helixnavigator.ui.editor.line.LinesLayer;
import org.team2363.helixnavigator.ui.editor.obstacle.ObstaclesLayer;
import org.team2363.helixnavigator.ui.editor.waypoint.WaypointsLayer;

import javafx.collections.ListChangeListener;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class PathPane extends Pane {

    private final DocumentManager documentManager;

    private final BackgroundLayer backgroundLayer;
    private final FieldImageLayer fieldImageLayer;
    private final LinesLayer linesLayer;
    private final WaypointsLayer waypointsLayer;
    private final ObstaclesLayer obstaclesLayer;
    
    public PathPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        backgroundLayer = new BackgroundLayer(this.documentManager);
        fieldImageLayer = new FieldImageLayer(this.documentManager);
        linesLayer = new LinesLayer(this.documentManager);
        waypointsLayer = new WaypointsLayer(this.documentManager);
        obstaclesLayer = new ObstaclesLayer(this.documentManager);
        updateLayers();
        // This next line also accounts for the line layer since they both change simultaneously
        waypointsLayer.getChildren().addListener((ListChangeListener.Change<? extends Node> change) -> updateLayers());
        obstaclesLayer.getChildren().addListener((ListChangeListener.Change<? extends Node> change) -> updateLayers());

        setOnMousePressed(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                if (event.getButton() == MouseButton.MIDDLE) {
                    this.documentManager.getStage().getScene().setCursor(Cursor.CLOSED_HAND);
                }
                this.documentManager.actions().handleMousePressedAsPan(event);
            }
        });
        setOnMouseDragged(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.actions().handleMouseDraggedAsPan(event);
            }
        });
        setOnMouseReleased(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.getStage().getScene().setCursor(Cursor.DEFAULT);
            }
        });
        setOnScroll(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.actions().handleScrollAsZoom(event);
            }
        });

        Rectangle clip = new Rectangle();
        layoutBoundsProperty().addListener((currentValue, oldValue, newValue) -> {
            clip.setWidth(newValue.getWidth());
            clip.setHeight(newValue.getHeight());
            if (documentManager.actions().getLockZoom()) {
                documentManager.actions().zoomToFit();
            }
        });
        setClip(clip);
        Rectangle backgroundRectangle = backgroundLayer.getRectangle();
        backgroundRectangle.widthProperty().bind(clip.widthProperty());
        backgroundRectangle.heightProperty().bind(clip.heightProperty());
        this.documentManager.pathAreaWidthProperty().bind(clip.widthProperty());
        this.documentManager.pathAreaHeightProperty().bind(clip.heightProperty());
    }

    public void updateLayers() {
        getChildren().clear();
        getChildren().addAll(backgroundLayer.getChildren());
        getChildren().addAll(fieldImageLayer.getChildren());
        getChildren().addAll(linesLayer.getChildren());
        getChildren().addAll(waypointsLayer.getChildren());
        getChildren().addAll(obstaclesLayer.getChildren());
    }
}