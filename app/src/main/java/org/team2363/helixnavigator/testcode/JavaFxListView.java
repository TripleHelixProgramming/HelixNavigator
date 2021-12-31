package org.team2363.helixnavigator.testcode;
// this was downloaded from https://newbedev.com/listview-using-custom-cell-factory-doesn-t-update-after-items-deleted
// thanks!
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class JavaFxListView extends Application {

    private static class Car {
        private String plate;

        public Car(String plate, String string2, String string3, double d) {
            this.plate = plate;
        }

        public String getPlate() {
            return plate;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        ListView<Car> plateList = new ListView<Car>();
        plateList.setCellFactory(new Callback<ListView<Car>, ListCell<Car>>() {

            @Override
            public ListCell<Car> call(ListView<Car> param) {
                ListCell<Car> cell = new ListCell<Car>() {

                    @Override
                    protected void updateItem(Car item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getPlate());
                        } else {
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });
        Button delete = new Button("Delete");
        ObservableList<Car> sample = FXCollections.observableArrayList();
        sample.add(new Car("123-abc", "opel", "corsa", 5.5));
        sample.add(new Car("123-cba", "vw", "passat", 7.5));

        delete.setOnAction((e) -> {
            plateList.getItems().remove(plateList.getSelectionModel().getSelectedItem());
            ObservableList<Car> t = plateList.getItems();
            plateList.setItems(t);
        });

        plateList.setItems(sample);
        arg0.setScene(new Scene(new VBox(plateList, delete)));
        arg0.show();
    }
}