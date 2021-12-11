package com.team2363.helixnavigator.ui.editor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EditorTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setWidth(1500);
        primaryStage.setHeight(1000);
        TrajectoryPane pane = new TrajectoryPane(primaryStage.widthProperty(), primaryStage.heightProperty());
        Scene scene = new Scene(pane, 1800, 900);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Canvas canvas = new Canvas(800, 500);
        // StackPane pane = new StackPane();
        // Scene scene = new Scene(pane, 800, 500);
        // GraphicsContext gc = canvas.getGraphicsContext2D();
        // gc.setStroke(Color.BLACK);
        // gc.setLineWidth(10);
        // scene.setOnMousePressed(e -> {
        //     gc.beginPath();
        //     gc.lineTo(e.getSceneX(), e.getSceneY());
        //     gc.stroke();
        // });
        // scene.setOnMouseDragged(e -> {
        //     gc.lineTo(e.getSceneX(), e.getSceneY());
        //     gc.stroke();
        // });

        // pane.getChildren().add(canvas);
        // primaryStage.setScene(scene);
        // primaryStage.show();

    }
    
}
