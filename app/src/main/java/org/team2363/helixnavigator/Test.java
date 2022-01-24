package org.team2363.helixnavigator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane innerPane = new StackPane();
        Circle circle = new Circle(20, 20, 5);
        Pane outerPane = new Pane(innerPane, circle);


        Scene scene = new Scene(outerPane);
        primaryStage.setScene(scene);
        innerPane.setOnMouseClicked(event -> {
            System.out.println("Inner pane clicked");
        });

        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}