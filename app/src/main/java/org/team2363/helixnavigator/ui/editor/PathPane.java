package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class PathPane extends Pane {

    private final DocumentManager documentManager;

    private final BackgroundRectangle backgroundRectangle;
    private final PathElementsPane pathElementsPane;
    
    public PathPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        backgroundRectangle = new BackgroundRectangle(this.documentManager);
        pathElementsPane = new PathElementsPane(this.documentManager);

        getChildren().addAll(backgroundRectangle, pathElementsPane, backgroundRectangle.getSelectionRectangle());

        setOnMousePressed(event -> {
            if (this.documentManager.getIsDocumentOpen() && !this.documentManager.actions().getLockZoom()
                    && event.getButton() == MouseButton.MIDDLE) {
                setCursor(Cursor.CLOSED_HAND);
            }
            this.documentManager.actions().handleMousePressedAsPan(event);
        });
        setOnMouseDragged(this.documentManager.actions()::handleMouseDraggedAsPan);
        setOnMouseDragged(event -> {
            this.documentManager.actions().handleMouseDraggedAsPan(event);
        });
        setOnMouseReleased(event -> {
            setCursor(Cursor.DEFAULT);
        });
        setOnScroll(this.documentManager.actions()::handleScrollAsZoom);

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(this.documentManager.pathAreaWidthProperty());
        clip.heightProperty().bind(this.documentManager.pathAreaHeightProperty());
        setClip(clip);
    }
}