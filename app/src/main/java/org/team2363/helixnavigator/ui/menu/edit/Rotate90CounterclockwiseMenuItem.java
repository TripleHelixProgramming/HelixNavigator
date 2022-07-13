package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class Rotate90CounterclockwiseMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public Rotate90CounterclockwiseMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Rotate 90\u00B0 Counterclockwise");
        setAccelerator(KeyCombination.keyCombination("shortcut+LEFT"));
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        documentManager.actions().rotateSelection90Counterclockwise();
    }
}