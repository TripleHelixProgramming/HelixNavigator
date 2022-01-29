package org.team2363.helixnavigator.ui.editor.toolbar;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;

public class PathToolBar extends ToolBar {

    private final DocumentManager documentManager;

    private final ToggleButton lockZoom = new ToggleButton("Lock Zoom");
    private final ToggleButton showOrigin = new ToggleButton("Show Origin");

    public PathToolBar(DocumentManager documentManager) {
        this.documentManager = documentManager;

        lockZoom.setOnAction(this::lockZoomToggled);
        this.documentManager.actions().lockZoomProperty().bind(lockZoom.selectedProperty());
        this.documentManager.actions().showOriginProperty().bind(showOrigin.selectedProperty());

        getItems().addAll(lockZoom, showOrigin);
    }

    public void lockZoomToggled(ActionEvent event) {
        if (lockZoom.isSelected()) {
            documentManager.actions().zoomToFit();
        }
    }
}