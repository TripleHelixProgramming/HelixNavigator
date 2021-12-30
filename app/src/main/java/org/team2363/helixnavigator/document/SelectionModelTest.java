package org.team2363.helixnavigator.document;

import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SelectionModelTest {
    public static void main(String[] args) {
        HPolygonPoint[] points = new HPolygonPoint[10];
        for (int i = 0; i < points.length; i++) {
            points[i] = new HPolygonPoint();
            points[i].setX(i);
            points[i].setY(i);
        }
        ObservableList<HPolygonPoint> list = FXCollections.<HPolygonPoint>observableArrayList(points);
        
        HSelectionModel<HPolygonPoint> selectionModel = new HSelectionModel<>(list);
        selectionModel.select(5);
        selectionModel.select(5);
        System.out.println(selectionModel.getSelectedIndices());
        System.out.println(selectionModel.getSelectedItems());


    }
}
