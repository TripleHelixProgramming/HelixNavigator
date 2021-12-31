package org.team2363.helixnavigator;

import com.jlbabilino.json.JSONSerializer;

import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint.WaypointType;
import org.team2363.helixnavigator.ui.prompts.WaypointEditDialog;
import org.team2363.lib.ui.prompts.FilteredTextField;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        HWaypoint waypoint = new HWaypoint(WaypointType.SOFT);
        System.out.println(JSONSerializer.serializeString(waypoint));
        WaypointEditDialog dialog = new WaypointEditDialog(waypoint);
        dialog.showAndWait();
        System.out.println(JSONSerializer.serializeString(waypoint));
        // HardWaypointView view1 = new HardWaypointView();
        // view1.setX(100);
        // view1.setY(100);
        // HardWaypointView view2 = new HardWaypointView();
        // view2.setX(150);
        // view2.setY(100);
        // view2.setSelected(true);
        // SoftWaypointView view3 = new SoftWaypointView();
        // view3.setX(100);
        // view3.setY(150);
        // SoftWaypointView view4 = new SoftWaypointView();
        // view4.setX(150);
        // view4.setY(150);
        // view4.setSelected(true);

        // primaryStage.setScene(new Scene(new Pane(view1, view2, view3, view4)));
        // primaryStage.setWidth(1000);
        // primaryStage.setHeight(1000);
        // primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}