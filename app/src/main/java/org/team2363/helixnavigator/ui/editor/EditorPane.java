package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

public class EditorPane extends VBox {
    private final DocumentManager documentManager;
    private final ToolBar topToolbar;
    public EditorPane(DocumentManager documentManager) {
        this.documentManager = documentManager;
        topToolbar = new ToolBar();
    }
}
