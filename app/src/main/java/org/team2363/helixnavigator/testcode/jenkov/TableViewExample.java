package org.team2363.helixnavigator.testcode.jenkov;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class TableViewExample extends Application {

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        TableView<Point> tableView = new TableView<>();

        TableColumn<Point, Integer> indexColumn = new TableColumn<>("Index");
        indexColumn.setCellFactory(column -> new TableCell<Point, Integer>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index + 1));
                }
            }
        });
        indexColumn.setSortable(false);
        indexColumn.setReorderable(false);

        TableColumn<Point, Double> xColumn = new TableColumn<>("X Value");
        
        xColumn.setCellFactory(TextFieldTableCell.<Point, Double>forTableColumn(decimalConverter));
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        xColumn.setSortable(false);
        xColumn.setReorderable(false);

        TableColumn<Point, String> yColumn = new TableColumn<>("Y Value");

        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        yColumn.setSortable(false);
        yColumn.setReorderable(false);

        tableView.getColumns().add(indexColumn);
        tableView.getColumns().add(xColumn);
        tableView.getColumns().add(yColumn);

        tableView.setEditable(true);

        tableView.getItems().add(new Point());
        tableView.getItems().add(new Point());

        VBox vbox = new VBox(tableView);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

}