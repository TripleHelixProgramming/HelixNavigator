// package org.team2363.helixnavigator.ui.document;

// import org.team2363.helixnavigator.document.HSelectableElement;
// import org.team2363.helixnavigator.document.HSelectionModel;

// import javafx.beans.property.ObjectProperty;
// import javafx.beans.property.SimpleObjectProperty;
// import javafx.beans.value.ObservableValue;
// import javafx.collections.FXCollections;
// import javafx.collections.ListChangeListener;
// import javafx.collections.ObservableList;
// import javafx.scene.control.ListView;
// import javafx.scene.control.MultipleSelectionModel;
// import javafx.scene.control.SelectionMode;

// public class SelectableElementListView<E extends HSelectableElement> extends ListView<E> {

//     private final ObservableList<E> BLANK = FXCollections.<E>observableArrayList();
//     private final ObjectProperty<HSelectionModel<E>> selectionModel = new SimpleObjectProperty<>(this, "selectionModel", null);

//     private final ListChangeListener<? super Integer> onListViewSelectedIndicesChanged = this::listViewSelectedIndicesChanged;
//     private final ListChangeListener<? super Integer> onItemsSelectedIndicesChanged = this::itemsSelectedIndicesChanged;

//     public SelectableElementListView() {
//         setEditable(true);
//         setSelectionModel(value);
//         getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//         getSelectionModel().getSelectedIndices().addListener(onListViewSelectedIndicesChanged);
//         itemsProperty().addListener(this::itemsChanged);
//     }

//     private void listViewSelectedIndicesChanged(ListChangeListener.Change<? extends Integer> change) {
//         if (documentManager.getIsDocumentOpen() && documentManager.getDocument().isPathSelected()) {
//             documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().getSelectedIndices().removeListener(onPathSelectedWaypointsIndicesChanged);
//             documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().clear();
//             documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().selectIndices(getSelectionModel().getSelectedIndices());
//             documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().getSelectedIndices().addListener(onPathSelectedWaypointsIndicesChanged);
//         }
//     }

//     private void itemsChanged(ObservableValue<? extends ObservableList<E>> currentItems, ObservableList<E> oldItems, ObservableList<E> newItems) {
//         unloadItems(oldItems);
//         loadSelectedPath(newItems);
//     }
//     private void unloadItems(ObservableList<E> oldPath) {
//         if (oldPath != null) {
//             getSelectionModel().getSelectedIndices().removeListener(onListViewSelectedIndicesChanged);
//             setItems(BLANK);
//             getSelectionModel().getSelectedIndices().addListener(onListViewSelectedIndicesChanged);
//             oldPath.getWaypointsSelectionModel().getSelectedIndices().removeListener(onPathSelectedWaypointsIndicesChanged);
//         }
//     }
//     private void loadSelectedPath(ObservableList<E> newPath) {
//         if (newPath != null) {
//             setItems(newPath.getWaypoints());
//             setContextMenu(noneSelectedContextMenu);
//             for (int index : documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().getSelectedIndices()) {
//                 getSelectionModel().select(index);
//             }
//             newPath.getWaypointsSelectionModel().getSelectedIndices().addListener(onPathSelectedWaypointsIndicesChanged);
//         }
//     }
//     private void itemsSelectedIndicesChanged(ListChangeListener.Change<? extends Integer> change) {
//         getSelectionModel().getSelectedIndices().removeListener(onListViewSelectedIndicesChanged);
//         getSelectionModel().clearSelection();
//         for (int index : change.getList()) {
//             getSelectionModel().select(index);
//         }
//         getSelectionModel().getSelectedIndices().addListener(onListViewSelectedIndicesChanged);
//     }
// }