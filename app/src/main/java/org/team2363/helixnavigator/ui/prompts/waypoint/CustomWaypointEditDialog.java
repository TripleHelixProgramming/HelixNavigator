package org.team2363.helixnavigator.ui.prompts.waypoint;

import static org.team2363.helixnavigator.global.Standards.ExportedUnits.ANGULAR_SPEED_UNIT;
import static org.team2363.helixnavigator.global.Standards.ExportedUnits.SPEED_UNIT;

import java.util.List;

import javax.measure.quantity.Speed;

import org.team2363.helixnavigator.document.timeline.HCustomWaypoint;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedAngularSpeed;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedSpeed;
import org.team2363.lib.ui.validation.IntegerTextField;
import org.team2363.lib.ui.validation.UnitTextField;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import si.uom.quantity.AngularSpeed;

public class CustomWaypointEditDialog extends WaypointEditDialog {
    
    private HCustomWaypoint customWaypoint;
    private HCustomWaypoint backupCustomWaypoint;

    private final Text headingText = new Text("Heading:");
    private final HeadingTextField headingTextField = new HeadingTextField();
    private final Text velocityXText = new Text("Velocity X:");
    private final UnitTextField<Speed> velocityXTextField = new UnitTextField<>(SPEED_UNIT, SupportedSpeed.UNITS);
    private final Text velocityYText = new Text("Velocity Y:");
    private final UnitTextField<Speed> velocityYTextField = new UnitTextField<>(SPEED_UNIT, SupportedSpeed.UNITS);
    private final Text angularVelocityText = new Text("Angular Velocity:");
    private final UnitTextField<AngularSpeed> angularVelocityTextField = new UnitTextField<>(ANGULAR_SPEED_UNIT, SupportedAngularSpeed.UNITS);

    private final Text activeConstraints = new Text("Active Constraints:");
    private final CheckBox xConstrainedCheckBox = new CheckBox("X");
    private final CheckBox yConstrainedCheckBox = new CheckBox("Y");
    private final CheckBox headingConstrainedCheckBox = new CheckBox("Heading");
    private final CheckBox velocityXConstrainedCheckBox = new CheckBox("Velocity X");
    private final CheckBox velocityYConstrainedCheckBox = new CheckBox("Velocity Y");
    private final CheckBox velocityMagnitudeConstrainedCheckBox = new CheckBox("Velocity Magnitude");
    private final CheckBox angularVelocityConstrainedCheckBox = new CheckBox("Angular Velocity");
    private final Text controlIntervalCountText = new Text("Control Interval Count:");
    private final IntegerTextField controlIntervalCountTextField = new IntegerTextField(0, Integer.MAX_VALUE);

    public CustomWaypointEditDialog(HCustomWaypoint customWaypoint) {
        super(customWaypoint, new HCustomWaypoint());
        this.customWaypoint = (HCustomWaypoint) waypoint;
        this.backupCustomWaypoint = (HCustomWaypoint) backupWaypoint;
        GridPane.setConstraints(headingText, 0, ADDITIONAL_PROPERTIES_ROW);
        GridPane.setConstraints(headingTextField, 1, ADDITIONAL_PROPERTIES_ROW);
        GridPane.setConstraints(velocityXText, 0, ADDITIONAL_PROPERTIES_ROW + 1);
        GridPane.setConstraints(velocityXTextField, 1, ADDITIONAL_PROPERTIES_ROW + 1);
        GridPane.setConstraints(velocityYText, 0, ADDITIONAL_PROPERTIES_ROW + 2);
        GridPane.setConstraints(velocityYTextField, 1, ADDITIONAL_PROPERTIES_ROW + 2);
        GridPane.setConstraints(angularVelocityText, 0, ADDITIONAL_PROPERTIES_ROW + 3);
        GridPane.setConstraints(angularVelocityTextField, 1, ADDITIONAL_PROPERTIES_ROW + 3);
        GridPane.setConstraints(controlIntervalCountText, 0, ADDITIONAL_PROPERTIES_ROW + 4);
        GridPane.setConstraints(controlIntervalCountTextField, 1, ADDITIONAL_PROPERTIES_ROW + 4);

        addGridItems(List.of(headingText, headingTextField, velocityXText, velocityXTextField,
                velocityYText, velocityYTextField, angularVelocityText, angularVelocityTextField,
                controlIntervalCountText, controlIntervalCountTextField));

        vBox.getChildren().add(ADDITIONAL_NODES_ROW    , activeConstraints);
        vBox.getChildren().add(ADDITIONAL_NODES_ROW + 1, xConstrainedCheckBox);
        vBox.getChildren().add(ADDITIONAL_NODES_ROW + 2, yConstrainedCheckBox);
        vBox.getChildren().add(ADDITIONAL_NODES_ROW + 3, headingConstrainedCheckBox);
        vBox.getChildren().add(ADDITIONAL_NODES_ROW + 4, velocityXConstrainedCheckBox);
        vBox.getChildren().add(ADDITIONAL_NODES_ROW + 5, velocityYConstrainedCheckBox);
        vBox.getChildren().add(ADDITIONAL_NODES_ROW + 6, velocityMagnitudeConstrainedCheckBox);
        vBox.getChildren().add(ADDITIONAL_NODES_ROW + 7, angularVelocityConstrainedCheckBox);

        backupWaypoint();
        // Set ui to values of HWaypoint:
        initializeTextFields();
        // Now bind the Waypoint to the values of the ui
        bindWaypoint();
    }

    @Override
    protected void initializeTextFields() {
        super.initializeTextFields();
        headingTextField.setValue(customWaypoint.getHeading());
        velocityXTextField.setValue(customWaypoint.getVelocityX());
        velocityYTextField.setValue(customWaypoint.getVelocityY());
        angularVelocityTextField.setValue(customWaypoint.getAngularVelocity());
        xConstrainedCheckBox.setSelected(customWaypoint.isXConstrained());
        yConstrainedCheckBox.setSelected(customWaypoint.isYConstrained());
        headingConstrainedCheckBox.setSelected(customWaypoint.isHeadingConstrained());
        velocityXConstrainedCheckBox.setSelected(customWaypoint.isVelocityXConstrained());
        velocityYConstrainedCheckBox.setSelected(customWaypoint.isVelocityYConstrained());
        velocityMagnitudeConstrainedCheckBox.setSelected(customWaypoint.isVelocityMagnitudeConstrained());
        angularVelocityConstrainedCheckBox.setSelected(customWaypoint.isAngularVelocityConstrained());
        controlIntervalCountTextField.setValue(customWaypoint.getControlIntervalCount());
    }

    @Override
    protected void unbindWaypoint() {
        super.unbindWaypoint();
        customWaypoint.headingProperty().unbind();
        customWaypoint.velocityXProperty().unbind();
        customWaypoint.velocityYProperty().unbind();
        customWaypoint.angularVelocityProperty().unbind();
        customWaypoint.xConstrainedProperty().unbind();
        customWaypoint.yConstrainedProperty().unbind();
        customWaypoint.headingConstrainedProperty().unbind();
        customWaypoint.velocityXConstrainedProperty().unbind();
        customWaypoint.velocityYConstrainedProperty().unbind();
        customWaypoint.velocityMagnitudeConstrainedProperty().unbind();
        customWaypoint.angularVelocityConstrainedProperty().unbind();
        customWaypoint.controlIntervalCountProperty().unbind();
    }

    @Override
    protected void bindWaypoint() {
        super.bindWaypoint();
        customWaypoint.headingProperty().bind(headingTextField.valueProperty());
        customWaypoint.velocityXProperty().bind(velocityXTextField.valueProperty());
        customWaypoint.velocityYProperty().bind(velocityYTextField.valueProperty());
        customWaypoint.angularVelocityProperty().bind(angularVelocityTextField.valueProperty());
        customWaypoint.xConstrainedProperty().bind(xConstrainedCheckBox.selectedProperty());
        customWaypoint.yConstrainedProperty().bind(yConstrainedCheckBox.selectedProperty());
        customWaypoint.headingConstrainedProperty().bind(headingConstrainedCheckBox.selectedProperty());
        customWaypoint.velocityXConstrainedProperty().bind(velocityXConstrainedCheckBox.selectedProperty());
        customWaypoint.velocityYConstrainedProperty().bind(velocityYConstrainedCheckBox.selectedProperty());
        customWaypoint.velocityMagnitudeConstrainedProperty().bind(velocityMagnitudeConstrainedCheckBox.selectedProperty());
        customWaypoint.angularVelocityConstrainedProperty().bind(angularVelocityConstrainedCheckBox.selectedProperty());
        customWaypoint.controlIntervalCountProperty().bind(controlIntervalCountTextField.valueProperty());
    }

    @Override
    protected void backupWaypoint() {
        super.backupWaypoint();
        backupCustomWaypoint.setHeading(customWaypoint.getHeading());
        backupCustomWaypoint.setVelocityX(customWaypoint.getVelocityX());
        backupCustomWaypoint.setVelocityY(customWaypoint.getVelocityY());
        backupCustomWaypoint.setAngularVelocity(customWaypoint.getAngularVelocity());
        backupCustomWaypoint.setXConstrained(customWaypoint.isXConstrained());
        backupCustomWaypoint.setYConstrained(customWaypoint.isYConstrained());
        backupCustomWaypoint.setHeadingConstrained(customWaypoint.isHeadingConstrained());
        backupCustomWaypoint.setVelocityXConstrained(customWaypoint.isVelocityXConstrained());
        backupCustomWaypoint.setVelocityYConstrained(customWaypoint.isVelocityYConstrained());
        backupCustomWaypoint.setVelocityMagnitudeConstrained(customWaypoint.isVelocityMagnitudeConstrained());
        backupCustomWaypoint.setAngularVelocityConstrained(customWaypoint.isAngularVelocityConstrained());
        backupCustomWaypoint.setControlIntervalCount(customWaypoint.getControlIntervalCount());
    }

    @Override
    protected void restoreBackup() {
        super.restoreBackup();
        customWaypoint.setHeading(backupCustomWaypoint.getHeading());
        customWaypoint.setVelocityX(backupCustomWaypoint.getVelocityX());
        customWaypoint.setVelocityY(backupCustomWaypoint.getVelocityY());
        customWaypoint.setAngularVelocity(backupCustomWaypoint.getAngularVelocity());
        customWaypoint.setXConstrained(backupCustomWaypoint.isXConstrained());
        customWaypoint.setYConstrained(backupCustomWaypoint.isYConstrained());
        customWaypoint.setHeadingConstrained(backupCustomWaypoint.isHeadingConstrained());
        customWaypoint.setVelocityXConstrained(backupCustomWaypoint.isVelocityXConstrained());
        customWaypoint.setVelocityYConstrained(backupCustomWaypoint.isVelocityYConstrained());
        customWaypoint.setVelocityMagnitudeConstrained(backupCustomWaypoint.isVelocityMagnitudeConstrained());
        customWaypoint.setAngularVelocityConstrained(backupCustomWaypoint.isAngularVelocityConstrained());
        customWaypoint.setControlIntervalCount(backupCustomWaypoint.getControlIntervalCount());
    }
}