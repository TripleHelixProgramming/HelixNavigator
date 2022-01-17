package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;

public class BackgroundRectangle extends Rectangle {

    private final DocumentManager documentManager;
    
    public BackgroundRectangle(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setOnMouseClicked(event -> {
            System.out.println("Background clicked");
            if (event.getButton() == MouseButton.PRIMARY
                    && this.documentManager.getIsDocumentOpen()
                    && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.getDocument().getSelectedPath().clearSelection();
            }
        });
    }
}
