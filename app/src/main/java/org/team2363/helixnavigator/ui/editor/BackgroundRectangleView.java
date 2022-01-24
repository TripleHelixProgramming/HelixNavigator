package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.global.Standards;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class BackgroundRectangleView implements ElementView {

    private final DocumentManager documentManager;

    private final Rectangle rectangle = new Rectangle();
    private final StackPane pane = new StackPane(rectangle);
    
    public BackgroundRectangleView(DocumentManager documentManager) {
        this.documentManager = documentManager;

        rectangle.setFill(Standards.BACKGROUND_COLOR);
        rectangle.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY
                    && this.documentManager.getIsDocumentOpen()
                    && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.actions().clearSelection();
            }
        });
    }

    @Override
    public Node getView() {
        return pane;
    }
}
