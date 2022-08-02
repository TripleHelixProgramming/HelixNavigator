package org.team2363.helixnavigator.global;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.jlbabilino.json.InvalidJSONTranslationConfiguration;
import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONParserException;

import org.team2363.helixnavigator.document.field.configuration.HDefaultFieldConfiguration;

public class DefaultFieldConfigurations {

    private static final Logger LOGGER = Logger.getLogger("org.team2363.helixnavigator.global");

    private static final Map<String, HDefaultFieldConfiguration> fieldConfigurationMap = new HashMap<>();

    public static void loadDefaultFieldConfigurations() {
        LOGGER.info("Loading default field configurations...");
        int index = 0;
        InputStream currentStream;
        while ((currentStream = DefaultFieldConfigurations.class.getResourceAsStream("field_configuration_" + index + ".json")) != null) {
            try {
                HDefaultFieldConfiguration fieldConfiguration = JSONDeserializer.deserialize(new String(currentStream.readAllBytes()), HDefaultFieldConfiguration.class);
                currentStream.close(); // This does nothing but I need it to avoid the warning
                fieldConfigurationMap.put(fieldConfiguration.getName(), fieldConfiguration);
                LOGGER.info("Loaded field configuration: \"" + fieldConfiguration.getName() + "\"");
            } catch (NullPointerException e) {
                LOGGER.finer("Failed to load a file: null");
            } catch (IOException e) {
                LOGGER.finer("Failed to load a file: " + e.getMessage());
            } catch (JSONParserException e) {
                LOGGER.finer("Failed to parse a file: " + e.getMessage());
            } catch (InvalidJSONTranslationConfiguration e) {
                LOGGER.severe("Internal JSON translation configuration error: " + e.getMessage());
            } catch (JSONDeserializerException e) {
                LOGGER.finer("Failed to deserialize a file: " + e.getMessage());
            } finally {
                index++;
            }
        }
    }

    public static boolean containsName(String name) {
        return fieldConfigurationMap.containsKey(name);
    }

    public static HDefaultFieldConfiguration forName(String name) throws DefaultResourceUnavailableException {
        if (!containsName(name)) {
            throw new DefaultResourceUnavailableException("Default field configuration named \"" + name + "\" does not exist");
        }
        return fieldConfigurationMap.get(name);
    }

    public static Set<String> listNames() {
        return fieldConfigurationMap.keySet();
    }
}