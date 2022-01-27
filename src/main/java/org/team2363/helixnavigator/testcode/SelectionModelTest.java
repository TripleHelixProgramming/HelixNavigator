package org.team2363.helixnavigator.testcode;

import org.team2363.helixnavigator.document.HPathElement;
import org.team2363.helixnavigator.document.HSelectionModel;
import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SelectionModelTest {
    public static void main(String[] args) {
        HPolygonPoint[] points = new HPolygonPoint[10];
        for (int i = 0; i < points.length; i++) {
            points[i] = new HPolygonPoint();
            points[i].setName("point_" + i);
        }
        ObservableList<HPolygonPoint> items = FXCollections.<HPolygonPoint>observableArrayList(points[0], points[1]);
        HSelectionModel<HPolygonPoint> selectionModel = new HSelectionModel<>(items);
        selectionModel.select(0);
        printStuff(items, selectionModel);
        items.add(0, points[2]);
        printStuff(items, selectionModel);
        items.remove(0);
        printStuff(items, selectionModel);
    }

    private static void printStuff(ObservableList<? extends HPathElement> items, HSelectionModel<? extends HPathElement> selectionModel) {
        System.out.println();
        System.out.println("Items:             " + items);
        System.out.println("Selected Indicies: " + selectionModel.getSelectedIndices());
        System.out.println("Selected Items:    " + selectionModel.getSelectedItems());
    }
}
