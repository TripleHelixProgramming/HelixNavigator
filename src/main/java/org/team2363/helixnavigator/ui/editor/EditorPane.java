package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.ui.editor.toolbar.PathToolBar;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class EditorPane extends VBox {

    private final DocumentManager documentManager;

    private final PathToolBar pathToolBar;
    private final PathPane pathPane;
    private final ForegroundInfoText infoText;
    private final StackPane bottomStack;

    public EditorPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        pathToolBar = new PathToolBar(this.documentManager);
        pathPane = new PathPane(this.documentManager);
        infoText = new ForegroundInfoText();
        bottomStack = new StackPane(infoText.getForegroundPane());

        setPadding(new Insets(0, 10, 10, 5));
        setSpacing(10.0);
        VBox.setVgrow(bottomStack, Priority.ALWAYS);
        setAlignment(Pos.CENTER);

        getChildren().addAll(pathToolBar, bottomStack);

        bottomStack.layoutBoundsProperty().addListener((currentValue, oldValue, newValue) -> {
            this.documentManager.setPathAreaWidth(newValue.getWidth());
            this.documentManager.setPathAreaHeight(newValue.getHeight());
            if (this.documentManager.actions().getLockZoom()) {
                this.documentManager.actions().zoomToFit();
            }
        });
        bottomStack.setOnMousePressed(event -> {
            if (this.documentManager.getIsDocumentOpen() && !this.documentManager.actions().getLockZoom()
                    && event.getButton() == MouseButton.MIDDLE) {
                bottomStack.setCursor(Cursor.CLOSED_HAND);
            }
            this.documentManager.actions().handleMousePressedAsPan(event);
        });
        bottomStack.setOnMouseDragged(this.documentManager.actions()::handleMouseDraggedAsPan);
        bottomStack.setOnMouseReleased(event -> {
            bottomStack.setCursor(Cursor.DEFAULT);
        });
        bottomStack.setOnScroll(this.documentManager.actions()::handleScrollAsZoom);

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(this.documentManager.pathAreaWidthProperty());
        clip.heightProperty().bind(this.documentManager.pathAreaHeightProperty());
        bottomStack.setClip(clip);

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        loadDocument(newDocument);
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            disableInfoText();
        } else {
            enableInfoText();
        }
    }

    private void disableInfoText() {
        bottomStack.getChildren().set(0, pathPane);
    }
    private void enableInfoText() {
        bottomStack.getChildren().set(0, infoText.getForegroundPane());
    }
}