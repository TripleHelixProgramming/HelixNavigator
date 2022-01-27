package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.global.Standards;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ForegroundInfoText {
    
    private final Text text = new Text("NO DOCUMENT OPEN");
    private final StackPane pane = new StackPane(text);

    public ForegroundInfoText() {
        pane.setBackground(new Background(new BackgroundFill(Standards.BACKGROUND_COLOR, null, null)));
    }

    public StackPane getForegroundPane() {
        return pane;
    }
}