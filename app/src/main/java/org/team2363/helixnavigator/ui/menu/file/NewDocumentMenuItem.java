package org.team2363.helixnavigator.ui.menu.file;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class NewDocumentMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public NewDocumentMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("New Document...");
        setAccelerator(KeyCombination.keyCombination("shortcut+N"));
        setOnAction(this::action);
    }

    public void action(ActionEvent event) {
        documentManager.requestNewDocument();
    }
}