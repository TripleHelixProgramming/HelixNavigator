package org.team2363.helixnavigator.ui;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainStage {

    private final Stage stage;
    private final DocumentManager documentManager;
    private final MainScene mainScene;

    public MainStage(Stage stage) {
        this.stage = stage;
        documentManager = new DocumentManager();
        mainScene = new MainScene(documentManager);
        this.stage.setTitle("Helix Navigator");
        this.stage.setHeight(600);
        this.stage.setWidth(800);
        this.stage.setOnCloseRequest(this::onWindowCloseRequest);
        this.stage.setScene(mainScene.getScene());
    }

    public void show() {
        stage.show();
    }

    private void onWindowCloseRequest(WindowEvent event) {
        if (!documentManager.requestDocumentClose()) { // if document NOT closed, don't close window
            event.consume();
        }
    }
}
