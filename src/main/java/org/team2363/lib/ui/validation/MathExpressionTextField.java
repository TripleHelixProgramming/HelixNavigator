package org.team2363.lib.ui.validation;

import static org.team2363.lib.ui.validation.FilteredTextField.filterFor;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import org.team2363.lib.math.ExpressionParser;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class MathExpressionTextField extends TextField {

    public static final Pattern EXPRESSION_VALIDATOR = Pattern.compile(".*");
    public static final UnaryOperator<TextFormatter.Change> EXPRESSION_FILTER = filterFor(Integer.MAX_VALUE, EXPRESSION_VALIDATOR);
    
    private UnaryOperator<Double> inputTransformation = input -> input;
    private UnaryOperator<Double> outputTransformation = output -> output;
    private final StringConverter<Double> expressionConverter = new StringConverter<Double>() {

        @Override
        public String toString(Double object) {
            if (object != null) {
                return Double.toString(outputTransformation.apply(object));
            } else {
                return "";
            }
        }

        @Override
        public Double fromString(String string) {
            double rawValue;
            try {
                rawValue = ExpressionParser.eval(string);
            } catch (Exception e) {
                rawValue = 0.0;
            }
            return inputTransformation.apply(rawValue);
        }
    };
    private final ObjectProperty<Double> value;

    public MathExpressionTextField() {
        // System.out.println("TESTING: Instantiating Math text field");
        TextFormatter<Double> formatter = new TextFormatter<Double>(expressionConverter, 0.0, EXPRESSION_FILTER);
        value = formatter.valueProperty();
        setTextFormatter(formatter);
        setText("0.0");
        focusedProperty().addListener((val, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                Platform.runLater(this::selectAll);
            }
        });
        setOnAction(event -> {
            selectAll();
        });
    }

    public final ObjectProperty<Double> valueProperty() {
        return value;
    }
    public final void setValue(double value) {
        this.value.set(value);
    }
    public final double getValue() {
        return value.get();
    }

    public void setInputTransformation(UnaryOperator<Double> transformation) {
        inputTransformation = transformation;
    }
    public void setOutputTransformation(UnaryOperator<Double> transformation) {
        outputTransformation = transformation;
    }
}