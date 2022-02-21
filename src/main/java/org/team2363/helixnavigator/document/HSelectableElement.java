package org.team2363.helixnavigator.document;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

public abstract class HSelectableElement {

    private final ReadOnlyBooleanWrapper selected = new ReadOnlyBooleanWrapper(this, "selected", false);

    protected HSelectableElement() {
    }

    public abstract void translateRelativeX(double x);

    public abstract void translateRelativeY(double y);

    public void translateRelative(double x, double y) {
        translateRelativeX(x);
        translateRelativeY(y);
    }

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