/*
 * Copyright (C) 2021 Justin Babilino
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
package com.team2363.helixnavigator.ui.document;

import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author Justin Babilino
 */
public class DocumentPane extends VBox {
    private final PathChooserBox pathChooserBox = new PathChooserBox();
    private final WaypointListView waypointListView = new WaypointListView();
    public DocumentPane() {
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10.0);
        setMinWidth(10);
        setMaxWidth(Region.USE_COMPUTED_SIZE);
        getChildren().add(pathChooserBox);
        getChildren().add(waypointListView);
    }
}
