package org.team2363.helixnavigator.ui.menu.help;

import java.io.InputStream;

import org.team2363.helixnavigator.global.Standards;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AboutStage {
    
    private static final Font FONT = Font.font("Helvetica Neue", FontWeight.THIN, FontPosture.REGULAR, 11);

    private final ImageView iconView = new ImageView();
    private final Text applicationNameText = new Text(Standards.APPLICATION_NAME);
    private final Text versionText = new Text("Version " + Standards.APPLICATION_VERSION);
    private final Text createdByText = new Text("Created by Justin Babilino");
    private final Text copyrightText = new Text("Copyright © 2021-2022 Triple Helix Robotics");
    private final VBox layout = new VBox(iconView, applicationNameText, versionText, createdByText, copyrightText);
    private final Scene scene = new Scene(layout);
    private final Stage stage = new Stage();

    public AboutStage() {
        InputStream stream = AboutStage.class.getResourceAsStream("/icon.png");
        if (stream != null) {
            iconView.setImage(new Image(stream));
        }
        iconView.setFitWidth(100);
        iconView.setFitHeight(100);
        applicationNameText.setFont(FONT);
        versionText.setFont(FONT);
        createdByText.setFont(FONT);
        copyrightText.setFont(FONT);
        layout.setAlignment(Pos.CENTER);
        stage.setResizable(false);
        stage.setMinWidth(300);
        stage.setMaxWidth(300);
        stage.setMinHeight(230);
        stage.setMaxHeight(230);
        stage.setScene(scene);
    }

    public void show() {
        if (stage.isShowing()) {
            stage.requestFocus();
        }
        stage.show();
    }
}