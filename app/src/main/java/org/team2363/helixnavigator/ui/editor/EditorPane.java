package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EditorPane extends VBox {

    private final DocumentManager documentManager;

    private final PathToolBar pathToolBar;
    private final PathPane pathPane;
    private final Text infoText;
    private final StackPane bottomStack;

    public EditorPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        pathToolBar = new PathToolBar(this.documentManager);
        pathPane = new PathPane(this.documentManager);
        infoText = new Text();
        bottomStack = new StackPane(pathPane, infoText);

        setPadding(new Insets(0, 10, 10, 5));
        setSpacing(10.0);
        VBox.setVgrow(bottomStack, Priority.ALWAYS);
        setAlignment(Pos.CENTER);

        getChildren().add(pathToolBar);
        getChildren().add(bottomStack);

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            oldDocument.selectedPathIndexProperty().removeListener(this::selectedPathIndexChanged);
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            loadSelectedPathIndex(newDocument.getSelectedPathIndex());
            newDocument.selectedPathIndexProperty().addListener(this::selectedPathIndexChanged);
        } else {
            enableInfoText();
            infoText.setText("NO DOCUMENT OPEN");
        }
    }

    private void selectedPathIndexChanged(ObservableValue<? extends Number> currentIndex, Number oldIndex, Number newIndex) {
        loadSelectedPathIndex(newIndex);
    }

    private void loadSelectedPathIndex(Number newIndex) {
        if (newIndex.intValue() >= 0) {
            disableInfoText();
        } else {
            enableInfoText();
            infoText.setText("NO PATH OPEN");
        }
    }

    private void enableInfoText() {
        if (!bottomStack.getChildren().contains(infoText)) {
            bottomStack.getChildren().add(1, infoText);
        }
    }

    private void disableInfoText() {
        if (bottomStack.getChildren().contains(infoText)) {
            bottomStack.getChildren().remove(1);
        }
    }
}