package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.helixnavigator.ui.prompts.waypoint.WaypointEditDialog;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;

public abstract class WaypointView {

    private final HWaypoint waypoint;

    private final Circle selectionCircle = new Circle(12.0);
    private final Circle waypointCircle = new Circle(10.0);
    protected final Pane waypointPane = new Pane(selectionCircle, waypointCircle);
    protected final Pane pane = new Pane(waypointPane);

    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);

    protected final Translate centerTranslate = new Translate();

    private final MenuItem editMenuItem = new MenuItem("Edit...");
    private final ContextMenu contextMenu = new ContextMenu(editMenuItem);

    protected WaypointView(HWaypoint waypoint) {
        this.waypoint = waypoint;

        selectionCircle.setFill(Color.ORANGE);
        waypointCircle.setFill(Standards.COLOR_PALETTE[3]);
        
        waypointCircle.setStroke(Standards.COLOR_PALETTE[0]);
        waypointCircle.setStrokeType(StrokeType.INSIDE);
        waypointCircle.setStrokeWidth(3.0);
        selectionCircle.setOpacity(0.0);

        pane.setOnContextMenuRequested(event -> {
            contextMenu.show(pane, event.getScreenX(), event.getScreenY());
        });
        editMenuItem.setOnAction(event -> WaypointEditDialog.dialog(this.waypoint).show());
        waypointPane.setPickOnBounds(false);
        pane.setPickOnBounds(false);

        // For y you must subtract to flip the coordinates, in graphics +y is down but in math/engineering/robotics +y is up (i think):
        centerTranslate.xProperty().bind(this.waypoint.xProperty().multiply(zoomScale));
        centerTranslate.yProperty().bind(this.waypoint.yProperty().negate().multiply(zoomScale));
        waypointPane.getTransforms().add(centerTranslate);

        updateSelected(this.waypoint.isSelected());
        this.waypoint.selectedProperty().addListener((currentValue, wasSelected, isSelected) -> {
            updateSelected(isSelected);
        });
    }

    private void updateSelected(boolean isSelected) {
        selectionCircle.setOpacity(isSelected ? 1.0 : 0.0);
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

    public Pane getWaypointView() {
        return waypointPane;
    }

    public Pane getView() {
        return pane;
    }
}