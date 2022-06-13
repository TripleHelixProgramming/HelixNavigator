package org.team2363.helixnavigator.ui.prompts.documentconfig;

import org.team2363.helixnavigator.document.HDocument;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class RobotTab extends Tab {

    private final HDocument document;
    private final Label content = new Label("beep boop");

    public RobotTab(HDocument document) {
        this.document = document;
        setText("Robot");
        setContent(content);
        setClosable(false);
    }
}