package org.team2363.helixnavigator;

import org.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.document.waypoint.HSoftWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.helixnavigator.ui.MainStage;
import org.team2363.helixnavigator.ui.document.WaypointListCell;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private MainStage mainStage;

    @Override
    public void start(Stage primaryStage) {
        ObservableList<HWaypoint> items = FXCollections.<HWaypoint>observableArrayList(new HSoftWaypoint(), new HHardWaypoint());
        ListView<HWaypoint> listView = new ListView<>(items);
        listView.setCellFactory(list -> new WaypointListCell());
        Pane pane = new Pane(listView);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}