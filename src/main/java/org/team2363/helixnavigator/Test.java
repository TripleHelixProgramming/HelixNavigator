package org.team2363.helixnavigator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        Polygon poly = new Polygon(
            0, 20,
            10, 0,
            40, 10,
            30, 50,
            20, 30
        );
        Button b = new Button("__add__");
        b.setOnAction(e -> {
            poly.getPoints().addAll(2.0, 40.0, 23.0, 54.0);
        });
        primaryStage.setScene(new Scene(new VBox(poly, b)));
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }
}