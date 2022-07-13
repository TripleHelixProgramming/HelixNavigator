package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class DeleteMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public DeleteMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Delete");
        setAccelerator(KeyCombination.keyCombination("DELETE"));
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            documentManager.actions().deleteSelection();
        }
    }
}