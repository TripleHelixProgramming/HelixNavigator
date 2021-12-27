package org.team2363.helixnavigator.global;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.jlbabilino.json.JSON;
import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONParser;

import org.team2363.helixnavigator.document.field.image.HCustomFieldImage;
import org.team2363.helixnavigator.document.field.image.HFieldImage;

import javafx.scene.image.Image;

public class DefaultFieldImages {

    private static final File fieldsDirectory = new File(DefaultFieldImages.class.getResource("/field_images/").getFile());
    private static final File[] files;
    private static final Map<String, HFieldImage> fieldImageMap = new HashMap<>();

    static {
        File[] allFiles = fieldsDirectory.listFiles();
        int filesLength = allFiles.length / 2;
        files = new File[filesLength];
        int filesIndex = 0;
        for (int allFilesIndex = 0; allFilesIndex < allFiles.length; allFilesIndex++) {
            File file = allFiles[allFilesIndex];
            if (file.getName().endsWith(".json")) {
                files[filesIndex] = file;
                filesIndex++;
            }
        }
    }

    public static HFieldImage fromName(String name) {
        return fieldImageMap.get(name);
    }

    private static void loadFieldImages() {
        for (File file : files) {
            try {
                String jsonString = Files.readString(Path.of(file.toURI()));
                JSON json = JSONParser.parseStringAsJSON(jsonString);
                HCustomFieldImage fieldImage = JSONDeserializer.deserializeJSON(json, HCustomFieldImage.class);
                String name = fieldImage.getName();
                fieldImageMap.put(name, fieldImage);
            } catch (IOException | JSONDeserializerException e) {
                // do nothing
            }
        }
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