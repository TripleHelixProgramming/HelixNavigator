// /*
//  * Copyright (C) 2021 Justin Babilino
//  *
//  * This program is free software: you can redistribute it and/or modify
//  * it under the terms of the GNU General Public License as published by
//  * the Free Software Foundation, either version 3 of the License, or
//  * (at your option) any later version.
//  *
//  * This program is distributed in the hope that it will be useful,
//  * but WITHOUT ANY WARRANTY; without even the implied warranty of
//  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  * GNU General Public License for more details.
//  *
//  * You should have received a copy of the GNU General Public License
//  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
//  */
// package org.team2363.helixnavigator.ui.document;

// import org.team2363.helixnavigator.document.DocumentManager;
// import org.team2363.helixnavigator.document.HPath;
// import org.team2363.helixnavigator.ui.prompts.StrictStringPrompt;

// import javafx.event.ActionEvent;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Alert.AlertType;
// import javafx.scene.control.Button;
// import javafx.scene.control.ButtonType;
// import javafx.scene.control.ComboBox;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.util.StringConverter;

// /**
//  *
//  * @author Justin Babilino
//  */
// public class PathChooserBox extends HBox {
//     private static final StringConverter<HPath> stringConverter = new StringConverter<HPath>() {
//         @Override
//         public String toString(HPath object) {
//             if (object == null) {
//                 return "";
//             }
//             return object.getName();
//         }

//         @Override
//         public HPath fromString(String string) {
//             return null;
//         }
//     };
//     private final ComboBox<HPath> pathChooser = new ComboBox<>();
//     private final Button plusButton = new Button("+");
//     private final Button minusButton = new Button("-");

//     public PathChooserBox() {
//         CurrentDocument.documentProperty().addListener((currentVal, oldVal, newVal) -> refreshDocument());
//         pathChooser.setOnAction(this::pathSelected);
//         pathChooser.setConverter(stringConverter);
//         pathChooser.setMaxWidth(200);
//         HBox.setHgrow(pathChooser, Priority.ALWAYS);
//         plusButton.setOnAction(this::plusButtonPressed);
//         minusButton.setOnAction(this::minusButtonPressed);
//         setSpacing(10.0);
//         getChildren().addAll(pathChooser, plusButton, minusButton);
//     }

//     private void pathSelected(ActionEvent e) {
//         if (CurrentDocument.isDocumentOpen()) {
//             CurrentDocument.getDocument().setSelectedPathIndex(pathChooser.getSelectionModel().getSelectedIndex());
//         }
//     }

//     private void plusButtonPressed(ActionEvent e) {
//         if (CurrentDocument.isDocumentOpen()) {
//             StrictStringPrompt prompt = new StrictStringPrompt("Enter a valid path name", "Path name",
//                     HPath.MAX_PATH_NAME_LENGTH, HPath.VALID_PATH_NAME);
//             prompt.showAndWait().ifPresent(response -> {
//                 HPath path = new HPath();
//                 path.setName(response);
//                 int newPathIndex = CurrentDocument.getDocument().getPaths().size();
//                 CurrentDocument.getDocument().getPaths().add(newPathIndex, path);
//                 CurrentDocument.getDocument().setSelectedPathIndex(newPathIndex);
//             });
//         }
//     }

//     private void minusButtonPressed(ActionEvent e) {
//         if (CurrentDocument.isDocumentOpen() && !CurrentDocument.getDocument().getPaths().isEmpty()) {
//             Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this path \""
//                     + pathChooser.getValue().nameProperty().getValue() + "\"?");
//             alert.showAndWait().filter(result -> result == ButtonType.OK).ifPresent(result -> {
//                 CurrentDocument.getDocument().getPaths().remove(CurrentDocument.getDocument().getSelectedPathIndex());
//                 if (CurrentDocument.getDocument().getSelectedPathIndex() < 0
//                         && !CurrentDocument.getDocument().getPaths().isEmpty()) {
//                     CurrentDocument.getDocument().setSelectedPathIndex(0);
//                 }
//             });
//         }
//     }

//     private void refreshDocument() {
//         pathChooser.setItems(CurrentDocument.getDocument().getPaths());
//         pathChooser.getSelectionModel().select(CurrentDocument.getDocument().getSelectedPathIndex());
//         CurrentDocument.getDocument().selectedPathIndexProperty().addListener((currentVal, oldVal, newVal) -> {
//             Console.out.set("index = " + newVal.intValue());
//             pathChooser.getSelectionModel().select(newVal.intValue());
//         });
//     }
// }
