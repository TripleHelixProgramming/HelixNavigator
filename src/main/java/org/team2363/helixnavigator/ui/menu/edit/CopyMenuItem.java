package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class CopyMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public CopyMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Copy");
        setAccelerator(KeyCombination.keyCombination("shortcut+C"));
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        documentManager.actions().copy();
    }
}