package org.team2363.helixnavigator.ui.menu;

import org.team2363.helixnavigator.document.DocumentManager;
// import org.team2363.helixnavigator.ui.menu.edit.EditMenu;
// import org.team2363.helixnavigator.ui.menu.file.FileMenu;
import org.team2363.helixnavigator.ui.menu.view.ViewMenu;

import javafx.scene.control.MenuBar;

public class MainMenuBar extends MenuBar {

    private final DocumentManager documentManager;

    // private final FileMenu fileMenu;
    // private final EditMenu editMenu;
    private final ViewMenu viewMenu;

    public MainMenuBar(DocumentManager documentManager) {
        this.documentManager = documentManager;

        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            useSystemMenuBarProperty().set(true);
        }

        // fileMenu = new FileMenu();
        // editMenu = new EditMenu();
        viewMenu = new ViewMenu();

        // getMenus().add(fileMenu);
        // getMenus().add(editMenu);
        getMenus().add(viewMenu);
    }    
}
