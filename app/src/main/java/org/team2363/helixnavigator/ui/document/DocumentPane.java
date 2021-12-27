package org.team2363.helixnavigator.ui.document;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class DocumentPane extends VBox {

    private final DocumentManager documentManager;

    private final PathChooserBox pathChooserBox;
    private final WaypointListView waypointListView;

    public DocumentPane(DocumentManager documentManager) {
        this.documentManager = documentManager;
        pathChooserBox = new PathChooserBox(documentManager);
        waypointListView = new WaypointListView(documentManager);
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10.0);
        setMinWidth(10);
        setMaxWidth(Region.USE_COMPUTED_SIZE);
        getChildren().add(pathChooserBox);
        getChildren().add(waypointListView);
    }
}