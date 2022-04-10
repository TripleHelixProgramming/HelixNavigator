package org.team2363.helixnavigator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        ListView<String> lv = new ListView<>();
        lv.getItems().addAll("Hamburger", "Apple", "Roger", "Preemium");
        lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        System.out.println("Index: " + lv.getSelectionModel().getSelectedIndex());
        lv.getSelectionModel().selectedIndexProperty().addListener((a, b, c) -> {System.out.println(c);});
        primaryStage.setScene(new Scene(lv));
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }
}