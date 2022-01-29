package org.team2363.helixnavigator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        // double robotLength = 50;
        // double robotWidth = 50;
        // double robotHeading = 30;
        
        // Rectangle background = new Rectangle();
        // background.setFill(Color.LIGHTGRAY);
        // Rectangle robotRect = new Rectangle();
        // robotRect.setTranslateX(200);
        // robotRect.setTranslateY(200);
        // robotRect.setFill(null);
        // robotRect.setStrokeLineCap(StrokeLineCap.ROUND);
        // robotRect.setStrokeWidth(10);
        // robotRect.setStroke(Color.DEEPSKYBLUE);
        // robotRect.setArcWidth(6);
        // robotRect.setArcHeight(6);
        // robotRect.setWidth(robotLength);
        // robotRect.setHeight(robotWidth);
        // robotRect.setRotate(robotHeading);
        
        // Pane pane = new Pane(background, robotRect);
        // pane.layoutBoundsProperty().addListener((obsVal, oldVal, newVal) -> {
        //     background.setWidth(newVal.getWidth());
        //     background.setHeight(newVal.getHeight());
        // });
        // primaryStage.setScene(new Scene(pane));
        // primaryStage.setWidth(400);
        // primaryStage.setHeight(400);
        // robotRect.setOnMouseClicked(event -> {
        //     System.out.println("Robot Rect");
        // });
        // background.setOnMouseClicked(event -> {
        //     System.out.println("Background");
        // });
        // primaryStage.show();

        Circle a = new Circle(100, 100, 50);
        a.setFill(Color.TRANSPARENT);
        a.setStroke(Color.BLACK);
        Circle b = new Circle(300, 100, 50);
        Circle c = new Circle(500, 100, 50);
        Pane obstaclePane = new Pane(b);
        Pane waypointPane = new Pane(a, c);
        
        obstaclePane.setPickOnBounds(false);
        waypointPane.setPickOnBounds(false);
        primaryStage.setScene(new Scene(new Pane(obstaclePane, waypointPane)));
        obstaclePane.setOnMouseClicked(event -> {
            System.out.println("OBSTACLE CLICKED");
        });
        a.setOnMouseClicked(event -> {
            System.out.println("WAYPOINT CLICKED");
        });
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }
}