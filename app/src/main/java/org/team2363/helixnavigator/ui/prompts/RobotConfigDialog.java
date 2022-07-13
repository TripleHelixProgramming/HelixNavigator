package org.team2363.helixnavigator.ui.prompts;

import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;

import org.team2363.helixnavigator.document.HRobotConfiguration;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.helixnavigator.global.Standards.ExportedUnits;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedAngularSpeed;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedLength;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedMass;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedMomentOfInertia;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedTorque;
import org.team2363.lib.ui.validation.IntegerTextField;
import org.team2363.lib.ui.validation.UnitTextField;
import org.team2363.lib.unit.MomentOfInertia;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import si.uom.quantity.AngularSpeed;
import si.uom.quantity.Torque;

public class RobotConfigDialog {

    private final HRobotConfiguration backupRobotConfiguration;
    private final HRobotConfiguration robotConfiguration;

    private final Text teamNumberText = new Text("Team Number:");
    private final IntegerTextField teamNumberTextField = new IntegerTextField();
    private final Text bumperLengthText = new Text("Bumper Length:");
    private final UnitTextField<Length> bumperLengthTextField = new UnitTextField<>(ExportedUnits.LENGTH_UNIT, SupportedLength.UNITS);
    private final Text bumperWidthText = new Text("Bumper Width:");
    private final UnitTextField<Length> bumperWidthTextField = new UnitTextField<>(ExportedUnits.LENGTH_UNIT, SupportedLength.UNITS);
    private final Text wheelHorizontalDistanceText = new Text("Wheel Horizontal Distance:");
    private final UnitTextField<Length> wheelHorizontalDistanceTextField = new UnitTextField<>(ExportedUnits.LENGTH_UNIT, SupportedLength.UNITS);
    private final Text wheelVerticalDistanceText = new Text("Wheel Vertical Distance:");
    private final UnitTextField<Length> wheelVerticalDistanceTextField = new UnitTextField<>(ExportedUnits.LENGTH_UNIT, SupportedLength.UNITS);
    private final Text massText = new Text("Mass:");
    private final UnitTextField<Mass> massTextField = new UnitTextField<>(ExportedUnits.MASS_UNIT, SupportedMass.UNITS);
    private final Text momentOfInertiaText = new Text("Moment of Inertia:");
    private final UnitTextField<MomentOfInertia> momentOfInertiaTextField = new UnitTextField<>(ExportedUnits.MOMENT_OF_INERTIA_UNIT, SupportedMomentOfInertia.UNITS);
    private final Text motorMaxAngularSpeedText = new Text("Motor Max Angular Speed:");
    private final UnitTextField<AngularSpeed> motorMaxAngularSpeedTextField = new UnitTextField<>(ExportedUnits.ANGULAR_SPEED_UNIT, SupportedAngularSpeed.UNITS);
    private final Text motorMaxTorqueText = new Text("Motor Max Torque:");
    private final UnitTextField<Torque> motorMaxTorqueTextField = new UnitTextField<>(ExportedUnits.TORQUE_UNIT, SupportedTorque.UNITS);
    private final GridPane propertyGrid = new GridPane();
    private final Button okButton = new Button("OK");
    private final Button cancelButton = new Button("Cancel");
    private final HBox buttonBox = new HBox(okButton, cancelButton);
    private final VBox vBox = new VBox(propertyGrid, buttonBox);
    private final Scene scene = new Scene(vBox);
    private final Stage stage = new Stage();

    public RobotConfigDialog(HRobotConfiguration robotConfiguration) {
        this.robotConfiguration = robotConfiguration;

        okButton.setDefaultButton(true);
        GridPane.setConstraints(teamNumberText, 0, 0);
        GridPane.setConstraints(teamNumberTextField, 1, 0);
        GridPane.setConstraints(bumperLengthText, 0, 1);
        GridPane.setConstraints(bumperLengthTextField, 1, 1);
        GridPane.setConstraints(bumperWidthText, 0, 2);
        GridPane.setConstraints(bumperWidthTextField, 1, 2);
        GridPane.setConstraints(wheelHorizontalDistanceText, 0, 3);
        GridPane.setConstraints(wheelHorizontalDistanceTextField, 1, 3);
        GridPane.setConstraints(wheelVerticalDistanceText, 0, 4);
        GridPane.setConstraints(wheelVerticalDistanceTextField, 1, 4);
        GridPane.setConstraints(massText, 0, 5);
        GridPane.setConstraints(massTextField, 1, 5);
        GridPane.setConstraints(momentOfInertiaText, 0, 6);
        GridPane.setConstraints(momentOfInertiaTextField, 1, 6);
        GridPane.setConstraints(motorMaxAngularSpeedText, 0, 7);
        GridPane.setConstraints(motorMaxAngularSpeedTextField, 1, 7);
        GridPane.setConstraints(motorMaxTorqueText, 0, 8);
        GridPane.setConstraints(motorMaxTorqueTextField, 1, 8);
        propertyGrid.setHgap(10);
        propertyGrid.setVgap(10);
        propertyGrid.getChildren().addAll(teamNumberText, teamNumberTextField, bumperLengthText,
                bumperLengthTextField, bumperWidthText, bumperWidthTextField, wheelHorizontalDistanceText,
                wheelHorizontalDistanceTextField, wheelVerticalDistanceText, wheelVerticalDistanceTextField,
                massText, massTextField, momentOfInertiaText, momentOfInertiaTextField, motorMaxAngularSpeedText,
                motorMaxAngularSpeedTextField, motorMaxTorqueText, motorMaxTorqueTextField);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        stage.setScene(scene);
        stage.setResizable(false);

        bumperLengthTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        bumperWidthTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        wheelHorizontalDistanceTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        wheelVerticalDistanceTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        massTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        momentOfInertiaTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        motorMaxAngularSpeedTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        motorMaxTorqueTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);


        backupRobotConfiguration = new HRobotConfiguration();
        backupRobotConfiguration.importConfiguration(robotConfiguration);

        initializeTextFields();
        bindConfiguration();

        okButton.setOnAction(event -> {
            backupRobotConfiguration.importConfiguration(robotConfiguration);
            stage.close();
        });
        cancelButton.setOnAction(event -> {
            restoreBackup();
            stage.close();
        });
        stage.setOnCloseRequest(event -> {
            restoreBackup();
        });
    }

    /**
     * Initializes the text fields with the values of {@code robotConfiguration}
     */
    private void initializeTextFields() {
        teamNumberTextField.setValue(robotConfiguration.getTeamNumber());
        bumperLengthTextField.setValue(robotConfiguration.getBumperLength());
        bumperWidthTextField.setValue(robotConfiguration.getBumperWidth());
        wheelHorizontalDistanceTextField.setValue(robotConfiguration.getWheelHorizontalDistance());
        wheelVerticalDistanceTextField.setValue(robotConfiguration.getWheelVerticalDistance());
        massTextField.setValue(robotConfiguration.getMass());
        momentOfInertiaTextField.setValue(robotConfiguration.getMomentOfInertia());
        motorMaxAngularSpeedTextField.setValue(robotConfiguration.getMotorMaxAngularSpeed());
        motorMaxTorqueTextField.setValue(robotConfiguration.getMotorMaxTorque());
    }

    /**
     * Unbinds all values in {@code robotConfiguration}
     */
    private void unbindConfiguration() {
        robotConfiguration.teamNumberProperty().unbind();
        robotConfiguration.bumperLengthProperty().unbind();
        robotConfiguration.bumperWidthProperty().unbind();
        robotConfiguration.wheelHorizontalDistanceProperty().unbind();
        robotConfiguration.wheelVerticalDistanceProperty().unbind();
        robotConfiguration.massProperty().unbind();
        robotConfiguration.momentOfInertiaProperty().unbind();
        robotConfiguration.motorMaxAngularSpeedProperty().unbind();
        robotConfiguration.motorMaxTorqueProperty().unbind();
    }

    /**
     * Binds the values in {@code robotConfiguration} to the values of the text fields
     */
    private void bindConfiguration() {
        robotConfiguration.teamNumberProperty().bind(teamNumberTextField.valueProperty());
        robotConfiguration.bumperLengthProperty().bind(bumperLengthTextField.valueProperty());
        robotConfiguration.bumperWidthProperty().bind(bumperWidthTextField.valueProperty());
        robotConfiguration.wheelHorizontalDistanceProperty().bind(wheelHorizontalDistanceTextField.valueProperty());
        robotConfiguration.wheelVerticalDistanceProperty().bind(wheelVerticalDistanceTextField.valueProperty());
        robotConfiguration.massProperty().bind(massTextField.valueProperty());
        robotConfiguration.momentOfInertiaProperty().bind(momentOfInertiaTextField.valueProperty());
        robotConfiguration.motorMaxAngularSpeedProperty().bind(motorMaxAngularSpeedTextField.valueProperty());
        robotConfiguration.motorMaxTorqueProperty().bind(motorMaxTorqueTextField.valueProperty());
    }

    private void restoreBackup() {
        unbindConfiguration();
        robotConfiguration.importConfiguration(backupRobotConfiguration);
        initializeTextFields();
        bindConfiguration();
    }

    public void show() {
        if (stage.isShowing()) {
            stage.requestFocus();
        } else {
            stage.show();
        }
    }

    public void close() {
        stage.close();
    }
}