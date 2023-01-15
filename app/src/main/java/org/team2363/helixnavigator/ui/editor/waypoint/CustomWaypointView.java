package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.timeline.HCustomWaypoint;
import org.team2363.helixnavigator.global.Standards;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;

public class CustomWaypointView extends WaypointView {

    private final RobotView robotView = new RobotView();

    private final HCustomWaypoint customWaypoint;
    
    public CustomWaypointView(HCustomWaypoint customWaypoint) {
        super(customWaypoint);

        this.customWaypoint = customWaypoint;

        setWaypointFill(Color.GREEN);

        robotView.getView().getTransforms().add(centerTranslate);
        robotView.headingProperty().bind(this.customWaypoint.headingProperty());
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
            customWaypoint.setHeading(angle * (-Math.PI/180));
        });

        if (customWaypoint.isHeadingConstrained()) {
            showRobot();
        } else {
            hideRobot();
        }
        customWaypoint.headingConstrainedProperty().addListener((obsVal, wasConstr, isContr) -> {
            if (isContr) {
                showRobot();
            } else {
                hideRobot();
            }
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

    private void hideRobot() {
        robotView.getView().setOpacity(0.0);
        robotView.getView().setMouseTransparent(true);
    }

    private void showRobot() {
        robotView.getView().setOpacity(1.0);
        robotView.getView().setMouseTransparent(false);
    }
}