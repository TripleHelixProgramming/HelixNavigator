package org.team2363.helixnavigator.document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.jlbabilino.json.JSON;
import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONParser;

import org.team2363.helixnavigator.ui.prompts.SavePrompt;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ButtonType;

public class DocumentManager {

    private final ObjectProperty<HDocument> document = new SimpleObjectProperty<HDocument>(this, "document", null);

    public final ObjectProperty<HDocument> documentProperty() {
        return document;
    }

    public final void setDocument(HDocument value) {
        document.set(value);
    }

    public final HDocument getDocument() {
        return document.get();
    }

    public final void closeDocument() {
        setDocument(null);
    }

    public final boolean isDocumentOpen() {
        return getDocument() != null;
    }

    /**
     * Attempts to close the document by prompting the user. The user can decide to
     * close and save, close without saving, or keep the current document open.
     * 
     * @return <code>true</code> if document was closed or no document was open,
     *         <code>false</code> if document was not closed
     */
    public final boolean requestDocumentClose() {
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

    /**
     * True if successfully opened, false otherwise.
     * @param file
     * @return
     */
    public final boolean openDocument(File file) {
        try {
            String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
            JSON parsedJSON = JSONParser.parseStringAsJSON(jsonString);
            JSONDeserializer.deserializeJSON(parsedJSON, HDocument.class);
            return true;
        } catch (IOException e) {
            // TODO: show alert box
            return false;
        } catch (JSONDeserializerException e) {
            // TODO: show alert box
            return false;
        }
    }

    public final boolean promptDocumentOpen() {
        if (requestDocumentClose()) {
            
        }
        return true;
    }
}
