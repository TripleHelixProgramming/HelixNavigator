package org.team2363.helixnavigator.ui;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.ui.document.DocumentPane;
import org.team2363.helixnavigator.ui.editor.EditorPane;
import org.team2363.helixnavigator.ui.menu.MainMenuBar;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

public class MainPane extends VBox {

    private final DocumentManager documentManager;

    private final MainMenuBar mainMenuBar;

    private final DocumentPane documentPane;
    private final EditorPane editorPane;
    private final SplitPane middleRow;

    public MainPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        mainMenuBar = new MainMenuBar(this.documentManager);

        documentPane = new DocumentPane(this.documentManager);
        editorPane = new EditorPane(this.documentManager);
        middleRow = new SplitPane(documentPane, editorPane);

        getChildren().addAll(mainMenuBar, middleRow);
    }
}
