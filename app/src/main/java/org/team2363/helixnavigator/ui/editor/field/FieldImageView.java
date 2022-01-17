package org.team2363.helixnavigator.ui.editor.field;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.field.image.HFieldImage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class FieldImageView extends ImageView {

    private final DocumentManager documentManager;

    private final Scale unitsScale = new Scale();
    private final Translate centerTranslate = new Translate();
    private final Scale zoomScale = new Scale();
    private final Translate zoomTranslate = new Translate();

    private final ChangeListener<? super HFieldImage> onFieldImageChanged = this::fieldImageChanged;

    public FieldImageView(DocumentManager documentManager) {
        this.documentManager = documentManager;
        this.documentManager.documentProperty().addListener(this::documentChanged);

        // the first item in the list is the last translation applied:
        getTransforms().addAll(zoomTranslate, zoomScale, centerTranslate, unitsScale);

        setOnMouseClicked(event -> {
            System.out.println("Field Image clicked");
            if (event.getButton() == MouseButton.PRIMARY
                    && this.documentManager.getIsDocumentOpen()
                    && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.getDocument().getSelectedPath().clearSelection();
            }
        });
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            unloadFieldImage(oldDocument.getFieldImage());
            oldDocument.fieldImageProperty().removeListener(onFieldImageChanged);
            zoomScale.xProperty().unbind();
            zoomScale.yProperty().unbind();
            zoomTranslate.xProperty().unbind();
            zoomTranslate.yProperty().unbind();
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            loadFieldImage(newDocument.getFieldImage());
            newDocument.fieldImageProperty().addListener(onFieldImageChanged);
            zoomScale.xProperty().bind(newDocument.zoomScaleProperty());
            zoomScale.yProperty().bind(newDocument.zoomScaleProperty());
            zoomTranslate.xProperty().bind(newDocument.zoomTranslateXProperty());
            zoomTranslate.yProperty().bind(newDocument.zoomTranslateYProperty());
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
}