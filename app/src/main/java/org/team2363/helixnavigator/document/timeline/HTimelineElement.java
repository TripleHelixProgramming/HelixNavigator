package org.team2363.helixnavigator.document.timeline;

import org.team2363.helixnavigator.document.HPathElement;

public abstract class HTimelineElement extends HPathElement {

    public static enum TimelineElementType {
        WAYPOINT, INITIAL_GUESS, OBSTACLE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public abstract TimelineElementType getTimelineElementType();
    public boolean isWaypoint() {
        return false;
    }
    public boolean isInitialGuessPoint() {
        return false;
    }
    public boolean isObstacle() {
        return false;
    }
}