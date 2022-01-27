package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.global.Standards;

import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class BackgroundRectangle {

    private final DocumentManager documentManager;

    private final Rectangle rectangle = new Rectangle();

    private final Translate pathAreaCounterTranslate = new Translate();
    private final Translate zoomTranslateCounterTranslate = new Translate();
    
    public BackgroundRectangle(DocumentManager documentManager) {
        this.documentManager = documentManager;

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);

        rectangle.setFill(Standards.BACKGROUND_COLOR);
        rectangle.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY
                    && this.documentManager.getIsDocumentOpen()
                    && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.actions().clearSelection();
            }
        });
        pathAreaCounterTranslate.xProperty().bind(this.documentManager.pathAreaWidthProperty().multiply(-0.5));
        pathAreaCounterTranslate.yProperty().bind(this.documentManager.pathAreaHeightProperty().multiply(-0.5));

        rectangle.widthProperty().bind(this.documentManager.pathAreaWidthProperty());
        rectangle.heightProperty().bind(this.documentManager.pathAreaHeightProperty());
        rectangle.getTransforms().addAll(pathAreaCounterTranslate, zoomTranslateCounterTranslate);
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            zoomTranslateCounterTranslate.xProperty().unbind();
            zoomTranslateCounterTranslate.yProperty().unbind();
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            zoomTranslateCounterTranslate.xProperty().bind(newDocument.zoomTranslateXProperty().negate());
            zoomTranslateCounterTranslate.yProperty().bind(newDocument.zoomTranslateYProperty().negate());
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
