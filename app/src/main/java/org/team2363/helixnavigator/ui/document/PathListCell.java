package org.team2363.helixnavigator.ui.document;

import org.team2363.helixnavigator.document.HAutoRoutine;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PathListCell extends ListCell<HAutoRoutine> {

    public static final Callback<ListView<HAutoRoutine>, ListCell<HAutoRoutine>> PATH_CELL_FACTORY = 
            new Callback<ListView<HAutoRoutine>, ListCell<HAutoRoutine>>() {
        @Override
        public ListCell<HAutoRoutine> call(ListView<HAutoRoutine> listView) {
            return new PathListCell();
        }
    };

    public PathListCell() {
    }

    @Override
    public void updateItem(HAutoRoutine item, boolean empty) {
        super.updateItem(item, empty);
        textProperty().unbind();
        if (empty || item == null) {
            setText("");
        } else {
            textProperty().bind(item.nameProperty());
        }
    }
}