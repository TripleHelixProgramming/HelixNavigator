package org.team2363.helixnavigator.ui.menu.tools;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.scene.control.Menu;

public class ToolsMenu extends Menu {

    private final DocumentManager documentManager;

    private final EditDocumentConfigurationMenuItem editDocumentConfigurationMenuItem;
    private final EditRobotConfigurationMenuItem editRobotConfigurationMenuItem;

    public ToolsMenu(DocumentManager documentManager) {
        this.documentManager = documentManager;

        editRobotConfigurationMenuItem = new EditRobotConfigurationMenuItem(this.documentManager);
        editDocumentConfigurationMenuItem = new EditDocumentConfigurationMenuItem(this.documentManager);

        setText("_Tools");
        getItems().add(editDocumentConfigurationMenuItem);
        getItems().add(editRobotConfigurationMenuItem);
    }
}