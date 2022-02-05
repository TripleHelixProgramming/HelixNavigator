// package org.team2363.lib.ui.prompts;

// import javafx.application.Platform;
// import javafx.beans.property.ObjectProperty;
// import javafx.scene.control.TextFormatter;
// import javafx.scene.control.TextInputDialog;

// public class IntegerTextInputDialog extends TextInputDialog {

//     private final ObjectProperty<Integer> value;

//     public IntegerTextInputDialog() {
//         TextFormatter<Integer> formatter = new TextFormatter<>(IntegerTextField.INTEGER_CONVERTER, 0, IntegerTextField.INTEGER_FILTER);
//         value = formatter.valueProperty();
//         getEditor().setTextFormatter(formatter);
//         getEditor().setText("0");
//         getEditor().focusedProperty().addListener((val, wasFocused, isNowFocused) -> {
//             if (isNowFocused) {
//                 Platform.runLater(getEditor()::selectAll);
//             }
//         });
//         setHeaderText("Enter an integer");
//     }

//     public final ObjectProperty<Integer> valueProperty() {
//         return value;
//     }

//     public final void setValue(int value) {
//         this.value.set(value);
//     }

//     public final int getValue() {
//         return value.get();
//     }
// }