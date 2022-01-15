package org.team2363.helixnavigator.ui.document;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DocumentPane extends VBox {

    private final DocumentManager documentManager;

    private final PathChooserBox pathChooserBox;
    private final WaypointListView waypointListView;
    private final ObstacleListView obstacleListView;

    public DocumentPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        pathChooserBox = new PathChooserBox(this.documentManager);
        waypointListView = new WaypointListView(this.documentManager);
        obstacleListView = new ObstacleListView(this.documentManager);

        setPadding(new Insets(10, 5, 10, 10));
        setSpacing(10.0);
        VBox.setVgrow(waypointListView, Priority.ALWAYS);
        VBox.setVgrow(obstacleListView, Priority.ALWAYS);

        getChildren().addAll(pathChooserBox, waypointListView, obstacleListView);
    }
}