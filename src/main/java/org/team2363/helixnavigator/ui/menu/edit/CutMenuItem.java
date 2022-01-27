package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class CutMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public CutMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Cut");
        setAccelerator(KeyCombination.keyCombination("shortcut+X"));
        setOnAction(this::action);
    }

    public void action(ActionEvent event) {
        System.out.println("Cutting");
    }
}