package org.team2363.helixnavigator.ui.editor.obstacle;

import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import org.team2363.helixnavigator.global.Standards;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class PolygonObstacleView extends ObstacleView {

    private final HPolygonObstacle polygonObstacle;

    private final PolygonWrapper polygonWrapper = new PolygonWrapper();
    private final Polygon polygon;

    public PolygonObstacleView(HPolygonObstacle polygonObstacle) {
        super(polygonObstacle);

        this.polygonObstacle = polygonObstacle;

        polygonWrapper.setPoints(this.polygonObstacle.getPoints());
        polygonWrapper.zoomScaleProperty().bind(zoomScaleProperty());
        polygon = polygonWrapper.getPolygon();
        polygon.setFill(Standards.OBSTACLE_COLOR);
        polygon.setStroke(Color.ORANGE);
        polygon.setStrokeWidth(0.0);
        polygon.setStrokeType(StrokeType.OUTSIDE);

        pane.getChildren().add(polygon);
        
        updateSelected(polygonObstacle.isSelected());
    }

    @Override
    protected void updateSelected(boolean isSelected) {
        polygon.setStrokeWidth(isSelected ? 2.0 : 0.0);
    }
}