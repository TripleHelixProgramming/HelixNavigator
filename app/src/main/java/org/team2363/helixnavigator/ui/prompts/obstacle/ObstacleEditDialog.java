package org.team2363.helixnavigator.ui.prompts.obstacle;

import static org.team2363.helixnavigator.global.Standards.ExportedUnits.LENGTH_UNIT;

import javax.measure.quantity.Length;

import org.team2363.helixnavigator.document.obstacle.HCircleObstacle;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import org.team2363.helixnavigator.document.obstacle.HRectangleObstacle;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedLength;
import org.team2363.lib.ui.validation.FilteredTextField;
import org.team2363.lib.ui.validation.UnitTextField;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class ObstacleEditDialog {

    public static ObstacleEditDialog dialog(HObstacle obstacle) {
        switch (obstacle.getObstacleType()) {
            case CIRCLE:
                return new CircleObstacleEditDialog((HCircleObstacle) obstacle);
            case POLYGON:
                return new PolygonObstacleEditDialog((HPolygonObstacle) obstacle);
            case RECTANGLE:
                return new RectangleObstacleEditDialog((HRectangleObstacle) obstacle);
            default:
                return null;
        }
    }

    protected final HObstacle obstacle;
    protected final HObstacle backupObstacle;

    private final Text nameText = new Text("Name:");
    private final FilteredTextField nameTextField = new FilteredTextField(Standards.MAX_NAME_LENGTH, Standards.VALID_NAME);
    private final Text safetyDistanceText = new Text("Safety Distance:");
    private final UnitTextField<Length> safetyDistanceTextField = new UnitTextField<>(LENGTH_UNIT, SupportedLength.UNITS);
    protected final GridPane propertyGrid = new GridPane();
    /**
     * The index where new grid pane items can be added. For example, the circle obstacle
     * dialog can add the center x text field at this row.
     */
    protected static final int ADDITIONAL_PROPERTIES_ROW = 2;
    private final Button okButton = new Button("OK");
    private final Button cancelButton = new Button("Cancel");
    private final HBox buttonBox = new HBox(okButton, cancelButton);
    protected final VBox vBox = new VBox(propertyGrid, buttonBox);
    protected static final int ADDITIONAL_NODES_ROW = 1;
    private final Scene scene = new Scene(vBox);
    private final Stage stage = new Stage();

    public ObstacleEditDialog(HObstacle obstacle, HObstacle backupObstacle) {
        this.obstacle = obstacle;
        this.backupObstacle = backupObstacle;

        // okButton.setDefaultButton(true);
        GridPane.setConstraints(nameText, 0, 0);
        GridPane.setConstraints(nameTextField, 1, 0);
        GridPane.setConstraints(safetyDistanceText, 0, 1);
        GridPane.setConstraints(safetyDistanceTextField, 1, 1);
        propertyGrid.setHgap(10);
        propertyGrid.setVgap(10);
        propertyGrid.getChildren().addAll(nameText, nameTextField,
                safetyDistanceText, safetyDistanceTextField);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        stage.setScene(scene);
        stage.setWidth(250);
        stage.setResizable(false);

        okButton.setOnAction(event -> {
            unbindObstacle();
            stage.close();
        });
        cancelButton.setOnAction(event -> {
            unbindObstacle();
            restoreBackup();
            stage.close();
        });
        stage.setOnCloseRequest(event -> {
            unbindObstacle();
            restoreBackup();
        });

        safetyDistanceTextField.setInputTransformation(input -> Math.max(input, 0));
        safetyDistanceTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
    }

    protected void initializeTextFields() {
        nameTextField.setText(obstacle.getName());
        safetyDistanceTextField.setValue(obstacle.getSafetyDistance());
    }

    protected void unbindObstacle() {
        obstacle.nameProperty().unbind();
        obstacle.safetyDistanceProperty().unbind();
    }

    protected void bindObstacle() {
        obstacle.nameProperty().bind(nameTextField.textProperty());
        obstacle.safetyDistanceProperty().bind(safetyDistanceTextField.valueProperty());
    }

    protected void backupObstacle() {
        backupObstacle.setName(obstacle.getName());
        backupObstacle.setSafetyDistance(obstacle.getSafetyDistance());
    }

    protected void restoreBackup() {
        obstacle.setName(backupObstacle.getName());
        obstacle.setSafetyDistance(backupObstacle.getSafetyDistance());
    }

    public void show() {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void showAndWait() {
        stage.showAndWait();
    }
}