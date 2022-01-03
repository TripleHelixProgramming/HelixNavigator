package org.team2363.helixnavigator.global;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONParserException;

import org.team2363.helixnavigator.document.field.image.HDefaultFieldImage;

public class DefaultFieldImages {

    private static final File fieldsDirectory = new File(DefaultFieldImages.class.getResource(Standards.FIELD_IMAGES_PATH_PREFIX).getFile());
    private static final File[] files;
    private static final Map<String, HDefaultFieldImage> fieldImageMap = new HashMap<>();

    static {
        System.out.println("DefaultFieldImages: Loading default images...");
        File[] allFiles = fieldsDirectory.listFiles();
        int filesLength = allFiles.length / 2;
        files = new File[filesLength];
        int filesIndex = 0;
        for (int allFilesIndex = 0; allFilesIndex < allFiles.length; allFilesIndex++) {
            File file = allFiles[allFilesIndex];
            if (file.getName().endsWith(".json")) {
                System.out.println("DefaultFieldImages: found file \"" + file.getName() + "\"");
                files[filesIndex] = file;
                filesIndex++;
            }
        }
        for (File file : files) {
            try {
                System.out.println("DefaultFieldImages: Attempting to deserialize field image json.");
                HDefaultFieldImage fieldImage = JSONDeserializer.deserialize(file, HDefaultFieldImage.class);
                fieldImageMap.put(fieldImage.getName(), fieldImage);
                System.out.println("DefaultFieldImages: Deserialization was successful!");
            } catch (IOException e) {
                System.out.println("DefaultFieldImages: Error loading file: " + e.getMessage());
            } catch (JSONParserException e) {
                System.out.println("DefaultFieldImages: Error parsing file: " + e.getMessage());
            } catch (JSONDeserializerException e) {
                System.out.println("DefaultFieldImages: Error deserializing file: " + e.getMessage());
            }
        }
    }

    public static HDefaultFieldImage forName(String name) {
        return fieldImageMap.get(name);
    }

    public static Set<String> listNames() {
        return fieldImageMap.keySet();
    }
}