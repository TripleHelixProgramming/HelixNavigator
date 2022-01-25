package org.team2363.helixnavigator.ui.editor;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.ui.editor.toolbar.PathToolBar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class EditorPane extends VBox {

    private final DocumentManager documentManager;

    private final PathToolBar pathToolBar;
    private final PathPane pathPane;

    public EditorPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        pathToolBar = new PathToolBar(this.documentManager);
        pathPane = new PathPane(this.documentManager);

        setPadding(new Insets(0, 10, 10, 5));
        setSpacing(10.0);
        VBox.setVgrow(pathPane, Priority.ALWAYS);
        setAlignment(Pos.CENTER);

        getChildren().addAll(pathToolBar, pathPane);
    }
}