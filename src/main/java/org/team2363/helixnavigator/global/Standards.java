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
}