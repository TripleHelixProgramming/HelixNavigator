/*
 * Copyright (C) 2021 Justin Babilino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.team2363.helixnavigator.ui.document;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.ui.prompts.StrictStringPrompt;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;

public class PathChooserBox extends HBox {

    private static final StringConverter<HPath> stringConverter = new StringConverter<HPath>() {
        @Override
        public String toString(HPath object) {
            if (object == null) {
                return "";
            }
            return object.getName();
        }

        @Override
        public HPath fromString(String string) {
            return null;
        }
    };

    private final DocumentManager documentManager;

    private final ComboBox<HPath> pathChooser = new ComboBox<>(); // starts with blank array
    private final Button plusButton = new Button("+");
    private final Button minusButton = new Button("-");

    public PathChooserBox(DocumentManager documentManager) {
        this.documentManager = documentManager;
        this.documentManager.documentProperty().addListener((currentVal, oldVal, newVal) -> refreshDocument());
        pathChooser.setOnAction(this::pathSelected);
        pathChooser.setConverter(stringConverter);
        pathChooser.setMaxWidth(200);
        HBox.setHgrow(pathChooser, Priority.ALWAYS);
        plusButton.setOnAction(this::plusButtonPressed);
        minusButton.setOnAction(this::minusButtonPressed);
        setMinHeight(USE_COMPUTED_SIZE);
        setSpacing(10.0);
        getChildren().addAll(pathChooser, plusButton, minusButton);
    }

    private void pathSelected(ActionEvent e) {
        if (documentManager.getIsDocumentOpen()) {
            documentManager.getDocument().setSelectedPathIndex(pathChooser.getSelectionModel().getSelectedIndex());
        }
    }

    private void plusButtonPressed(ActionEvent e) {
        if (documentManager.getIsDocumentOpen()) {
            StrictStringPrompt prompt = new StrictStringPrompt("Enter a valid path name", "Path name",
                    HPath.MAX_PATH_NAME_LENGTH, HPath.VALID_PATH_NAME);
            prompt.showAndWait().ifPresent(response -> {
                HPath path = new HPath();
                path.setName(response);
                int newPathIndex = documentManager.getDocument().getPaths().size();
                documentManager.getDocument().getPaths().add(newPathIndex, path);
                System.out.println(documentManager.getDocument().getPaths());
                System.out.println(pathChooser.getItems());
                documentManager.getDocument().setSelectedPathIndex(newPathIndex);
            });
        }
    }

    private void minusButtonPressed(ActionEvent e) {
        if (documentManager.getIsDocumentOpen() && !documentManager.getDocument().getPaths().isEmpty()) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this path \""
                    + pathChooser.getValue().getName() + "\"?");
            alert.showAndWait().filter(result -> result == ButtonType.OK).ifPresent(result -> {
                documentManager.getDocument().getPaths().remove(documentManager.getDocument().getSelectedPathIndex());
                if (documentManager.getDocument().getSelectedPathIndex() < 0
                        && !documentManager.getDocument().getPaths().isEmpty()) {
                    documentManager.getDocument().setSelectedPathIndex(0);
                }
            });
        }
    }

    private void refreshDocument() {
        System.out.println("PathChooserBox: Refreshing Document.");
        if (documentManager.getIsDocumentOpen()) {
            System.out.println("PathChooserBox: new document loaded");
            pathChooser.setItems(documentManager.getDocument().getPaths());
            pathChooser.getSelectionModel().select(documentManager.getDocument().getSelectedPathIndex());
            documentManager.getDocument().selectedPathIndexProperty().addListener((currentVal, oldVal, newVal) -> {
                pathChooser.getSelectionModel().select(newVal.intValue());
            });
        } else {
            pathChooser.setItems(FXCollections.<HPath>observableArrayList());
        }
    }
}
