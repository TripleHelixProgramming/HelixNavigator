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
package org.team2363.lib.base64;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Justin Babilino
 */
public class Base64Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Base64Encoder encoder = new Base64Encoder("A:/bliss.png");
            System.out.println(encoder.getString());
            
            Base64Decoder decoder = new Base64Decoder(encoder.getEncodedBytes());
            Image image = new Image(decoder.getDecodedInputStream());
            ImageView iview = new ImageView(image);
            Scene scene = new Scene(new Pane(iview));
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        stage.show();
    }
}
