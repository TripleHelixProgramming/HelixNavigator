package org.team2363.helixnavigator.ui.editor.line;

import java.util.List;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public class LinesPane extends Pane {

    private final DocumentManager documentManager;

    // private final ObservableList<LineView> lineViews = FXCollections.<LineView>observableArrayList();

    private final ChangeListener<? super HPath> onSelectedPathChanged = this::selectedPathChanged;
    private final ListChangeListener<? super HWaypoint> onWaypointsChanged = this::waypointsChanged;
    
    public LinesPane(DocumentManager documentManager) {
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
            // lineViews.clear();
            getChildren().clear();
            oldPath.getWaypoints().removeListener(onWaypointsChanged);
        }
    }

    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            updateWaypoints(newPath.getWaypoints());
            newPath.getWaypoints().addListener(onWaypointsChanged);
        }
    }

    private void waypointsChanged(ListChangeListener.Change<? extends HWaypoint> change) {
        updateWaypoints(change.getList());
    }

    private void updateWaypoints(List<? extends HWaypoint> list) {
        // lineViews.clear();
        getChildren().clear();
        for (int i = 0; i < list.size() - 1; i++) {
            LineView lineView = new LineView();
            linkLineView(lineView, list.get(i), list.get(i + 1));
            // lineViews.add(i, lineView);
            getChildren().add(i, lineView.getView());
        }
    }

    private void linkLineView(LineView lineView, HWaypoint initialWaypoint, HWaypoint finalWaypoint) {
        lineView.startPointXProperty().bind(initialWaypoint.xProperty());
        lineView.startPointYProperty().bind(initialWaypoint.yProperty());
        lineView.endPointXProperty().bind(finalWaypoint.xProperty());
        lineView.endPointYProperty().bind(finalWaypoint.yProperty());
        lineView.zoomScaleProperty().bind(documentManager.getDocument().zoomScaleProperty());
        lineView.getView().setOnMouseClicked(documentManager.actions()::handleMouseClickedAsClearSelection);
    }
}