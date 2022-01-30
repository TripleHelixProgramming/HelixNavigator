package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.ui.editor.toolbar.PathToolBar;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EditorPane extends VBox {

    private final DocumentManager documentManager;

    private final PathToolBar pathToolBar;
    private final PathPane pathPane;
    private final InfoText infoText;
    private final StackPane bottomStack;

    public EditorPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        pathToolBar = new PathToolBar(this.documentManager);
        pathPane = new PathPane(this.documentManager);
        infoText = new InfoText();
        bottomStack = new StackPane(infoText);

        setPadding(new Insets(0, 10, 10, 5));
        setSpacing(10.0);
        VBox.setVgrow(bottomStack, Priority.ALWAYS);
        setAlignment(Pos.CENTER);

        bottomStack.layoutBoundsProperty().addListener((currentValue, oldValue, newValue) -> {
            this.documentManager.setPathAreaWidth(newValue.getWidth());
            this.documentManager.setPathAreaHeight(newValue.getHeight());
            if (this.documentManager.actions().getLockZoom()) {
                this.documentManager.actions().zoomToFit();
            }
        });

        getChildren().addAll(pathToolBar, bottomStack);

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged); //TODO: move all of these things to end of constructor
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
        bottomStack.getChildren().set(0, infoText);
    }
}