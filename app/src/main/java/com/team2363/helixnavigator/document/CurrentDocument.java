/*
 * Copyright (C) 2021 Justin Babilino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.team2363.helixnavigator.document;

import java.util.Optional;

import com.team2363.helixnavigator.ui.prompts.SavePrompt;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Justin Babilino
 */
public class CurrentDocument {
    private static final ObjectProperty<HDocument> document = new SimpleObjectProperty<HDocument>();

    public static ObjectProperty<HDocument> documentProperty() {
        return document;
    }

    public static void setDocument(HDocument value) {
        document.set(value);
    }

    public static HDocument getDocument() {
        return document.get();
    }

    public static void closeDocument() {
        setDocument(null);
    }

    public static boolean isDocumentOpen() {
        return getDocument() != null;
    }

    /**
     * Attempts to close the document by prompting the user. The user can decide to
     * close and save, close without saving, or keep the current document open.
     * 
     * @return <code>true</code> if document was closed or no document was open,
     *         <code>false</code> if document was not closed
     */
    public static boolean requestDocumentClose() {
        if (!isDocumentOpen() || getDocument().isSaved()) {
            closeDocument();
            return true;
        } else {
            SavePrompt prompt = new SavePrompt();
            Optional<ButtonType> response = prompt.showAndWait();
            if (response.get() == ButtonType.YES) {
                getDocument().save();
                closeDocument();
                return true;
            } else if (response.get() == ButtonType.NO) {
                closeDocument();
                return true;
            } else {
                return false;
            }
        }
    }
}