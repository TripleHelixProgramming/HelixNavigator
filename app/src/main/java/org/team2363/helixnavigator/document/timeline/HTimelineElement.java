package org.team2363.helixnavigator.document.timeline;

import org.team2363.helixnavigator.document.HPathElement;

import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.SerializedJSONObjectValue;

public abstract class HTimelineElement extends HPathElement {
    
    public static enum TimelineElementType {
        WAYPOINT, INITIAL_GUESS_POINT, COMMAND_TRIGGER;

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