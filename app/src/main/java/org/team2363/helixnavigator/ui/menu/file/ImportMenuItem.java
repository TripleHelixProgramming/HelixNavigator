package org.team2363.helixnavigator.ui.menu.file;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class ImportMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public ImportMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Import...");
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        System.out.println("Importing");
    }
}