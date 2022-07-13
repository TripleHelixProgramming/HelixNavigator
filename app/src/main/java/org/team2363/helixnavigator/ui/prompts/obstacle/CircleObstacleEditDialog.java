package org.team2363.helixnavigator.ui.prompts.obstacle;

import static org.team2363.helixnavigator.global.Standards.ExportedUnits.LENGTH_UNIT;

import javax.measure.quantity.Length;

import org.team2363.helixnavigator.document.obstacle.HCircleObstacle;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedLength;
import org.team2363.lib.ui.validation.UnitTextField;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class CircleObstacleEditDialog extends ObstacleEditDialog {

    private final HCircleObstacle circleObstacle;
    private final HCircleObstacle backupCircleObstacle;

    private final Text centerXText = new Text("Center X:");
    private final UnitTextField<Length> centerXTextField = new UnitTextField<Length>(LENGTH_UNIT, SupportedLength.UNITS);
    private final Text centerYText = new Text("Center Y:");
    private final UnitTextField<Length> centerYTextField = new UnitTextField<Length>(LENGTH_UNIT, SupportedLength.UNITS);
    private final Text radiusText = new Text("Radius:");
    private final UnitTextField<Length> radiusTextField = new UnitTextField<Length>(LENGTH_UNIT, SupportedLength.UNITS);

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

        radiusTextField.setInputTransformation(input -> Math.max(input, 0));
        radiusTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);

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