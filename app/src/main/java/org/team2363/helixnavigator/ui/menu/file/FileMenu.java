package org.team2363.helixnavigator.ui.menu.file;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.scene.control.Menu;
import javafx.scene.control.SeparatorMenuItem;

public class FileMenu extends Menu {

    private final DocumentManager documentManager;

    private final NewDocumentMenuItem newDocumentMenuItem;
    private final OpenDocumentMenuItem openDocumentMenuItem;
    private final SaveMenuItem saveMenuItem;
    private final SaveAsMenuItem saveAsMenuItem;
    private final ImportMenuItem importMenuItem;
    private final ExportMenuItem exportMenuItem;
    private final CloseDocumentMenuItem closeDocumentMenuItem;

    public FileMenu(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setText("_File");

        newDocumentMenuItem = new NewDocumentMenuItem(this.documentManager);
        openDocumentMenuItem = new OpenDocumentMenuItem(this.documentManager);
        saveMenuItem = new SaveMenuItem(this.documentManager);
        saveAsMenuItem = new SaveAsMenuItem(this.documentManager);
        importMenuItem = new ImportMenuItem(this.documentManager);
        exportMenuItem = new ExportMenuItem(this.documentManager);
        closeDocumentMenuItem = new CloseDocumentMenuItem(this.documentManager);

        getItems().add(newDocumentMenuItem);
        getItems().add(openDocumentMenuItem);
        getItems().add(new SeparatorMenuItem());
        getItems().add(saveMenuItem);
        getItems().add(saveAsMenuItem);
        getItems().add(new SeparatorMenuItem());
        getItems().add(importMenuItem);
        getItems().add(exportMenuItem);
        getItems().add(new SeparatorMenuItem());
        getItems().add(closeDocumentMenuItem);
    }
}