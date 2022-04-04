package org.team2363.helixnavigator.ui.prompts.obstacle;

import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class PolygonObstacleEditDialog extends ObstacleEditDialog {

    private final HPolygonObstacle polygonObstacle;
    private final HPolygonObstacle backupPolygonObstacle;

    private final PolygonPointsTableView pointsTable = new PolygonPointsTableView();
    private final Button addPointButton = new Button("+");
    private final Button removePointButton = new Button("-");
    private final HBox pointsButtons = new HBox(addPointButton, removePointButton);

    public PolygonObstacleEditDialog(HPolygonObstacle polygonObstacle) {
        super(polygonObstacle, new HPolygonObstacle());
        this.polygonObstacle = (HPolygonObstacle) obstacle;
        this.backupPolygonObstacle = (HPolygonObstacle) backupObstacle;

        pointsTable.setItems(this.polygonObstacle.getPoints());

        addPointButton.setOnAction(event -> {
            this.polygonObstacle.getPoints().add(new HPolygonPoint());
        });

        removePointButton.setOnAction(event -> {
            this.polygonObstacle.getPoints().remove(pointsTable.getSelectionModel().getSelectedIndex());
        });

        vBox.getChildren().add(ADDITIONAL_NODES_ROW, pointsTable);
        vBox.getChildren().add(ADDITIONAL_NODES_ROW + 1, pointsButtons);

        backupObstacle();
        // Set ui to values of Obstacle:
        initializeTextFields();
        // Now bind the Obstacle to the values of the ui
        bindObstacle();
    }

    @Override
    protected void initializeTextFields() {
        super.initializeTextFields();
    }

    @Override
    protected void unbindObstacle() {
        super.unbindObstacle();
    }

    @Override
    protected void bindObstacle() {
        super.bindObstacle();
    }

    @Override
    protected void backupObstacle() {
        super.backupObstacle();
    }

    @Override
    protected void restoreBackup() {
        super.restoreBackup();
    }
}