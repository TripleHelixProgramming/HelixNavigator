package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.ui.editor.field.FieldImageView;
import org.team2363.helixnavigator.ui.editor.waypoint.WaypointsView;

import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PathPane extends Pane {

    private final DocumentManager documentManager;

    private final FieldImageView fieldImageView;
    private final WaypointsView waypointsLayer;
    
    public PathPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        fieldImageView = new FieldImageView(this.documentManager);
        waypointsLayer = new WaypointsView(this.documentManager);
        getChildren().addAll(fieldImageView, waypointsLayer);

        setOnMousePressed(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                if (event.getButton() == MouseButton.MIDDLE) {
                    this.documentManager.getStage().getScene().setCursor(Cursor.CLOSED_HAND);
                }
                this.documentManager.getDocument().getSelectedPath().handleMousePressed(event);
            }
        });
        setOnMouseDragged(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.getDocument().getSelectedPath().handleMouseDragged(event);
            }
        });
        setOnMouseReleased(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.getStage().getScene().setCursor(Cursor.DEFAULT);
            }
        });
        setOnScroll(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.getDocument().getSelectedPath().handleScroll(event);
            }
        });
        setOnZoom(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.getDocument().getSelectedPath().handleZoom(event);
            }
        });

        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        Rectangle clip = new Rectangle();
        layoutBoundsProperty().addListener((currentValue, oldValue, newValue) -> {
            clip.setWidth(newValue.getWidth());
            clip.setHeight(newValue.getHeight());
        });
        setClip(clip);
    }
}