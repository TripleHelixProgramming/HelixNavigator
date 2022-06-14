package org.team2363.helixnavigator.ui.editor.trajectory;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.HTrajectory;
import org.team2363.helixnavigator.ui.editor.waypoint.RobotView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

public class AnimationPane extends Pane {

    private final DocumentManager documentManager;

    private final RobotView robotView = new RobotView();
    private final Translate robotTranslate = new Translate();

    private final ChangeListener<? super HPath> onSelectedPathChanged = this::selectedPathChanged;
    private final ChangeListener<? super HTrajectory> onTrajectoryChanged = this::trajectoryChanged;
    
    public AnimationPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setMouseTransparent(true);

        robotView.getView().getTransforms().add(robotTranslate);

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            robotView.bumperLengthProperty().unbind();
            robotView.bumperWidthProperty().unbind();
            robotView.zoomScaleProperty().unbind();
            unloadSelectedPath(oldDocument.getSelectedPath());
            oldDocument.selectedPathProperty().removeListener(onSelectedPathChanged);
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            robotView.bumperLengthProperty().bind(newDocument.getRobotConfiguration().bumperLengthProperty());
            robotView.bumperWidthProperty().bind(newDocument.getRobotConfiguration().bumperWidthProperty());
            robotView.zoomScaleProperty().bind(newDocument.zoomScaleProperty());
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
            robotTranslate.xProperty().unbind();
            robotTranslate.yProperty().unbind();
            robotView.headingProperty().unbind();
            getChildren().clear();
        }
    }
    private void loadTrajectory(HTrajectory newTrajectory) {
        if (newTrajectory != null) {
            robotTranslate.xProperty().bind(documentManager.getDocument().zoomScaleProperty().multiply(newTrajectory.robotLocation.xProperty()));
            robotTranslate.yProperty().bind(documentManager.getDocument().zoomScaleProperty().multiply(newTrajectory.robotLocation.yProperty().negate()));
            robotView.headingProperty().bind(newTrajectory.robotLocation.headingProperty());
            getChildren().add(robotView.getView());
        }
    }

    // private void timestampChanged(ObservableValue<? extends Number> currentIndex, Number oldIndex, Number newIndex) {
    //     unloadTrajectorySampleIndex(oldIndex.intValue());
    //     loadTrajectorySampleIndex(newIndex.intValue());
    // }
    // private void unloadTrajectorySampleIndex(int oldIndex) {
    //     if (oldIndex >= 0) {
    //         robotTranslate.xProperty().unbind();
    //         robotTranslate.yProperty().unbind();
    //     }
    // }
    // private void loadTrajectorySampleIndex(int newIndex) {
    //     if (newIndex >= 0) {
    //         HTrajectory trajectory = documentManager.getDocument().getSelectedPath().getTrajectory();
    //         robotTranslate.xProperty().bind(documentManager.getDocument().zoomScaleProperty().multiply(trajectory.samples.get(newIndex).x));
    //         robotTranslate.yProperty().bind(documentManager.getDocument().zoomScaleProperty().multiply(-trajectory.samples.get(newIndex).y));
    //         robotView.setHeading(trajectory.samples.get(newIndex).heading);
    //     }
    // }
}