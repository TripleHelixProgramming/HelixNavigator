package org.team2363.helixnavigator.ui.prompts;

import static org.team2363.helixnavigator.global.Standards.ExportedUnits.ANGLE_UNIT;
import static org.team2363.helixnavigator.global.Standards.ExportedUnits.LENGTH_UNIT;

import javax.measure.quantity.Angle;
import javax.measure.quantity.Length;

import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedAngle;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedLength;
import org.team2363.lib.ui.validation.UnitTextField;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class TransformDialog {

    private final HDocument document;

    private final Translate translate = new Translate();
    private final Rotate rotate = new Rotate();

    private final Text offsetXText = new Text("Offset X:");
    private final UnitTextField<Length> offsetXTextField = new UnitTextField<>(LENGTH_UNIT, SupportedLength.UNITS);
    private final Text offsetYText = new Text("Offset Y:");
    private final UnitTextField<Length> offsetYTextField = new UnitTextField<>(LENGTH_UNIT, SupportedLength.UNITS);
    private final GridPane translateGrid = new GridPane();
    private final Button applyTranslate = new Button("Apply Translate");
    private final Button applyInverseTranslate = new Button("Apply Inverse Translate");

    private final Text axisXText = new Text("Axis X:");
    private final UnitTextField<Length> axisXTextField = new UnitTextField<>(LENGTH_UNIT, SupportedLength.UNITS);
    private final Text axisYText = new Text("Axis Y:");
    private final UnitTextField<Length> axisYTextField = new UnitTextField<>(LENGTH_UNIT, SupportedLength.UNITS);
    private final Text angleText = new Text("Angle:");
    private final UnitTextField<Angle> angleTextField = new UnitTextField<>(ANGLE_UNIT, SupportedAngle.UNITS);
    private final GridPane rotateGrid = new GridPane();
    private final Button applyRotate = new Button("Rotate Counterclockwise");
    private final Button applyInverseRotate = new Button("Rotate Clockwise");

    private final HBox translateButtons = new HBox(applyTranslate, applyInverseTranslate);
    private final HBox rotateButtons = new HBox(applyRotate, applyInverseRotate);
    private final VBox vBox = new VBox(translateGrid, translateButtons,
            rotateGrid, rotateButtons);
    private final Scene scene = new Scene(vBox);
    private final Stage stage = new Stage();

    public TransformDialog(HDocument document) {
        this.document = document;

        GridPane.setConstraints(offsetXText, 0, 0);
        GridPane.setConstraints(offsetXTextField, 1, 0);
        GridPane.setConstraints(offsetYText, 0, 1);
        GridPane.setConstraints(offsetYTextField, 1, 1);
        translateGrid.setHgap(10);
        translateGrid.setVgap(10);
        translateGrid.getChildren().addAll(offsetXText, offsetXTextField, offsetYText, offsetYTextField);

        GridPane.setConstraints(axisXText, 0, 0);
        GridPane.setConstraints(axisXTextField, 1, 0);
        GridPane.setConstraints(axisYText, 0, 1);
        GridPane.setConstraints(axisYTextField, 1, 1);
        GridPane.setConstraints(angleText, 0, 2);
        GridPane.setConstraints(angleTextField, 1, 2);
        rotateGrid.setHgap(10);
        rotateGrid.setVgap(10);
        rotateGrid.getChildren().addAll(axisXText, axisXTextField, axisYText, axisYTextField, angleText, angleTextField);

        translateButtons.setAlignment(Pos.CENTER_RIGHT);
        rotateButtons.setAlignment(Pos.CENTER_RIGHT);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.setResizable(false);

        offsetXTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        offsetYTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        axisXTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        axisYTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);
        angleTextField.setDecimalFormat(Standards.GUI_NUMBER_FORMAT);

        translate.xProperty().bind(offsetXTextField.valueProperty());
        translate.yProperty().bind(offsetYTextField.valueProperty());

        rotate.pivotXProperty().bind(axisXTextField.valueProperty());
        rotate.pivotYProperty().bind(axisYTextField.valueProperty());
        rotate.angleProperty().bind(DoubleProperty.doubleProperty(angleTextField.valueProperty()).multiply(180 / Math.PI));

        applyTranslate.setOnAction(event -> {
            if (this.document.isPathSelected()) {
                this.document.getSelectedPath().transformSelectedElementsRelative(translate);
            }
        });
        applyInverseTranslate.setOnAction(event -> {
            if (document.isPathSelected()) {
                this.document.getSelectedPath().transformSelectedElementsRelative(translate.createInverse());
            }
        });
        applyRotate.setOnAction(event -> {
            if (document.isPathSelected()) {
                this.document.getSelectedPath().transformSelectedElementsRelative(rotate);
            }
        });
        applyInverseRotate.setOnAction(event -> {
            if (document.isPathSelected()) {
                try {
                    this.document.getSelectedPath().transformSelectedElementsRelative(rotate.createInverse());
                } catch (NonInvertibleTransformException e) {
                }
            }
        });
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }
}