package org.team2363.helixnavigator.ui.menu;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.ui.menu.edit.EditMenu;
import org.team2363.helixnavigator.ui.menu.file.FileMenu;
import org.team2363.helixnavigator.ui.menu.help.HelpMenu;
import org.team2363.helixnavigator.ui.menu.tools.ToolsMenu;
import org.team2363.helixnavigator.ui.menu.view.ViewMenu;

import javafx.scene.control.MenuBar;

public class MainMenuBar extends MenuBar {

    private final DocumentManager documentManager;

    private final FileMenu fileMenu;
    private final EditMenu editMenu;
    private final ViewMenu viewMenu;
    private final ToolsMenu toolsMenu;
    private final HelpMenu helpMenu;

    public MainMenuBar(DocumentManager documentManager) {
        this.documentManager = documentManager;

        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            useSystemMenuBarProperty().set(true);
        }

        fileMenu = new FileMenu(this.documentManager);
        editMenu = new EditMenu(this.documentManager);
        viewMenu = new ViewMenu(this.documentManager);
        toolsMenu = new ToolsMenu(this.documentManager);
        helpMenu = new HelpMenu();

        getMenus().addAll(fileMenu, editMenu, viewMenu, toolsMenu, helpMenu);
    }    
}
