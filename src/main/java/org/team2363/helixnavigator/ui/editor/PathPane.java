package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.ui.editor.field.FieldImageLayer;
import org.team2363.helixnavigator.ui.editor.line.LinesLayer;
import org.team2363.helixnavigator.ui.editor.obstacle.ObstaclesLayer;
import org.team2363.helixnavigator.ui.editor.waypoint.WaypointsLayer;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
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

        backgroundRectangle = new BackgroundRectangle(this.documentManager);
        fieldImageLayer = new FieldImageLayer(this.documentManager);
        obstaclesLayer = new ObstaclesLayer(this.documentManager);
        linesLayer = new LinesLayer(this.documentManager);
        waypointsLayer = new WaypointsLayer(this.documentManager);

        updateLayers();
        // This next line also accounts for the line layer since they both change simultaneously
        waypointsLayer.getChildren().addListener((ListChangeListener.Change<? extends Node> change) -> updateLayers());
        obstaclesLayer.getChildren().addListener((ListChangeListener.Change<? extends Node> change) -> updateLayers());

        pathAreaTranslate.xProperty().bind(this.documentManager.pathAreaWidthProperty().multiply(0.5));
        pathAreaTranslate.yProperty().bind(this.documentManager.pathAreaHeightProperty().multiply(0.5));

        getTransforms().addAll(pathAreaTranslate, zoomTranslateTranslate);

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged); //TODO: move all of these things to end of constructor
    }

    private void updateLayers() {
        getChildren().clear();
        getChildren().add(backgroundRectangle.getRectangle());
        getChildren().add(fieldImageLayer.getImageView());
        getChildren().add(fieldImageLayer.getOriginView().getView());
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
            zoomTranslateTranslate.xProperty().bind(newDocument.zoomTranslateXProperty());
            zoomTranslateTranslate.yProperty().bind(newDocument.zoomTranslateYProperty());
        }
    }
}