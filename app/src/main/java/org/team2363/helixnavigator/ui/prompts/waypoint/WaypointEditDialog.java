package org.team2363.helixnavigator.ui.prompts.waypoint;

import java.util.Collection;

import javax.measure.quantity.Length;

import org.team2363.helixnavigator.document.timeline.HCustomWaypoint;
import org.team2363.helixnavigator.document.timeline.HHardWaypoint;
import org.team2363.helixnavigator.document.timeline.HInitialGuessWaypoint;
import org.team2363.helixnavigator.document.timeline.HSoftWaypoint;
import org.team2363.helixnavigator.document.timeline.HWaypoint;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.helixnavigator.global.Standards.ExportedUnits;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedLength;
import org.team2363.lib.ui.validation.FilteredTextField;
import org.team2363.lib.ui.validation.UnitTextField;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class WaypointEditDialog {

    public static WaypointEditDialog dialog(HWaypoint waypoint) {
        switch (waypoint.getWaypointType()) {
            case SOFT:
                return new SoftWaypointEditDialog((HSoftWaypoint) waypoint);
            case HARD:
                return new HardWaypointEditDialog((HHardWaypoint) waypoint);
            case CUSTOM:
                return new CustomWaypointEditDialog((HCustomWaypoint) waypoint);
            case INITIAL_GUESS:
                return new InitialGuessWaypointEditDialog((HInitialGuessWaypoint) waypoint);
            default:
                return null;
        }
    }

    protected final HWaypoint waypoint;
    protected final HWaypoint backupWaypoint;

    private final Text nameText = new Text("Name:");
    private final FilteredTextField nameTextField = new FilteredTextField(Standards.MAX_NAME_LENGTH, Standards.VALID_NAME);
    private final Text xText = new Text("X:");
    private final UnitTextField<Length> xTextField = new UnitTextField<>(ExportedUnits.LENGTH_UNIT, SupportedLength.UNITS);
    private final Text yText = new Text("Y:");
    private final UnitTextField<Length> yTextField = new UnitTextField<>(ExportedUnits.LENGTH_UNIT, SupportedLength.UNITS);
    private final GridPane propertyGrid = new GridPane();
    /**
     * The index where new grid pane items can be added.
     */
    protected static final int ADDITIONAL_PROPERTIES_ROW = 3;
    private final Button okButton = new Button("OK");
    private final Button cancelButton = new Button("Cancel");
    private final HBox buttonBox = new HBox();
    protected final VBox vBox = new VBox();
    protected static final int ADDITIONAL_NODES_ROW = 1;
    private final Scene scene;
    private final Stage stage = new Stage();

    public WaypointEditDialog(HWaypoint waypoint, HWaypoint backupWaypoint) {
        this.waypoint = waypoint;
        this.backupWaypoint = backupWaypoint;

        // okButton.setDefaultButton(true);
        GridPane.setConstraints(nameText, 0, 0);
        GridPane.setConstraints(nameTextField, 1, 0);
        GridPane.setConstraints(xText, 0, 1);
        GridPane.setConstraints(xTextField, 1, 1);
        GridPane.setConstraints(yText, 0, 2);
        GridPane.setConstraints(yTextField, 1, 2);
        propertyGrid.setHgap(10);
        propertyGrid.setVgap(10);
        propertyGrid.getChildren().addAll(nameText, nameTextField, xText, xTextField,
                yText, yTextField);
        buttonBox.getChildren().addAll(okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(propertyGrid, buttonBox);
        scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setResizable(false);

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

    protected void addGridItems(Collection<? extends Node> items) {
        propertyGrid.getChildren().addAll(items);
    }

    protected void initializeTextFields() {
        nameTextField.setText(waypoint.getName());
        xTextField.setValue(waypoint.getX());
        yTextField.setValue(waypoint.getY());
    }

    protected void unbindWaypoint() {
        waypoint.nameProperty().unbind();
        waypoint.xProperty().unbind();
        waypoint.yProperty().unbind();
    }

    protected void bindWaypoint() {
        waypoint.nameProperty().bind(nameTextField.textProperty());
        waypoint.xProperty().bind(xTextField.valueProperty());
        waypoint.yProperty().bind(yTextField.valueProperty());
    }

    protected void backupWaypoint() {
        backupWaypoint.setName(waypoint.getName());
        backupWaypoint.setX(waypoint.getX());
        backupWaypoint.setY(waypoint.getY());
    }

    protected void restoreBackup() {
        waypoint.setName(backupWaypoint.getName());
        waypoint.setX(backupWaypoint.getX());
        waypoint.setY(backupWaypoint.getY());
    }

    public void show() {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void showAndWait() {
        stage.showAndWait();
    }
}
