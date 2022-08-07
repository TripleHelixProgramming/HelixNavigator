package org.team2363.helixnavigator.document;

import java.util.ArrayList;
import java.util.List;

import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.document.obstacle.HCircleObstacle;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;
import org.team2363.helixnavigator.document.timeline.HHolonomicWaypoint;
import org.team2363.helixnavigator.document.timeline.HInitialGuessPoint;
import org.team2363.helixnavigator.document.timeline.HTimelineElement;
import org.team2363.helixnavigator.document.timeline.HWaypoint;
import org.team2363.helixnavigator.ui.prompts.TransformDialog;
import org.team2363.helixnavigator.ui.prompts.documentconfig.DocumentConfigDialog;

import com.jlbabilino.json.InvalidJSONTranslationConfiguration;
import com.jlbabilino.json.JSONArray;
import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONEntry;
import com.jlbabilino.json.JSONObject;
import com.jlbabilino.json.JSONParser;
import com.jlbabilino.json.JSONParserException;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.JSONSerializerException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;

public class DocumentActions {

    private final DocumentManager documentManager;

    private final BooleanProperty lockZoom = new SimpleBooleanProperty(this, "lockZoom", false);
    private final BooleanProperty showOrigin = new SimpleBooleanProperty(this, "showOrigin", false);
    private final BooleanProperty autoWaypoint = new SimpleBooleanProperty(this, "autoWaypoint", false);

    private DocumentConfigDialog documentConfigDialog = null;
    private TransformDialog transformDialog = null;

    DocumentActions(DocumentManager documentManager) {
        this.documentManager = documentManager;

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }
    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            documentConfigDialog.close();
            transformDialog.close();
            documentConfigDialog = null;
            transformDialog = null;
        }
    }
    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            documentConfigDialog = new DocumentConfigDialog(newDocument);
            transformDialog = new TransformDialog(newDocument);
        }
    }

    public DocumentConfigDialog getDocumentConfigDialog() {
        return documentConfigDialog;
    }
    public TransformDialog getTransformDialog() {
        return transformDialog;
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
            document.getSelectedAutoRoutine().moveSelectedElementsRelative(scaledDeltaX, scaledDeltaY);
            lastElementsDragX = event.getSceneX();
            lastElementsDragY = event.getSceneY();
        }
    }

    public void handleMouseDraggedAsWaypointDragged(MouseEvent event, HHolonomicWaypoint triggeringWaypoint) {
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
            document.getSelectedAutoRoutine().moveSelectedElementsRelative(deltaX, deltaY, triggeringWaypoint);
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
            document.getSelectedAutoRoutine().moveSelectedPolygonPointsRelative(deltaX, deltaY, triggeringPolygonPoint);
        }
    }

    public void handleMouseClickedAsClearSelection(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY
                && this.documentManager.getIsDocumentOpen()
                && this.documentManager.getDocument().isAutoRoutineSelected()) {
            this.documentManager.getDocument().getSelectedAutoRoutine().clearSelection();
        }
    }

    public Point2D calculatePathAreaCoordinates(double x, double y) {
        double pathAreaX = 0;
        double pathAreaY = 0;
        if (documentManager.getIsDocumentOpen()) {
            pathAreaX = documentManager.getDocument().getZoomScale() * x
                    + documentManager.getDocument().getZoomTranslateX() + documentManager.getPathAreaWidth() / 2;
            pathAreaY = documentManager.getDocument().getZoomScale() * (-y)
                    + documentManager.getDocument().getZoomTranslateY() + documentManager.getPathAreaHeight() / 2;
        }
        return new Point2D(pathAreaX, pathAreaY);
    }

    public Point2D calculatePathAreaCoordinates(HWaypoint waypoint) {
        return calculatePathAreaCoordinates(waypoint.getX(), waypoint.getY());
    }
    public Point2D calculatePathAreaCoordinates(HInitialGuessPoint initialGuessPoint) {
        return calculatePathAreaCoordinates(initialGuessPoint.getX(), initialGuessPoint.getY());
    }
    public Point2D calculatePathAreaCoordinates(HCircleObstacle obstacle) {
        return calculatePathAreaCoordinates(obstacle.getCenterX(), obstacle.getCenterY());
    }
    public Point2D calculatePathAreaCoordinates(HPolygonPoint polygonPoint) {
        return calculatePathAreaCoordinates(polygonPoint.getX(), polygonPoint.getY());
    } 

    public void clearSelection() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            documentManager.getDocument().getSelectedAutoRoutine().clearSelection();
        }
    }

    public void selectAll() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            documentManager.getDocument().getSelectedAutoRoutine().getTimelineSelectionModel().selectAll();
            documentManager.getDocument().getSelectedAutoRoutine().getObstaclesSelectionModel().selectAll();
        }
    }

    public void deleteSelectedWaypoints() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            HAutoRoutine path = documentManager.getDocument().getSelectedAutoRoutine();
            Integer[] selectedIndices = path.getTimelineSelectionModel().getSelectedIndices().toArray(new Integer[0]);
            path.clearTimelineSelection();
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                path.getTimeline().remove(selectedIndices[i].intValue());
            }
        }
    }

    public void deleteSelectedObstacles() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            HAutoRoutine path = documentManager.getDocument().getSelectedAutoRoutine();
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

    public void cut() {
        copy();
        deleteSelection();
    }

    public void copy() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            String data;
            HAutoRoutine path = documentManager.getDocument().getSelectedAutoRoutine();
            int selectedWaypointsCount = path.getTimelineSelectionModel().getSelectedItems().size();
            int selectedObstaclesCount = path.getObstaclesSelectionModel().getSelectedItems().size();
            int totalCount = selectedWaypointsCount + selectedObstaclesCount;
            try {
                if (totalCount == 0) {
                    data = JSONSerializer.serializeString(path);
                } else if (totalCount == 1 && selectedWaypointsCount == 1) {
                    data = JSONSerializer.serializeString(path.getTimelineSelectionModel().getSelectedItem());
                } else if (totalCount == 1 && selectedObstaclesCount == 1) {
                    data = JSONSerializer.serializeString(path.getObstaclesSelectionModel().getSelectedItem());
                } else {
                    List<HPathElement> list = new ArrayList<>();
                    for (HTimelineElement waypoint : path.getTimelineSelectionModel().getSelectedItems()) {
                        list.add(waypoint);
                    }
                    for (HObstacle obstacle : path.getObstaclesSelectionModel().getSelectedItems()) {
                        list.add(obstacle);
                    }
                    data = JSONSerializer.serializeString(list);
                }
                Clipboard systemClipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(data);
                systemClipboard.setContent(content);
            } catch (JSONSerializerException | InvalidJSONTranslationConfiguration e) {
                // TODO: log error
            }
        }
    }

    private void paste(JSONEntry jsonEntry) {
        if (documentManager.getIsDocumentOpen()) {
            HDocument document = documentManager.getDocument();
            try {
                if (jsonEntry.isObject()) {
                    JSONObject jsonObject = (JSONObject) jsonEntry;
                    if (jsonObject.containsKey("waypoints")) {
                        HAutoRoutine pastedPath = JSONDeserializer.deserialize(jsonEntry, HAutoRoutine.class);
                        document.getAutoRoutines().add(pastedPath);
                        document.setSelectedAutoRoutineIndex(document.getAutoRoutines().size() - 1);
                    } else if (document.isAutoRoutineSelected()) {
                        HAutoRoutine path = document.getSelectedAutoRoutine();
                        if (jsonObject.containsKey("waypoint_type")) {
                            HHolonomicWaypoint waypoint = JSONDeserializer.deserialize(jsonEntry, HHolonomicWaypoint.class);
                            int index = path.getTimelineSelectionModel().getSelectedIndex() + 1;
                            path.getTimeline().add(index, waypoint);
                            path.getTimelineSelectionModel().select(index);
                        } else if (jsonObject.containsKey("obstacle_type")) {
                            HObstacle obstacle = JSONDeserializer.deserialize(jsonEntry, HObstacle.class);
                            int index = path.getObstaclesSelectionModel().getSelectedIndex() + 1;
                            path.getObstacles().add(index, obstacle);
                            path.getObstaclesSelectionModel().select(index);
                        }
                    }
                } else if (jsonEntry.isArray()) {
                    JSONArray jsonArray = (JSONArray) jsonEntry;
                    for (JSONEntry entry : jsonArray) {
                        documentManager.actions().clearSelection();
                        paste(entry);
                    }
                }
            } catch (JSONDeserializerException | InvalidJSONTranslationConfiguration e) {
                // TODO: log error
            }
        }
    }

    public void paste() {
        Clipboard systemClipboard = Clipboard.getSystemClipboard();
        if (systemClipboard.hasString()) {
            String data = systemClipboard.getString();
            try {
                JSONEntry jsonEntry = JSONParser.parseJSONEntry(data);
                paste(jsonEntry);
            } catch (JSONParserException e) {
            }
        }
    }

    private static final Rotate ROTATE_90_CLOCKWISE = new Rotate(-90);
    private static final Rotate ROTATE_90_COUNTERCLOCKWISE = new Rotate(90);
    private static final Rotate ROTATE_180 = new Rotate(180);
    public void rotateSelection90Clockwise() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            documentManager.getDocument().getSelectedAutoRoutine().transformSelectedElementsRelative(ROTATE_90_CLOCKWISE);
        }
    }
    public void rotateSelection90Counterclockwise() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            documentManager.getDocument().getSelectedAutoRoutine().transformSelectedElementsRelative(ROTATE_90_COUNTERCLOCKWISE);
        }
    }
    public void rotateSelection180() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            documentManager.getDocument().getSelectedAutoRoutine().transformSelectedElementsRelative(ROTATE_180);
        }
    }

    private void insertTimelineElement(int index, HTimelineElement timelineElement) {
        documentManager.getDocument().getSelectedAutoRoutine().getTimeline().add(index, timelineElement);
    }
    public void newPositionHolonomicWaypoint(int index) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            HHolonomicWaypoint newWaypoint = HHolonomicWaypoint.positionHolonomicWaypoint();
            newWaypoint.setName("Position Waypoint " + index);
            insertTimelineElement(index, newWaypoint);
        }
    }
    public void newHeadingHolonomicWaypoint(int index) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            HHolonomicWaypoint newWaypoint = HHolonomicWaypoint.headingHolonomicWaypoint();
            newWaypoint.setName("Heading Waypoint " + index);
            insertTimelineElement(index, newWaypoint);
        }
    }
    public void newStaticHolonomicWaypoint(int index) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            HHolonomicWaypoint newWaypoint = HHolonomicWaypoint.staticHolonomicWaypoint();
            newWaypoint.setName("Custom Waypoint " + index);
            insertTimelineElement(index, newWaypoint);
        }
    }
    public void newInitialGuessPoint(int index) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            HInitialGuessPoint newWaypoint = new HInitialGuessPoint();
            newWaypoint.setName("Initial Guess Point " + index);
            insertTimelineElement(index, newWaypoint);
        }
    }
    public void newPositionHolonomicWaypoint() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            newPositionHolonomicWaypoint(documentManager.getDocument().getSelectedAutoRoutine().getTimeline().size());
        }
    }
    public void newHeadingHolonomicWaypoint() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            newHeadingHolonomicWaypoint(documentManager.getDocument().getSelectedAutoRoutine().getTimeline().size());
        }
    }
    public void newStaticHolonomicWaypoint() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isAutoRoutineSelected()) {
            newStaticHolonomicWaypoint(documentManager.getDocument().getSelectedAutoRoutine().getTimeline().size());
        }
    }

    public final ReadOnlyObjectProperty<HDocument> documentProperty() {
        return documentManager.documentProperty();
    }
    public final HDocument getDocument() {
        return documentManager.getDocument();
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