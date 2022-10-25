package org.team2363.helixnavigator.document.timeline;

import org.team2363.helixnavigator.document.HPathElement;
import org.team2363.helixnavigator.document.timeline.waypoint.HInitialGuessPoint;
import org.team2363.helixnavigator.document.timeline.waypoint.HWaypoint;

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
        HOLONOMIC_WAYPOINT, INITIAL_GUESS_POINT, SEQUENTIAL_COMMAND_TRIGGER, PARALLEL_COMMAND_TRIGGER;

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
    public boolean isHolonomicWaypoint() {
        return getTimelineElementType() == TimelineElementType.HOLONOMIC_WAYPOINT;
    }
    public final boolean isInitialGuessPoint() {
        return getTimelineElementType() == TimelineElementType.INITIAL_GUESS_POINT;
    }
    public final boolean isSequentialCommandTrigger() {
        return getTimelineElementType() == TimelineElementType.SEQUENTIAL_COMMAND_TRIGGER;
    }
    public final boolean isParallelCommandTrigger() {
        return getTimelineElementType() == TimelineElementType.PARALLEL_COMMAND_TRIGGER;
    }

    public static Class<? extends HTimelineElement> timelineElementDeterminer(
            @DeserializedJSONObjectValue(key = "timeline_element_type") TimelineElementType timelineElementType) 
            throws JSONDeserializerException {
        switch (timelineElementType) {
            case HOLONOMIC_WAYPOINT:
                return HWaypoint.class;
            case INITIAL_GUESS_POINT:
                return HInitialGuessPoint.class;
            case PARALLEL_COMMAND_TRIGGER:
                return HParallelCommandTrigger.class;
            case SEQUENTIAL_COMMAND_TRIGGER:
                return HSequentialCommandTrigger.class;
            default:
                throw new JSONDeserializerException("Cannot have null timeline element type");
        }
    }
}