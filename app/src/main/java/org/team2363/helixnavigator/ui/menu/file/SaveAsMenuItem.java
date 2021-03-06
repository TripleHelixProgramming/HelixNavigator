package org.team2363.helixnavigator.ui.menu.file;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class SaveAsMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public SaveAsMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Save As...");
        setAccelerator(KeyCombination.keyCombination("shortcut+shift+S"));
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        documentManager.requestSaveAsDocument();
    }
}