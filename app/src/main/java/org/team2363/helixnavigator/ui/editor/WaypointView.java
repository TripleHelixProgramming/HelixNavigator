package org.team2363.helixnavigator.ui.editor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class WaypointView extends StackPane {

    private final Circle waypointCircle = new Circle(5.0);
    private final Circle selectionCircle = new Circle(8.0);

    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);

    public WaypointView() {

        waypointCircle.setFill(Color.PURPLE);
        selectionCircle.setFill(Color.ORANGE);

        getChildren().addAll(selectionCircle, waypointCircle);
    }

    public final BooleanProperty selectedProperty() {
        return selected;
    }

    public final void setSelected(boolean value) {
        selected.set(value);
    }

    public final boolean getSelected() {
        return selected.get();
    }
}
