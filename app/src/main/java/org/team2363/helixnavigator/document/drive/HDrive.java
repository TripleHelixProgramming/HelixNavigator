package org.team2363.helixnavigator.document.drive;

import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;
import org.team2363.helixnavigator.global.Standards.DefaultBumpers;
import org.team2363.helixnavigator.global.Standards.DefaultDrive;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.DeserializedJSONEntry;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONEntry;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public abstract class HDrive {

    @JSONSerializable(JSONType.STRING)
    @JSONDeserializable({JSONType.STRING})
    public static enum DriveType {
        HOLONOMIC;

        @DeserializedJSONConstructor
        public static DriveType forName(@DeserializedJSONEntry String name) {
            return valueOf(name.toUpperCase());
        }

        @SerializedJSONEntry
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    private final DoubleProperty mass = new SimpleDoubleProperty(this, "mass", DefaultDrive.MASS);
    private final DoubleProperty momentOfInertia = new SimpleDoubleProperty(this, "momentOfInertia", DefaultDrive.MOMENT_OF_INERTIA);
    private final ObjectProperty<HObstacle> bumpers = new SimpleObjectProperty<>(this, "bumpers", defaultBumpers());

    @SerializedJSONObjectValue(key = "drive_type")
    public abstract DriveType getDriveType();
    public boolean isHolonomicDrive() {
        return false;
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
    public double getMomentOfInertia() {
        return momentOfInertia.get();
    }

    public final ObjectProperty<HObstacle> bumpersProperty() {
        return bumpers;
    }
    public final void setBumpers(HObstacle value) {
        bumpers.set(value);
    }
    public final HObstacle getBumpers() {
        return bumpers.get();
    }

    public static HObstacle defaultBumpers() {
        HPolygonObstacle defaultBumpers = new HPolygonObstacle();
        defaultBumpers.setName("Default Bumpers");
        HPolygonPoint corner0 = new HPolygonPoint();
        corner0.setX(+DefaultBumpers.LENGTH / 2);
        corner0.setY(+DefaultBumpers.WIDTH / 2);
        HPolygonPoint corner1 = new HPolygonPoint();
        corner1.setX(-DefaultBumpers.LENGTH / 2);
        corner1.setY(+DefaultBumpers.WIDTH / 2);
        HPolygonPoint corner2 = new HPolygonPoint();
        corner2.setX(-DefaultBumpers.LENGTH / 2);
        corner2.setY(-DefaultBumpers.WIDTH / 2);
        HPolygonPoint corner3 = new HPolygonPoint();
        corner3.setX(+DefaultBumpers.LENGTH / 2);
        corner3.setY(-DefaultBumpers.WIDTH / 2);
        defaultBumpers.getPoints().addAll(corner0, corner1, corner2, corner3);
        return defaultBumpers;
    }

    public static HDrive defaultDrive() {
        return HHolonomicDrive.defaultHolonomicDrive();
    }

    @DeserializedJSONDeterminer
    public static Class<? extends HDrive> driveDeterminer(@DeserializedJSONObjectValue(key = "drive_type") DriveType driveType) throws JSONDeserializerException {
        switch (driveType) {
            case HOLONOMIC:
                return HHolonomicDrive.class;
            // case DIFFERENTIAL:
            //     return HDifferentialDrive.class;
            default:
                throw new JSONDeserializerException("Cannot have a null drive type");
        }
    }
}