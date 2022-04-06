package org.team2363.helixnavigator.ui.editor.obstacle;

import java.util.Collection;

import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.shape.Polygon;

public class PolygonWrapper {

    private final ObjectProperty<ObservableList<HPolygonPoint>> points =
            new SimpleObjectProperty<>(this, "points", FXCollections.observableArrayList());
    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);

    private final ListChangeListener<HPolygonPoint> onPointsListChanged = this::pointsListChanged;
    private final ChangeListener<Number> updateAllListener = (currentVal, oldVal, newVal) -> updateAllPoints();

    private final Polygon polygon = new Polygon();

    public PolygonWrapper() {
        points.addListener(this::listChanged);
        zoomScale.addListener(updateAllListener);
    }

    private void listChanged(ObservableValue<? extends ObservableList<HPolygonPoint>> currentPoints,
            ObservableList<HPolygonPoint> oldPoints,
            ObservableList<HPolygonPoint> newPoints) {
        unloadList(oldPoints);
        loadList(newPoints);
    }
    private void unloadList(ObservableList<HPolygonPoint> oldPoints) {
        if (oldPoints != null) {
            detachListeners(oldPoints);
            polygon.getPoints().clear();
        }
    }
    private void loadList(ObservableList<HPolygonPoint> newPoints) {
        if (newPoints != null) {
            initializePointsList();
            attachPointsListListeners();
            newPoints.addListener(onPointsListChanged);
        }
    }

    private void pointsListChanged(ListChangeListener.Change<? extends HPolygonPoint> change) {
        while (change.next()) {
            if (change.wasRemoved()) {
                detachListeners(change.getRemoved());
            }
        }
        detachListeners(getPoints());
        initializePointsList();
        attachPointsListListeners();
    }
    private void initializePointsList() {
        polygon.getPoints().clear();
        for (HPolygonPoint point : getPoints()) {
            polygon.getPoints().add(point.getX() * getZoomScale());
            polygon.getPoints().add(-point.getY() * getZoomScale());
        }
    }
    private void detachListeners(Collection<? extends HPolygonPoint> list) {
        for (HPolygonPoint point : list) {
            point.xProperty().removeListener(updateAllListener);
            point.yProperty().removeListener(updateAllListener);
        }
    }
    private void attachPointsListListeners() {
        for (HPolygonPoint point : getPoints()) {
            point.xProperty().addListener(updateAllListener);
            point.yProperty().addListener(updateAllListener);
        }
    }
    private void updateAllPoints() {
        // System.out.println("Update All");
        int pointCount = getPoints() != null ? getPoints().size() : 0;
        for (int i = 0; i < pointCount; i++) {
            polygon.getPoints().set(2 * i, getPoints().get(i).getX() * getZoomScale());
            polygon.getPoints().set(2 * i + 1, -getPoints().get(i).getY() * getZoomScale());
        }
    }

    public ObjectProperty<ObservableList<HPolygonPoint>> pointsProperty() {
        return points;
    }
    public void setPoints(ObservableList<HPolygonPoint> value) {
        points.set(value);
    }
    public ObservableList<HPolygonPoint> getPoints() {
        return points.get();
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

    public Polygon getPolygon() {
        return polygon;
    }
}