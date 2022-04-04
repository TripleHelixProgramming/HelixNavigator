package org.team2363.helixnavigator.ui.prompts.obstacle;

import org.team2363.helixnavigator.document.obstacle.HCircleObstacle;
import org.team2363.lib.ui.validation.DecimalTextField;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class CircleObstacleEditDialog extends ObstacleEditDialog {

    private final HCircleObstacle circleObstacle;
    private final HCircleObstacle backupCircleObstacle;

    private final Text centerXText = new Text("Center X:");
    private final DecimalTextField centerXTextField = new DecimalTextField();
    private final Text centerYText = new Text("Center Y:");
    private final DecimalTextField centerYTextField = new DecimalTextField();
    private final Text radiusText = new Text("Radius:");
    private final DecimalTextField radiusTextField = new DecimalTextField(0, Double.MAX_VALUE);

    public CircleObstacleEditDialog(HCircleObstacle circleObstacle) {
        super(circleObstacle, new HCircleObstacle());
        this.circleObstacle = (HCircleObstacle) obstacle;
        this.backupCircleObstacle = (HCircleObstacle) backupObstacle;

        GridPane.setConstraints(centerXText, 0, ADDITIONAL_PROPERTIES_ROW);
        GridPane.setConstraints(centerXTextField, 1, ADDITIONAL_PROPERTIES_ROW);
        GridPane.setConstraints(centerYText, 0, ADDITIONAL_PROPERTIES_ROW + 1);
        GridPane.setConstraints(centerYTextField, 1, ADDITIONAL_PROPERTIES_ROW + 1);
        GridPane.setConstraints(radiusText, 0, ADDITIONAL_PROPERTIES_ROW + 2);
        GridPane.setConstraints(radiusTextField, 1, ADDITIONAL_PROPERTIES_ROW + 2);

        propertyGrid.getChildren().addAll(centerXText, centerXTextField,
                centerYText, centerYTextField, radiusText, radiusTextField);

        backupObstacle();
        // Set ui to values of Obstacle:
        initializeTextFields();
        // Now bind the Obstacle to the values of the ui
        bindObstacle();
    }

    @Override
    protected void initializeTextFields() {
        super.initializeTextFields();
        centerXTextField.setValue(circleObstacle.getCenterX());
        centerYTextField.setValue(circleObstacle.getCenterY());
        radiusTextField.setValue(circleObstacle.getRadius());
    }

    @Override
    protected void unbindObstacle() {
        super.unbindObstacle();
        circleObstacle.centerXProperty().unbind();
        circleObstacle.centerYProperty().unbind();
        circleObstacle.radiusProperty().unbind();
    }

    @Override
    protected void bindObstacle() {
        super.bindObstacle();
        circleObstacle.centerXProperty().bind(centerXTextField.valueProperty());
        circleObstacle.centerYProperty().bind(centerYTextField.valueProperty());
        circleObstacle.radiusProperty().bind(radiusTextField.valueProperty());
    }

    @Override
    protected void backupObstacle() {
        super.backupObstacle();
        backupCircleObstacle.setCenterX(circleObstacle.getCenterX());
        backupCircleObstacle.setCenterY(circleObstacle.getCenterY());
        backupCircleObstacle.setRadius(circleObstacle.getRadius());
    }

    @Override
    protected void restoreBackup() {
        super.restoreBackup();
        circleObstacle.setCenterX(backupCircleObstacle.getCenterX());
        circleObstacle.setCenterY(backupCircleObstacle.getCenterY());
        circleObstacle.setRadius(backupCircleObstacle.getRadius());
    }
}