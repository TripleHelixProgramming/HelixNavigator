package org.team2363.helixnavigator.document;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

import org.team2363.helixnavigator.global.Standards.DefaultRobotConfiguration;
import org.team2363.helixtrajectory.SwerveDrive;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

@JSONSerializable
public class HRobotConfiguration {
    
    private final IntegerProperty teamNumber = new SimpleIntegerProperty(this, "teamNumber", DefaultRobotConfiguration.TEAM_NUMBER);
    private final DoubleProperty bumperLength = new SimpleDoubleProperty(this, "bumperLength", DefaultRobotConfiguration.BUMPER_LENGTH);
    private final DoubleProperty bumperWidth = new SimpleDoubleProperty(this, "bumperWidth", DefaultRobotConfiguration.BUMPER_WIDTH);
    private final DoubleProperty wheelHorizontalDistance = new SimpleDoubleProperty(this, "wheelHorizontalDistance", DefaultRobotConfiguration.WHEEL_HORIZONTAL_DISTANCE);
    private final DoubleProperty wheelVerticalDistance = new SimpleDoubleProperty(this, "wheelVerticalDistance", DefaultRobotConfiguration.WHEEL_VERTICAL_DISTANCE);
    private final DoubleProperty mass = new SimpleDoubleProperty(this, "mass", DefaultRobotConfiguration.MASS);
    private final DoubleProperty momentOfInertia = new SimpleDoubleProperty(this, "momentOfInertia", DefaultRobotConfiguration.MOMENT_OF_INERTIA);
    private final DoubleProperty motorMaxAngularSpeed = new SimpleDoubleProperty(this, "motorMaxSpeed", DefaultRobotConfiguration.MOTOR_MAX_ANGULAR_SPEED);
    private final DoubleProperty motorMaxTorque = new SimpleDoubleProperty(this, "motorMaxTorque", DefaultRobotConfiguration.MOTOR_MAX_TORQUE);
    private final DoubleProperty wheelRadius = new SimpleDoubleProperty(this, "wheelRadius", DefaultRobotConfiguration.WHEEL_RADIUS);

    @DeserializedJSONConstructor
    public HRobotConfiguration() {
    }

    public final IntegerProperty teamNumberProperty() {
        return teamNumber;
    }

    @DeserializedJSONTarget
    public final void setTeamNumber(@DeserializedJSONObjectValue(key = "team_number") int value) {
        teamNumber.set(value);
    }

    @SerializedJSONObjectValue(key = "team_number")
    public final int getTeamNumber() {
        return teamNumber.get();
    }

    public final DoubleProperty bumperLengthProperty() {
        return bumperLength;
    }

    @DeserializedJSONTarget
    public final void setBumperLength(@DeserializedJSONObjectValue(key = "bumper_length") double value) {
        bumperLength.set(value);
    }

    @SerializedJSONObjectValue(key = "bumper_length")
    public final double getBumperLength() {
        return bumperLength.get();
    }

    public final DoubleProperty bumperWidthProperty() {
        return bumperWidth;
    }

    @DeserializedJSONTarget
    public final void setBumperWidth(@DeserializedJSONObjectValue(key = "bumper_width") double value) {
        bumperWidth.set(value);
    }

    @SerializedJSONObjectValue(key = "bumper_width")
    public final double getBumperWidth() {
        return bumperWidth.get();
    }

    public final DoubleProperty wheelHorizontalDistanceProperty() {
        return wheelHorizontalDistance;
    }

    @DeserializedJSONTarget
    public final void setWheelHorizontalDistance(@DeserializedJSONObjectValue(key = "wheel_horizontal_distance") double value) {
        wheelHorizontalDistance.set(value);
    }

    @SerializedJSONObjectValue(key = "wheel_horizontal_distance")
    public final double getWheelHorizontalDistance() {
        return wheelHorizontalDistance.get();
    }

    public final DoubleProperty wheelVerticalDistanceProperty() {
        return wheelVerticalDistance;
    }

    @DeserializedJSONTarget
    public final void setWheelVerticalDistance(@DeserializedJSONObjectValue(key = "wheel_vertical_distance") double value) {
        wheelVerticalDistance.set(value);
    }

    @SerializedJSONObjectValue(key = "wheel_vertical_distance")
    public final double getWheelVerticalDistance() {
        return wheelVerticalDistance.get();
    }

    public final DoubleProperty massProperty() {
        return mass;
    }

    @DeserializedJSONTarget
    public final void setMass(@DeserializedJSONObjectValue(key = "mass") double value) {
        mass.set(value);
    }

    @SerializedJSONObjectValue(key = "mass")
    public final double getMass() {
        return mass.get();
    }

    public final DoubleProperty momentOfInertiaProperty() {
        return momentOfInertia;
    }

    @DeserializedJSONTarget
    public final void setMomentOfInertia(@DeserializedJSONObjectValue(key = "moment_of_inertia") double value) {
        momentOfInertia.set(value);
    }

    @SerializedJSONObjectValue(key = "moment_of_inertia")
    public final double getMomentOfInertia() {
        return momentOfInertia.get();
    }

    public final DoubleProperty motorMaxAngularSpeedProperty() {
        return motorMaxAngularSpeed;
    }

    @DeserializedJSONTarget
    public final void setMotorMaxAngularSpeed(@DeserializedJSONObjectValue(key = "motor_max_angular_speed") double value) {
        motorMaxAngularSpeed.set(value);
    }

    @SerializedJSONObjectValue(key = "motor_max_angular_speed")
    public final double getMotorMaxAngularSpeed() {
        return motorMaxAngularSpeed.get();
    }

    public final DoubleProperty motorMaxTorqueProperty() {
        return motorMaxTorque;
    }

    @DeserializedJSONTarget
    public final void setMotorMaxTorque(@DeserializedJSONObjectValue(key = "motor_max_torque") double value) {
        motorMaxTorque.set(value);
    }

    @SerializedJSONObjectValue(key = "motor_max_torque")
    public final double getMotorMaxTorque() {
        return motorMaxTorque.get();
    }

    public final DoubleProperty wheelRadiusProperty() {
        return wheelRadius;
    }

    @DeserializedJSONTarget
    public final void setWheelRadius(@DeserializedJSONObjectValue(key = "wheel_radius") double value) {
        wheelRadius.set(value);
    }

    @SerializedJSONObjectValue(key = "wheel_radius")
    public final double getWheelRadius() {
        return wheelRadius.get();
    }

    /**
     * Populates the settings in this {@code HRobotConfiguration} with the settings from
     * a given configuration.
     * 
     * @param otherConfiguration the configuration to pull values from
     */
    public void importConfiguration(HRobotConfiguration otherConfiguration) {
        setTeamNumber(otherConfiguration.getTeamNumber());
        setBumperLength(otherConfiguration.getBumperLength());
        setBumperWidth(otherConfiguration.getBumperWidth());
        setWheelHorizontalDistance(otherConfiguration.getWheelHorizontalDistance());
        setWheelVerticalDistance(otherConfiguration.getWheelVerticalDistance());
        setMass(otherConfiguration.getMass());
        setMomentOfInertia(otherConfiguration.getMomentOfInertia());
        setMotorMaxAngularSpeed(otherConfiguration.getMotorMaxAngularSpeed());
        setMotorMaxTorque(otherConfiguration.getMotorMaxTorque());
        setWheelRadius(otherConfiguration.getWheelRadius());
    }

    public SwerveDrive toDrive() {
        return new SwerveDrive(getWheelHorizontalDistance(), getWheelVerticalDistance(),
                getBumperLength(), getBumperWidth(), getMass(), getMomentOfInertia(),
                getMotorMaxAngularSpeed(), getMotorMaxTorque(), getWheelRadius());
    }
}