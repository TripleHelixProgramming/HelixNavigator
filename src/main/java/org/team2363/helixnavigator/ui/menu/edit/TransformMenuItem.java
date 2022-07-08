package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class TransformMenuItem extends MenuItem {

    private final DocumentManager documentManager;
    
    public TransformMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setText("Transform...");
        setAccelerator(KeyCombination.keyCombination("shortcut+T"));
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        if (this.documentManager.getIsDocumentOpen()) { // Redundant check
            documentManager.actions().getTransformDialog().show();
        }
    }
}