package org.team2363.helixnavigator.ui.editor;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public interface PathLayer {
    public ObservableList<? extends Node> getChildren();
}