package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.ui.editor.field.FieldImageLayer;
import org.team2363.helixnavigator.ui.editor.line.LinesLayer;
import org.team2363.helixnavigator.ui.editor.obstacle.ObstaclesLayer;
import org.team2363.helixnavigator.ui.editor.waypoint.WaypointsLayer;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class PathPane extends Pane {

    private final DocumentManager documentManager;

    private final BackgroundRectangle backgroundRectangle;
    private final FieldImageLayer fieldImageLayer;
    private final ObstaclesLayer obstaclesLayer;
    private final LinesLayer linesLayer;
    private final WaypointsLayer waypointsLayer;

    private final Translate pathAreaTranslate = new Translate();
    private final Translate zoomTranslateTranslate = new Translate();
    
    public PathPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);

        backgroundRectangle = new BackgroundRectangle(this.documentManager);
        fieldImageLayer = new FieldImageLayer(this.documentManager);
        obstaclesLayer = new ObstaclesLayer(this.documentManager);
        linesLayer = new LinesLayer(this.documentManager);
        waypointsLayer = new WaypointsLayer(this.documentManager);

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
        clip.translateXProperty().bind(clip.widthProperty().multiply(-0.5));
        clip.translateYProperty().bind(clip.heightProperty().multiply(-0.5));
        setClip(clip);

        this.documentManager.pathAreaWidthProperty().bind(clip.widthProperty());
        this.documentManager.pathAreaHeightProperty().bind(clip.heightProperty());

        pathAreaTranslate.xProperty().bind(clip.widthProperty().multiply(0.5));
        pathAreaTranslate.yProperty().bind(clip.heightProperty().multiply(0.5));

        getTransforms().addAll(pathAreaTranslate, zoomTranslateTranslate);
    }

    private void updateLayers() {
        getChildren().clear();
        getChildren().add(backgroundRectangle.getRectangle());
        getChildren().addAll(fieldImageLayer.getChildren());
        getChildren().addAll(obstaclesLayer.getChildren());
        getChildren().addAll(linesLayer.getChildren());
        getChildren().addAll(waypointsLayer.getChildren());
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            zoomTranslateTranslate.xProperty().unbind();
            zoomTranslateTranslate.yProperty().unbind();
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            zoomTranslateTranslate.xProperty().bind(newDocument.zoomTranslateXProperty().negate());
            zoomTranslateTranslate.yProperty().bind(newDocument.zoomTranslateYProperty().negate());
        }
    }
}