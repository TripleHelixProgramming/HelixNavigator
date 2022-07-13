package org.team2363.helixnavigator.ui.editor.obstacle;

import org.team2363.helixnavigator.document.obstacle.HObstacle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;

public abstract class ObstacleView {

    private final HObstacle obstacle;

    protected final Pane pane = new Pane();

    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);

    protected ObstacleView(HObstacle obstacle) {
        this.obstacle = obstacle;

        pane.setPickOnBounds(false);

        this.obstacle.selectedProperty().addListener((currentValue, wasSelected, isSelected) -> {
            updateSelected(isSelected);
        });
    }

    protected abstract void updateSelected(boolean isSelected);

    public final DoubleProperty zoomScaleProperty() {
        return zoomScale;
    }

    public final void setZoomScale(double value) {
        zoomScale.set(value);
    }

    public final double getZoomScale() {
        return zoomScale.get();
    }

    public Pane getView() {
        return pane;
    }
}