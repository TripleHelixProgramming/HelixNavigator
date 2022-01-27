package org.team2363.helixnavigator.ui.editor;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public interface PathLayer { //TODO: consider deleting this interface if polymorphism is never used
    public ObservableList<? extends Node> getChildren();
}