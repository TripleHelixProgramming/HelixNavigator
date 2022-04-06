package org.team2363.helixnavigator.ui.editor.obstacle;

import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;
import org.team2363.helixnavigator.global.Standards;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class PolygonPointView {

    private final HPolygonPoint polygonPoint;
    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);

    private final Pane pane = new Pane();
    private final Circle circle = new Circle(6.0);
    
    public PolygonPointView(HPolygonPoint polygonPoint) {
        this.polygonPoint = polygonPoint;

        circle.setFill(Standards.COLOR_PALETTE[0]);
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setStrokeWidth(0.0);
        circle.setStroke(Color.ORANGE);

        pane.setPickOnBounds(false);
        pane.getChildren().add(circle);

        circle.translateXProperty().bind(this.polygonPoint.xProperty().multiply(zoomScale));
        circle.translateYProperty().bind(this.polygonPoint.yProperty().multiply(zoomScale).negate());

        this.polygonPoint.selectedProperty().addListener((currentValue, wasSelected, isSelected) -> {
            updateSelected(isSelected);
        });
    }

    private void updateSelected(boolean isSelected) {
        circle.setStrokeWidth(isSelected ? 2.0 : 0.0);
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

    public Node getView() {
        return pane;
    }
}