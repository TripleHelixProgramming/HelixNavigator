package org.team2363.helixnavigator.document.field.configuration;

import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONObject;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.TypeMarker;

import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.document.obstacle.HObstacle;

import javafx.collections.ObservableList;

@JSONSerializable
public interface HFieldConfiguration {

    static final TypeMarker<HReferenceFieldConfiguration> REFERENCE_TYPE = new TypeMarker<>() {};

    public String getName();
    public HFieldImage getFieldImage();
    public ObservableList<HObstacle> getObstacles();

    @DeserializedJSONDeterminer
    public static TypeMarker<? extends HFieldConfiguration> determiner(JSONObject jsonObject) throws JSONDeserializerException {
        return REFERENCE_TYPE;
    }
}