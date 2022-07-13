package org.team2363.helixnavigator.ui.editor.trajectory;

import java.util.List;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.HTrajectory;
import org.team2363.helixnavigator.document.HTrajectorySample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;

public class SegmentsPane extends Pane {

    private final DocumentManager documentManager;

    private final ChangeListener<? super HPath> onSelectedPathChanged = this::selectedPathChanged;
    private final ChangeListener<? super HTrajectory> onTrajectoryChanged = this::trajectoryChanged;
    
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
            getChildren().clear();
        }
    }
    private void loadTrajectory(HTrajectory newTrajectory) {
        if (newTrajectory != null) {
            List<HTrajectorySample> samples = newTrajectory.samples;
            for (int i = 0; i < samples.size() - 1; i++) {
                SegmentView view = new SegmentView(samples.get(i).x, samples.get(i).y,
                        samples.get(i+1).x, samples.get(i+1).y);
                view.zoomScaleProperty().bind(documentManager.getDocument().zoomScaleProperty());
                getChildren().add(view.getView());
            }
        }
    }
}