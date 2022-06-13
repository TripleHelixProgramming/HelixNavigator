package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.global.Standards;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.shape.Line;

public class HardWaypointView extends WaypointView {

    private final Line cross1 = new Line(-4, 4, 4, -4);
    private final Line cross2 = new Line(-4, -4, 4, 4);
    private final RobotView robotView = new RobotView();

    private final BooleanProperty robotShown = new SimpleBooleanProperty(this, "robotShown", true);

    private final HHardWaypoint hardWaypoint;

    public HardWaypointView(HHardWaypoint hardWaypoint) {
        super(hardWaypoint);

        this.hardWaypoint = hardWaypoint;
        cross1.setStrokeWidth(3);
        cross2.setStrokeWidth(3);
        cross1.setStroke(Standards.COLOR_PALETTE[4]);
        cross2.setStroke(Standards.COLOR_PALETTE[4]);

        robotView.getView().getTransforms().add(centerTranslate);
        robotView.headingProperty().bind(this.hardWaypoint.headingProperty());
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
            hardWaypoint.setHeading(angle * (-Math.PI/180));
        });
        robotShown.addListener((obsVal, wasShown, isShown) -> {
            if (isShown) {
                showRobot();
            } else {
                hideRobot();
            }
        });

        waypointPane.getChildren().addAll(cross1, cross2);
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

    public final BooleanProperty robotShownProperty() {
        return robotShown;
    }

    public RobotView getRobotView() {
        return robotView;
    }

    public final void setRobotShown(boolean value) {
        robotShown.set(value);
    }

    public final boolean isRobotShown() {
        return robotShown.get();
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