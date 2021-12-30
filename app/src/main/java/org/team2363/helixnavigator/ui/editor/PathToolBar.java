package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class PathToolBar extends ToolBar {

    private final DocumentManager documentManager;

    private final Button showPointsButton;

    public PathToolBar(DocumentManager documentManager) {
        this.documentManager = documentManager;

        showPointsButton = new Button("Show Points");

        getItems().add(showPointsButton);
    }
}