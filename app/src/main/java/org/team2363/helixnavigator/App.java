package org.team2363.helixnavigator;

import org.team2363.helixnavigator.global.DefaultResources;
import org.team2363.helixnavigator.ui.MainStage;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private MainStage mainStage;

    @Override
    public void start(Stage primaryStage) {
        DefaultResources.loadAllResources();
        mainStage = new MainStage(primaryStage);
        mainStage.show();
    }
}