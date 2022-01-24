package org.team2363.helixnavigator.document;

import org.team2363.helixnavigator.document.field.image.HFieldImage;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class DocumentActions {

    private final DocumentManager documentManager;

    private final BooleanProperty lockZoom = new SimpleBooleanProperty(this, "lockZoom", false);

    DocumentActions(DocumentManager documentManager) {
        this.documentManager = documentManager;
    }

    public void pan(double deltaX, double deltaY) {
        if (documentManager.getIsDocumentOpen()) {
            HDocument document = documentManager.getDocument();
            document.setZoomTranslateX(document.getZoomTranslateX() + deltaX);
            document.setZoomTranslateY(document.getZoomTranslateY() + deltaY);
        }
    }

    public void zoom(double factor, double pivotX, double pivotY) {
        if (documentManager.getIsDocumentOpen()) {
            HDocument document = documentManager.getDocument();
            // This code allows for zooming in or out about a certain point
            double s = factor;
            double xci = document.getZoomTranslateX();
            double xp = pivotX;
            double xd = (1-s)*(xp-xci);
            double yci = document.getZoomTranslateY();
            double yp = pivotY;
            double yd = (1-s)*(yp-yci);
            document.setZoomTranslateX(document.getZoomTranslateX() + xd);
            document.setZoomTranslateY(document.getZoomTranslateY() + yd);
            document.setZoomScale(document.getZoomScale() * s);
        }
    }

    public void zoomToFit() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().getFieldImage() != null) {
            HDocument document = documentManager.getDocument();
            HFieldImage fieldImage = document.getFieldImage();
            double areaWidth = documentManager.getPathAreaWidth();
            double areaHeight = documentManager.getPathAreaHeight();
            double imageRawWidth = fieldImage.getImage().getWidth();
            double imageRawHeight = fieldImage.getImage().getHeight();
            double imageScaledWidth = imageRawWidth * fieldImage.getImageRes();
            double imageScaledHeight = imageRawHeight * fieldImage.getImageRes();
            double areaRatio = areaWidth / areaHeight;
            double imageRatio = imageRawWidth / imageRawHeight;
            double scale;
            if (imageRatio > areaRatio) { // if image is wider than area
                scale = areaWidth / imageScaledWidth;
            } else { // if image is thinner than area
                scale = areaHeight / imageScaledHeight;
            }
            // take the negative distance away from the actual center of the image and the center of the coordinate system, then apply the zoom scale.
            double translateX = scale * (fieldImage.getImageCenterX() - imageScaledWidth / 2);
            double translateY = scale * (fieldImage.getImageCenterY() - imageScaledHeight / 2);
            document.setZoomScale(scale);
            document.setZoomTranslateX(translateX);
            document.setZoomTranslateY(translateY);
        }
    }

    // Temporary variables:
    private double lastBackgroundDragX;
    private double lastBackgroundDragY;
    public void handleMousePressedAsPan(MouseEvent event) {
        if (!getLockZoom() && event.getButton() == MouseButton.MIDDLE) {
            lastBackgroundDragX = event.getX();
            lastBackgroundDragY = event.getY();
        }
    }
    public void handleMouseDraggedAsPan(MouseEvent event) {
        if (documentManager.getIsDocumentOpen() && !getLockZoom() && event.getButton() == MouseButton.MIDDLE) {
            pan(event.getX() - lastBackgroundDragX, event.getY() - lastBackgroundDragY);
            lastBackgroundDragX = event.getX();
            lastBackgroundDragY = event.getY();
        }
    }

    public void handleScrollAsZoom(ScrollEvent event) {
        if (documentManager.getIsDocumentOpen() && !getLockZoom()) {
            int pixels = (int) (-event.getDeltaY());
            double factor;
            if (pixels >= 0) {
                factor = 0.995;
            } else {
                factor = 1.005;
                pixels = -pixels;
            }
            double pivotX = event.getX();
            double pivotY = event.getY();
            for (int i = 0; i < pixels; i++) {
                zoom(factor, pivotX, pivotY);
            }
        }
    }

    private double lastElementsDragX;
    private double lastElementsDragY;
    public void handleMouseDragBeginAsElementsDragBegin(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            lastElementsDragX = event.getSceneX();
            lastElementsDragY  = event.getSceneY();
        }
    }
    public void handleMouseDraggedAsElementsDragged(MouseEvent event) {
        if (documentManager.getIsDocumentOpen() && event.getButton() == MouseButton.PRIMARY) {
            HDocument document = documentManager.getDocument();
            double deltaX = event.getSceneX() - lastElementsDragX;
            double deltaY = event.getSceneY() - lastElementsDragY;
            double scaledDeltaX = deltaX / document.getZoomScale();
            double scaledDeltaY = -deltaY / document.getZoomScale(); // negative since coordinate system is mirrored vertically
            document.getSelectedPath().moveSelectedElementsRelative(scaledDeltaX, scaledDeltaY);
            lastElementsDragX = event.getSceneX();
            lastElementsDragY = event.getSceneY();
        }
    }

    public void clearSelection() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            documentManager.getDocument().getSelectedPath().clearSelection();
        }
    }

    public final BooleanProperty lockZoomProperty() {
        return lockZoom;
    }

    public final void setLockZoom(boolean value) {
        lockZoom.set(value);
    }

    public final boolean getLockZoom() {
        return lockZoom.get();
    }
}