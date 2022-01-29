package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.ui.editor.field.FieldImagePane;
import org.team2363.helixnavigator.ui.editor.line.LinesPane;
import org.team2363.helixnavigator.ui.editor.obstacle.ObstaclesPane;
import org.team2363.helixnavigator.ui.editor.waypoint.WaypointsPane;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

public class PathPane extends Pane {

    private final DocumentManager documentManager;

    private final BackgroundRectangle backgroundRectangle;
    private final FieldImagePane fieldImagePane;
    private final ObstaclesPane obstaclesPane;
    private final LinesPane linesPane;
    private final WaypointsPane waypointsPane;

    private final Translate pathAreaTranslate = new Translate();
    private final Translate zoomTranslateTranslate = new Translate();
    
    public PathPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        backgroundRectangle = new BackgroundRectangle(this.documentManager);
        fieldImagePane = new FieldImagePane(this.documentManager);
        obstaclesPane = new ObstaclesPane(this.documentManager);
        linesPane = new LinesPane(this.documentManager);
        waypointsPane = new WaypointsPane(this.documentManager);

        getChildren().addAll(backgroundRectangle.getRectangle(), fieldImagePane, obstaclesPane, linesPane, waypointsPane);

        pathAreaTranslate.xProperty().bind(this.documentManager.pathAreaWidthProperty().multiply(0.5));
        pathAreaTranslate.yProperty().bind(this.documentManager.pathAreaHeightProperty().multiply(0.5));

        getTransforms().addAll(pathAreaTranslate, zoomTranslateTranslate);

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged); //TODO: move all of these things to end of constructor
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