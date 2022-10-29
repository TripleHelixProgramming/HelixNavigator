package org.team2363.helixnavigator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SplitPane sp = new SplitPane();
        final StackPane sp1 = new StackPane();
        sp1.getChildren().add(new Button("Button One"));
        final StackPane sp2 = new StackPane();
        sp2.getChildren().add(new Button("Button Two"));
        final StackPane sp3 = new StackPane();
        sp3.getChildren().add(new Button("Button Three"));
        sp.getItems().addAll(sp1, sp2, sp3);
        sp.setDividerPositions(0.3f, 0.6f, 0.9f);
        primaryStage.setScene(new Scene(sp));
        primaryStage.show();
    }
}