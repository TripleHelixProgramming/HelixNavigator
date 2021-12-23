package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class RedoMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public RedoMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Redo");
        setAccelerator(KeyCombination.keyCombination("shortcut+Y"));
        setOnAction(this::action);
    }

    public void action(ActionEvent event) {
        System.out.println("Redoing");
    }
}