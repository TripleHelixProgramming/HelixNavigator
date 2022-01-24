package org.team2363.helixnavigator.ui.editor;

import javafx.scene.Node;

/**
 * So i don't forget, this interface exists so there are no naming conflicts.
 * For instance, the background rectangle needs to have an independent width
 * property that doesn't conflict with the width property of the pane.
 */
public interface ElementView {
    public Node getView();
}