package org.team2363.helixnavigator.ui.menu.tools;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class EditRobotConfigurationMenuItem extends MenuItem {

    private final DocumentManager documentManager;
    
    public EditRobotConfigurationMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setText("Edit Robot Configuration...");
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        this.documentManager.actions().getRobotConfigDialog().show();
    }
}