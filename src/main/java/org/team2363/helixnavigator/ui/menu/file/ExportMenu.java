package org.team2363.helixnavigator.ui.menu.file;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.scene.control.Menu;

public class ExportMenu extends Menu {

    private final DocumentManager documentManager;

    private final ExportWaypointBundleMenuItem exportWaypointBundleMenuItem;

    public ExportMenu(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Export");
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());

        exportWaypointBundleMenuItem = new ExportWaypointBundleMenuItem(this.documentManager);

        getItems().addAll(exportWaypointBundleMenuItem);
    }
}