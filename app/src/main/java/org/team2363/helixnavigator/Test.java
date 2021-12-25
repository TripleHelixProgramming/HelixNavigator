package org.team2363.helixnavigator;

import org.team2363.helixnavigator.global.DefaultFieldImages;
import org.team2363.helixnavigator.ui.MainStage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private MainStage mainStage;

    @Override
    public void start(Stage primaryStage) {
        Image image = DefaultFieldImages.fromName("2020");
        ImageView imageView = new ImageView(image);
        imageView.setScaleX(.2);
        imageView.setScaleY(.2);
        imageView.setX(-1000);
        imageView.setY(-350);
        Pane pane = new Pane(imageView);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}