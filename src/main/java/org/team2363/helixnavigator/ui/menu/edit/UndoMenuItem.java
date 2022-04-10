package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class UndoMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public UndoMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Undo");
        setAccelerator(KeyCombination.keyCombination("shortcut+Z"));
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        System.out.println("Undoing");
    }
}