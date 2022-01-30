package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.global.Standards;

import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;

public class BackgroundRectangle extends Rectangle {

    private final DocumentManager documentManager;
    
    public BackgroundRectangle(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setFill(Standards.BACKGROUND_COLOR);
        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY
                    && this.documentManager.getIsDocumentOpen()
                    && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.actions().clearSelection();
            }
        });

        widthProperty().bind(this.documentManager.pathAreaWidthProperty());
        heightProperty().bind(this.documentManager.pathAreaHeightProperty());
    }
}