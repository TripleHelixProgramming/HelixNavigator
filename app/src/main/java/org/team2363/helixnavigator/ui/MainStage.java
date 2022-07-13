package org.team2363.helixnavigator.ui;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.global.Standards;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainStage {

    private final Stage stage;
    private final DocumentManager documentManager;
    private final MainScene mainScene;

    public MainStage(Stage stage) {
        this.stage = stage;
        documentManager = new DocumentManager(this.stage);
        mainScene = new MainScene(documentManager);
        this.stage.setTitle(Standards.APPLICATION_NAME);
        this.stage.setHeight(700);
        this.stage.setWidth(1200);
        this.stage.setMinWidth(400);
        this.stage.setMinHeight(100);
        this.stage.setOnCloseRequest(this::onWindowCloseRequest);
        this.stage.setScene(mainScene.getScene());
    }

    public void show() {
        stage.show();
    }

    private void onWindowCloseRequest(WindowEvent event) {
        if (!documentManager.requestCloseDocument()) { // if document NOT closed, don't close window
            event.consume();
        }
    }
}
