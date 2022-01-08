package org.team2363.helixnavigator.global;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONParserException;

import org.team2363.helixnavigator.document.field.image.DefaultFieldImageNotFoundException;
import org.team2363.helixnavigator.document.field.image.HDefaultFieldImage;

public class DefaultFieldImages {

    private static final Map<String, HDefaultFieldImage> fieldImageMap = new HashMap<>();

    static {
        System.out.println("DefaultFieldImages: Loading default images...");
        int index = 0;
        InputStream currentStream;
        while ((currentStream = DefaultFieldImages.class.getResourceAsStream("field_" + index + ".json")) != null) {
            try {
                HDefaultFieldImage fieldImage = JSONDeserializer.deserialize(new String(currentStream.readAllBytes()), HDefaultFieldImage.class);
                currentStream.close(); // This does nothing but I need it to avoid the warning
                fieldImageMap.put(fieldImage.getName(), fieldImage);
                System.out.println("DefaultFieldImages: Loaded image: \"" + fieldImage.getName() + "\"");
            } catch (NullPointerException e) {
                System.out.println("DefaultFieldImages: Failed to load a file: null");
            } catch (IOException e) {
                System.out.println("DefaultFieldImages: Failed to load a file: " + e.getMessage());
            } catch (JSONParserException e) {
                System.out.println("DefaultFieldImages: Failed to parse a file: " + e.getMessage());
            } catch (JSONDeserializerException e) {
                System.out.println("DefaultFieldImages: Failed to deserialize a file: " + e.getMessage());
            } finally {
                index++;
            }
        }
    }

    public static boolean containsName(String name) {
        return fieldImageMap.containsKey(name);
    }

    public static HDefaultFieldImage forName(String name) throws DefaultFieldImageNotFoundException {
        if (!containsName(name)) {
            throw new DefaultFieldImageNotFoundException("Default field image named \"" + name + "\" does not exist");
        }
        return fieldImageMap.get(name);
    }

    public static Set<String> listNames() {
        return fieldImageMap.keySet();
    }
}