package org.team2363.helixnavigator.ui.menu.edit;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.scene.control.Menu;
import javafx.scene.control.SeparatorMenuItem;

public class EditMenu extends Menu {

    private final DocumentManager documentManager;

    private final UndoMenuItem undoMenuItem;
    private final RedoMenuItem redoMenuItem;
    private final CutMenuItem cutMenuItem;
    private final CopyMenuItem copyMenuItem;
    private final PasteMenuItem pasteMenuItem;

    public EditMenu(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setText("_Edit");
        undoMenuItem = new UndoMenuItem(documentManager);
        redoMenuItem = new RedoMenuItem(documentManager);
        cutMenuItem = new CutMenuItem(documentManager);
        copyMenuItem = new CopyMenuItem(documentManager);
        pasteMenuItem = new PasteMenuItem(documentManager);

        getItems().add(undoMenuItem);
        getItems().add(redoMenuItem);
        getItems().add(new SeparatorMenuItem());
        getItems().add(cutMenuItem);
        getItems().add(copyMenuItem);
        getItems().add(pasteMenuItem);
        getItems().add(new SeparatorMenuItem());
    }
}
