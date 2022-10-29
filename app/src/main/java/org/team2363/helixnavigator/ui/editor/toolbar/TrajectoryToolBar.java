package org.team2363.helixnavigator.ui.editor.toolbar;

import static org.team2363.helixnavigator.global.Standards.ExportedUnits.TIME_UNIT;

import java.io.File;
import java.io.IOException;

import javax.measure.quantity.Time;

import org.team2363.helixnavigator.document.DocumentActions;
import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.HTrajectory;
import org.team2363.helixnavigator.global.Standards;
import org.team2363.lib.ui.validation.UnitTextField;

import com.jlbabilino.json.InvalidJSONTranslationConfiguration;
import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.JSONSerializerException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class TrajectoryToolBar extends ToolBar {

    private final DocumentManager documentManager;

    private final ChangeListener<? super HPath> onSelectedPathChanged = this::selectedPathChanged;
    private final ChangeListener<? super HTrajectory> onTrajectoryChanged = this::trajectoryChanged;

    private final Button generateTraj = new Button("Generate");
    private final Button importTraj = new Button("Import Traj");
    private final Button exportTraj = new Button ("Export Traj");
    private final Slider timestampSlider = new Slider();
    private final UnitTextField<Time> timestampInput = new UnitTextField<>(TIME_UNIT, Standards.SupportedUnits.SupportedTime.UNITS);
    private final ToggleButton animateButton = new ToggleButton("Animate");

    private Timeline timeline;

    public TrajectoryToolBar(DocumentManager documentManager) {
        this.documentManager = documentManager;

        timestampSlider.setMinWidth(400.0);
        getItems().addAll(importTraj, exportTraj, generateTraj, timestampSlider, animateButton);

        DocumentActions.IS_GENERATION_RUNNING.addListener((obsVal, isIdle, isGenerating) -> {
            System.out.println("Generation status changed to: " + isGenerating);
            if (isIdle) {
                generateTraj.setText("Generate");
            } else {
                generateTraj.setText("Generating...");
            }
            generateTraj.setDisable(isGenerating);
        });
        generateTraj.setOnAction(event -> {
            documentManager.actions().generateTrajectory();
        });
        importTraj.setOnAction(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                FileChooser chooser = new FileChooser();
                File result = chooser.showOpenDialog(this.documentManager.getStage());
                try {
                    HTrajectory traj = JSONDeserializer.deserialize(result, HTrajectory.class);
                    this.documentManager.getDocument().getSelectedPath().setTrajectory(traj);
                    System.out.println("Loaded traj");
                } catch (IOException | InvalidJSONTranslationConfiguration | JSONDeserializerException e) {
                    System.out.println("Error when importing traj: " + e.getMessage());
                }
            }
        });
        exportTraj.setOnAction(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected() &&
                    this.documentManager.getDocument().getSelectedPath().getTrajectory() != null) {
                HTrajectory traj = this.documentManager.getDocument().getSelectedPath().getTrajectory();
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().add(Standards.TRAJECTORY_FILE_TYPE);
                File result = chooser.showSaveDialog(this.documentManager.getStage());
                if (result != null) {
                    try {
                        JSONSerializer.serializeFile(traj, result);
                        System.out.println("Exported traj");
                    } catch (IOException | InvalidJSONTranslationConfiguration | JSONSerializerException e) {
                        System.out.println("Error when exporting traj: " + e.getMessage());
                    }
                }
            }
        });
        animateButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected() &&
                    documentManager.getDocument().getSelectedPath().getTrajectory() != null) {
                updateAnimationMode(isSelected);
            }
        });

        timestampSlider.setBlockIncrement(0.001);
        timestampSlider.setMin(0.0);

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            unloadSelectedPath(oldDocument.getSelectedPath());
            oldDocument.selectedPathProperty().removeListener(onSelectedPathChanged);
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            loadSelectedPath(newDocument.getSelectedPath());
            newDocument.selectedPathProperty().addListener(onSelectedPathChanged);
        }
    }

    private void selectedPathChanged(ObservableValue<? extends HPath> currentPath, HPath oldPath, HPath newPath) {
        unloadSelectedPath(oldPath);
        loadSelectedPath(newPath);
    }

    private void unloadSelectedPath(HPath oldPath) {
        if (oldPath != null) {
            unloadTrajectory(oldPath.getTrajectory());
            oldPath.trajectoryProperty().removeListener(onTrajectoryChanged);
        }
    }

    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            loadTrajectory(newPath.getTrajectory());
            newPath.trajectoryProperty().addListener(onTrajectoryChanged);
        }
    }

    private void trajectoryChanged(ObservableValue<? extends HTrajectory> currentTrajectory, HTrajectory oldTrajectory, HTrajectory newTrajectory) {
        unloadTrajectory(oldTrajectory);
        loadTrajectory(newTrajectory);
    }
    private void unloadTrajectory(HTrajectory oldTrajectory) {
        if (oldTrajectory != null) {
            oldTrajectory.timestampProperty().unbind();
            updateAnimationMode(false);
        }
    }
    private void loadTrajectory(HTrajectory newTrajectory) {
        if (newTrajectory != null) {
            timestampSlider.setMax(newTrajectory.duration);
            newTrajectory.timestampProperty().bind(timestampSlider.valueProperty());
            HTrajectory traj = documentManager.getDocument().getSelectedPath().getTrajectory();
            KeyValue initialValue = new KeyValue(timestampSlider.valueProperty(), 0.0);
            KeyFrame initialFrame = new KeyFrame(Duration.ZERO, initialValue);
            KeyValue endValue = new KeyValue(timestampSlider.valueProperty(), traj.duration);
            KeyFrame endFrame = new KeyFrame(Duration.seconds(traj.duration), endValue);
            timeline = new Timeline(initialFrame, endFrame);
            timeline.setAutoReverse(true);
            timeline.setCycleCount(-1);
            // timeline.play();
            updateAnimationMode(animateButton.isSelected());
        }
    }

    private void updateAnimationMode(boolean isAnimating) {
        if (isAnimating) {
            timeline.playFrom(Duration.seconds(timestampSlider.getValue()));
        } else {
            timeline.stop();
        }
    }
}