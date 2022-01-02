package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.ui.editor.field.FieldImageView;
import org.team2363.helixnavigator.ui.editor.waypoint.WaypointsLayer;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PathPane extends Pane {

    private final DocumentManager documentManager;

    private final FieldImageView fieldImageView;
    private WaypointsLayer waypointsLayer = new WaypointsLayer(null);
    
    public PathPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        fieldImageView = new FieldImageView(this.documentManager);
        getChildren().addAll(fieldImageView, waypointsLayer);

        setOnMousePressed(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
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
                this.documentManager.getDocument().getSelectedPath().handleMouseReleased(event);
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

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);

        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        Rectangle clip = new Rectangle();
        layoutBoundsProperty().addListener((currentValue, oldValue, newValue) -> {
            clip.setWidth(newValue.getWidth());
            clip.setHeight(newValue.getHeight());
        });
        setClip(clip);
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }
    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            oldDocument.selectedPathProperty().addListener(this::selectedPathChanged);
        }
    }
    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            loadSelectedPath(newDocument.getSelectedPath());
            newDocument.selectedPathProperty().addListener(this::selectedPathChanged);
        }
    }
    private void selectedPathChanged(ObservableValue<? extends HPath> currentPath, HPath oldPath, HPath newPath) {
        unloadSelectedPath(oldPath);
        loadSelectedPath(newPath);
    }
    private void unloadSelectedPath(HPath oldPath) {
    }
    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            System.out.println("Loaded new waypoint layer");
            waypointsLayer = new WaypointsLayer(newPath);
            getChildren().set(1, waypointsLayer);
        }
    }
}