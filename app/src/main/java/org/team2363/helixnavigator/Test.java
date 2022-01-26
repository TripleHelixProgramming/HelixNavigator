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
        Circle paneCircle = new Circle(40);
        Circle directCircle = new Circle(30);
        Pane pane = new Pane(paneCircle);
        StackPane outerStack = new StackPane(directCircle);
        primaryStage.setScene(new Scene(outerStack));
        // System.out.println(pane.getBoundsInParent().getWidth());
        System.out.println(directCircle.getBoundsInParent().getWidth());
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }
}