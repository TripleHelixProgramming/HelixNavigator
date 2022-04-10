package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.helixnavigator.global.Standards;

import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;

public class BackgroundRectangle extends Rectangle {

    private final DocumentManager documentManager;
    
    public BackgroundRectangle(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setFill(Standards.BACKGROUND_COLOR);
        setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY
                    && this.documentManager.getIsDocumentOpen()
                    && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.actions().clearSelection();
                if (this.documentManager.actions().getAutoWaypoint()) {
                    HWaypoint newWaypoint;
                    newWaypoint = new HHardWaypoint();
                    double x = (event.getX() - this.documentManager.getPathAreaWidth() / 2 - this.documentManager.getDocument().getZoomTranslateX()) / this.documentManager.getDocument().getZoomScale();
                    double y = -(event.getY() - this.documentManager.getPathAreaHeight() / 2 - this.documentManager.getDocument().getZoomTranslateY()) / this.documentManager.getDocument().getZoomScale();
                    newWaypoint.setX(x);
                    newWaypoint.setY(y);
                    int index = this.documentManager.getDocument().getSelectedPath().getWaypoints().size();
                    newWaypoint.setName("Hard Waypoint " + index);
                    this.documentManager.getDocument().getSelectedPath().getWaypoints().add(index, newWaypoint);
                    this.documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().select(index);
                }
            }
        });

        widthProperty().bind(this.documentManager.pathAreaWidthProperty());
        heightProperty().bind(this.documentManager.pathAreaHeightProperty());
    }
}