package org.team2363.helixnavigator.ui.editor.obstacle;

import org.team2363.helixnavigator.document.obstacle.HCircleObstacle;
import org.team2363.helixnavigator.global.Standards;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;

public class CircleObstacleView extends ObstacleView {

    private final HCircleObstacle circleObstacle;

    private final Circle circle = new Circle();

    private final Translate centerTranslate = new Translate();

    public CircleObstacleView(HCircleObstacle circleObstacle) {
        super(circleObstacle);

        this.circleObstacle = circleObstacle;

        circle.setStroke(Color.ORANGE);
        circle.setStrokeWidth(0.0);
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setFill(Standards.OBSTACLE_COLOR);

        pane.getChildren().addAll(circle);

        centerTranslate.xProperty().bind(this.circleObstacle.centerXProperty().multiply(zoomScaleProperty()));
        centerTranslate.yProperty().bind(this.circleObstacle.centerYProperty().multiply(zoomScaleProperty()).negate());
        pane.getTransforms().addAll(centerTranslate);

        circle.radiusProperty().bind(this.circleObstacle.radiusProperty().multiply(zoomScaleProperty()));

        updateSelected(circleObstacle.isSelected());
    }

    @Override
    protected void updateSelected(boolean isSelected) {
        circle.setStrokeWidth(isSelected ? 2.0 : 0.0);
    }
}
