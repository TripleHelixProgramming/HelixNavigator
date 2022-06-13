package org.team2363.helixnavigator.ui.editor.toolbar;

import java.io.File;
import java.io.IOException;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HTrajectory;

import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.stage.FileChooser;

public class TrajectoryToolBar extends ToolBar {

    private final DocumentManager documentManager;

    private final Button importTraj = new Button("Import Traj");

    public TrajectoryToolBar(DocumentManager documentManager) {
        this.documentManager = documentManager;

        getItems().addAll(importTraj);

        importTraj.setOnAction(event -> {
            if (this.documentManager.getIsDocumentOpen() && this.documentManager.getDocument().isPathSelected()) {
                FileChooser chooser = new FileChooser();
                File result = chooser.showOpenDialog(this.documentManager.getStage());
                try {
                    HTrajectory traj = JSONDeserializer.deserialize(result, HTrajectory.class);
                    this.documentManager.getDocument().getSelectedPath().setTrajectory(traj);
                    System.out.println("Loaded traj");
                } catch (IOException | JSONDeserializerException e) {
                    System.out.println("Error when importing traj: " + e.getMessage());
                }
            }
        });
    }
}