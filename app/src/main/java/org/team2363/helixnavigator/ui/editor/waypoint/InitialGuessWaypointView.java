package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.timeline.HInitialGuessWaypoint;
import org.team2363.helixnavigator.global.Standards;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;

public class InitialGuessWaypointView extends WaypointView {

    private final RobotView robotView = new RobotView();

    private final HInitialGuessWaypoint initialGuessWaypoint;
    
    public InitialGuessWaypointView(HInitialGuessWaypoint initialGuessWaypoint) {
        super(initialGuessWaypoint);

        this.initialGuessWaypoint = initialGuessWaypoint;

        setWaypointFill(Color.SKYBLUE);

        robotView.getView().getTransforms().add(centerTranslate);
        robotView.headingProperty().bind(this.initialGuessWaypoint.headingProperty());
        robotView.zoomScaleProperty().bind(zoomScaleProperty());
        robotView.getView().setOnMouseDragged(event -> {
            // System.out.println("Dot dragged");
            double x = event.getX();
            double y = event.getY();
            double[] lockAngles = {-180, -90, 0, 90, 180};
            double lockRadius = Standards.HEADING_LOCK_RADIUS;
            double angle = Math.toDegrees(Math.atan2(y, x));
            // System.out.println(angle);
            for (double lockAngle : lockAngles) {
                if (!event.isShiftDown() && Math.abs(angle - lockAngle) <= lockRadius) {
                    if (lockAngle == -180) {
                        lockAngle = 180;
                    }
                    angle = lockAngle;
                    break;
                }
            }
            initialGuessWaypoint.setHeading(angle * (-Math.PI/180));
        });
    }

    public final DoubleProperty bumperLengthProperty() {
        return robotView.bumperLengthProperty();
    }

    public final void setBumperLength(double value) {
        robotView.setBumperLength(value);
    }

    public final double getBumperLength() {
        return robotView.getBumperLength();
    }

    public final DoubleProperty bumperWidthProperty() {
        return robotView.bumperWidthProperty();
    }

    public final void setBumperWidth(double value) {
        robotView.setBumperWidth(value);
    }

    public final double getBumperWidth() {
        return robotView.getBumperWidth();
    }

    public final DoubleProperty headingProperty() {
        return robotView.headingProperty();
    }

    public final void setHeading(double value) {
        robotView.setHeading(value);
    }

    public final double getHeading() {
        return robotView.getHeading();
    }

    public RobotView getRobotView() {
        return robotView;
    }
}