package org.team2363.helixnavigator.ui.editor;

import javafx.beans.property.BooleanProperty;

public interface PathElementView extends ElementView {
    public BooleanProperty selectedProperty();
    public void setSelected(boolean value);
    public boolean getSelected();
}
