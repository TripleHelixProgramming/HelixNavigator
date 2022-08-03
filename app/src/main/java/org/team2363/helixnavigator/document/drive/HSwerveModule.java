package org.team2363.helixnavigator.document.drive;

import org.team2363.helixnavigator.global.Standards.DefaultSwerveModule;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HSwerveModule {

    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);
    private final DoubleProperty wheelRadius = new SimpleDoubleProperty(this, "wheelRadius", DefaultSwerveModule.WHEEL_RADIUS);
    private final DoubleProperty wheelMaxAngularVelocity = new SimpleDoubleProperty(this, "wheelMaxAngularVelocity", DefaultSwerveModule.WHEEL_MAX_ANGULAR_VELOCITY);
    private final DoubleProperty wheelMaxTorque = new SimpleDoubleProperty(this, "wheelMaxTorque", DefaultSwerveModule.WHEEL_MAX_TORQUE);

    @DeserializedJSONConstructor
    public HSwerveModule() {
    }

    public DoubleProperty xProperty() {
        return x;
    }
    @DeserializedJSONTarget
    public void setX(@DeserializedJSONObjectValue(key = "x") double value) {
        x.set(value);
    }
    @SerializedJSONObjectValue(key = "x")
    public double getX() {
        return x.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }
    @DeserializedJSONTarget
    public void setY(@DeserializedJSONObjectValue(key = "y") double value) {
        y.set(value);
    }
    @SerializedJSONObjectValue(key = "y")
    public double getY() {
        return y.get();
    }

    public DoubleProperty wheelRadiusProperty() {
        return wheelRadius;
    }
    @DeserializedJSONTarget
    public void setWheelRadius(@DeserializedJSONObjectValue(key = "wheel_radius") double value) {
        wheelRadius.set(value);
    }
    @SerializedJSONObjectValue(key = "wheel_radius")
    public double getWheelRadius() {
        return wheelRadius.get();
    }

    public DoubleProperty wheelMaxAngularVelocityProperty() {
        return wheelMaxAngularVelocity;
    }
    @DeserializedJSONTarget
    public void setWheelMaxAngularVelocity(@DeserializedJSONObjectValue(key = "wheel_max_angular_velocity") double value) {
        wheelMaxAngularVelocity.set(value);
    }
    @SerializedJSONObjectValue(key = "wheel_max_angular_velocity")
    public double getWheelMaxAngularVelocity() {
        return wheelMaxAngularVelocity.get();
    }

    public DoubleProperty wheelMaxTorqueProperty() {
        return wheelMaxTorque;
    }
    @DeserializedJSONTarget
    public void setWheelMaxTorque(@DeserializedJSONObjectValue(key = "wheel_max_torque") double value) {
        wheelMaxTorque.set(value);
    }
    @SerializedJSONObjectValue(key = "wheel_max_torque")
    public double getWheelMaxTorque() {
        return wheelMaxTorque.get();
    }
}