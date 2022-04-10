package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class DeselectAllMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public DeselectAllMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Deselect All");
        setAccelerator(KeyCombination.keyCombination("shortcut+shift+A"));
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        documentManager.actions().clearSelection();
    }
}