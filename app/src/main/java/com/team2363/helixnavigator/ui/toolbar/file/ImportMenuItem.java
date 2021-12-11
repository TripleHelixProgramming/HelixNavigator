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

package com.team2363.helixnavigator.ui.toolbar.file;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

/**
 * This class represents the "Import" menu item in the file menu.
 * 
 * @author Justin Babilino
 */
public class ImportMenuItem extends MenuItem {
    /**
     * Constructs an <code>ImportMenuItem</code> and sets its text and action.
     */
    public ImportMenuItem() {
        setText("Import...");
        
        setOnAction(this::action);
    }
    
    /**
     * The event that occurs when the menu item is pressed.
     * 
     * @param event
     */
    public void action(ActionEvent event) {
        System.out.println("Importing");
    }
}