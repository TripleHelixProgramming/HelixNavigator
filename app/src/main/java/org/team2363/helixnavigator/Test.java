package org.team2363.helixnavigator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

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
        InputStream s = Test.class.getResourceAsStream("/script.py");
        File file = Files.createTempFile("script", ".py").toFile();
        FileOutputStream os = new FileOutputStream(file);
        s.transferTo(os);
        os.close();
        ProcessBuilder builder = new ProcessBuilder("python3", file.getAbsolutePath());
        Process process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println(file.getAbsolutePath());
        // launch(args);
    }
}