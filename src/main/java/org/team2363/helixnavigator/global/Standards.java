package org.team2363.helixnavigator.global;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.unmodifiableObservableList;

import java.io.File;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import javax.measure.Quantity;
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
import javafx.stage.FileChooser.ExtensionFilter;
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
    public static final ExtensionFilter DOCUMENT_FILE_TYPE = new ExtensionFilter("HelixNavigator Document (*.json)", "*.json");
    public static final ExtensionFilter TRAJECTORY_FILE_TYPE = new ExtensionFilter("HelixTrajectory Trajectory (*.json)", "*.json");
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
        public static final double BUMPER_LENGTH = 0.954;
        public static final double BUMPER_WIDTH = 0.903;
        public static final double WHEEL_HORIZONTAL_DISTANCE = 0.622;
        public static final double WHEEL_VERTICAL_DISTANCE = 0.572;
        public static final double MASS = 46.7;
        public static final double MOMENT_OF_INERTIA = 5.6;
        public static final double MOTOR_MAX_ANGULAR_SPEED = 70;
        public static final double MOTOR_MAX_TORQUE = 1.9;
        public static final double WHEEL_RADIUS = 0.051;
    }

    public static final double MIN_HEADING = -Math.PI;
    public static final double MAX_HEADING = Math.PI;
    public static final double HEADING_LOCK_RADIUS = 5;

    public static final DecimalFormat GUI_NUMBER_FORMAT = new DecimalFormat("#.######");

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

    public static interface SupportedUnit<T extends Quantity<T>> {
        public Unit<T> unit();
    }
    // @SuppressWarnings("unchecked")
    public static final class SupportedUnits {
        public static enum SupportedTime implements SupportedUnit<Time> {
            SECOND(Units.SECOND);

            private final Unit<Time> unit;
            private SupportedTime(Unit<Time> unit) {
                this.unit = unit;
            }
            @Override
            public Unit<Time> unit() {
                return unit;
            }
            @Override
            public String toString() {
                return name().toLowerCase();
            }
            public static final ObservableList<SupportedTime> SUPPORTED_UNITS =
                    unmodifiableObservableList(observableArrayList(values()));
            public static final ObservableList<Unit<Time>> UNITS;
            static {
                ObservableList<Unit<Time>> list = observableArrayList();
                for (SupportedTime supportedUnit : values()) {
                    list.add(supportedUnit.unit);
                }
                UNITS = unmodifiableObservableList(list);
            }
        }
        public static enum SupportedLength implements SupportedUnit<Length> {
            METRE(Units.METRE),
            FOOT(USCustomary.FOOT),
            INCH(USCustomary.INCH);

            private final Unit<Length> unit;
            private SupportedLength(Unit<Length> unit) {
                this.unit = unit;
            }
            @Override
            public Unit<Length> unit() {
                return unit;
            }
            @Override
            public String toString() {
                return name().toLowerCase();
            }
            public static final ObservableList<SupportedLength> SUPPORTED_UNITS =
                    unmodifiableObservableList(observableArrayList(values()));
            public static final ObservableList<Unit<Length>> UNITS;
            static {
                ObservableList<Unit<Length>> list = observableArrayList();
                for (SupportedLength supportedUnit : values()) {
                    list.add(supportedUnit.unit);
                }
                UNITS = unmodifiableObservableList(list);
            }
        }
        public static enum SupportedMass implements SupportedUnit<Mass> {
            KILOGRAM(Units.KILOGRAM),
            POUND(USCustomary.POUND),
            GRAM(Units.GRAM),
            OUNCE(USCustomary.OUNCE);

            private final Unit<Mass> unit;
            private SupportedMass(Unit<Mass> unit) {
                this.unit = unit;
            }
            @Override
            public Unit<Mass> unit() {
                return unit;
            }
            @Override
            public String toString() {
                return name().toLowerCase();
            }
            public static final ObservableList<SupportedMass> SUPPORTED_UNITS =
                    unmodifiableObservableList(observableArrayList(values()));
            public static final ObservableList<Unit<Mass>> UNITS;
            static {
                ObservableList<Unit<Mass>> list = observableArrayList();
                for (SupportedMass supportedUnit : values()) {
                    list.add(supportedUnit.unit);
                }
                UNITS = unmodifiableObservableList(list);
            }
        }
        public static enum SupportedSpeed implements SupportedUnit<Speed> {
            METRE_PER_SECOND(Units.METRE_PER_SECOND),
            FOOT_PER_SECOND(USCustomary.FOOT_PER_SECOND),
            KILOMETRE_PER_HOUR(Units.KILOMETRE_PER_HOUR),
            MILE_PER_HOUR(USCustomary.MILE_PER_HOUR);

            private final Unit<Speed> unit;
            private SupportedSpeed(Unit<Speed> unit) {
                this.unit = unit;
            }
            @Override
            public Unit<Speed> unit() {
                return unit;
            }
            @Override
            public String toString() {
                return name().toLowerCase();
            }
            public static final ObservableList<SupportedSpeed> SUPPORTED_UNITS =
                    unmodifiableObservableList(observableArrayList(values()));
            public static final ObservableList<Unit<Speed>> UNITS;
            static {
                ObservableList<Unit<Speed>> list = observableArrayList();
                for (SupportedSpeed supportedUnit : values()) {
                    list.add(supportedUnit.unit);
                }
                UNITS = unmodifiableObservableList(list);
            }
        }
        public static enum SupportedAcceleration implements SupportedUnit<Acceleration> {
            METRE_PER_SECOND(Units.METRE_PER_SQUARE_SECOND),
            FOOT_PER_SECOND(CustomUnits.FOOT_PER_SQUARE_SECOND);

            private final Unit<Acceleration> unit;
            private SupportedAcceleration(Unit<Acceleration> unit) {
                this.unit = unit;
            }
            @Override
            public Unit<Acceleration> unit() {
                return unit;
            }
            @Override
            public String toString() {
                return name().toLowerCase();
            }
            public static final ObservableList<SupportedAcceleration> SUPPORTED_UNITS =
                    unmodifiableObservableList(observableArrayList(values()));
            public static final ObservableList<Unit<Acceleration>> UNITS;
            static {
                ObservableList<Unit<Acceleration>> list = observableArrayList();
                for (SupportedAcceleration supportedUnit : values()) {
                    list.add(supportedUnit.unit);
                }
                UNITS = unmodifiableObservableList(list);
            }
        }
        public static enum SupportedAngle implements SupportedUnit<Angle> {
            DEGREE_ANGLE(NonSI.DEGREE_ANGLE),
            RADIAN(Units.RADIAN),
            REVOLUTION(NonSI.REVOLUTION);

            private final Unit<Angle> unit;
            private SupportedAngle(Unit<Angle> unit) {
                this.unit = unit;
            }
            @Override
            public Unit<Angle> unit() {
                return unit;
            }
            @Override
            public String toString() {
                return name().toLowerCase();
            }
            public static final ObservableList<SupportedAngle> SUPPORTED_UNITS =
                    unmodifiableObservableList(observableArrayList(values()));
            public static final ObservableList<Unit<Angle>> UNITS;
            static {
                ObservableList<Unit<Angle>> list = observableArrayList();
                for (SupportedAngle supportedUnit : values()) {
                    list.add(supportedUnit.unit);
                }
                UNITS = unmodifiableObservableList(list);
            }
        }
        public static enum SupportedAngularSpeed implements SupportedUnit<AngularSpeed> {
            REVOLUTION_PER_MINUTE(CustomUnits.REVOLUTION_PER_MINUTE),
            RADIAN_PER_SECOND(SI.RADIAN_PER_SECOND),
            DEGREE_ANGLE_PER_SECOND(CustomUnits.DEGREE_ANGLE_PER_SECOND);

            private final Unit<AngularSpeed> unit;
            private SupportedAngularSpeed(Unit<AngularSpeed> unit) {
                this.unit = unit;
            }
            @Override
            public Unit<AngularSpeed> unit() {
                return unit;
            }
            @Override
            public String toString() {
                return name().toLowerCase();
            }
            public static final ObservableList<SupportedAngularSpeed> SUPPORTED_UNITS =
                    unmodifiableObservableList(observableArrayList(values()));
            public static final ObservableList<Unit<AngularSpeed>> UNITS;
            static {
                ObservableList<Unit<AngularSpeed>> list = observableArrayList();
                for (SupportedAngularSpeed supportedUnit : values()) {
                    list.add(supportedUnit.unit);
                }
                UNITS = unmodifiableObservableList(list);
            }
        }
        public static enum SupportedTorque implements SupportedUnit<Torque> {
            NEWTON_METRE(CustomUnits.NEWTON_METRE),
            POUND_FOOT(CustomUnits.POUND_FOOT);

            private final Unit<Torque> unit;
            private SupportedTorque(Unit<Torque> unit) {
                this.unit = unit;
            }
            @Override
            public Unit<Torque> unit() {
                return unit;
            }
            @Override
            public String toString() {
                return name().toLowerCase();
            }
            public static final ObservableList<SupportedTorque> SUPPORTED_UNITS =
                    unmodifiableObservableList(observableArrayList(values()));
            public static final ObservableList<Unit<Torque>> UNITS;
            static {
                ObservableList<Unit<Torque>> list = observableArrayList();
                for (SupportedTorque supportedUnit : values()) {
                    list.add(supportedUnit.unit);
                }
                UNITS = unmodifiableObservableList(list);
            }
        }
        public static enum SupportedMomentOfInertia implements SupportedUnit<MomentOfInertia> {
            KILOGRAM_SQUARE_METRE(CustomUnits.KILOGRAM_SQUARE_METRE),
            POUND_FOOT(CustomUnits.POUND_SQUARE_INCH);

            private final Unit<MomentOfInertia> unit;
            private SupportedMomentOfInertia(Unit<MomentOfInertia> unit) {
                this.unit = unit;
            }
            @Override
            public Unit<MomentOfInertia> unit() {
                return unit;
            }
            @Override
            public String toString() {
                return name().toLowerCase();
            }
            public static final ObservableList<SupportedMomentOfInertia> SUPPORTED_UNITS =
                    unmodifiableObservableList(observableArrayList(values()));
            public static final ObservableList<Unit<MomentOfInertia>> UNITS;
            static {
                ObservableList<Unit<MomentOfInertia>> list = observableArrayList();
                for (SupportedMomentOfInertia supportedUnit : values()) {
                    list.add(supportedUnit.unit);
                }
                UNITS = unmodifiableObservableList(list);
            }
        }
    }
}