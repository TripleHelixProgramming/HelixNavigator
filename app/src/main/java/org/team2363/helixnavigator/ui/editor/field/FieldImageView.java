package org.team2363.helixnavigator.ui.editor.field;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.field.image.HFieldImage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class FieldImageView extends ImageView {

    private final DocumentManager documentManager;

    private final Scale unitsScale = new Scale();
    private final Translate centerTranslate = new Translate();
    private final Scale zoomScale = new Scale();
    private final Translate zoomTranslate = new Translate();

    private final ChangeListener<? super HFieldImage> onFieldImageChanged = this::fieldImageChanged;
    private final ChangeListener<? super HPath> onSelectedPathChanged = this::selectedPathChanged;

    public FieldImageView(DocumentManager documentManager) {
        this.documentManager = documentManager;
        this.documentManager.documentProperty().addListener(this::documentChanged);

        // the first item in the list of transforms is the last translation applied
        getTransforms().addAll(zoomTranslate, zoomScale, centerTranslate, unitsScale);
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            unloadFieldImage(oldDocument.getFieldImage());
            unloadSelectedPath(oldDocument.getSelectedPath());
            oldDocument.fieldImageProperty().removeListener(onFieldImageChanged);
            oldDocument.selectedPathProperty().removeListener(onSelectedPathChanged);
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            loadFieldImage(newDocument.getFieldImage());
            loadSelectedPath(newDocument.getSelectedPath());
            newDocument.fieldImageProperty().addListener(onFieldImageChanged);
            newDocument.selectedPathProperty().addListener(onSelectedPathChanged);
        }
    }

    private void fieldImageChanged(ObservableValue<? extends HFieldImage> currentFieldImage, HFieldImage oldFieldImage, HFieldImage newFieldImage) {
        unloadFieldImage(oldFieldImage);
        loadFieldImage(newFieldImage);
    }

    private void unloadFieldImage(HFieldImage fieldImage) {
        if (fieldImage != null) {
            setImage(null);
            unitsScale.setX(1.0);
            unitsScale.setY(1.0);
            centerTranslate.setX(0.0);
            centerTranslate.setY(0.0);
        }
    }

    private void loadFieldImage(HFieldImage fieldImage) {
        if (fieldImage != null) {
            setImage(fieldImage.getImage());
            unitsScale.setX(fieldImage.getImageRes());
            unitsScale.setY(fieldImage.getImageRes());
            centerTranslate.setX(-fieldImage.getImageCenterX());
            centerTranslate.setY(-fieldImage.getImageCenterY());
        }
    }

    private void selectedPathChanged(ObservableValue<? extends HPath> currentPath, HPath oldPath, HPath newPath) {
        unloadSelectedPath(oldPath);
        loadSelectedPath(newPath);
    }

    private void unloadSelectedPath(HPath oldPath) {
        if (oldPath != null) {
            zoomScale.xProperty().unbind();
            zoomScale.yProperty().unbind();
            zoomTranslate.xProperty().unbind();
            zoomTranslate.yProperty().unbind();
            zoomScale.setX(1.0);
            zoomScale.setY(1.0);
            zoomTranslate.setX(0.0);
            zoomTranslate.setY(0.0);
        }
    }

    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            zoomScale.xProperty().bind(newPath.zoomScaleProperty());
            zoomScale.yProperty().bind(newPath.zoomScaleProperty());
            zoomTranslate.xProperty().bind(newPath.zoomOffsetXProperty());
            zoomTranslate.yProperty().bind(newPath.zoomOffsetYProperty());
        }
    }
}