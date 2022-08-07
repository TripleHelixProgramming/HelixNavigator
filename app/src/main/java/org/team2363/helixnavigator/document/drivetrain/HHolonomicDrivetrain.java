package org.team2363.helixnavigator.document.drivetrain;

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

public abstract class HHolonomicDrivetrain extends HDrivetrain {

    @JSONSerializable(JSONType.STRING)
    @JSONDeserializable({JSONType.STRING})
    public static enum HolonomicDrivetrainType {
        SWERVE;

        @DeserializedJSONConstructor
        public static HolonomicDrivetrainType forName(@DeserializedJSONEntry String name) {
            return valueOf(name.toUpperCase());
        }

        @SerializedJSONEntry
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    @Override
    public DrivetrainType getDrivetrainType() {
        return DrivetrainType.HOLONOMIC;
    }
    @Override
    public boolean isHolonomicDrivetrain() {
        return true;
    }

    @SerializedJSONObjectValue(key = "holonomic_drivetrain_type")
    public abstract HolonomicDrivetrainType getHolonomicDrivetrainType();
    public boolean isSwerveHolonomicDrivetrain() {
        return false;
    }

    public static HHolonomicDrivetrain defaultHolonomicDrivetrain() {
        return HSwerveDrivetrain.defaultSwerveDrivetrain();
    }
    
    @DeserializedJSONDeterminer
    public static Class<? extends HHolonomicDrivetrain> holonomicDrivetrainDeterminer(
            @DeserializedJSONObjectValue(key = "holonomic_drivetrain_type") HolonomicDrivetrainType holonomicDrivetrainType)
            throws JSONDeserializerException {
        switch (holonomicDrivetrainType) {
            case SWERVE:
                return HSwerveDrivetrain.class;
            default:
                throw new JSONDeserializerException("Cannot have a null holonomic drivetrain type");
        }
    }
}
