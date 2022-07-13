package org.team2363.helixnavigator.ui.menu.help;

import org.team2363.helixnavigator.global.Standards;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class AboutMenuItem extends MenuItem {

    private final AboutStage aboutStage = new AboutStage();

    public AboutMenuItem() {
        setText("About " + Standards.APPLICATION_NAME);
        setOnAction(this::action);
    }

    public void action(ActionEvent event) {
        aboutStage.show();
    }
}