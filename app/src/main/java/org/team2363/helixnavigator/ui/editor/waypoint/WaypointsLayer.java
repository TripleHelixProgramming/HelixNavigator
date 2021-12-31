package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;

public class WaypointsLayer extends Pane {

    private final DocumentManager documentManager;
    
    public WaypointsLayer(DocumentManager documentManager) {
        this.documentManager = documentManager;
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        
    }

    private void unloadDocument() {

    }

    private void loadDocument() {

    }
}