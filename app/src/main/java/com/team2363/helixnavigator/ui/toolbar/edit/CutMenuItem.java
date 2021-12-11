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

package com.team2363.helixnavigator.ui.toolbar.edit;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

/**
 * This class represents the "Cut" menu item in the edit menu.
 * 
 * @author Justin Babilino
 */
public class CutMenuItem extends MenuItem {
    /**
     * Image for the graphic.
     */
    private final Image graphic;
    /**
     * Viewer for the graphic.
     */
    private final ImageView graphicView;
    /**
     * Constructs a <code>CutMenuItem</code> and sets its text, graphic, and action.
     */
    public CutMenuItem() {
        setText("Cut");
        setAccelerator(KeyCombination.keyCombination("shortcut+X"));
        
        graphic = new Image(getClass().getResourceAsStream("/icons/edit/cut.png"));
        graphicView = new ImageView(graphic);
        graphicView.setSmooth(true);
        graphicView.setFitWidth(20);
        graphicView.setFitHeight(20);
        setGraphic(graphicView);
        
        setOnAction(this::action);
    }
    
    /**
     * The event that occurs when the menu item is pressed.
     * 
     * @param event
     */
    public void action(ActionEvent event) {
        System.out.println("Cutting");
    }
}