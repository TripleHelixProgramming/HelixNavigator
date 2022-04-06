package org.team2363.helixnavigator.ui.prompts;

import org.team2363.helixnavigator.document.HRobotConfiguration;
import org.team2363.lib.ui.validation.IntegerTextField;
import org.team2363.lib.ui.validation.MathExpressionTextField;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RobotConfigDialog {

    private final HRobotConfiguration backupRobotConfiguration;
    private final HRobotConfiguration robotConfiguration;

    private final Text teamNumberText = new Text("Team Number:");
    private final IntegerTextField teamNumberTextField = new IntegerTextField();
    private final Text bumperLengthText = new Text("Bumper Length:");
    private final MathExpressionTextField bumperLengthTextField = new MathExpressionTextField();
    private final Text bumperWidthText = new Text("Bumper Width:");
    private final MathExpressionTextField bumperWidthTextField = new MathExpressionTextField();
    private final Text wheelHorizontalDistanceText = new Text("Wheel Horizontal Distance:");
    private final MathExpressionTextField wheelHorizontalDistanceTextField = new MathExpressionTextField();
    private final Text wheelVerticalDistanceText = new Text("Wheel Vertical Distance:");
    private final MathExpressionTextField wheelVerticalDistanceTextField = new MathExpressionTextField();
    private final Text massText = new Text("Mass:");
    private final MathExpressionTextField massTextField = new MathExpressionTextField();
    private final Text momentOfInertiaText = new Text("Moment of Inertia:");
    private final MathExpressionTextField momentOfInertiaTextField = new MathExpressionTextField();
    private final Text motorMaxAngularSpeedText = new Text("Motor Max Angular Speed:");
    private final MathExpressionTextField motorMaxAngularSpeedTextField = new MathExpressionTextField();
    private final Text motorMaxTorqueText = new Text("Motor Max Torque:");
    private final MathExpressionTextField motorMaxTorqueTextField = new MathExpressionTextField();
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