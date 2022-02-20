package org.team2363.helixnavigator.ui.prompts.obstacle;

import org.team2363.helixnavigator.document.obstacle.HCircleObstacle;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import org.team2363.helixnavigator.document.obstacle.HRectangleObstacle;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.lib.ui.prompts.ConstrainedDecimalTextField;
import org.team2363.lib.ui.prompts.FilteredTextField;

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
    private final ConstrainedDecimalTextField safetyDistanceTextField = new ConstrainedDecimalTextField(0, Double.MAX_VALUE);
    protected final GridPane propertyGrid = new GridPane();
    /**
     * The index where new grid pane items can be added. For example, the circle obstacle
     * dialog can add the center x text field at this row.
     */
    protected final int additionalItemsRow = 2;
    private final Button okButton = new Button("OK");
    private final Button cancelButton = new Button("Cancel");
    private final HBox buttonBox = new HBox(okButton, cancelButton);
    protected final VBox vBox = new VBox(propertyGrid, buttonBox);
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
    }

    protected void initializeTextFields() {
        nameTextField.setText(obstacle.getName());
        safetyDistanceTextField.setValue(obstacle.getSafetyDistance());
    }

    protected void unbindObstacle() {
        obstacle.nameProperty().unbind();
    }

    protected void bindObstacle() {
        obstacle.nameProperty().bind(nameTextField.textProperty());
    }

    protected void backupObstacle() {
        backupObstacle.setName(obstacle.getName());
    }

    protected void restoreBackup() {
        obstacle.setName(backupObstacle.getName());
    }

    public void show() {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void showAndWait() {
        stage.showAndWait();
    }
}