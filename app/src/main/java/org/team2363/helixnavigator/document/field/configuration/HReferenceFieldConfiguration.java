package org.team2363.helixnavigator.document.field.configuration;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.global.DefaultFieldConfigurations;
import org.team2363.helixnavigator.global.DefaultResourceUnavailableException;

import javafx.collections.ObservableList;

@JSONSerializable
public class HReferenceFieldConfiguration implements HFieldConfiguration {

    private HDefaultFieldConfiguration fieldConfiguration;

    @DeserializedJSONConstructor
    public HReferenceFieldConfiguration(@DeserializedJSONObjectValue(key = "default") String name) throws DefaultResourceUnavailableException {
        fieldConfiguration = DefaultFieldConfigurations.forName(name);
    }

    @SerializedJSONObjectValue(key = "default")
    @Override
    public String getName() {
        return fieldConfiguration.getName();
    }

    @Override
    public HFieldImage getFieldImage() {
        return fieldConfiguration.getFieldImage();
    }

    @Override
    public ObservableList<HObstacle> getObstacles() {
        return fieldConfiguration.getObstacles();
    }
}