/*
 * Copyright (C) 2021 Triple Helix Robotics - FRC Team 2363
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.team2363.helixnavigator.ui;

import com.team2363.helixnavigator.ui.console.Log;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This class generates a dialog where the user can decide whether to close the
 * application or not, and whether to save the current project.
 *
 * @author Justin Babilino
 */
public class WindowClosedDialog implements EventHandler<WindowEvent> {

    /**
     * Main <code>Stage</code>.
     */
    private final Stage mainStage;
    /**
     * Dialog <code>Stage</code>.
     */
    private final Stage dialogStage;
    /**
     * Dialog <code>Scene</code>.
     */
    private final Scene dialogScene;
    /**
     * Layout that combines the message and the buttons.
     */
    private final VBox dialogLayout;
    /**
     * Layout that combines the buttons in a row.
     */
    private final HBox buttonLayout;
    /**
     * Contains the message of the prompt.
     */
    private final Label confirmationLabel;
    /**
     * The "Yes" button.
     */
    private final Button yesButton;
    /**
     * The "No" button.
     */
    private final Button noButton;
    /**
     * The "Cancel" button.
     */
    private final Button cancelButton;

    /**
     * Constructs a <code>WindowClosedDialog</code> object. The
     * <code>Stage</code> passed in must be the window that created the prompt
     * -- it will be closed if the user selects an option that closes the
     * window.
     *
     * @param stage the <code>Stage</code> that should be closed.
     */
    public WindowClosedDialog(Stage stage) {
        dialogStage = new Stage();
        mainStage = stage;
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setWidth(250);
        dialogStage.setHeight(150);
        dialogStage.setResizable(false);
        dialogStage.setTitle("Confirmation");
        dialogStage.setOnCloseRequest(this::dialogClosed);
        confirmationLabel = new Label("Do you want to save the Project?");
        yesButton = new Button("Yes");
        yesButton.setOnAction(this::yesPressed);
        noButton = new Button("No");
        noButton.setOnAction(this::noPressed);
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(this::cancelPressed);
        buttonLayout = new HBox(yesButton, noButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setPadding(new Insets(10, 10, 10, 10));
        buttonLayout.setSpacing(10);
        dialogLayout = new VBox(confirmationLabel, buttonLayout);
        dialogLayout.setAlignment(Pos.CENTER);
        dialogLayout.setPadding(new Insets(10, 10, 10, 10));
        dialogLayout.setSpacing(10);
        dialogScene = new Scene(dialogLayout);
        dialogStage.setScene(dialogScene);
    }

    /**
     * Closes the dialog box.
     */
    public void closeDialog() {
        dialogStage.close();
    }

    /**
     * Closes the dialog box and the main stage.S
     */
    private void closeMainStage() {
        closeDialog();
        Log.close();
        mainStage.close();
    }

    /**
     * Manages the event that happens when "Yes" is pressed.
     *
     * @param event the event that occurs when the button is pressed.
     */
    private void yesPressed(ActionEvent event) {
        closeMainStage();
    }

    /**
     * Manages the event that happens when "No" is pressed.
     *
     * @param event the event that occurs when the button is pressed.
     */
    private void noPressed(ActionEvent event) {
        closeMainStage();
    }

    /**
     * Manages the event that happens when "Cancel" is pressed.
     *
     * @param event the event that occurs when the button is pressed.
     */
    private void cancelPressed(ActionEvent event) {
        closeDialog();
    }

    /**
     * Manages the event that happens when "Yes" is pressed.
     *
     * @param event the event that occurs when the button is pressed.
     */
    private void dialogClosed(WindowEvent event) {
        closeDialog();
    }

    /**
     * The event handler that is called when the original stage is closed.
     *
     * @param event the event that occurs when the original stage is closed.
     */
    @Override
    public void handle(WindowEvent event) {
        event.consume();
        dialogStage.show();
    }
}
