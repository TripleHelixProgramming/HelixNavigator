package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.global.Standards;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class InfoText extends StackPane {
    
    private final Text text = new Text("NO DOCUMENT OPEN");

    public InfoText() {
        getChildren().addAll(text);
        setBackground(new Background(new BackgroundFill(Standards.BACKGROUND_COLOR, null, null)));
    }
}