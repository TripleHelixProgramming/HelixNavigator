package org.team2363.helixnavigator.ui.menu.tools;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.ui.prompts.documentconfig.DocumentConfigDialog;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class EditDocumentConfigurationMenuItem extends MenuItem {

    private final DocumentManager documentManager;

    private DocumentConfigDialog dialog;
    
    public EditDocumentConfigurationMenuItem(DocumentManager documentManager) {
        this.documentManager = documentManager;

        setText("Edit Document Configuration...");
        setOnAction(this::action);
        disableProperty().bind(this.documentManager.isDocumentOpenProperty().not());

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);
    }

    public void action(ActionEvent event) {
        dialog.show();
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            dialog.close();
            dialog = null;
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            dialog = new DocumentConfigDialog(newDocument);
        }
    }
}