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

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * This class creates the main scene that can be placed on the main window.
 *
 * @author Justin Babilino
 */
public class MainScene {

    /**
     * The main <code>Scene</code>.
     */
    private final Scene mainScene;

    /**
     * The main pane that is placed on the scene.
     */
    private final Pane mainPane;

    /**
     * Creates the main scene.
     */
    public MainScene() {
        mainPane = new MainPane();
        mainScene = new Scene(mainPane);
    }
    
    public Scene getScene() {
        return mainScene;
    }
}
