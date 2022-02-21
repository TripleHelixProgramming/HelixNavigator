package org.team2363.helixnavigator.ui.editor.toolbar;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;

public class PathToolBar extends ToolBar {

    private final DocumentManager documentManager;

    private final ToggleButton lockZoom = new ToggleButton("Lock Zoom");
    private final ToggleButton showOrigin = new ToggleButton("Show Origin");
    private final Button debug = new Button("Debug");

    public PathToolBar(DocumentManager documentManager) {
        this.documentManager = documentManager;

        lockZoom.setOnAction(this::lockZoomToggled);
        this.documentManager.actions().lockZoomProperty().bind(lockZoom.selectedProperty());
        this.documentManager.actions().showOriginProperty().bind(showOrigin.selectedProperty());
        debug.setOnAction(event -> {
            if (this.documentManager.getIsDocumentOpen() &&
                    this.documentManager.getDocument().isPathSelected() &&
                    !this.documentManager.getDocument().getSelectedPath().getWaypoints().isEmpty()) {
                debug.setText(String.valueOf(this.documentManager.getDocument().getSelectedPath().getWaypoints().get(0).isSelected()));
            }
        });

        getItems().addAll(lockZoom, showOrigin, debug);
    }

    public void lockZoomToggled(ActionEvent event) {
        if (lockZoom.isSelected()) {
            documentManager.actions().zoomToFit();
        }
    }
}