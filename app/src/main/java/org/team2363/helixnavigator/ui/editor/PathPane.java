package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.ui.editor.field.FieldImageView;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PathPane extends Pane {

    private final DocumentManager documentManager;

    private final FieldImageView fieldImageView;
    
    public PathPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        fieldImageView = new FieldImageView(this.documentManager);
        getChildren().add(fieldImageView);

        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
    }
}