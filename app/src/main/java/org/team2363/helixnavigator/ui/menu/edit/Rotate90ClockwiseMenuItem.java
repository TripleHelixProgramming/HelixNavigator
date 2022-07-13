package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class Rotate90ClockwiseMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public Rotate90ClockwiseMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Rotate 90\u00B0 Clockwise");
        setAccelerator(KeyCombination.keyCombination("shortcut+RIGHT"));
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        documentManager.actions().rotateSelection90Clockwise();
    }
}