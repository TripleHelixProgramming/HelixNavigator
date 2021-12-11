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

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class creates a <code>Pane</code> that displays the console output.
 * 
 * @author Justin Babilino
 */
public class ConsolePane extends HBox {
    private final Text textView = new Text();
    private final ImageView graphicView = new ImageView();
    public ConsolePane() {
        textView.setFont(new Font(16));
        textView.textProperty().bind(Console.out);
        graphicView.setFitWidth(20);
        graphicView.setFitHeight(20);
        Console.graphic.addListener((observableVal, oldVal, newVal) -> {
            if (newVal != ConsoleGraphic.NONE) {
                graphicView.disableProperty().set(false);
                graphicView.setImage(newVal.image);
            } else {
                graphicView.disableProperty().set(true);
            }
        });
        setSpacing(10);
        setPadding(new Insets(0, 0, 5, 5));
        getChildren().addAll(graphicView, textView);
    }
}
