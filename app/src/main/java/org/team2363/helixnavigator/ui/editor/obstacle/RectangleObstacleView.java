package org.team2363.helixnavigator.ui.editor.obstacle;

import org.team2363.helixnavigator.document.obstacle.HRectangleObstacle;
import org.team2363.helixnavigator.global.Standards;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class RectangleObstacleView extends ObstacleView {

    private final HRectangleObstacle rectangleObstacle;

    private final Rectangle rectangle = new Rectangle();

    private final Translate originTranslate = new Translate();
    private final Rotate rotation = new Rotate();
    private final Translate centerTranslate = new Translate();

    public RectangleObstacleView(HRectangleObstacle rectangleObstacle) {
        super(rectangleObstacle);

        this.rectangleObstacle = rectangleObstacle;

        rectangle.setStroke(Color.ORANGE);
        rectangle.setStrokeWidth(0.0);
        rectangle.setStrokeType(StrokeType.OUTSIDE);
        rectangle.setFill(Standards.OBSTACLE_COLOR);

        pane.getChildren().addAll(rectangle);

        originTranslate.xProperty().bind(this.rectangleObstacle.lengthProperty().multiply(zoomScaleProperty()).multiply(-0.5));
        originTranslate.yProperty().bind(this.rectangleObstacle.widthProperty().multiply(zoomScaleProperty().multiply(-0.5)));
        rotation.angleProperty().bind(this.rectangleObstacle.rotateAngleProperty().multiply(-180 / Math.PI));
        centerTranslate.xProperty().bind(this.rectangleObstacle.centerXProperty().multiply(zoomScaleProperty()));
        centerTranslate.yProperty().bind(this.rectangleObstacle.centerYProperty().multiply(zoomScaleProperty()).negate());
        pane.getTransforms().addAll(centerTranslate, rotation, originTranslate);

        rectangle.widthProperty().bind(this.rectangleObstacle.lengthProperty().multiply(zoomScaleProperty()));
        rectangle.heightProperty().bind(this.rectangleObstacle.widthProperty().multiply(zoomScaleProperty()));

        updateSelected(rectangleObstacle.isSelected());
    }

    @Override
    protected void updateSelected(boolean isSelected) {
        rectangle.setStrokeWidth(isSelected ? 2.0 : 0.0);
    }
}
