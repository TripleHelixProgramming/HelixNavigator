package org.team2363.helixnavigator.global;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.unmodifiableObservableList;

import java.io.File;
import java.util.regex.Pattern;

import javax.measure.Unit;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.Angle;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;

import org.team2363.lib.unit.CustomUnits;
import org.team2363.lib.unit.MomentOfInertia;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import si.uom.NonSI;
import si.uom.SI;
import si.uom.quantity.AngularSpeed;
import si.uom.quantity.Torque;
import systems.uom.common.USCustomary;
import tech.units.indriya.unit.Units;

public class Standards {

    /**
     * The name of this application
     */
    public static final String APPLICATION_NAME = "HelixNavigator";
    /**
     * The current version of this application
     */
    public static final String APPLICATION_VERSION = "0.0.1";
    /**
     * The location of the "Documents" folder for the user
     */
    public static final File USER_DOCUMENTS_DIR = new File(System.getProperty("user.home") + File.separator + "Documents"); // TODO: make this work for every platform
    /**
     * One or more characters; a-z, 0-9, space, hyphen, underscore allowed
     */
    public static final Pattern VALID_NAME = Pattern.compile("[a-z0-9 _\\-]+", Pattern.CASE_INSENSITIVE);
    /**
     * Up to 50 characters
     */
    public static final int MAX_NAME_LENGTH = 50;
    public static final String DEFAULT_FIELD_IMAGE = "2022: Rapid React";
    public static final double DEFAULT_OBSTACLE_SAFETY_DISTANCE = 5.0;
    public static final Color BACKGROUND_COLOR = Color.gray(0.85);
    public static final Color[] COLOR_PALETTE = {
        Color.valueOf("#343233"),
        Color.valueOf("#399e5a"),
        Color.valueOf("#47e3ff"),
        Color.valueOf("#0E73AA"),
        Color.valueOf("#ed1c24")
    };
    public static final Color OBSTACLE_COLOR = Standards.COLOR_PALETTE[2].deriveColor(0, 1, 1, .6);

    public static final class DefaultRobotConfiguration {
        public static final int TEAM_NUMBER = 2363;
        public static final double BUMPER_LENGTH = 37;
        public static final double BUMPER_WIDTH = 37;
        public static final double WHEEL_HORIZONTAL_DISTANCE = 24;
        public static final double WHEEL_VERTICAL_DISTANCE = 24;
        public static final double MASS = 100;
        public static final double MOMENT_OF_INERTIA = 100;
        public static final double MOTOR_MAX_ANGULAR_SPEED = 1000;
        public static final double MOTOR_MAX_TORQUE = 1000;
    }

    public static final double MIN_HEADING = -180;
    public static final double MAX_HEADING = 180;
    public static final double HEADING_LOCK_RADIUS = 5;

    public static final class ExportedUnits {
        public static final Unit<Time> TIME_UNIT = Units.SECOND;
        public static final Unit<Length> LENGTH_UNIT = Units.METRE;
        public static final Unit<Mass> MASS_UNIT = Units.KILOGRAM;
        public static final Unit<Speed> SPEED_UNIT = Units.METRE_PER_SECOND;
        public static final Unit<Acceleration> ACCELERATION_UNIT = Units.METRE_PER_SQUARE_SECOND;
        public static final Unit<Angle> ANGLE_UNIT = Units.RADIAN;
        public static final Unit<AngularSpeed> ANGULAR_SPEED_UNIT = SI.RADIAN_PER_SECOND;
        public static final Unit<Torque> TORQUE_UNIT = CustomUnits.NEWTON_METRE;
        public static final Unit<MomentOfInertia> MOMENT_OF_INERTIA_UNIT = CustomUnits.KILOGRAM_SQUARE_METRE;
    }

    // @SuppressWarnings("unchecked")
    public static final class SupportedUnits {
        public static enum SupportedTime {
            SECOND(Units.SECOND);

            public final Unit<Time> unit;
            private SupportedTime(Unit<Time> unit) {
                this.unit = unit;
            }
            @Override
            public String toString() {
                return unit.toString();
            }
            public static final ObservableList<SupportedTime> units =
                    unmodifiableObservableList(observableArrayList(values()));
        }
        public static enum SupportedLength {
            METRE(Units.METRE),
            FOOT(USCustomary.FOOT),
            INCH(USCustomary.INCH);

            public final Unit<Length> unit;
            private SupportedLength(Unit<Length> unit) {
                this.unit = unit;
            }
            @Override
            public String toString() {
                return unit.toString();
            }
            public static final ObservableList<SupportedLength> units =
                    unmodifiableObservableList(observableArrayList(values()));
        }
        public static enum SupportedMass {
            KILOGRAM(Units.KILOGRAM),
            POUND(USCustomary.POUND),
            GRAM(Units.GRAM),
            OUNCE(USCustomary.OUNCE);

            public final Unit<Mass> unit;
            private SupportedMass(Unit<Mass> unit) {
                this.unit = unit;
            }
            @Override
            public String toString() {
                return unit.toString();
            }
            public static final ObservableList<SupportedMass> units =
                    unmodifiableObservableList(observableArrayList(values()));
        }
        public static enum SupportedSpeed {
            METRE_PER_SECOND(Units.METRE_PER_SECOND),
            FOOT_PER_SECOND(USCustomary.FOOT_PER_SECOND),
            KILOMETRE_PER_HOUR(Units.KILOMETRE_PER_HOUR),
            MILE_PER_HOUR(USCustomary.MILE_PER_HOUR);

            public final Unit<Speed> unit;
            private SupportedSpeed(Unit<Speed> unit) {
                this.unit = unit;
            }
            @Override
            public String toString() {
                return unit.toString();
            }
            public static final ObservableList<SupportedSpeed> units =
                    unmodifiableObservableList(observableArrayList(values()));
        }
        public static enum SupportedAcceleration {
            METRE_PER_SECOND(Units.METRE_PER_SQUARE_SECOND),
            FOOT_PER_SECOND(CustomUnits.FOOT_PER_SQUARE_SECOND);

            public final Unit<Acceleration> unit;
            private SupportedAcceleration(Unit<Acceleration> unit) {
                this.unit = unit;
            }
            @Override
            public String toString() {
                return unit.toString();
            }
            public static final ObservableList<SupportedAcceleration> units =
                    unmodifiableObservableList(observableArrayList(values()));
        }
        public static enum SupportedAngle {
            DEGREE_ANGLE(NonSI.DEGREE_ANGLE),
            RADIAN(Units.RADIAN),
            REVOLUTION(NonSI.REVOLUTION);

            public final Unit<Angle> unit;
            private SupportedAngle(Unit<Angle> unit) {
                this.unit = unit;
            }
            @Override
            public String toString() {
                return unit.toString();
            }
            public static final ObservableList<SupportedAngle> units =
                    unmodifiableObservableList(observableArrayList(values()));
        }
        public static enum SupportedAngularSpeed {
            REVOLUTION_PER_MINUTE(CustomUnits.REVOLUTION_PER_MINUTE),
            RADIAN_PER_SECOND(SI.RADIAN_PER_SECOND),
            DEGREE_ANGLE_PER_SECOND(CustomUnits.DEGREE_ANGLE_PER_SECOND);

            public final Unit<AngularSpeed> unit;
            private SupportedAngularSpeed(Unit<AngularSpeed> unit) {
                this.unit = unit;
            }
            @Override
            public String toString() {
                return unit.toString();
            }
            public static final ObservableList<SupportedAngularSpeed> units =
                    unmodifiableObservableList(observableArrayList(values()));
        }
        public static enum SupportedTorque {
            NEWTON_METRE(CustomUnits.NEWTON_METRE),
            POUND_FOOT(CustomUnits.POUND_FOOT);

            public final Unit<Torque> unit;
            private SupportedTorque(Unit<Torque> unit) {
                this.unit = unit;
            }
            @Override
            public String toString() {
                return unit.toString();
            }
            public static final ObservableList<SupportedTorque> units =
                    unmodifiableObservableList(observableArrayList(values()));
        }
        public static enum SupportedMomentOfInertia {
            KILOGRAM_SQUARE_METRE(CustomUnits.KILOGRAM_SQUARE_METRE),
            POUND_FOOT(CustomUnits.POUND_SQUARE_INCH);

            public final Unit<MomentOfInertia> unit;
            private SupportedMomentOfInertia(Unit<MomentOfInertia> unit) {
                this.unit = unit;
            }
            @Override
            public String toString() {
                return unit.toString();
            }
            public static final ObservableList<SupportedMomentOfInertia> units =
                    unmodifiableObservableList(observableArrayList(values()));
        }
    }

    public static void main(String[] args) {
    }
}