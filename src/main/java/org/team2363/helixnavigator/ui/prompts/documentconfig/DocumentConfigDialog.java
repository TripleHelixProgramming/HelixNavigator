package org.team2363.helixnavigator.ui.prompts.documentconfig;

import org.team2363.helixnavigator.document.HDocument;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DocumentConfigDialog {

    private final HDocument document;

    private final GeneralTab generalTab;
    private final RobotTab robotTab;
    private final TabPane tabPane;
    private final Button okButton = new Button("OK");
    private final HBox buttonBox = new HBox(okButton);
    private final VBox vBox;
    private final Scene scene;
    private final Stage stage = new Stage();

    public DocumentConfigDialog(HDocument document) {
        this.document = document;

        generalTab = new GeneralTab(this.document);
        robotTab = new RobotTab(this.document);
        tabPane = new TabPane(generalTab, robotTab);
        vBox = new VBox(tabPane, buttonBox);
        scene = new Scene(vBox);

        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        stage.setScene(scene);
        
        okButton.setOnAction(event -> close());
    }
    
    public void show() {
        if (stage.isShowing()) {
            stage.requestFocus();
        } else {
            stage.show();
        }
    }

    public void close() {
        stage.close();
    }
}
