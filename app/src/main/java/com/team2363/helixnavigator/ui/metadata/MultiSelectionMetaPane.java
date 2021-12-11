package com.team2363.helixnavigator.ui.metadata;

import com.team2363.helixnavigator.document.HPath;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MultiSelectionMetaPane extends VBox {

    private final HPath hPath;
    private final Text titleText = new Text("Translate Selection");
    
    private final HBox translateRelativeXBox;
    private final Text translateRelativeXText = new Text("Translate X: ");
    private final TextField translateRelativeXTextField = new TextField("0.0");

    private final HBox translateRelativeYBox;
    private final Text translateRelativeYText = new Text("Translate Y: ");
    private final TextField translateRelativeYTextField = new TextField("0.0");

    public MultiSelectionMetaPane(HPath hPath) {
        this.hPath = hPath;

        translateRelativeXTextField.setOnAction(this::onTranslateRelativeX);
        translateRelativeYTextField.setOnAction(this::onTranslateRelativeY);

        translateRelativeXBox = new HBox(translateRelativeXText, translateRelativeXTextField);
        translateRelativeYBox = new HBox(translateRelativeYText, translateRelativeYTextField);

        getChildren().addAll(titleText, translateRelativeXBox, translateRelativeYBox);

    }

    private void onTranslateRelativeX(ActionEvent event) {
        String textEntered = translateRelativeXTextField.getText();
        try {
            final double value = Double.parseDouble(textEntered);
            if (hPath.inPolygonPointMode()) {
                hPath.getPolygonPointsSelectionModel().getSelectedItems().forEach(element -> element.translateRelativeX(value));
            } else {
                hPath.getWaypointsSelectionModel().getSelectedItems().forEach(element -> element.translateRelativeX(value));
                hPath.getObstaclesSelectionModel().getSelectedItems().forEach(element -> element.translateRelativeX(value));
            }
        } catch (NumberFormatException e) {
            translateRelativeXTextField.setText("0.0");
        }
    }

    private void onTranslateRelativeY(ActionEvent event) {
        String textEntered = translateRelativeYTextField.getText();
        try {
            final double value = Double.parseDouble(textEntered);
            if (hPath.inPolygonPointMode()) {
                hPath.getPolygonPointsSelectionModel().getSelectedItems().forEach(element -> element.translateRelativeY(value));
            } else {
                hPath.getWaypointsSelectionModel().getSelectedItems().forEach(element -> element.translateRelativeY(value));
                hPath.getObstaclesSelectionModel().getSelectedItems().forEach(element -> element.translateRelativeY(value));
            }
        } catch (NumberFormatException e) {
            translateRelativeYTextField.setText("0.0");
        }
    }
}