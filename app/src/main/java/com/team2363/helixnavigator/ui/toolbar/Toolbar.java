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

package com.team2363.helixnavigator.ui.toolbar;

import com.team2363.helixnavigator.ui.toolbar.edit.EditMenu;
import com.team2363.helixnavigator.ui.toolbar.file.FileMenu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

/**
 * This class represents the menu bar at the top of the scene.
 * 
 * @author Justin Babilino
 */
public class Toolbar extends MenuBar {
    
    /**
     * The "File" menu.
     */
    private final Menu fileMenu;
    /**
     * The "Edit" menu.
     */
    private final Menu editMenu;
    /**
     * The "View" menu.
     */
//    private final Menu viewMenu;
    
    /**
     * Constructs a <code>Toolbar</code> object, along with
     * the subsequent menus that are attached to it.
     */
    public Toolbar() {
        fileMenu = new FileMenu();
        editMenu = new EditMenu();
//        viewMenu = new ViewMenu();
        getMenus().add(fileMenu);
        getMenus().add(editMenu);
//        getMenus().add(viewMenu);
    }    
}
