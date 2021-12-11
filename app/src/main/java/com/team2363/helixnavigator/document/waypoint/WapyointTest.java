package com.team2363.helixnavigator.document.waypoint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class WapyointTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Circle circle = new Circle();
        circle.setFill(Color.BLUE);
        circle.setRadius(10);
        circle.setStroke(Color.ORANGE);
        circle.setOnMouseDragged(event -> {
            System.out.print("dragging: x: ");
            System.out.print(event.getX() + " y: ");
            System.out.println(event.getY());
            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
        });
        circle.setOnMousePressed(event -> {
            System.out.println("into drag");
            circle.setStrokeWidth(6);
        });
        circle.setOnMouseReleased(event -> {
            System.out.println("out of drag");
            circle.setStrokeWidth(0);
        });
        Pane stack = new Pane();
        stack.getChildren().add(circle);
        Scene scene = new Scene(stack);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
