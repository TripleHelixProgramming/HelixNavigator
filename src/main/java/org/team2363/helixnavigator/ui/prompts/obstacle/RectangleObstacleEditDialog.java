package org.team2363.helixnavigator.ui.prompts.obstacle;

import org.team2363.helixnavigator.document.obstacle.HRectangleObstacle;

public class RectangleObstacleEditDialog extends ObstacleEditDialog {

    private final HRectangleObstacle rectangleObstacle;
    private final HRectangleObstacle backupRectangleObstacle;
    
    public RectangleObstacleEditDialog(HRectangleObstacle rectangleObstacle) {
        super(rectangleObstacle, new HRectangleObstacle());
        this.rectangleObstacle = (HRectangleObstacle) obstacle;
        this.backupRectangleObstacle = (HRectangleObstacle) backupObstacle;

        backupObstacle();
        // Set ui to values of Obstacle:
        initializeTextFields();
        // Now bind the Obstacle to the values of the ui
        bindObstacle();
    }
}