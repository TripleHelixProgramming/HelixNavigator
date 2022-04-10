package org.team2363.helixnavigator.ui.prompts.obstacle;

import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

public class PolygonPointsTableView extends TableView<HPolygonPoint> {

    private final StringConverter<Double> decimalConverter = new StringConverter<Double>() {

        @Override
        public String toString(Double object) {
            if (object != null) {
                return Double.toString(object);
            } else {
                return "";
            }
        }

        @Override
        public Double fromString(String string) {
            double rawValue;
            try {
                rawValue = Double.parseDouble(string);
            } catch (NumberFormatException e) {
                rawValue = 0.0;
            }
            return rawValue;
        }
    };

    public PolygonPointsTableView() {
        TableColumn<HPolygonPoint, Double> xColumn = new TableColumn<>("X");
        xColumn.setCellFactory(TextFieldTableCell.forTableColumn(decimalConverter));
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        xColumn.setSortable(false);
        xColumn.setResizable(false);
        xColumn.setReorderable(false);
        xColumn.setPrefWidth(113);
        TableColumn<HPolygonPoint, Double> yColumn = new TableColumn<>("Y");
        yColumn.setCellFactory(TextFieldTableCell.forTableColumn(decimalConverter));
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        yColumn.setSortable(false);
        yColumn.setResizable(false);
        yColumn.setReorderable(false);
        yColumn.setPrefWidth(113);

        getColumns().add(xColumn);
        getColumns().add(yColumn);

        setEditable(true);
    }
}