package org.team2363.helixnavigator.ui.menu.help;

import javafx.scene.control.Menu;

public class HelpMenu extends Menu {

    private final AboutMenuItem aboutMenuItem;

    public HelpMenu() {
        setText("_Help");
        aboutMenuItem = new AboutMenuItem();

        getItems().add(aboutMenuItem);
    }
}
