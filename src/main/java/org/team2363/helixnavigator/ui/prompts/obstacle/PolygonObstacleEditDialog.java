// package org.team2363.helixnavigator.ui.prompts.obstacle;

// import java.util.ArrayList;
// import java.util.List;

// import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
// import org.team2363.lib.ui.prompts.ConstrainedDecimalTextField;
// import org.team2363.lib.ui.prompts.DecimalTextField;

// import javafx.scene.Node;
// import javafx.scene.control.Button;
// import javafx.scene.layout.GridPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.text.Text;

// public class PolygonObstacleEditDialog extends ObstacleEditDialog {

//     private final HPolygonObstacle polygonObstacle;
//     private final HPolygonObstacle backupPolygonObstacle;

//     private final List<Node> polygonPointFields = new ArrayList<>();
//     private final Text centerXText = new Text("Center X:");
//     private final DecimalTextField centerXTextField = new DecimalTextField();
//     private final ConstrainedDecimalTextField radiusTextField = new ConstrainedDecimalTextField(0, Double.MAX_VALUE);
//     private final Button addPointButton = new Button("+");
//     private final HBox pointsButtons = new HBox();

//     public PolygonObstacleEditDialog(HPolygonObstacle polygonObstacle) {
//         super(polygonObstacle, new HPolygonObstacle());
//         this.polygonObstacle = (HPolygonObstacle) obstacle;
//         this.backupPolygonObstacle = (HPolygonObstacle) backupObstacle;

//         GridPane.setConstraints(centerXText, 0, additionalItemsRow);
//         GridPane.setConstraints(centerXTextField, 1, additionalItemsRow);

//         propertyGrid.getChildren().addAll(centerXText, centerXTextField);

//         backupObstacle();
//         // Set ui to values of Obstacle:
//         initializeTextFields();
//         // Now bind the Obstacle to the values of the ui
//         bindObstacle();
//     }

//     private void clearPolygonPointFields() {
//         propertyGrid.getChildren().removeAll(polygonPointFields);
//     }

//     private void addPolygonPointField(int index) {
//         Text polygonPointText = new Text("Point " + index);
//         Text xText = new Text("X:");
//         DecimalTextField x;
//         Text yText = new Text("Y:");

//     }

//     @Override
//     protected void initializeTextFields() {
//         super.initializeTextFields();
//         centerXTextField.setValue(circleObstacle.getCenterX());
//         centerYTextField.setValue(circleObstacle.getCenterY());
//         radiusTextField.setValue(circleObstacle.getRadius());
//     }

//     @Override
//     protected void unbindObstacle() {
//         super.unbindObstacle();
//         circleObstacle.centerXProperty().unbind();
//         circleObstacle.centerYProperty().unbind();
//         circleObstacle.radiusProperty().unbind();
//     }

//     @Override
//     protected void bindObstacle() {
//         super.bindObstacle();
//         circleObstacle.centerXProperty().bind(centerXTextField.valueProperty());
//         circleObstacle.centerYProperty().bind(centerYTextField.valueProperty());
//         circleObstacle.radiusProperty().bind(radiusTextField.valueProperty());
//     }

//     @Override
//     protected void backupObstacle() {
//         super.backupObstacle();
//         backupCircleObstacle.setCenterX(circleObstacle.getCenterX());
//         backupCircleObstacle.setCenterY(circleObstacle.getCenterY());
//         backupCircleObstacle.setRadius(circleObstacle.getRadius());
//     }

//     @Override
//     protected void restoreBackup() {
//         super.restoreBackup();
//         circleObstacle.setCenterX(backupCircleObstacle.getCenterX());
//         circleObstacle.setCenterY(backupCircleObstacle.getCenterY());
//         circleObstacle.setRadius(backupCircleObstacle.getRadius());
//     }
// }