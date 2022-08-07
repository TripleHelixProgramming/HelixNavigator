package org.team2363.helixnavigator.ui.editor.trajectory;

import java.util.List;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.compiled.HHolonomicTrajectory;
import org.team2363.helixnavigator.document.compiled.HHolonomicTrajectorySample;
import org.team2363.helixnavigator.document.HAutoRoutine;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;

public class SegmentsPane extends Pane {

    private final DocumentManager documentManager;

    private final ChangeListener<? super HAutoRoutine> onSelectedPathChanged = this::selectedPathChanged;
    private final ChangeListener<? super HHolonomicTrajectory> onTrajectoryChanged = this::trajectoryChanged;
    
    public SegmentsPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setMouseTransparent(true);

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            unloadSelectedPath(oldDocument.getSelectedAutoRoutine());
            oldDocument.selectedPathProperty().removeListener(onSelectedPathChanged);
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            loadSelectedPath(newDocument.getSelectedAutoRoutine());
            newDocument.selectedPathProperty().addListener(onSelectedPathChanged);
        }
    }

    private void selectedPathChanged(ObservableValue<? extends HAutoRoutine> currentPath, HAutoRoutine oldPath, HAutoRoutine newPath) {
        unloadSelectedPath(oldPath);
        loadSelectedPath(newPath);
    }

    private void unloadSelectedPath(HAutoRoutine oldPath) {
        if (oldPath != null) {
            unloadTrajectory(oldPath.getCompiledAutoRoutine());
            oldPath.compiledAutoRoutineProperty().removeListener(onTrajectoryChanged);
        }
    }

    private void loadSelectedPath(HAutoRoutine newPath) {
        if (newPath != null) {
            loadTrajectory(newPath.getCompiledAutoRoutine());
            newPath.compiledAutoRoutineProperty().addListener(onTrajectoryChanged);
        }
    }

    private void trajectoryChanged(ObservableValue<? extends HHolonomicTrajectory> currentTrajectory, HHolonomicTrajectory oldTrajectory, HHolonomicTrajectory newTrajectory) {
        unloadTrajectory(oldTrajectory);
        loadTrajectory(newTrajectory);
    }
    private void unloadTrajectory(HHolonomicTrajectory oldTrajectory) {
        if (oldTrajectory != null) {
            getChildren().clear();
        }
    }
    private void loadTrajectory(HHolonomicTrajectory newTrajectory) {
        if (newTrajectory != null) {
            List<HHolonomicTrajectorySample> samples = newTrajectory.samples;
            for (int i = 0; i < samples.size() - 1; i++) {
                SegmentView view = new SegmentView(samples.get(i).x, samples.get(i).y,
                        samples.get(i+1).x, samples.get(i+1).y);
                view.zoomScaleProperty().bind(documentManager.getDocument().zoomScaleProperty());
                getChildren().add(view.getView());
            }
        }
    }
}