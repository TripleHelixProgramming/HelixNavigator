package org.team2363.helixnavigator.ui.editor.field;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.field.image.HFieldImage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
public class FieldImageLayer {

    private final DocumentManager documentManager;

    private final ObservableList<Node> children = FXCollections.observableArrayList();
    private final ObservableList<Node> childrenUnmodifiable = FXCollections.unmodifiableObservableList(children);

    private final ImageView imageView = new ImageView();
    private final Scale unitsScale = new Scale();
    private final Translate centerTranslate = new Translate();
    private final Scale zoomScale = new Scale();
    private final OriginView originView = new OriginView();

    private final ChangeListener<? super HFieldImage> onFieldImageChanged = this::fieldImageChanged;

    public FieldImageLayer(DocumentManager documentManager) {
        this.documentManager = documentManager;

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);

        children.addAll(imageView, originView.getView());

        // the first item in the list is the last translation applied:
        imageView.getTransforms().addAll(zoomScale, centerTranslate, unitsScale);

        imageView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY
                    && this.documentManager.getIsDocumentOpen()
                    && this.documentManager.getDocument().isPathSelected()) {
                this.documentManager.getDocument().getSelectedPath().clearSelection();
            }
        });
        originView.getView().setOnMouseClicked(event -> {
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
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            loadFieldImage(newDocument.getFieldImage());
            newDocument.fieldImageProperty().addListener(onFieldImageChanged);
            zoomScale.xProperty().bind(newDocument.zoomScaleProperty());
            zoomScale.yProperty().bind(newDocument.zoomScaleProperty());
        }
    }

    private void fieldImageChanged(ObservableValue<? extends HFieldImage> currentFieldImage, HFieldImage oldFieldImage, HFieldImage newFieldImage) {
        unloadFieldImage(oldFieldImage);
        loadFieldImage(newFieldImage);
    }

    private void unloadFieldImage(HFieldImage fieldImage) {
        if (fieldImage != null) {
            imageView.setImage(null);
            unitsScale.setX(1.0);
            unitsScale.setY(1.0);
            centerTranslate.setX(0.0);
            centerTranslate.setY(0.0);
        }
    }

    private void loadFieldImage(HFieldImage fieldImage) {
        if (fieldImage != null) {
            imageView.setImage(fieldImage.getImage());
            unitsScale.setX(fieldImage.getImageRes());
            unitsScale.setY(fieldImage.getImageRes());
            centerTranslate.setX(-fieldImage.getImageCenterX());
            centerTranslate.setY(-fieldImage.getImageCenterY());
        }
    }

    public ObservableList<? extends Node> getChildren() {
        return childrenUnmodifiable;
    }
}