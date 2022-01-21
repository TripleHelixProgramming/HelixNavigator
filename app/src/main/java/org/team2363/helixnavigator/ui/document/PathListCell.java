package org.team2363.helixnavigator.ui.document;

import org.team2363.helixnavigator.document.HPath;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PathListCell extends ListCell<HPath> {

    public static final Callback<ListView<HPath>, ListCell<HPath>> PATH_CELL_FACTORY = 
            new Callback<ListView<HPath>, ListCell<HPath>>() {
        @Override
        public ListCell<HPath> call(ListView<HPath> listView) {
            return new PathListCell();
        }
    };

    public PathListCell() {
    }

    @Override
    public void updateItem(HPath item, boolean empty) {
        super.updateItem(item, empty);
        textProperty().unbind();
        if (empty || item == null) {
            setText("");
        } else {
            textProperty().bind(item.nameProperty());
        }
    }
}