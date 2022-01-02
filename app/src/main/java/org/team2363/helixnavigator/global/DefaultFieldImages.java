package org.team2363.helixnavigator.global;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jlbabilino.json.JSONDeserializer;

import org.team2363.helixnavigator.document.field.image.HDefaultFieldImage;

public class DefaultFieldImages {

    private static final File fieldsDirectory = new File(DefaultFieldImages.class.getResource(Standards.FIELD_IMAGES_PATH_PREFIX).getFile());
    private static final File[] files;
    private static final Map<String, HDefaultFieldImage> fieldImageMap = new HashMap<>();

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
        for (File file : files) {
            try {
                HDefaultFieldImage fieldImage = JSONDeserializer.deserialize(file, HDefaultFieldImage.class);
                fieldImageMap.put(fieldImage.getName(), fieldImage);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                // this should never happen
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