package org.team2363.helixnavigator.global;

import java.io.File;
import java.util.regex.Pattern;

import javafx.scene.paint.Color;

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
}