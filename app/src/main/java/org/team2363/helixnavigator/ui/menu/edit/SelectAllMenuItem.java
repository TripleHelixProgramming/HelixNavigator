package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class SelectAllMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public SelectAllMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Select All");
        setAccelerator(KeyCombination.keyCombination("shortcut+A"));
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        documentManager.actions().selectAll();
    }
}