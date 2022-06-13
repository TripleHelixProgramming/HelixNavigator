package org.team2363.lib.ui.validation;

import java.text.DecimalFormat;
import java.util.function.UnaryOperator;

import javax.measure.Quantity;
import javax.measure.Unit;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import tech.units.indriya.quantity.Quantities;

public class UnitTextField<T extends Quantity<T>> extends HBox {

    private final Unit<T> baseUnit;

    private final ChoiceBox<Unit<T>> unitChoiceBox = new ChoiceBox<>();
    private final MathExpressionTextField textField = new MathExpressionTextField();

    private UnaryOperator<Double> inputTransformation = input -> input;

    public UnitTextField(Unit<T> baseUnit, ObservableList<Unit<T>> unitChoices) {
        // System.out.println("TESTING: Instantiating unit text field");
        this.baseUnit = baseUnit;

        getChildren().addAll(textField, unitChoiceBox);
        unitChoiceBox.setMinWidth(60.0);
        unitChoiceBox.setMaxWidth(60.0);
        HBox.setHgrow(textField, Priority.ALWAYS);

        unitChoiceBox.setItems(unitChoices);
        unitChoiceBox.getSelectionModel().select(0);
        unitChoiceBox.setOnAction(event -> {
            // Might be a more natural way to do this.
            // You need to probe the text field so the text gets updated.
            textField.setValue(textField.getValue());
        });

        textField.setInputTransformation(input -> {
            Unit<T> chosenUnit = unitChoiceBox.getValue();
            Quantity<T> quantity = Quantities.getQuantity(input, chosenUnit);
            input = quantity.to(baseUnit).getValue().doubleValue();
            return inputTransformation.apply(input);
        });
        textField.setOutputTransformation(output -> {
            Unit<T> chosenUnit = unitChoiceBox.getValue();
            Quantity<T> quantity = Quantities.getQuantity(output, this.baseUnit);
            output = quantity.to(chosenUnit).getValue().doubleValue();
            return output;
        });
    }

    public ObjectProperty<Double> valueProperty() {
        return textField.valueProperty();
    }
    public void setValue(double value) {
        textField.setValue(value);
    }
    public double getValue() {
        return textField.getValue();
    }

    public void setCurrentUnit(Unit<T> unit) {
        unitChoiceBox.getSelectionModel().select(unit);
        textField.setValue(textField.getValue());
    }

    public void setDecimalFormat(DecimalFormat decimalFormat) {
        textField.setDecimalFormat(decimalFormat);
    }
    public void setInputTransformation(UnaryOperator<Double> transformation) {
        //TODO: add null checks for MathExpressionTextField
        if (transformation != null) {
            inputTransformation = transformation;
        }
    }
}
