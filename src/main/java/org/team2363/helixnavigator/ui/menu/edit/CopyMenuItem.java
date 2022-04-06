package org.team2363.helixnavigator.ui.menu.edit;

import java.util.ArrayList;
import java.util.List;

import com.jlbabilino.json.JSONSerializer;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.HPathElement;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCombination;

public class CopyMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public CopyMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Copy");
        setAccelerator(KeyCombination.keyCombination("shortcut+C"));
        setOnAction(this::action);
    }

    public void action(ActionEvent event) {
        String data = null;
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            HPath path = documentManager.getDocument().getSelectedPath();
            int selectedWaypointsCount = path.getWaypointsSelectionModel().getSelectedItems().size();
            int selectedObstaclesCount = path.getObstaclesSelectionModel().getSelectedItems().size();
            int totalCount = selectedWaypointsCount + selectedObstaclesCount;
            if (totalCount == 0) {
                data = JSONSerializer.serializeString(path);
            } else if (totalCount == 1 && selectedWaypointsCount == 1) {
                data = JSONSerializer.serializeString(path.getWaypointsSelectionModel().getSelectedItem());
            } else if (totalCount == 1 && selectedObstaclesCount == 1) {
                data = JSONSerializer.serializeString(path.getObstaclesSelectionModel().getSelectedItem());
            } else {
                List<HPathElement> list = new ArrayList<>();
                for (HWaypoint waypoint : path.getWaypointsSelectionModel().getSelectedItems()) {
                    list.add(waypoint);
                }
                for (HObstacle obstacle : path.getObstaclesSelectionModel().getSelectedItems()) {
                    list.add(obstacle);
                }
                data = JSONSerializer.serializeString(list);
            }
        }
        if (data != null) {
            Clipboard systemClipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(data);
            systemClipboard.setContent(content);
        }
    }
}