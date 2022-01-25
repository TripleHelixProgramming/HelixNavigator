package org.team2363.helixnavigator.ui.editor.field;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.ui.editor.PathLayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
//TODO: show origin as icon
public class FieldImageLayer {

    private final DocumentManager documentManager;

    private final ObservableList<Node> children = FXCollections.observableArrayList();
    private final ObservableList<Node> childrenUnmodifiable = FXCollections.unmodifiableObservableList(children);

    private final ImageView imageView = new ImageView();
    private final Scale unitsScale = new Scale();
    private final Translate centerTranslate = new Translate();
    private final Scale zoomScale = new Scale();
    private final Translate zoomTranslate = new Translate();
    private final Translate pathAreaTranslate = new Translate();
    private final OriginView originView = new OriginView();

    private final ChangeListener<? super HFieldImage> onFieldImageChanged = this::fieldImageChanged;

    public FieldImageLayer(DocumentManager documentManager) {
        this.documentManager = documentManager;

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);

        children.addAll(imageView, originView);

        pathAreaTranslate.xProperty().bind(this.documentManager.pathAreaWidthProperty().multiply(0.5));
        pathAreaTranslate.yProperty().bind(this.documentManager.pathAreaHeightProperty().multiply(0.5));

        // the first item in the list is the last translation applied:
        imageView.getTransforms().addAll(pathAreaTranslate, zoomTranslate, zoomScale, centerTranslate, unitsScale);

        imageView.setOnMouseClicked(event -> {
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
            imageView.setImage(null);
            unitsScale.setX(1.0);
            unitsScale.setY(1.0);
            centerTranslate.setX(0.0);
            centerTranslate.setY(0.0);
            originView.pathAreaWidthProperty().unbind();
            originView.pathAreaHeightProperty().unbind();
            originView.zoomTranslateXProperty().unbind();
            originView.zoomTranslateYProperty().unbind();
            originView.zoomScaleProperty().unbind();
        }
    }

    private void loadFieldImage(HFieldImage fieldImage) {
        if (fieldImage != null) {
            imageView.setImage(fieldImage.getImage());
            unitsScale.setX(fieldImage.getImageRes());
            unitsScale.setY(fieldImage.getImageRes());
            centerTranslate.setX(-fieldImage.getImageCenterX());
            centerTranslate.setY(-fieldImage.getImageCenterY());
            originView.pathAreaWidthProperty().bind(documentManager.pathAreaWidthProperty());
            originView.pathAreaHeightProperty().bind(documentManager.pathAreaHeightProperty());
            originView.zoomTranslateXProperty().bind(documentManager.getDocument().zoomTranslateXProperty());
            originView.zoomTranslateYProperty().bind(documentManager.getDocument().zoomTranslateYProperty());
            originView.zoomScaleProperty().bind(documentManager.getDocument().zoomScaleProperty());
        }
    }

    public ObservableList<? extends Node> getChildren() {
        return childrenUnmodifiable;
    }
}