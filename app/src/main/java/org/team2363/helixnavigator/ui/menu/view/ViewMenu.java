package org.team2363.helixnavigator.ui.menu.view;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.scene.control.Menu;

public class ViewMenu extends Menu {

    private final DocumentManager documentManager;

    private final ZoomToFitMenuItem zoomToFitMenuItem;

    public ViewMenu(DocumentManager documentManager) {
        this.documentManager = documentManager;

        zoomToFitMenuItem = new ZoomToFitMenuItem(this.documentManager);

        setText("_View");
        getItems().add(zoomToFitMenuItem);
    }
}