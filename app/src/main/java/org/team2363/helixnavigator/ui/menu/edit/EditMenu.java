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
// package org.team2363.helixnavigator.ui.menu.edit;

// import javafx.scene.control.Menu;
// import javafx.scene.control.MenuItem;
// import javafx.scene.control.SeparatorMenuItem;

// /**
//  * This class manages the "Edit" menu in the toolbar.
//  *
//  * @author Justin Babilino
//  */
// public class EditMenu extends Menu {

//     /**
//      * The "Undo" menu item.
//      */
//     private final MenuItem undoMenuItem;
//     /**
//      * The "Redo" menu item.
//      */
//     private final MenuItem redoMenuItem;
//     /**
//      * The "Cut" menu item.
//      */
//     private final MenuItem cutMenuItem;
//     /**
//      * The "Copy" menu item.
//      */
//     private final MenuItem copyMenuItem;
//     /**
//      * The "Paste" menu item.
//      */
//     private final MenuItem pasteMenuItem;

//     /**
//      * Constructs a <code>EditMenu</code> along with its subsequent menu items.
//      */
//     public EditMenu() {
//         setText("_Edit");
//         undoMenuItem = new UndoMenuItem();
//         redoMenuItem = new RedoMenuItem();
//         cutMenuItem = new CutMenuItem();
//         copyMenuItem = new CopyMenuItem();
//         pasteMenuItem = new PasteMenuItem();
//         getItems().add(undoMenuItem);
//         getItems().add(redoMenuItem);
//         getItems().add(new SeparatorMenuItem());
//         getItems().add(cutMenuItem);
//         getItems().add(copyMenuItem);
//         getItems().add(pasteMenuItem);
//         getItems().add(new SeparatorMenuItem());
//     }
// }
