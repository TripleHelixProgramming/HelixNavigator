package org.team2363.helixnavigator.ui;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.scene.Scene;

public class MainScene {

    private final Scene scene;
    private final MainPane mainPane;

    public MainScene(DocumentManager documentManager) {
        mainPane = new MainPane(documentManager);
        scene = new Scene(mainPane);
    }
    
    public Scene getScene() {
        return scene;
    }

    public MainPane getMainPane() {
        return mainPane;
    }
}