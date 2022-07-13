package org.team2363.helixnavigator.ui.menu.file;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class ExportWaypointBundleMenuItem extends MenuItem {

    private final DocumentManager documentManager;
    
    public ExportWaypointBundleMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setText("Export waypoint bundle...");
        setOnAction(this::action);
    }

    private void action(ActionEvent event) {
        documentManager.requestExportWaypointBundle();
    }
}