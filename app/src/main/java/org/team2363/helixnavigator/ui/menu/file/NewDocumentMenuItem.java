// /*
//  * Copyright (C) 2021 Triple Helix Robotics - FRC Team 2363
//  *
//  * This program is free software: you can redistribute it and/or modify
//  * it under the terms of the GNU General Public License as published by
//  * the Free Software Foundation, either version 3 of the License, or
//  * (at your option) any later version.
//  *
//  * This program is distributed in the hope that it will be useful,
//  * but WITHOUT ANY WARRANTY; without even the implied warranty of
//  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  * GNU General Public License for more details.
//  *
//  * You should have received a copy of the GNU General Public License
//  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
//  */

// package com.team2363.helixnavigator.ui.toolbar.file;

// import java.io.File;
// import java.io.IOException;

// import com.team2363.helixnavigator.App;
// import com.team2363.helixnavigator.document.CurrentDocument;
// import com.team2363.helixnavigator.document.HDocument;

// import javafx.event.ActionEvent;
// import javafx.scene.control.MenuItem;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.input.KeyCombination;
// import javafx.stage.FileChooser;
// import javafx.stage.FileChooser.ExtensionFilter;

// /**
//  * This class represents the "New Document" menu item in the file menu.
//  * 
//  * @author Justin Babilino
//  */
// public class NewDocumentMenuItem extends MenuItem {
//     /**
//      * Image for the graphic.
//      */
//     private final Image graphic;
//     /**
//      * Viewer for the graphic.
//      */
//     private final ImageView graphicView;

//     /**
//      * Constructs a <code>NewDocumentMenuItem</code> and sets its text, graphic, and
//      * action.
//      */
//     public NewDocumentMenuItem() {
//         setText("New Document...");
//         setAccelerator(KeyCombination.keyCombination("shortcut+N"));

//         graphic = new Image(getClass().getResourceAsStream("/icons/file/newdocument.png"));
//         graphicView = new ImageView(graphic);
//         graphicView.setSmooth(true);
//         graphicView.setFitWidth(20);
//         graphicView.setFitHeight(20);
//         setGraphic(graphicView);

//         setOnAction(this::action);
//     }

//     /**
//      * The event that occurs when the menu item is pressed.
//      * 
//      * @param event
//      */
//     public void action(ActionEvent event) {
//         System.out.println("Creating new document...");
//         boolean closeSuccess = CurrentDocument.requestDocumentClose();
//         if (closeSuccess) {
//             FileChooser chooser = new FileChooser();
//             chooser.setTitle("New Document Location");
//             chooser.getExtensionFilters().add(new ExtensionFilter("Helix Navigator Document", "*.json"));
//             File chosenFile = chooser.showSaveDialog(App.getMainStage());
//             if (chosenFile != null) {
//                 try {
//                     chosenFile.createNewFile();
//                     HDocument newDocument = new HDocument();
//                     newDocument.setSaveLocation(chosenFile);
//                     newDocument.setSaved(true);
//                     CurrentDocument.setDocument(newDocument);
//                 } catch (IOException e) {
//                     System.out.println(e);
//                 }
//             }
//         }
//     }
// }