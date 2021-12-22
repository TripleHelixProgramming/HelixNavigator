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
package org.team2363.helixnavigator.ui.prompts;

import java.util.Optional;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Justin Babilino
 */
public class PromptTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DecimalPrompt prompt = new DecimalPrompt();
        Optional<String> optional = prompt.showAndWait();
        if (optional.isPresent()) {
            System.out.println(Double.parseDouble(optional.get()));
        }
        stop();
    }
}
