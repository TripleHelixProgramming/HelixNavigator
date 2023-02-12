package org.team2363.helixnavigator.document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.document.obstacle.HCircleObstacle;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;
import org.team2363.helixnavigator.document.timeline.HCustomWaypoint;
import org.team2363.helixnavigator.document.timeline.HHardWaypoint;
import org.team2363.helixnavigator.document.timeline.HInitialGuessWaypoint;
import org.team2363.helixnavigator.document.timeline.HSoftWaypoint;
import org.team2363.helixnavigator.document.timeline.HWaypoint;
import org.team2363.helixnavigator.ui.prompts.RobotConfigDialog;
import org.team2363.helixnavigator.ui.prompts.TransformDialog;
import org.team2363.helixtrajectory.HolonomicPath;
import org.team2363.helixtrajectory.HolonomicTrajectory;
import org.team2363.helixtrajectory.InvalidPathException;
import org.team2363.helixtrajectory.Obstacle;
import org.team2363.helixtrajectory.OptimalTrajectoryGenerator;
import org.team2363.helixtrajectory.PluginLoadException;
import org.team2363.helixtrajectory.SwerveDrivetrain;
import org.team2363.helixtrajectory.TrajectoryGenerationException;

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
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;

public class DocumentActions {

    private final DocumentManager documentManager;

    private final BooleanProperty lockZoom = new SimpleBooleanProperty(this, "lockZoom", false);
    private final BooleanProperty showOrigin = new SimpleBooleanProperty(this, "showOrigin", false);
    private final BooleanProperty autoWaypoint = new SimpleBooleanProperty(this, "autoWaypoint", false);

    private RobotConfigDialog robotConfigDialog = null;
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
            robotConfigDialog.close();
            transformDialog.close();
            robotConfigDialog = null;
            transformDialog = null;
        }
    }
    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            robotConfigDialog = new RobotConfigDialog(newDocument.getRobotConfiguration());
            transformDialog = new TransformDialog(newDocument);
        }
    }

    public RobotConfigDialog getRobotConfigDialog() {
        return robotConfigDialog;
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
    public Point2D calculatePathAreaCoordinates(HCircleObstacle obstacle) {
        return calculatePathAreaCoordinates(obstacle.getCenterX(), obstacle.getCenterY());
    }
    public Point2D calculatePathAreaCoordinates(HPolygonPoint polygonPoint) {
        return calculatePathAreaCoordinates(polygonPoint.getX(), polygonPoint.getY());
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

    public void cut() {
        copy();
        deleteSelection();
    }

    public void copy() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            String data;
            HPath path = documentManager.getDocument().getSelectedPath();
            int selectedWaypointsCount = path.getWaypointsSelectionModel().getSelectedItems().size();
            int selectedObstaclesCount = path.getObstaclesSelectionModel().getSelectedItems().size();
            int totalCount = selectedWaypointsCount + selectedObstaclesCount;
            try {
                if (totalCount == 0) {
                    data = JSONSerializer.serializeString(path);
                } else if (totalCount == 1 && selectedWaypointsCount == 1) {
                    data = JSONSerializer.serializeString(path.getWaypointsSelectionModel().getSelectedItem());
                } else if (totalCount == 1 && selectedObstaclesCount == 1) {
                    data = JSONSerializer.serializeString(path.getObstaclesSelectionModel().getSelectedItem());
                } else {
                    List<HPathElement> list = new ArrayList<>();
                    for (HWaypoint waypoint : path.getWaypointsSelectionModel().getSelectedItems()) {
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
                        HPath pastedPath = JSONDeserializer.deserialize(jsonEntry, HPath.class);
                        document.getPaths().add(pastedPath);
                        document.setSelectedPathIndex(document.getPaths().size() - 1);
                    } else if (document.isPathSelected()) {
                        HPath path = document.getSelectedPath();
                        if (jsonObject.containsKey("waypoint_type")) {
                            HWaypoint waypoint = JSONDeserializer.deserialize(jsonEntry, HWaypoint.class);
                            int index = path.getWaypointsSelectionModel().getSelectedIndex() + 1;
                            path.getWaypoints().add(index, waypoint);
                            path.getWaypointsSelectionModel().select(index);
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

    private static class TrajectoryGenerationService extends Service<HolonomicTrajectory> {

        private SwerveDrivetrain drive = null;
        private HolonomicPath path = null;

        TrajectoryGenerationService() {
        }

        @Override
        protected Task<HolonomicTrajectory> createTask() {
            Task<HolonomicTrajectory> optimizeTask = new Task<HolonomicTrajectory>() {
                @Override
                protected HolonomicTrajectory call() throws PluginLoadException, InvalidPathException, TrajectoryGenerationException {
                    System.out.println("Path optimizing: " + path.toString());
                    if (drive != null && path != null) {
                        HolonomicTrajectory traj = OptimalTrajectoryGenerator.generate(drive, path);
                        System.out.println("Generation complete, closing task...");
                        return traj;
                    } else {
                        throw new TrajectoryGenerationException("No path specified for optimization service.");
                    }
                }
            };
            return optimizeTask;
        }
    }

    private final TrajectoryGenerationService service = new TrajectoryGenerationService();
    public final ReadOnlyBooleanProperty generationRunningProperty() {
        return service.runningProperty();
    }
    public void generateTrajectory() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            HDocument hDocument = documentManager.getDocument();
            HPath hPath = documentManager.getDocument().getSelectedPath();
            if (hPath.getWaypoints().size() < 2) {
                Alert notEnoughWaypointsAlert = new Alert(AlertType.ERROR, "Must have at least 2 waypoints!");
                notEnoughWaypointsAlert.initModality(Modality.APPLICATION_MODAL);
                notEnoughWaypointsAlert.show();
                return;
            }
            List<HWaypoint> onlyCustom = hPath.getWaypoints().filtered(waypoint -> waypoint.isCustom());
            if (onlyCustom.size() != hPath.getWaypoints().size()) {
                Alert onlyCustomAllowed = new Alert(AlertType.ERROR, "Only custom waypoints are allowed!");
                onlyCustomAllowed.initModality(Modality.APPLICATION_MODAL);
                onlyCustomAllowed.show();
                return;
            }
            ((HCustomWaypoint) hPath.getWaypoints().get(0)).setControlIntervalCount(0);

            Consumer<HCustomWaypoint> applyVelocityConstraint = waypoint -> {
                waypoint.setVelocityX(0.0);
                waypoint.setVelocityY(0.0);
                waypoint.setAngularVelocity(0.0);
                waypoint.setVelocityXConstrained(true);
                waypoint.setVelocityYConstrained(true);
                waypoint.setVelocityMagnitudeConstrained(true);
                waypoint.setAngularVelocityConstrained(true);
            };

            applyVelocityConstraint.accept(((HCustomWaypoint) hPath.getWaypoints().get(0)));
            applyVelocityConstraint.accept(((HCustomWaypoint) hPath.getWaypoints().get(hPath.getWaypoints().size() - 1)));

            SwerveDrivetrain drive = hDocument.getRobotConfiguration().toDrive();
            List<Obstacle> obstacles = new ArrayList<>(hPath.getObstacles().size());
            for (int i = 0; i < hPath.getObstacles().size(); i++) {
                obstacles.add(hPath.getObstacles().get(i).toObstacle());
            }
            HolonomicPath path = hPath.toPath(obstacles);
            service.drive = drive;
            service.path = path;
            service.setOnSucceeded(workerState -> {
                HolonomicTrajectory traj = (HolonomicTrajectory) workerState.getSource().getValue();
                hPath.setTrajectory(HTrajectory.fromTrajectory(traj));
            });
            if (!service.isRunning()) {
                service.restart();
            }
        }
    }

    private static final Rotate ROTATE_90_CLOCKWISE = new Rotate(-90);
    private static final Rotate ROTATE_90_COUNTERCLOCKWISE = new Rotate(90);
    private static final Rotate ROTATE_180 = new Rotate(180);
    public void rotateSelection90Clockwise() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            documentManager.getDocument().getSelectedPath().transformSelectedElementsRelative(ROTATE_90_CLOCKWISE);
        }
    }
    public void rotateSelection90Counterclockwise() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            documentManager.getDocument().getSelectedPath().transformSelectedElementsRelative(ROTATE_90_COUNTERCLOCKWISE);
        }
    }
    public void rotateSelection180() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            documentManager.getDocument().getSelectedPath().transformSelectedElementsRelative(ROTATE_180);
        }
    }

    public void flipObjectsToOppositeSide() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            HDocument document = documentManager.getDocument();
            HFieldImage fieldImage = document.getFieldImage();
            Scale mirrorTrans = new Scale(-1, 1);
            Translate flipTrans = new Translate(fieldImage.getFieldAreaWidth(), 0.0);
            Transform totalTrans = flipTrans.createConcatenation(mirrorTrans);
            documentManager.getDocument().getSelectedPath().transformSelectedElementsRelative(totalTrans);
        }
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
    public void newCustomWaypoint(int index) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            HWaypoint newWaypoint = new HCustomWaypoint();
            newWaypoint.setName("Custom Waypoint " + index);
            insertWaypoint(index, newWaypoint);
        }
    }
    public void newInitialGuessWaypoint(int index) {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            HWaypoint newWaypoint = new HInitialGuessWaypoint();
            newWaypoint.setName("Initial Guess Waypoint " + index);
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
    public void newCustomWaypoint() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            newCustomWaypoint(documentManager.getDocument().getSelectedPath().getWaypoints().size());
        }
    }
    public void newInitialGuessWaypoint() {
        if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
            newInitialGuessWaypoint(documentManager.getDocument().getSelectedPath().getWaypoints().size());
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