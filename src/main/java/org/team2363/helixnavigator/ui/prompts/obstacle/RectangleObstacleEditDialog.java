package org.team2363.helixnavigator.ui.prompts.obstacle;

import org.team2363.helixnavigator.document.obstacle.HRectangleObstacle;
import org.team2363.lib.ui.validation.DecimalTextField;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class RectangleObstacleEditDialog extends ObstacleEditDialog {

    private final HRectangleObstacle rectangleObstacle;
    private final HRectangleObstacle backupRectangleObstacle;

    private final Text centerXText = new Text("Center X:");
    private final DecimalTextField centerXTextField = new DecimalTextField();
    private final Text centerYText = new Text("Center Y:");
    private final DecimalTextField centerYTextField = new DecimalTextField();
    private final Text lengthText = new Text("Length:");
    private final DecimalTextField lengthTextField = new DecimalTextField(0, Double.MAX_VALUE);
    private final Text widthText = new Text("Width:");
    private final DecimalTextField widthTextField = new DecimalTextField(0, Double.MAX_VALUE);
    private final Text angleText = new Text("Angle:");
    private final DecimalTextField angleTextField = new DecimalTextField();

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