package org.team2363.helixnavigator.ui.editor.waypoint;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;

public class RobotView {

    private final Rectangle robotRectangle = new Rectangle();
    private final Circle headingDot = new Circle();
    private final Pane robotPane = new Pane(robotRectangle, headingDot);
    private final Pane pane = new Pane(robotPane);

    private final Rotate headingRotate = new Rotate();

    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);
    private final DoubleProperty bumperLength = new SimpleDoubleProperty(this, "bumperLength", 30.0);
    private final DoubleProperty bumperWidth = new SimpleDoubleProperty(this, "bumperWidth", 30.0);
    private final DoubleProperty heading = new SimpleDoubleProperty(this, "heading", 0.0);

    public RobotView() {
        robotRectangle.setFill(Color.TRANSPARENT);
        robotRectangle.setMouseTransparent(true);
        robotRectangle.setStrokeLineCap(StrokeLineCap.ROUND);
        robotRectangle.setStrokeType(StrokeType.INSIDE);
        robotRectangle.strokeWidthProperty().bind(zoomScale.multiply(4));
        robotRectangle.setStroke(Color.DEEPSKYBLUE);
        robotRectangle.setArcWidth(6);
        robotRectangle.setArcHeight(6);
        robotRectangle.xProperty().bind(bumperLength.multiply(-0.5).multiply(zoomScale));
        robotRectangle.yProperty().bind(bumperWidth.multiply(-0.5).multiply(zoomScale));
        robotRectangle.widthProperty().bind(bumperLength.multiply(zoomScale));
        robotRectangle.heightProperty().bind(bumperWidth.multiply(zoomScale));
        headingDot.setFill(Color.DEEPSKYBLUE);
        headingDot.radiusProperty().bind(zoomScale.multiply(4));
        headingDot.centerXProperty().bind(bumperLength.multiply(0.5).multiply(zoomScale).subtract(robotRectangle.strokeWidthProperty().multiply(0.5)));
        headingRotate.angleProperty().bind(heading);
        robotPane.getTransforms().add(headingRotate);
        robotPane.setPickOnBounds(false);
        pane.setPickOnBounds(false);
    }

    public final DoubleProperty zoomScaleProperty() {
        return zoomScale;
    }

    public final void setZoomScale(double value) {
        zoomScale.set(value);
    }

    public final double getZoomScale() {
        return zoomScale.get();
    }

    public final DoubleProperty bumperLengthProperty() {
        return bumperLength;
    }

    public final void setBumperLength(double value) {
        bumperLength.set(value);
    }

    public final double getBumperLength() {
        return bumperLength.get();
    }

    public final DoubleProperty bumperWidthProperty() {
        return bumperWidth;
    }

    public final void setBumperWidth(double value) {
        bumperWidth.set(value);
    }

    public final double getBumperWidth() {
        return bumperWidth.get();
    }

    public final DoubleProperty headingProperty() {
        return heading;
    }

    public final void setHeading(double value) {
        heading.set(value);
    }

    public final double getHeading() {
        return heading.get();
    }

    public Pane getView() {
        return pane;
    }
}