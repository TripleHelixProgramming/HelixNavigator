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
    private final DeleteMenuItem deleteMenuItem;

    private final SelectAllMenuItem selectAllMenuItem;
    private final DeselectAllMenuItem deselectAllMenuItem;

    private final TransformMenuItem transformMenuItem;
    private final Rotate90ClockwiseMenuItem rotate90ClockwiseMenuItem;
    private final Rotate90CounterclockwiseMenuItem rotate90CounterclockwiseMenuItem;
    private final Rotate180MenuItem rotate180MenuItem;

    public EditMenu(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setText("_Edit");
        undoMenuItem = new UndoMenuItem(this.documentManager);
        redoMenuItem = new RedoMenuItem(this.documentManager);
        cutMenuItem = new CutMenuItem(this.documentManager);
        copyMenuItem = new CopyMenuItem(this.documentManager);
        pasteMenuItem = new PasteMenuItem(this.documentManager);
        deleteMenuItem = new DeleteMenuItem(this.documentManager);
        selectAllMenuItem = new SelectAllMenuItem(this.documentManager);
        deselectAllMenuItem = new DeselectAllMenuItem(this.documentManager);
        transformMenuItem = new TransformMenuItem(this.documentManager);
        rotate90ClockwiseMenuItem = new Rotate90ClockwiseMenuItem(this.documentManager);
        rotate90CounterclockwiseMenuItem = new Rotate90CounterclockwiseMenuItem(this.documentManager);
        rotate180MenuItem = new Rotate180MenuItem(this.documentManager);

        getItems().add(undoMenuItem);
        getItems().add(redoMenuItem);
        getItems().add(new SeparatorMenuItem());
        getItems().add(cutMenuItem);
        getItems().add(copyMenuItem);
        getItems().add(pasteMenuItem);
        getItems().add(deleteMenuItem);
        getItems().add(new SeparatorMenuItem());
        getItems().add(selectAllMenuItem);
        getItems().add(deselectAllMenuItem);
        getItems().add(new SeparatorMenuItem());
        getItems().add(transformMenuItem);
        getItems().add(rotate90ClockwiseMenuItem);
        getItems().add(rotate90CounterclockwiseMenuItem);
        getItems().add(rotate180MenuItem);
    }
}
