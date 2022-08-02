package org.team2363.helixnavigator.document;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

public abstract class HSelectableElement {

    private final ReadOnlyBooleanWrapper selected = new ReadOnlyBooleanWrapper(this, "selected", false);

    public final ReadOnlyBooleanProperty selectedProperty() {
        return selected.getReadOnlyProperty();
    }

    final void setSelected(boolean value) {
        selected.set(value);
    }

    public final boolean isSelected() {
        return selected.get();
    }
}