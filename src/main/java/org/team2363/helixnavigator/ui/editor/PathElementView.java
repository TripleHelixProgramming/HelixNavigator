package org.team2363.helixnavigator.ui.editor;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;

public interface PathElementView {
    public Node getView();
    public BooleanProperty selectedProperty();
    public void setSelected(boolean value);
    public boolean getSelected();
}
