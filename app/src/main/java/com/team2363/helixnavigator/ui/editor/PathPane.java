package com.team2363.helixnavigator.ui.editor;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PathPane extends StackPane {
    private final Pane pane = new Pane();
    private final FieldImageView fieldImageView = new FieldImageView();
    
    public PathPane() {

        getChildren().add(pane);
    }
}