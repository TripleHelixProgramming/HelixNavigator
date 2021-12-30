package org.team2363.helixnavigator;

import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.document.field.image.HReferenceFieldImage;
import org.team2363.helixnavigator.global.DefaultFieldImages;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        System.out.println(DefaultFieldImages.listNames());
        HFieldImage fieldReference = new HReferenceFieldImage("2020: Infinite Recharge");
        ImageView imageView = new ImageView(fieldReference.getImage());
        primaryStage.setScene(new Scene(new Pane(imageView)));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}