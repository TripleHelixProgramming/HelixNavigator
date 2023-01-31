package org.team2363.helixnavigator.global;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.jlbabilino.json.InvalidJSONTranslationConfiguration;
import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONParserException;

import org.team2363.helixnavigator.document.field.image.HDefaultFieldImage;
import org.team2363.helixnavigator.document.field.image.HFieldImage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DefaultFieldImages {

    //TODO: make sure all loggers use this same naming convention and are all private static final (low dev priority)
    private static final Logger LOGGER = Logger.getLogger("org.team2363.helixnavigator.global");

    private static final String[] FIELD_IMAGE_FILES = {
        "wpifieldimages/2018-powerup.json",
        "wpifieldimages/2019-deepspace.json",
        "wpifieldimages/2020-infiniterecharge.json",
        "wpifieldimages/2021-barrelracing.path.json",
        "wpifieldimages/2021-bouncepath.json",
        "wpifieldimages/2021-galacticsearcha.json",
        "wpifieldimages/2021-galacticsearchb.json",
        "wpifieldimages/2021-infiniterecharge.json",
        "wpifieldimages/2021-slalompath.json",
        "wpifieldimages/2022-rapidreact.json",
        "wpifieldimages/2023-chargedup.json",
        "extrafieldimages/blankfield.json",
        "extrafieldimages/2021-infiniterechargeathome.json"
    };

    private static final Map<String, HDefaultFieldImage> fieldImageMap = new HashMap<>();
    private static final ObservableList<String> names = FXCollections.observableArrayList();
    private static final ObservableList<String> namesUnmodifiable = FXCollections.unmodifiableObservableList(names);
    private static final ObservableList<HFieldImage> fieldImages = FXCollections.observableArrayList();
    private static final ObservableList<HFieldImage> fieldImagesUnmodifiable = FXCollections.unmodifiableObservableList(fieldImages);

    public static void loadDefaultFieldImages() { // TODO: make sure this is only able to be run once
        LOGGER.info("Loading default images...");
        for (int fileIndex = 0; fileIndex < FIELD_IMAGE_FILES.length; fileIndex++) {
            InputStream stream = DefaultFieldImages.class.getResourceAsStream(FIELD_IMAGE_FILES[fileIndex]);
            if (stream == null) {
                LOGGER.finer("Failed to load field image file \"" + FIELD_IMAGE_FILES[fileIndex] + "\"");
                continue;
            }
            try {
                HDefaultFieldImage fieldImage = JSONDeserializer.deserialize(new String(stream.readAllBytes()), HDefaultFieldImage.class);
                stream.close();
                fieldImageMap.put(fieldImage.getName(), fieldImage);
                LOGGER.info("Loaded image: \"" + fieldImage.getName() + "\"");
            } catch (NullPointerException | IOException e) {
                LOGGER.finer("Failed to load field image file \"" + FIELD_IMAGE_FILES[fileIndex] + "\":" + e.getMessage());
            } catch (JSONParserException e) {
                LOGGER.finer("Failed to parse field image json in file\"" + FIELD_IMAGE_FILES[fileIndex] + "\":" + e.getMessage());
            } catch (InvalidJSONTranslationConfiguration e) {
                LOGGER.severe("Internal JSON translation configuration error, contact developer: " + e.getMessage());
            } catch (JSONDeserializerException e) {
                LOGGER.finer("Failed to deserialize field image json in file \"" + FIELD_IMAGE_FILES[fileIndex] + "\":" + e.getMessage());
            }
        }
        names.addAll(fieldImageMap.keySet());
        fieldImages.addAll(fieldImageMap.values());
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

    public static ObservableList<String> listNames() {
        return namesUnmodifiable;
    }

    public static ObservableList<HFieldImage> listImages() {
        return fieldImagesUnmodifiable;
    }
}