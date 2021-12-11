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

import javafx.scene.image.Image;

/**
 * This enum class allows for a selection of several graphics to display on the
 * console.
 * 
 * @author Justin Babilino
 */
public enum ConsoleGraphic {
    NONE(null),
    INFO(new Image(ConsoleGraphic.class.getResourceAsStream("/icons/console/info.png"))),
    WARNING(new Image(ConsoleGraphic.class.getResourceAsStream("/icons/console/warning.png"))),
    WORKING(new Image(ConsoleGraphic.class.getResourceAsStream("/icons/console/working.png"))),
    COMPLETE(new Image(ConsoleGraphic.class.getResourceAsStream("/icons/console/complete.png"))),
    ERROR(new Image(ConsoleGraphic.class.getResourceAsStream("/icons/console/error.png")));
    
    public final Image image;
    
    private ConsoleGraphic(Image image) {
        this.image = image;
    }
}
