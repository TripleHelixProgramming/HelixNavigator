package org.team2363.helixnavigator.document.timeline;

import javafx.beans.property.ReadOnlyDoubleProperty;

public abstract class HSequentialAction extends HAction {

    public abstract ReadOnlyDoubleProperty durationProperty();

    public double getDuration() {
        return durationProperty().get();
    }
}