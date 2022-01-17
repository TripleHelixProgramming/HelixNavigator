package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BackgroundLayer implements PathLayer {

    private final DocumentManager documentManager;

    private final ObservableList<Node> children = FXCollections.observableArrayList();
    private final ObservableList<Node> childrenUnmodifiable = FXCollections.unmodifiableObservableList(children);

    private final Rectangle rectangle = new Rectangle();
    
    public BackgroundLayer(DocumentManager documentManager) {
        this.documentManager = documentManager;

        children.add(rectangle);

        rectangle.setFill(Color.grayRgb(220));
        rectangle.setOnMouseClicked(event -> {
            System.out.println("Background clicked");
            if (event.getButton() == MouseButton.PRIMARY
                    && this.documentManager.getIsDocumentOpen()
                    && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.getDocument().getSelectedPath().clearSelection();
            }
        });
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public ObservableList<Node> getChildren() {
        return childrenUnmodifiable;
    }
}
