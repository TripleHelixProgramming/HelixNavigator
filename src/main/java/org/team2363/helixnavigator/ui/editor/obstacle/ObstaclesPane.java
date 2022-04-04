// package org.team2363.helixnavigator.ui.editor.obstacle;

// import java.util.List;

// import org.team2363.helixnavigator.document.DocumentManager;
// import org.team2363.helixnavigator.document.HDocument;
// import org.team2363.helixnavigator.document.HPath;
// import org.team2363.helixnavigator.document.obstacle.HCircleObstacle;
// import org.team2363.helixnavigator.document.obstacle.HObstacle;
// import org.team2363.helixnavigator.ui.editor.PathElementView;
// import org.team2363.lib.ui.MouseEventWrapper;

// import javafx.beans.value.ChangeListener;
// import javafx.beans.value.ObservableValue;
// import javafx.collections.FXCollections;
// import javafx.collections.ListChangeListener;
// import javafx.collections.ObservableList;
// import javafx.event.EventHandler;
// import javafx.scene.input.MouseButton;
// import javafx.scene.input.MouseEvent;
// import javafx.scene.layout.Pane;

// public class ObstaclesPane extends Pane {

//     private final DocumentManager documentManager;

//     private final Pane obstaclesPane = new Pane();
//     private final ChangeListener<? super HPath> onSelectedPathChanged = this::selectedPathChanged;
//     private final ListChangeListener<? super HObstacle> onObstaclesChanged = this::obstaclesChanged;
    
//     public ObstaclesPane(DocumentManager documentManager) {
//         this.documentManager = documentManager;

//         setPickOnBounds(false);

//         loadDocument(this.documentManager.getDocument());
//         this.documentManager.documentProperty().addListener(this::documentChanged);
//     }

//     private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
//         unloadDocument(oldDocument);
//         loadDocument(newDocument);
//     }

//     private void unloadDocument(HDocument oldDocument) {
//         if (oldDocument != null) {
//             unloadSelectedPath(oldDocument.getSelectedPath());
//             oldDocument.selectedPathProperty().removeListener(onSelectedPathChanged);
//         }
//     }

//     private void loadDocument(HDocument newDocument) {
//         if (newDocument != null) {
//             loadSelectedPath(newDocument.getSelectedPath());
//             newDocument.selectedPathProperty().addListener(onSelectedPathChanged);
//         }
//     }

//     private void selectedPathChanged(ObservableValue<? extends HPath> currentPath, HPath oldPath, HPath newPath) {
//         unloadSelectedPath(oldPath);
//         loadSelectedPath(newPath);
//     }

//     private void unloadSelectedPath(HPath oldPath) {
//         if (oldPath != null) {
//             getChildren().clear();
//             oldPath.getObstacles().removeListener(onObstaclesChanged);
//         }
//     }

//     private void loadSelectedPath(HPath newPath) {
//         if (newPath != null) {
//             updateObstacles(newPath.getObstacles());
//             newPath.getObstacles().addListener(onObstaclesChanged);
//         }
//     }

//     private void obstaclesChanged(ListChangeListener.Change<? extends HObstacle> change) {
//         updateObstacles(change.getList());
//     }

//     private void updateObstacles(List<? extends HObstacle> list) {
//         getChildren().clear();
//         for (int i = 0; i < list.size(); i++) {
//             HObstacle obstacle = list.get(i);
//             PathElementView obstacleView;
//             switch (obstacle.getObstacleType()) {
//                 case CIRCLE:
//                     CircleObstacleView circleObstacleView = new CircleObstacleView();
//                     linkCircleObstacleView(i, circleObstacleView, (HCircleObstacle) obstacle);
//                     obstacleView = circleObstacleView;
//                     break;
//                 case POLYGON:
//                     PolygonObstacleView polygonObstacleView = new PolygonObstacleView();
//                     obstacleView = polygonObstacleView;
//                     break;
//                 default:
//                     obstacleView = null;
//                     break;

//             }
//             obstacleViews.add(i, obstacleView);
//             getChildren().add(i, obstacleView.getView());
//         }
//     }

//     private void obstaclesSelectedIndicesChanged(ListChangeListener.Change<? extends Integer> change) {
//         updateSelectedObstacles();
//     }

//     private void updateSelectedObstacles() {
//         for (PathElementView obstacleView : obstacleViews) {
//             obstacleView.setSelected(false);
//         }
//         for (int i : documentManager.getDocument().getSelectedPath().getObstaclesSelectionModel().getSelectedIndices()) {
//             PathElementView obstacleView = obstacleViews.get(i);
//             obstacleView.setSelected(true);
//         }
//     }

//     private void linkCircleObstacleView(int index, CircleObstacleView circleObstacleView, HCircleObstacle circleObstacle) {
//         circleObstacleView.centerXProperty().bind(circleObstacle.centerXProperty());
//         circleObstacleView.centerYProperty().bind(circleObstacle.centerYProperty());
//         circleObstacleView.radiusProperty().bind(circleObstacle.radiusProperty());
//         circleObstacleView.zoomScaleProperty().bind(documentManager.getDocument().zoomScaleProperty());

//         EventHandler<MouseEvent> onMousePressed = event -> {
//         };
//         EventHandler<MouseEvent> onMouseDragBegin = event -> {
//             if (event.getButton() == MouseButton.PRIMARY) {
//                 if (!event.isShortcutDown() && !documentManager.getDocument().getSelectedPath().getObstaclesSelectionModel().isSelected(index)) {
//                     documentManager.getDocument().getSelectedPath().clearSelection();
//                 }
//                 documentManager.getDocument().getSelectedPath().getObstaclesSelectionModel().select(index);
//                 documentManager.actions().handleMouseDragBeginAsElementsDragBegin(event);
//             }
//         };
//         EventHandler<MouseEvent> onMouseDragged = event -> {
//             if (event.getButton() == MouseButton.PRIMARY) {
//                 documentManager.actions().handleMouseDraggedAsElementsDragged(event);
//             }
//         };
//         EventHandler<MouseEvent> onMouseDragEnd = event -> {
//         };
//         EventHandler<MouseEvent> onMouseReleased = event -> {
//             if (event.getButton() == MouseButton.PRIMARY) {
//                 if (!event.isShortcutDown()) {
//                     boolean selected = documentManager.getDocument().getSelectedPath().getObstaclesSelectionModel().isSelected(index);
//                     documentManager.getDocument().getSelectedPath().clearSelection();
//                     documentManager.getDocument().getSelectedPath().getObstaclesSelectionModel().setSelected(index, selected);
//                 }
//                 documentManager.getDocument().getSelectedPath().getObstaclesSelectionModel().toggle(index);
//             }
//         };

//         MouseEventWrapper eventWrapper = new MouseEventWrapper(onMousePressed, onMouseDragBegin, onMouseDragged, onMouseDragEnd, onMouseReleased);
//         circleObstacleView.getView().setOnMousePressed(eventWrapper.getOnMousePressed());
//         circleObstacleView.getView().setOnMouseDragged(eventWrapper.getOnMouseDragged());
//         circleObstacleView.getView().setOnMouseReleased(eventWrapper.getOnMouseReleased());
//     }
// }