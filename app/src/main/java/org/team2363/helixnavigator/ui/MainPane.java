package org.team2363.helixnavigator.ui;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.ui.document.DocumentPane;
import org.team2363.helixnavigator.ui.editor.EditorPane;
import org.team2363.helixnavigator.ui.menu.MainMenuBar;

import javafx.scene.layout.BorderPane;

public class MainPane extends BorderPane {

    private final DocumentManager documentManager;

    private final MainMenuBar mainMenuBar;
    private final DocumentPane documentPane;
    private final EditorPane editorPane;

    public MainPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        mainMenuBar = new MainMenuBar(this.documentManager);
        documentPane = new DocumentPane(this.documentManager);
        editorPane = new EditorPane(this.documentManager);

        setTop(mainMenuBar);
        setLeft(documentPane);
        setCenter(editorPane);
    }
}
