package org.team2363.helixnavigator.document;

import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;
import org.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.document.waypoint.HSoftWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class DocumentActions {

    private final DocumentManager documentManager;

    private final BooleanProperty lockZoom = new SimpleBooleanProperty(this, "lockZoom", false);
    private final BooleanProperty showOrigin = new SimpleBooleanProperty(this, "showOrigin", false);
    private final BooleanProperty autoWaypoint = new SimpleBooleanProperty(this, "autoWaypoint", false);

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
            // xci and yci are the coordinates of the origin point relative to the path area
            double s = factor;
            double xci = document.getZoomTranslateX() + documentManager.getPathAreaWidth() / 2;
            double xp = pivotX;
            double xd = (1-s)*(xp-xci);
            double yci = document.getZoomTranslateY() + documentManager.getPathAreaHeight() / 2;
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
        if (documentManager.getIsDocumentOpen() && !getLockZoom() && event.getButton() == MouseButton.SECONDARY) {
            lastBackgroundDragX = event.getSceneX();
            lastBackgroundDragY = event.getSceneY();
        }
    }
    public void handleMouseDraggedAsPan(MouseEvent event) {
        if (documentManager.getIsDocumentOpen() && !getLockZoom() && event.getButton() == MouseButton.SECONDARY) {
            pan(event.getSceneX() - lastBackgroundDragX, event.getSceneY() - lastBackgroundDragY);
            lastBackgroundDragX = event.getSceneX();
            lastBackgroundDragY = event.getSceneY();
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
            // System.out.println("X: " + event.getX() + " Y: " + event.getY());
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
            // System.out.println("X: " + event.getX() + " Y: " + event.getY());
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

    public void handleMouseDraggedAsWaypointDragged(MouseEvent event, HWaypoint triggeringWaypoint) {
        if (documentManager.getIsDocumentOpen() && event.getButton() == MouseButton.PRIMARY) {
            // System.out.println("X: " + event.getX() + " Y: " + event.getY());
            HDocument document = documentManager.getDocument();
            double newX = event.getX() / document.getZoomScale();
            double newY = -event.getY() / document.getZoomScale();
            if (Math.hypot(event.getX(), event.getY()) <= 10) {
                newX = 0;
                newY = 0;
            }
            double deltaX = newX - triggeringWaypoint.getX();
            double deltaY = newY - triggeringWaypoint.getY();
            triggeringWaypoint.setX(newX);
            triggeringWaypoint.setY(newY);
            document.getSelectedPath().moveSelectedElementsRelative(deltaX, deltaY, triggeringWaypoint);
        }
    }

    public void handleMouseDraggedAsPolygonPointDragged(MouseEvent event, HPolygonPoint triggeringPolygonPoint) {
        if (documentManager.getIsDocumentOpen() && event.getButton() == MouseButton.PRIMARY) {
            // System.out.println("X: " + event.getX() + " Y: " + event.getY());
            HDocument document = documentManager.getDocument();
            double newX = event.getX() / document.getZoomScale();
            double newY = -event.getY() / document.getZoomScale();
            double deltaX = newX - triggeringPolygonPoint.getX();
            double deltaY = newY - triggeringPolygonPoint.getY();
            triggeringPolygonPoint.setX(newX);
            triggeringPolygonPoint.setY(newY);
            document.getSelectedPath().moveSelectedPolygonPointsRelative(deltaX, deltaY, triggeringPolygonPoint);
        }
    }

    public void handleMouseClickedAsClearSelection(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY
                && this.documentManager.getIsDocumentOpen()
                && this.documentManager.getDocument().isPathSelected()) {
            this.documentManager.getDocument().getSelectedPath().clearSelection();
        }
    }

    public void clearSelection() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            documentManager.getDocument().getSelectedPath().clearSelection();
        }
    }

    public void selectAll() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().selectAll();
            documentManager.getDocument().getSelectedPath().getObstaclesSelectionModel().selectAll();
        }
    }

    public void deleteSelectedWaypoints() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            HPath path = documentManager.getDocument().getSelectedPath();
            Integer[] selectedIndices = path.getWaypointsSelectionModel().getSelectedIndices().toArray(new Integer[0]);
            path.clearWaypointsSelection();
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                path.getWaypoints().remove(selectedIndices[i].intValue());
            }
        }
    }

    public void deleteSelectedObstacles() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            HPath path = documentManager.getDocument().getSelectedPath();
            Integer[] selectedIndices = path.getObstaclesSelectionModel().getSelectedIndices().toArray(new Integer[0]);
            path.clearObstaclesSelection();
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                path.getObstacles().remove(selectedIndices[i].intValue());
            }
        }
    }

    public void deleteSelection() {
        deleteSelectedWaypoints();
        deleteSelectedObstacles();
    }

    private void insertWaypoint(int index, HWaypoint waypoint) {
        documentManager.getDocument().getSelectedPath().getWaypoints().add(index, waypoint);
    }
    public void newSoftWaypoint(int index) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            HWaypoint newWaypoint = new HSoftWaypoint();
            newWaypoint.setName("Soft Waypoint " + index);
            insertWaypoint(index, newWaypoint);
        }
    }
    public void newHardWaypoint(int index) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            HWaypoint newWaypoint = new HHardWaypoint();
            newWaypoint.setName("Hard Waypoint " + index);
            insertWaypoint(index, newWaypoint);
        }
    }
    public void newSoftWaypoint() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            newSoftWaypoint(documentManager.getDocument().getSelectedPath().getWaypoints().size());
        }
    }
    public void newHardWaypoint() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            newHardWaypoint(documentManager.getDocument().getSelectedPath().getWaypoints().size());
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

    public final BooleanProperty showOriginProperty() {
        return showOrigin;
    }

    public final void setShowOrigin(boolean value) {
        showOrigin.set(value);
    }

    public final boolean getShowOrigin() {
        return showOrigin.get();
    }

    public final BooleanProperty autoWaypointProperty() {
        return autoWaypoint;
    }

    public final void setAutoWaypoint(boolean value) {
        autoWaypoint.set(value);
    }

    public final boolean getAutoWaypoint() {
        return autoWaypoint.get();
    }
}