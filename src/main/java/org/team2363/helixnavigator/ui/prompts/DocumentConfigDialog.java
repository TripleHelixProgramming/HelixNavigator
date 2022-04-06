package org.team2363.helixnavigator.ui.prompts;

import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.document.field.image.HReferenceFieldImage;
import org.team2363.helixnavigator.global.DefaultFieldImages;
import org.team2363.helixnavigator.global.DefaultResourceUnavailableException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DocumentConfigDialog {

    private static final ObservableList<String> choices = FXCollections.observableArrayList();

    static {
        choices.add("None");
        choices.addAll(DefaultFieldImages.listNames());
    }

    private final HDocument document;

    private final ChoiceBox<String> fieldChoiceBox = new ChoiceBox<>();
    private final Tab fieldTab = new Tab("Field", fieldChoiceBox);
    private final Tab robotTab = new Tab("Robot", new Label("beep boop"));
    private final TabPane tabPane = new TabPane(fieldTab, robotTab);
    private final Button okButton = new Button("OK");
    private final HBox buttonBox = new HBox(okButton);
    private final VBox vBox = new VBox(tabPane, buttonBox);
    private final Scene scene = new Scene(vBox);
    private final Stage stage = new Stage();

    public DocumentConfigDialog(HDocument document) {
        this.document = document;

        fieldTab.setClosable(false);
        robotTab.setClosable(false);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        stage.setScene(scene);

        // we are trusting that this is the only thing that could change
        // the field image
        fieldChoiceBox.setItems(choices);
        HFieldImage currentImage = this.document.getFieldImage();
        if (currentImage == null) {
            fieldChoiceBox.getSelectionModel().select(0);
        } else {
            fieldChoiceBox.getSelectionModel().select(currentImage.getName());
        }
        fieldChoiceBox.setOnAction(event -> {
            String newName = fieldChoiceBox.getValue();
            HFieldImage newImage = null;
            if (!newName.equals("None")) {
                try {
                    newImage = new HReferenceFieldImage(newName);
                } catch (DefaultResourceUnavailableException e) {
                }
            }
            this.document.setFieldImage(newImage);
        });
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
