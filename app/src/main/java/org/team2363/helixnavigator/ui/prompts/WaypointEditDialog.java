package org.team2363.helixnavigator.ui.prompts;

import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint.WaypointType;
import org.team2363.lib.ui.prompts.DecimalTextField;
import org.team2363.lib.ui.prompts.FilteredTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WaypointEditDialog {

    private static final ObservableList<WaypointType> ALL_WAYPOINT_TYPES = FXCollections.observableArrayList(WaypointType.values());

    private final HWaypoint backupWaypoint;
    private final HWaypoint waypoint;

    private final Text nameText = new Text("Name:");
    private final FilteredTextField nameTextField = new FilteredTextField(HWaypoint.MAX_WAYPOINT_NAME_LENGTH, HWaypoint.VALID_WAYPOINT_NAME);
    private final Text waypointTypeText = new Text("Waypoint Type:");
    private final ChoiceBox<WaypointType> waypointTypeChoiceBox = new ChoiceBox<>(ALL_WAYPOINT_TYPES);
    private final Text xText = new Text("X:");
    private final DecimalTextField xTextField = new DecimalTextField();
    private final Text yText = new Text("Y:");
    private final DecimalTextField yTextField = new DecimalTextField();
    private final Text headingText = new Text("Heading:");
    private final DecimalTextField headingTextField = new DecimalTextField();
    private final GridPane propertyGrid = new GridPane();
    private final Button okButton = new Button("OK");
    private final Button cancelButton = new Button("Cancel");
    private final HBox buttonBox = new HBox();
    private final VBox vBox = new VBox();
    private final Scene scene;
    private final Stage stage = new Stage();

    public WaypointEditDialog(HWaypoint waypoint) {
        this.waypoint = waypoint;

        okButton.setDefaultButton(true);
        GridPane.setConstraints(nameText, 0, 0);
        GridPane.setConstraints(nameTextField, 1, 0);
        GridPane.setConstraints(waypointTypeText, 0, 1);
        GridPane.setConstraints(waypointTypeChoiceBox, 1, 1);
        GridPane.setConstraints(xText, 0, 2);
        GridPane.setConstraints(xTextField, 1, 2);
        GridPane.setConstraints(yText, 0, 3);
        GridPane.setConstraints(yTextField, 1, 3);
        GridPane.setConstraints(headingText, 0, 4);
        GridPane.setConstraints(headingTextField, 1, 4);
        waypointTypeChoiceBox.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(waypointTypeChoiceBox, Priority.ALWAYS);
        propertyGrid.setHgap(10);
        propertyGrid.setVgap(10);
        propertyGrid.getChildren().addAll(nameText, nameTextField, waypointTypeText,
                waypointTypeChoiceBox, xText, xTextField, yText, yTextField, headingText, headingTextField);
        buttonBox.getChildren().addAll(okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(propertyGrid, buttonBox);
        scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setWidth(240);
        stage.setHeight(260);

        // Make backup point (to restore if cancelled)
        backupWaypoint = new HWaypoint();
        backupWaypoint.setName(this.waypoint.getName());
        backupWaypoint.setWaypointType(this.waypoint.getWaypointType());
        backupWaypoint.setX(this.waypoint.getX());
        backupWaypoint.setY(this.waypoint.getY());
        backupWaypoint.setHeading(this.waypoint.getHeading());
        // Set ui to values of HWaypoint:
        nameTextField.setText(this.waypoint.getName());
        waypointTypeChoiceBox.setValue(this.waypoint.getWaypointType());
        if (this.waypoint.isSoft()) {
            hideHeading();
        }
        xTextField.setText(String.valueOf(this.waypoint.getX()));
        yTextField.setText(String.valueOf(this.waypoint.getY()));
        headingTextField.setText(String.valueOf(this.waypoint.getHeading()));
        // Now bind the Waypoint to the values of the ui
        this.waypoint.nameProperty().bind(nameTextField.textProperty());
        this.waypoint.waypointTypeProperty().addListener((currentVal, oldVal, newVal) -> {
            switch (newVal) {
                case SOFT:
                    hideHeading();
                    break;
                case HARD:
                    showHeading();
                    break;
            }
        });
        this.waypoint.waypointTypeProperty().bind(waypointTypeChoiceBox.valueProperty());
        this.waypoint.xProperty().bind(xTextField.valueProperty());
        this.waypoint.yProperty().bind(yTextField.valueProperty());
        this.waypoint.headingProperty().bind(headingTextField.valueProperty());

        okButton.setOnAction(event -> {
            unbindWaypoint();
            stage.close();
        });
        cancelButton.setOnAction(event -> {
            unbindWaypoint();
            restoreBackup();
            stage.close();
        });
        stage.setOnCloseRequest(event -> {
            unbindWaypoint();
            restoreBackup();
        });
    }

    private void unbindWaypoint() {
        waypoint.nameProperty().unbind();
        waypoint.waypointTypeProperty().unbind();
        waypoint.xProperty().unbind();
        waypoint.yProperty().unbind();
        waypoint.headingProperty().unbind();
    }

    private void restoreBackup() {
        waypoint.setName(backupWaypoint.getName());
        waypoint.setWaypointType(backupWaypoint.getWaypointType());
        waypoint.setX(backupWaypoint.getX());
        waypoint.setY(backupWaypoint.getY());
        waypoint.setHeading(backupWaypoint.getHeading());
    }

    private void hideHeading() {
        headingTextField.setDisable(true);
        // propertyGrid.getChildren().removeAll(headingText, headingTextField);
    }

    private void showHeading() {
        headingTextField.setDisable(false);
        // propertyGrid.getChildren().addAll(headingText, headingTextField);
    }

    public void show() {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void showAndWait() {
        stage.showAndWait();
    }
}
