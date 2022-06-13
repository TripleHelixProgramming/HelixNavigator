package org.team2363.helixnavigator.ui.editor.trajectory;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.ui.editor.waypoint.RobotView;

import javafx.scene.layout.Pane;

public class TrajectoryPane extends Pane {

    private final DocumentManager documentManager;

    private final SegmentsPane segmentsPane;
    private final RobotView animatedRobot;
    
    public TrajectoryPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        segmentsPane = new SegmentsPane(this.documentManager);
        animatedRobot = new RobotView();

        getChildren().add(segmentsPane);
        setPickOnBounds(false);
    }
}