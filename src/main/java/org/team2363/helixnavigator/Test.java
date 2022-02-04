package org.team2363.helixnavigator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        double robotLength = 50;
        double robotWidth = 50;

        Rectangle robotRect = new Rectangle();
        robotRect.setX(-robotLength / 2);
        robotRect.setY(-robotWidth / 2);
        robotRect.setFill(Color.TRANSPARENT);
        robotRect.setMouseTransparent(true);
        robotRect.setStrokeLineCap(StrokeLineCap.ROUND);
        robotRect.setStrokeType(StrokeType.CENTERED);
        robotRect.setStrokeWidth(10);
        robotRect.setStroke(Color.DEEPSKYBLUE);
        robotRect.setArcWidth(6);
        robotRect.setArcHeight(6);
        robotRect.setWidth(robotLength);
        robotRect.setHeight(robotWidth);
        Circle pivot = new Circle(robotLength / 2, 0, 10);
        pivot.setFill(Color.DEEPSKYBLUE);
        Pane robotPane = new Pane(robotRect, pivot);
        Rotate rotate = new Rotate();
        robotPane.getTransforms().add(rotate);
        Pane containerPane = new Pane(robotPane);
        // robotPane.setPickOnBounds(false);
        Pane outerPane = new Pane(containerPane);
        outerPane.setTranslateX(200);
        outerPane.setTranslateY(200);
        primaryStage.setScene(new Scene(outerPane));
        primaryStage.setWidth(400);
        primaryStage.setHeight(400);
        containerPane.setOnMouseDragged(event -> {
            double x = event.getX();
            double y = -event.getY();
            double[] lockAngles = {-180, -90, 0, 90, 180};
            double lockRadius = 10;
            double angle = Math.toDegrees(Math.atan2(y, x));
            System.out.println(angle);
            for (double lockAngle : lockAngles) {
                if (!event.isShiftDown() && Math.abs(angle - lockAngle) <= lockRadius) {
                    System.out.println("Snaped to " + lockAngle);
                    angle = lockAngle;
                    break;
                }
            }
            rotate.setAngle(-angle);
            // System.out.println("X: " + x + " Y: " + y);
        });
        primaryStage.show();

        // Circle a = new Circle(100, 100, 50);
        // a.setFill(Color.TRANSPARENT);
        // a.setStroke(Color.BLACK);
        // Circle b = new Circle(300, 100, 50);
        // Circle c = new Circle(500, 100, 50);
        // Pane obstaclePane = new Pane(b);
        // Pane waypointPane = new Pane(a, c);
        
        // obstaclePane.setPickOnBounds(false);
        // waypointPane.setPickOnBounds(false);
        // primaryStage.setScene(new Scene(new Pane(obstaclePane, waypointPane)));
        // obstaclePane.setOnMouseClicked(event -> {
        //     System.out.println("OBSTACLE CLICKED");
        // });
        // a.setOnMouseClicked(event -> {
        //     System.out.println("WAYPOINT CLICKED");
        // });
        // primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }
}