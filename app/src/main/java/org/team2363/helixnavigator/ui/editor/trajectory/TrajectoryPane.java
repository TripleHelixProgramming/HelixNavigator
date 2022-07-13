package org.team2363.helixnavigator.ui.editor.trajectory;

import org.team2363.helixnavigator.document.DocumentManager;

import javafx.scene.layout.Pane;

public class TrajectoryPane extends Pane {

    private final DocumentManager documentManager;

    private final SegmentsPane segmentsPane;
    private final AnimationPane animationPane;
    
    public TrajectoryPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        segmentsPane = new SegmentsPane(this.documentManager);
        animationPane = new AnimationPane(this.documentManager);

        getChildren().addAll(segmentsPane, animationPane);
        setPickOnBounds(false);
    }
}