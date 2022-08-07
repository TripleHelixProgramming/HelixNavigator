package org.team2363.helixnavigator.document.timeline;

import com.jlbabilino.json.DeserializedJSONConstructor;

import javafx.scene.transform.Transform;

public class HCommandTrigger extends HTimelineElement {

    @DeserializedJSONConstructor
    public HCommandTrigger() {

    }

    @Override
    public void transformRelative(Transform transform) {
    }

    @Override
    public void translateRelativeX(double x) {
    }

    @Override
    public void translateRelativeY(double y) {
    }

    @Override
    public TimelineElementType getTimelineElementType() {
        return null;
    }
    @Override
    public boolean isCommandTrigger() {
        return true;
    }
}