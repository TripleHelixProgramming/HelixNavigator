package org.team2363.helixnavigator.ui.menu.file;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class OpenDocumentMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public OpenDocumentMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Open Document...");
        setAccelerator(KeyCombination.keyCombination("shortcut+O"));
        setOnAction(this::action);
    }

    public void action(ActionEvent event) {
        documentManager.requestOpenDocument();
    }
}