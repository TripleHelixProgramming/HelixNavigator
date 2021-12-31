package org.team2363.helixnavigator.ui.editor.field;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.field.image.HFieldImage;

import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;

public class FieldImageView extends ImageView {

    private final DocumentManager documentManager;

    public FieldImageView(DocumentManager documentManager) {
        this.documentManager = documentManager;
        documentManager.documentProperty().addListener(this::documentChanged);
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            unloadFieldImage(oldDocument.getFieldImage());
            unloadSelectedPath(oldDocument.getSelectedPath());
            oldDocument.fieldImageProperty().removeListener(this::fieldImageChanged);
            oldDocument.selectedPathProperty().removeListener(this::selectedPathChanged);
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            loadFieldImage(newDocument.getFieldImage());
            loadSelectedPath(newDocument.getSelectedPath());
            newDocument.fieldImageProperty().addListener(this::fieldImageChanged);
            newDocument.selectedPathProperty().addListener(this::selectedPathChanged);
        }
    }

    private void fieldImageChanged(ObservableValue<? extends HFieldImage> currentFieldImage, HFieldImage oldFieldImage, HFieldImage newFieldImage) {
        unloadFieldImage(oldFieldImage);
        loadFieldImage(newFieldImage);
    }

    private void unloadFieldImage(HFieldImage fieldImage) {
        if (fieldImage != null) {
            setImage(null);
        }
    }

    private void loadFieldImage(HFieldImage fieldImage) {
        if (fieldImage != null) {
            setImage(fieldImage.getImage());
        }
    }

    private void selectedPathChanged(ObservableValue<? extends HPath> currentPath, HPath oldPath, HPath newPath) {
        unloadSelectedPath(oldPath);
        loadSelectedPath(newPath);
    }

    private void unloadSelectedPath(HPath oldPath) {
        if (oldPath != null) {
            xProperty().unbind();
            yProperty().unbind();
            setOnScroll(null);
        }
    }

    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            xProperty().bind(newPath.zoomXOffsetProperty());
            yProperty().bind(newPath.zoomYOffsetProperty());
            setOnScroll(newPath::handleScroll);
        }
    }
}