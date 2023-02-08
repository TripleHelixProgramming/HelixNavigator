package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class FlipToOppositeSideMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public FlipToOppositeSideMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Flip Objects to Opposite Side");
        // setAccelerator(KeyCombination.keyCombination("shortcut+RIGHT"));
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        documentManager.actions().flipObjectsToOppositeSide();
    }
}