package org.team2363.helixnavigator.ui.prompts.obstacle;

import static org.team2363.helixnavigator.global.Standards.ExportedUnits.ANGLE_UNIT;
import static org.team2363.helixnavigator.global.Standards.ExportedUnits.LENGTH_UNIT;

import javax.measure.quantity.Angle;
import javax.measure.quantity.Length;

import org.team2363.helixnavigator.document.obstacle.HRectangleObstacle;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedAngle;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedLength;
import org.team2363.lib.ui.validation.UnitTextField;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class RectangleObstacleEditDialog extends ObstacleEditDialog {

    private final HRectangleObstacle rectangleObstacle;
    private final HRectangleObstacle backupRectangleObstacle;

    private final Text centerXText = new Text("Center X:");
    private final UnitTextField<Length> centerXTextField = new UnitTextField<>(LENGTH_UNIT, SupportedLength.UNITS);
    private final Text centerYText = new Text("Center Y:");
    private final UnitTextField<Length> centerYTextField = new UnitTextField<>(LENGTH_UNIT, SupportedLength.UNITS);
    private final Text lengthText = new Text("Length:");
    private final UnitTextField<Length> lengthTextField = new UnitTextField<>(LENGTH_UNIT, SupportedLength.UNITS);
    private final Text widthText = new Text("Width:");
    private final UnitTextField<Length> widthTextField = new UnitTextField<>(LENGTH_UNIT, SupportedLength.UNITS);
    private final Text angleText = new Text("Angle:");
    private final UnitTextField<Angle> angleTextField = new UnitTextField<>(ANGLE_UNIT, SupportedAngle.UNITS);

    public RectangleObstacleEditDialog(HRectangleObstacle rectangleObstacle) {
        super(rectangleObstacle, new HRectangleObstacle());
        this.rectangleObstacle = (HRectangleObstacle) obstacle;
        this.backupRectangleObstacle = (HRectangleObstacle) backupObstacle;

        GridPane.setConstraints(centerXText, 0, ADDITIONAL_PROPERTIES_ROW);
        GridPane.setConstraints(centerXTextField, 1, ADDITIONAL_PROPERTIES_ROW);
        GridPane.setConstraints(centerYText, 0, ADDITIONAL_PROPERTIES_ROW + 1);
        GridPane.setConstraints(centerYTextField, 1, ADDITIONAL_PROPERTIES_ROW + 1);
        GridPane.setConstraints(lengthText, 0, ADDITIONAL_PROPERTIES_ROW + 2);
        GridPane.setConstraints(lengthTextField, 1, ADDITIONAL_PROPERTIES_ROW + 2);
        GridPane.setConstraints(widthText, 0, ADDITIONAL_PROPERTIES_ROW + 3);
        GridPane.setConstraints(widthTextField, 1, ADDITIONAL_PROPERTIES_ROW + 3);
        GridPane.setConstraints(angleText, 0, ADDITIONAL_PROPERTIES_ROW + 4);
        GridPane.setConstraints(angleTextField, 1, ADDITIONAL_PROPERTIES_ROW + 4);

        propertyGrid.getChildren().addAll(centerXText, centerXTextField,
                centerYText, centerYTextField, lengthText, lengthTextField,
                widthText, widthTextField, angleText, angleTextField);

        lengthTextField.setInputTransformation(input -> Math.max(input, 0));
        widthTextField.setInputTransformation(input -> Math.max(input, 0));
        centerXTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        centerYTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        lengthTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        widthTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        angleTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);

        backupObstacle();
        // Set ui to values of Obstacle:
        initializeTextFields();
        // Now bind the Obstacle to the values of the ui
        bindObstacle();
    }

    @Override
    protected void initializeTextFields() {
        super.initializeTextFields();
        centerXTextField.setValue(rectangleObstacle.getCenterX());
        centerYTextField.setValue(rectangleObstacle.getCenterY());
        lengthTextField.setValue(rectangleObstacle.getLength());
        widthTextField.setValue(rectangleObstacle.getWidth());
        angleTextField.setValue(rectangleObstacle.getRotateAngle());
    }

    @Override
    protected void unbindObstacle() {
        super.unbindObstacle();
        rectangleObstacle.centerXProperty().unbind();
        rectangleObstacle.centerYProperty().unbind();
        rectangleObstacle.lengthProperty().unbind();
        rectangleObstacle.widthProperty().unbind();
        rectangleObstacle.rotateAngleProperty().unbind();
    }

    @Override
    protected void bindObstacle() {
        super.bindObstacle();
        rectangleObstacle.centerXProperty().bind(centerXTextField.valueProperty());
        rectangleObstacle.centerYProperty().bind(centerYTextField.valueProperty());
        rectangleObstacle.lengthProperty().bind(lengthTextField.valueProperty());
        rectangleObstacle.widthProperty().bind(widthTextField.valueProperty());
        rectangleObstacle.rotateAngleProperty().bind(angleTextField.valueProperty());
    }

    @Override
    protected void backupObstacle() {
        super.backupObstacle();
        backupRectangleObstacle.setCenterX(rectangleObstacle.getCenterX());
        backupRectangleObstacle.setCenterY(rectangleObstacle.getCenterY());
        backupRectangleObstacle.setLength(rectangleObstacle.getLength());
        backupRectangleObstacle.setWidth(rectangleObstacle.getWidth());
        backupRectangleObstacle.setRotateAngle(rectangleObstacle.getRotateAngle());
    }

    @Override
    protected void restoreBackup() {
        super.restoreBackup();
        rectangleObstacle.setCenterX(backupRectangleObstacle.getCenterX());
        rectangleObstacle.setCenterY(backupRectangleObstacle.getCenterY());
        rectangleObstacle.setLength(backupRectangleObstacle.getLength());
        rectangleObstacle.setWidth(backupRectangleObstacle.getWidth());
        rectangleObstacle.setRotateAngle(backupRectangleObstacle.getRotateAngle());
    }
}