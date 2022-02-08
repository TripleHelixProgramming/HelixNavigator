package org.team2363.helixnavigator.ui.prompts.obstacle;

import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;

public class PolygonObstacleEditDialog extends ObstacleEditDialog {
    
    private final HPolygonObstacle polygonObstacle;
    private final HPolygonObstacle backupPolygonObstacle;

    public PolygonObstacleEditDialog(HPolygonObstacle polygonObstacle) {
        super(polygonObstacle, new HPolygonObstacle());
        this.polygonObstacle = (HPolygonObstacle) obstacle;
        this.backupPolygonObstacle = (HPolygonObstacle) backupObstacle;

        backupObstacle();
        // Set ui to values of Obstacle:
        initializeTextFields();
        // Now bind the Obstacle to the values of the ui
        bindObstacle();
    }
}