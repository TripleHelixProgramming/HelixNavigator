package org.team2363.helixnavigator.global;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONParserException;

import org.team2363.helixnavigator.document.field.image.HDefaultFieldImage;

public class DefaultFieldImages {

    //TODO: make sure all loggers use this same naming convention and are all private static final (low priority)
    private static final Logger LOGGER = Logger.getLogger("org.team2363.helixnavigator.global");

    private static final Map<String, HDefaultFieldImage> fieldImageMap = new HashMap<>();

    public static void loadDefaultFieldImages() {
        LOGGER.info("Loading default images...");
        int index = 0;
        InputStream currentStream;
        while ((currentStream = DefaultFieldImages.class.getResourceAsStream("field_image_" + index + ".json")) != null) {
            try {
                HDefaultFieldImage fieldImage = JSONDeserializer.deserialize(new String(currentStream.readAllBytes()), HDefaultFieldImage.class);
                currentStream.close(); // This does nothing but I need it to avoid the warning
                fieldImageMap.put(fieldImage.getName(), fieldImage);
                LOGGER.info("Loaded image: \"" + fieldImage.getName() + "\"");
            } catch (NullPointerException e) {
                LOGGER.finer("Failed to load a file: null");
            } catch (IOException e) {
                LOGGER.finer("Failed to load a file: " + e.getMessage());
            } catch (JSONParserException e) {
                LOGGER.finer("Failed to parse a file: " + e.getMessage());
            } catch (JSONDeserializerException e) {
                LOGGER.finer("Failed to deserialize a file: " + e.getMessage());
            } finally {
                index++;
            }
        }
    }

    public static boolean containsName(String name) {
        return fieldImageMap.containsKey(name);
    }

    public static HDefaultFieldImage forName(String name) throws DefaultResourceUnavailableException {
        if (!containsName(name)) {
            throw new DefaultResourceUnavailableException("Default field image named \"" + name + "\" does not exist");
        }
        return fieldImageMap.get(name);
    }

    public static Set<String> listNames() {
        return fieldImageMap.keySet();
    }
}