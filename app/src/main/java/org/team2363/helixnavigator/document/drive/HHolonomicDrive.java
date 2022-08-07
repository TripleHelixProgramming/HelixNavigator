package org.team2363.helixnavigator.document.drive;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.DeserializedJSONEntry;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONEntry;
import com.jlbabilino.json.SerializedJSONObjectValue;

public abstract class HHolonomicDrive extends HDrive {

    @JSONSerializable(JSONType.STRING)
    @JSONDeserializable({JSONType.STRING})
    public static enum HolonomicDriveType {
        SWERVE;

        @DeserializedJSONConstructor
        public static HolonomicDriveType forName(@DeserializedJSONEntry String name) {
            return valueOf(name.toUpperCase());
        }

        @SerializedJSONEntry
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    @Override
    public DriveType getDriveType() {
        return DriveType.HOLONOMIC;
    }
    @Override
    public boolean isHolonomicDrive() {
        return true;
    }

    @SerializedJSONObjectValue(key = "holonomic_drive_type")
    public abstract HolonomicDriveType getHolonomicDriveType();
    public boolean isSwerveHolonomicDrive() {
        return false;
    }

    public static HHolonomicDrive defaultHolonomicDrive() {
        return HSwerveDrive.defaultSwerveDrive();
    }
    
    @DeserializedJSONDeterminer
    public static Class<? extends HHolonomicDrive> holonomicDriveDeterminer(@DeserializedJSONObjectValue(key = "holonomic_drive_type") HolonomicDriveType holonomicDriveType) throws JSONDeserializerException {
        switch (holonomicDriveType) {
            case SWERVE:
                return HSwerveDrive.class;
            default:
                throw new JSONDeserializerException("Cannot have a null holonomic drive type");
        }
    }
}
