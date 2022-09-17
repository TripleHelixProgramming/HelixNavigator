package org.team2363.helixnavigator.document.timeline;

import java.util.List;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONEntry;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.JSONEntry.JSONType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@JSONSerializable(JSONType.OBJECT)
public class HTimeline {
    
    private final ObservableList<HTimelineElement> elements = FXCollections.observableArrayList();

    @DeserializedJSONConstructor
    public HTimeline(
            @DeserializedJSONEntry List<HTimelineElement> elements) {
        elements.addAll(elements);
    }

    public ObservableList<HTimelineElement> getElements() {
        return elements;
    }
}