package org.team2363.helixnavigator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {

        Circle innerMostCircle = new Circle();
        innerMostCircle.setRadius(10);
        innerMostCircle.setCenterX(10);
        innerMostCircle.setCenterY(10);
        Circle innerMostCircleClip = new Circle();
        innerMostCircleClip.setRadius(10);
        innerMostCircleClip.setCenterX(10);
        innerMostCircleClip.setCenterY(10);
        Pane innerMostPane = new Pane(innerMostCircle);
        innerMostPane.setClip(innerMostCircleClip);

        Circle innerCircle = new Circle();
        innerCircle.setRadius(10);
        innerCircle.setCenterX(40);
        innerCircle.setCenterY(40);
        Circle innerCircleClip = new Circle();
        innerCircleClip.setRadius(10);
        innerCircleClip.setCenterX(40);
        innerCircleClip.setCenterY(40);
        Pane innerPane = new Pane(innerCircle);
        innerPane.setClip(innerCircleClip);

        Circle outerCircle = new Circle();
        outerCircle.setRadius(10);
        outerCircle.setCenterX(80);
        outerCircle.setCenterY(80);
        Circle outerCircleClip = new Circle();
        outerCircleClip.setRadius(10);
        outerCircleClip.setCenterX(80);
        outerCircleClip.setCenterY(80);
        Pane outerPane = new Pane(outerCircle);
        outerPane.setClip(outerCircleClip);

        Pane pane = new Pane(innerMostPane, innerPane, outerPane);
        primaryStage.setScene(new Scene(pane));
        primaryStage.setWidth(140);
        primaryStage.setHeight(140);
        innerMostPane.setOnMouseClicked(event -> {
            System.out.println("Inner most circle");
        });
        innerPane.setOnMouseClicked(event -> {
            System.out.println("Inner Circle");
        });
        // innerPane.setOnMouseClicked(event -> {
        //     System.out.println("Inner Pane");
        // });
        outerPane.setOnMouseClicked(event -> {
            System.out.println("Outer Circle");
        });
        // outerPane.setOnMouseClicked(event -> {
        //     System.out.println("Outer Pane");
        // });
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}