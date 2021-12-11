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
package com.team2363.helixnavigator.ui.console;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class allows for debug text to be displayed on the bottom of the
 * program window. A <code>StringProperty</code> is used to achieve this.
 * 
 * @author Justin Babilino
 */
public class Console {
    /**
     * The output console as a <code>StringProperty</code>
     */
    public static final StringProperty out = new SimpleStringProperty("Welcome to Helix Trajectory!");
    /**
     * The dynamic graphic property that complements the console text and
     * provides general context about what type of message is being displayed.
     */
    public static final ObjectProperty<ConsoleGraphic> graphic = new SimpleObjectProperty<>(ConsoleGraphic.NONE);
    
    static {
        out.addListener((observableVal, oldVal, newVal) -> {Log.log(newVal);});
    }
}