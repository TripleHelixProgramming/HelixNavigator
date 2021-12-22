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

// package org.team2363.helixnavigator.ui.toolbar.file;

// import javafx.scene.control.Menu;
// import javafx.scene.control.MenuItem;
// import javafx.scene.control.SeparatorMenuItem;

// /**
//  * This class manages the "File" menu in the toolbar.
//  * 
//  * @author Justin Babilino
//  */
// public class FileMenu extends Menu {
    
//     /**
//      * The "New Project" menu item.
//      */
//     private final MenuItem newProjectMenuItem;
//     /**
//      * The "Open Project" menu item.
//      */
//     private final MenuItem openProjectMenuItem;
//     private final MenuItem closeProjectMenuItem;
//     /**
//      * The "Save" menu item.
//      */
//     private final MenuItem saveMenuItem;
//     /**
//      * The "Save As" menu item.
//      */
//     private final MenuItem saveAsMenuItem;
//     /**
//      * The "Import" menu item.
//      */
//     private final MenuItem importMenuItem;
//     /**
//      * The "Export" menu item.
//      */
//     private final MenuItem exportMenuItem;
    
//     /**
//      * Constructs a <code>FileMenu</code> along with its subsequent menu items.
//      */
//     public FileMenu() {
//         setText("_File");
//         newProjectMenuItem = new NewDocumentMenuItem();
//         openProjectMenuItem = new OpenProjectMenuItem();
//         closeProjectMenuItem = new ;
//         saveMenuItem = new SaveMenuItem();
//         saveAsMenuItem = new SaveAsMenuItem();
//         importMenuItem = new ImportMenuItem();
//         exportMenuItem = new ExportMenuItem();
//         getItems().add(newProjectMenuItem);
//         getItems().add(openProjectMenuItem);
//         getItems().add(new SeparatorMenuItem());
//         getItems().add(saveMenuItem);
//         getItems().add(saveAsMenuItem);
//         getItems().add(new SeparatorMenuItem());
//         getItems().add(importMenuItem);
//         getItems().add(exportMenuItem);
//     }
// }