package org.team2363.helixnavigator.ui.menu.edit;

import com.jlbabilino.json.JSONArray;
import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONEntry;
import com.jlbabilino.json.JSONObject;
import com.jlbabilino.json.JSONParser;
import com.jlbabilino.json.JSONParserException;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCombination;

public class PasteMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    public PasteMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;
        setText("Paste");
        setAccelerator(KeyCombination.keyCombination("shortcut+V"));
        setOnAction(this::action);
    }

    public void action(ActionEvent event) {
        Clipboard systemClipboard = Clipboard.getSystemClipboard();
        if (systemClipboard.hasString()) {
            String data = systemClipboard.getString();
            try {
                JSONEntry jsonEntry = JSONParser.parseJSONEntry(data);
                paste(jsonEntry);
            } catch (JSONParserException e) {
            }
        }
    }

    public void paste(JSONEntry jsonEntry) {
        if (documentManager.getIsDocumentOpen()) {
            HDocument document = documentManager.getDocument();
            try {
                if (jsonEntry.isObject()) {
                    JSONObject jsonObject = (JSONObject) jsonEntry;
                    if (jsonObject.containsKey("waypoints")) {
                        HPath pastedPath = JSONDeserializer.deserialize(jsonEntry, HPath.class);
                        document.getPaths().add(pastedPath);
                        document.setSelectedPathIndex(document.getPaths().size() - 1);
                    } else if (documentManager.getDocument().isPathSelected()) {
                        if (jsonObject.containsKey("waypoint_type")) {
                            HWaypoint waypoint = JSONDeserializer.deserialize(jsonEntry, HWaypoint.class);
                            int index = document.getSelectedPath().getWaypointsSelectionModel().getSelectedIndex() + 1;
                            document.getSelectedPath().getWaypoints().add(index, waypoint);
                        } else if (jsonObject.containsKey("obstacle_type")) {
                            HObstacle obstacle = JSONDeserializer.deserialize(jsonEntry, HObstacle.class);
                            int index = document.getSelectedPath().getObstaclesSelectionModel().getSelectedIndex() + 1;
                            document.getSelectedPath().getObstacles().add(index, obstacle);
                        }
                    }
                } else if (jsonEntry.isArray()) {
                    JSONArray jsonArray = (JSONArray) jsonEntry;
                    System.out.println(jsonArray.toJSONText());
                    for (JSONEntry entry : jsonArray) {
                        // System.out.println(entry.toJSONText());
                        // System.out.println();
                        paste(entry);
                    }
                }
            } catch (JSONDeserializerException e) {
            }
        }
    }
}