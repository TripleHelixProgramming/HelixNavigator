package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.ui.editor.field.FieldImageLayer;
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
    private final WaypointsLayer waypointsLayer;
    
    public PathPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        backgroundLayer = new BackgroundLayer(this.documentManager);
        fieldImageLayer = new FieldImageLayer(this.documentManager);
        waypointsLayer = new WaypointsLayer(this.documentManager);
        updateLayers();
        waypointsLayer.getChildren().addListener((ListChangeListener.Change<? extends Node> change) -> updateLayers());

        setOnMousePressed(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                if (event.getButton() == MouseButton.MIDDLE) {
                    this.documentManager.getStage().getScene().setCursor(Cursor.CLOSED_HAND);
                }
                this.documentManager.getDocument().handleBackgroundPressed(event);
            }
        });
        setOnMouseDragged(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.getDocument().handleBackgroundDragged(event);
            }
        });
        setOnMouseReleased(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.getStage().getScene().setCursor(Cursor.DEFAULT);
            }
        });
        setOnScroll(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.getDocument().handleScroll(event);
            }
        });

        Rectangle clip = new Rectangle();
        layoutBoundsProperty().addListener((currentValue, oldValue, newValue) -> {
            clip.setWidth(newValue.getWidth());
            clip.setHeight(newValue.getHeight());
        });
        setClip(clip);
        Rectangle backgroundRectangle = backgroundLayer.getRectangle();
        backgroundRectangle.widthProperty().bind(clip.widthProperty());
        backgroundRectangle.heightProperty().bind(clip.heightProperty());
    }

    public void updateLayers() {
        getChildren().clear();
        getChildren().addAll(backgroundLayer.getChildren());
        getChildren().addAll(fieldImageLayer.getChildren());
        getChildren().addAll(waypointsLayer.getChildren());
    }
}