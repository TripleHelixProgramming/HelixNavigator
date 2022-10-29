package org.team2363.helixnavigator.ui.document;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.lib.ui.validation.FilteredTextInputDialog;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class PathChooserBox extends HBox {

    private static final ObservableList<HPath> BLANK = FXCollections.<HPath>observableArrayList();

    private final DocumentManager documentManager;

    private final ComboBox<HPath> pathChooser = new ComboBox<>(); // starts with blank array
    private final Button plusButton = new Button("+");
    private final Button minusButton = new Button("-");
    private final Button renameButton = new Button("R");

    private final ChangeListener<? super Number> onSelectedPathIndexChanged = this::selectedPathIndexChanged;

    public PathChooserBox(DocumentManager documentManager) {
        this.documentManager = documentManager;

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);

        pathChooser.setOnAction(this::pathSelected);
        // Note that it is not necessary to check if the selected path changes in the document since
        // the path chooser box has exclusive control over the selected path, no other ui elements
        // should change it.
        pathChooser.setCellFactory(PathListCell.PATH_CELL_FACTORY);
        // seems weird to me that this doesn't happen automatically:
        pathChooser.setButtonCell(new PathListCell());
        pathChooser.setMaxWidth(Double.POSITIVE_INFINITY);
        HBox.setHgrow(pathChooser, Priority.ALWAYS);
        plusButton.setOnAction(this::plusButtonPressed);
        minusButton.setOnAction(this::minusButtonPressed);
        renameButton.setOnAction(this::renameButtonPressed);
        setSpacing(10.0);
        getChildren().addAll(pathChooser, plusButton, minusButton, renameButton);
    }

    private void pathSelected(ActionEvent event) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().hasPaths()) {
            int newIndex = pathChooser.getSelectionModel().getSelectedIndex();
            if (newIndex < 0) {
                newIndex = 0; // this fixes a bug in ComboBox where
                              // deleting the first item in the list
                              // causes the selected index to go to -1
                pathChooser.getSelectionModel().select(newIndex);
            }
            documentManager.getDocument().setSelectedPathIndex(newIndex);
        }
    }

    private void plusButtonPressed(ActionEvent event) {
        if (documentManager.getIsDocumentOpen()) {
            FilteredTextInputDialog prompt = new FilteredTextInputDialog();
            prompt.setHeaderText("Enter a valid path name");
            prompt.getEditor().setPromptText("Path name");
            prompt.setMaxChars(Standards.MAX_NAME_LENGTH);
            prompt.setValidator(Standards.VALID_NAME);
            prompt.showAndWait().ifPresent(response -> {
                HPath path = new HPath();
                path.setName(response);
                int newPathIndex = documentManager.getDocument().getPaths().size();
                documentManager.getDocument().getPaths().add(path);
                documentManager.getDocument().setSelectedPathIndex(newPathIndex);
            });
        }
    }

    private void minusButtonPressed(ActionEvent event) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this path \""
                    + pathChooser.getValue().getName() + "\"?");
            alert.showAndWait().filter(result -> result == ButtonType.OK).ifPresent(result -> {
                documentManager.getDocument().getPaths().remove(documentManager.getDocument().getSelectedPathIndex());
                // this will trigger pathSelected()
            });
        }
    }

    private void renameButtonPressed(ActionEvent event) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            FilteredTextInputDialog prompt = new FilteredTextInputDialog();
            prompt.setHeaderText("Enter a valid path name");
            prompt.getEditor().setPromptText("Renamed path name");
            prompt.setMaxChars(Standards.MAX_NAME_LENGTH);
            prompt.setValidator(Standards.VALID_NAME);
            prompt.showAndWait().ifPresent(response -> {
                documentManager.getDocument().getSelectedPath().setName(response);
                // TODO: the name on the combo box doesn't update dynamically,
                // meaning renaming doesn't have a visual effect.
                // This can be fixed by making a custom cell for HPath
                // (Similar to WaypointListCell)
            });
        }
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            pathChooser.setItems(BLANK);
            oldDocument.selectedPathIndexProperty().removeListener(onSelectedPathIndexChanged);
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            pathChooser.setItems(newDocument.getPaths());
            loadSelectedPathIndex(newDocument.getSelectedPathIndex());
            newDocument.selectedPathIndexProperty().addListener(onSelectedPathIndexChanged);
        }
    }

    private void selectedPathIndexChanged(ObservableValue<? extends Number> currentIndex, Number oldIndex, Number newIndex) {
        loadSelectedPathIndex(newIndex);
    }

    private void loadSelectedPathIndex(Number newIndex) {
        pathChooser.getSelectionModel().select(newIndex.intValue());
    }
}
