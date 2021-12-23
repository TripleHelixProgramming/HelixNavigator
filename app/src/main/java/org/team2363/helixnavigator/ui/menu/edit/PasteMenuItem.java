package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class PasteMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public PasteMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Paste");
        setAccelerator(KeyCombination.keyCombination("shortcut+V"));
        setOnAction(this::action);
    }

    public void action(ActionEvent event) {
        System.out.println("Pasting");
    }
}