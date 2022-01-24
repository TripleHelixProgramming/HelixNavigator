package org.team2363.helixnavigator.ui.editor;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ForegroundRectangleView implements ElementView {
    
    private final Text text = new Text("NO DOCUMENT OPEN");
    private final StackPane pane = new StackPane(text);

    public ForegroundRectangleView() {
        pane.setOpacity(0.5);
    }

    @Override
    public Node getView() {
        return pane;
    }
}