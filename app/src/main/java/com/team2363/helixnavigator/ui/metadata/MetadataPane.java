package com.team2363.helixnavigator.ui.metadata;

import com.team2363.helixnavigator.document.CurrentDocument;
import com.team2363.helixnavigator.document.HPath;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MetadataPane extends VBox {

    private Pane noDocumentMetaPane = new StackPane(new Text("NO DOCUMENT LOADED"));
    private Pane noPathMetaPane = new StackPane(new Text("NO PATH LOADED"));
    private Pane multiSelectionMetaPane;
    private Pane waypointSelectionMetaPane;
    private Pane obstacleSelectionMetaPane;

    private static enum Mode {
        NO_DOCUMENT, NO_PATH, MULTI_SELECTION, WAYPOINT_SELECTION, OBSTACLE_SELECTION
    }

    public MetadataPane() {
        setMode(Mode.NO_DOCUMENT);
        CurrentDocument.documentProperty().addListener((currentVal, oldVal, newVal) -> refreshDocument());

        setMinWidth(100);
    }

    private void setMode(Mode mode) {
        getChildren().clear();
        switch (mode) {
            case NO_DOCUMENT:
                getChildren().add(noDocumentMetaPane);
                break;
            case NO_PATH:
                getChildren().add(noPathMetaPane);
                break;
            case MULTI_SELECTION:
                getChildren().add(multiSelectionMetaPane);
                break;
            case WAYPOINT_SELECTION:
                getChildren().add(multiSelectionMetaPane);
                getChildren().add(waypointSelectionMetaPane);
                break;
            case OBSTACLE_SELECTION:
                getChildren().add(multiSelectionMetaPane);
                getChildren().add(obstacleSelectionMetaPane);
                break; // no need for default bc this is a private method, never will be null
        }
    }

    private void updatePanes(HPath newPath) {
        multiSelectionMetaPane = new MultiSelectionMetaPane(newPath);
    }
    
    private void refreshDocument() {
        if (CurrentDocument.getDocument() == null) {
            setMode(Mode.NO_DOCUMENT);
        } else {
            CurrentDocument.getDocument().selectedPathIndexProperty().addListener((currentVal, oldVal, newVal) -> {
                if (newVal.intValue() < 0) {
                    setMode(Mode.NO_PATH);
                } else {
                    updatePanes(CurrentDocument.getDocument().getSelectedPath());
                }
            });
        }
    }
}