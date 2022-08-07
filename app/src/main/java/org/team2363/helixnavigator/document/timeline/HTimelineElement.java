package org.team2363.helixnavigator.document.timeline;

import org.team2363.helixnavigator.document.HPathElement;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONEntry;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONEntry;
import com.jlbabilino.json.SerializedJSONObjectValue;
import com.jlbabilino.json.JSONEntry.JSONType;

public abstract class HTimelineElement extends HPathElement {
    @JSONSerializable(JSONType.STRING)
    @JSONDeserializable({JSONType.STRING})
    public static enum TimelineElementType {
        WAYPOINT, INITIAL_GUESS_POINT, COMMAND_TRIGGER;

        @DeserializedJSONConstructor
        public static TimelineElementType forName(@DeserializedJSONEntry String name) {
            return valueOf(name.toUpperCase());
        }

        @SerializedJSONEntry
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    @SerializedJSONObjectValue(key = "timeline_element_type")
    public abstract TimelineElementType getTimelineElementType();
    public boolean isWaypoint() {
        return false;
    }
    public boolean isInitialGuessPoint() {
        return false;
    }
    public boolean isCommandTrigger() {
        return false;
    }

    public static Class<? extends HTimelineElement> timelineElementDeterminer(
            @DeserializedJSONObjectValue(key = "timeline_element_type") TimelineElementType timelineElementType) 
            throws JSONDeserializerException {
        switch (timelineElementType) {
            case WAYPOINT:
                return HWaypoint.class;
            case INITIAL_GUESS_POINT:
                return HInitialGuessPoint.class;
            case COMMAND_TRIGGER:
                return HCommandTrigger.class;
            default:
                throw new JSONDeserializerException("Cannot have null timeline element type");
        }
    }
}