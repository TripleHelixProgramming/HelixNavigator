package org.team2363.helixnavigator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.logging.Logger;

import org.team2363.helixnavigator.global.Logs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(new Pane()));
        primaryStage.setWidth(1000);
        primaryStage.setHeight(1000);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        Logs.initialize();
        Logger.getLogger("org.team2363.helixnavigator.document").info("this is a test");
        // launch(args);
    }
}