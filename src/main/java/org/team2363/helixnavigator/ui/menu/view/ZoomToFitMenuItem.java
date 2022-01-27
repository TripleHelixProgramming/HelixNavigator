package org.team2363.helixnavigator.ui.menu.view;

import java.util.logging.Logger;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class ZoomToFitMenuItem extends MenuItem {

    private final Logger logger = Logger.getLogger("org.team2363.helixnavigator.ui.menu.view");

    private final DocumentManager documentManager;

    public ZoomToFitMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Zoom To Fit");
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());
    }

    public void action(ActionEvent event) {
        logger.info("Zooming to fit.");
        documentManager.actions().zoomToFit();
    }
}