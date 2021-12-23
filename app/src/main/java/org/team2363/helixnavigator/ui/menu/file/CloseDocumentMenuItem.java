package org.team2363.helixnavigator.ui.menu.file;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class CloseDocumentMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public CloseDocumentMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Close Document");
        setAccelerator(KeyCombination.keyCombination("shortcut+W"));
        setOnAction(this::action);
    }

    public void action(ActionEvent event) {
        documentManager.requestCloseDocument();
    }
}