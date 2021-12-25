package org.team2363.helixnavigator.global;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class DefaultFieldImages {

    private static final File fieldsDirectory = new File(BuiltInFields.class.getResource("/field_images/").getFile());
    private static final File[] files;
    private static final Map<String, Image> imageMap = new HashMap<>();

    static {
        files = fieldsDirectory.listFiles();
        for (File file : files) {
            imageMap.put(nameFromFile(file), imageFromFile(file));
        }
    }

    public static Image fromName(String name) {
        return imageMap.get(name);
    }

    private static String nameFromFile(File file) {
        String fullName = file.getName();
        int indexOfDot = fullName.indexOf(".");
        return fullName.substring(0, indexOfDot);
    }

    private static Image imageFromFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            return new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}