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
        VBox.setVgrow(pathPane, Priority.ALWAYS);
        setAlignment(Pos.CENTER);

        getChildren().addAll(pathToolBar, infoText.getForegroundPane());

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
        getChildren().set(1, pathPane);
    }
    private void enableInfoText() {
        getChildren().set(1, infoText.getForegroundPane());
    }
}