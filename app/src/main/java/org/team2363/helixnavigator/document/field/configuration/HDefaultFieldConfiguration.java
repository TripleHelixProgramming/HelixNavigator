package org.team2363.helixnavigator.document.field.configuration;

import java.util.List;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;
import com.jlbabilino.json.JSONEntry.JSONType;

import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.document.obstacle.HObstacle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HDefaultFieldConfiguration implements HFieldConfiguration {

    private String name;
    private HFieldImage fieldImage;
    private ObservableList<HObstacle> obstacles;

    @DeserializedJSONConstructor
    public HDefaultFieldConfiguration(
            @DeserializedJSONObjectValue(key = "name") String name,
            @DeserializedJSONObjectValue(key = "field_image") HFieldImage fieldImage,
            @DeserializedJSONObjectValue(key = "obstacles") List<HObstacle> obstacles) {
        this.name = name;
        this.fieldImage = fieldImage;
        this.obstacles = FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(obstacles));
    }

    @SerializedJSONObjectValue(key = "name")
    @Override
    public String getName() {
        return name;
    }

    @SerializedJSONObjectValue(key = "field_image")
    @Override
    public HFieldImage getFieldImage() {
        return fieldImage;
    }

    @SerializedJSONObjectValue(key = "obstacles")
    @Override
    public ObservableList<HObstacle> getObstacles() {
        return obstacles;
    }
}