package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class Rotate180MenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public Rotate180MenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Rotate 180\u00B0");
        setAccelerator(KeyCombination.keyCombination("shortcut+UP"));
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        documentManager.actions().rotateSelection180();
    }
}