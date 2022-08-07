package org.team2363.helixnavigator.document;

import org.team2363.helixnavigator.document.drive.HDrive;
import org.team2363.helixnavigator.global.Standards.DefaultRobotConfiguration;
import org.team2363.helixtrajectory.SwerveDrive;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HRobotConfiguration {
    
    private final IntegerProperty teamNumber = new SimpleIntegerProperty(this, "teamNumber", DefaultRobotConfiguration.TEAM_NUMBER);
    private final ObjectProperty<HDrive> drive = new SimpleObjectProperty<>(this, "drive", HDrive.defaultDrive());

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

    public final ObjectProperty<HDrive> driveProperty() {
        return drive;
    }
    @DeserializedJSONTarget
    public final void setDrive(@DeserializedJSONObjectValue(key = "drive") HDrive value) {
        drive.set(value);
    }
    @SerializedJSONObjectValue(key = "drive")
    public final HDrive getDrive() {
        return drive.get();
    }

    public SwerveDrive toDrive() {
        // TODO: fix this
        return null;
        // return new SwerveDrive(getWheelHorizontalDistance(), getWheelVerticalDistance(),
        //         getMass(), getMomentOfInertia(),
        //         getMotorMaxAngularSpeed(), getMotorMaxTorque(), getWheelRadius(), getBumpers());
    }
}