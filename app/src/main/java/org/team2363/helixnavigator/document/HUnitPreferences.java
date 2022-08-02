package org.team2363.helixnavigator.document;

import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedAcceleration;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedAngle;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedAngularSpeed;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedLength;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedMass;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedMomentOfInertia;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedSpeed;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedTime;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedTorque;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;
import com.jlbabilino.json.JSONEntry.JSONType;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HUnitPreferences {

    private final ObjectProperty<SupportedTime> timeUnit = new SimpleObjectProperty<>(this, "timeUnit", SupportedTime.values()[0]);
    private final ObjectProperty<SupportedLength> lengthUnit = new SimpleObjectProperty<>(this, "lengthUnit", SupportedLength.values()[0]);
    private final ObjectProperty<SupportedMass> massUnit = new SimpleObjectProperty<>(this, "massUnit", SupportedMass.values()[0]);
    private final ObjectProperty<SupportedSpeed> speedUnit = new SimpleObjectProperty<>(this, "speedUnit", SupportedSpeed.values()[0]);
    private final ObjectProperty<SupportedAcceleration> accelerationUnit = new SimpleObjectProperty<>(this, "accelerationUnit", SupportedAcceleration.values()[0]);
    private final ObjectProperty<SupportedAngle> angleUnit = new SimpleObjectProperty<>(this, "angleUnit", SupportedAngle.values()[0]);
    private final ObjectProperty<SupportedAngularSpeed> angularSpeedUnit = new SimpleObjectProperty<>(this, "angularSpeedUnit", SupportedAngularSpeed.values()[0]);
    private final ObjectProperty<SupportedTorque> torqueUnit = new SimpleObjectProperty<>(this, "torqueUnit", SupportedTorque.values()[0]);
    private final ObjectProperty<SupportedMomentOfInertia> momentOfInertiaUnit = new SimpleObjectProperty<>(this, "momentOfInertiaUnit", SupportedMomentOfInertia.values()[0]);

    @DeserializedJSONConstructor
    public HUnitPreferences() {
    }

    public ObjectProperty<SupportedTime> timeUnitProperty() {
        return timeUnit;
    }
    @DeserializedJSONTarget
    public void setTimeUnit(@DeserializedJSONObjectValue(key = "time_unit") SupportedTime value) {
        timeUnit.set(value);
    }
    @SerializedJSONObjectValue(key = "time_unit")
    public SupportedTime getTimeUnit() {
        return timeUnit.get();
    }

    public ObjectProperty<SupportedLength> lengthUnitProperty() {
        return lengthUnit;
    }
    @DeserializedJSONTarget
    public void setLengthUnit(@DeserializedJSONObjectValue(key = "length_unit") SupportedLength value) {
        lengthUnit.set(value);
    }
    @SerializedJSONObjectValue(key = "length_unit")
    public SupportedLength getLengthUnit() {
        return lengthUnit.get();
    }
    
    public ObjectProperty<SupportedMass> massUnitProperty() {
        return massUnit;
    }
    @DeserializedJSONTarget
    public void setMassUnit(@DeserializedJSONObjectValue(key = "mass_unit") SupportedMass value) {
        massUnit.set(value);
    }
    @SerializedJSONObjectValue(key = "mass_unit")
    public SupportedMass getMassUnit() {
        return massUnit.get();
    }

    public ObjectProperty<SupportedSpeed> speedUnitProperty() {
        return speedUnit;
    }
    @DeserializedJSONTarget
    public void setSpeedUnit(@DeserializedJSONObjectValue(key = "speed_unit") SupportedSpeed value) {
        speedUnit.set(value);
    }
    @SerializedJSONObjectValue(key = "speed_unit")
    public SupportedSpeed getSpeedUnit() {
        return speedUnit.get();
    }

    public ObjectProperty<SupportedAcceleration> accelerationUnitProperty() {
        return accelerationUnit;
    }
    @DeserializedJSONTarget
    public void setAccelerationUnit(@DeserializedJSONObjectValue(key = "acceleration_unit") SupportedAcceleration value) {
        accelerationUnit.set(value);
    }
    @SerializedJSONObjectValue(key = "acceleration_unit")
    public SupportedAcceleration getAccelerationUnit() {
        return accelerationUnit.get();
    }

    public ObjectProperty<SupportedAngle> angleUnitProperty() {
        return angleUnit;
    }
    @DeserializedJSONTarget
    public void setAngleUnit(@DeserializedJSONObjectValue(key = "angle_unit") SupportedAngle value) {
        angleUnit.set(value);
    }
    @SerializedJSONObjectValue(key = "angle_unit")
    public SupportedAngle getAngleUnit() {
        return angleUnit.get();
    }

    public ObjectProperty<SupportedAngularSpeed> angularSpeedUnitProperty() {
        return angularSpeedUnit;
    }
    @DeserializedJSONTarget
    public void setAngularSpeedUnit(@DeserializedJSONObjectValue(key = "angular_speed_unit") SupportedAngularSpeed value) {
        angularSpeedUnit.set(value);
    }
    @SerializedJSONObjectValue(key = "angular_speed_unit")
    public SupportedAngularSpeed getAngularSpeedUnit() {
        return angularSpeedUnit.get();
    }

    public ObjectProperty<SupportedTorque> torqueUnitProperty() {
        return torqueUnit;
    }
    @DeserializedJSONTarget
    public void setTorqueUnit(@DeserializedJSONObjectValue(key = "torque_unit") SupportedTorque value) {
        torqueUnit.set(value);
    }
    @SerializedJSONObjectValue(key = "torque_unit")
    public SupportedTorque getTorqueUnit() {
        return torqueUnit.get();
    }

    public ObjectProperty<SupportedMomentOfInertia> momentOfInertiaUnitProperty() {
        return momentOfInertiaUnit;
    }
    @DeserializedJSONTarget
    public void setMomentOfInertiaUnit(@DeserializedJSONObjectValue(key = "moment_of_inertia_unit") SupportedMomentOfInertia value) {
        momentOfInertiaUnit.set(value);
    }
    @SerializedJSONObjectValue(key = "moment_of_inertia_unit")
    public SupportedMomentOfInertia getMomentOfInertiaUnit() {
        return momentOfInertiaUnit.get();
    }

    public void importPreferences(HUnitPreferences otherPreferences) {
        setTimeUnit(otherPreferences.getTimeUnit());
        setLengthUnit(otherPreferences.getLengthUnit());
        setMassUnit(otherPreferences.getMassUnit());
        setSpeedUnit(otherPreferences.getSpeedUnit());
        setAccelerationUnit(otherPreferences.getAccelerationUnit());
        setAngleUnit(otherPreferences.getAngleUnit());
        setAngularSpeedUnit(otherPreferences.getAngularSpeedUnit());
        setTorqueUnit(otherPreferences.getTorqueUnit());
        setMomentOfInertiaUnit(otherPreferences.getMomentOfInertiaUnit());
    }
}