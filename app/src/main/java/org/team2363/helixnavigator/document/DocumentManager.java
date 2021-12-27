package org.team2363.helixnavigator.document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import com.jlbabilino.json.JSON;
import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONParser;
import com.jlbabilino.json.JSONParserException;
import com.jlbabilino.json.JSONSerializer;

import org.team2363.helixnavigator.ui.prompts.SavePrompt;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class DocumentManager {

    private static final ExtensionFilter DOCUMENT_FILE_TYPE = new ExtensionFilter("HelixNavigator Document (*.json)", "*.json");

    private final Stage stage;
    private final ReadOnlyObjectWrapper<HDocument> document = new ReadOnlyObjectWrapper<HDocument>(this, "document", null);
    private final ReadOnlyBooleanWrapper isDocumentOpen = new ReadOnlyBooleanWrapper(this, "isDocumentOpen", false);

    public DocumentManager(Stage stage) {
        this.stage = stage;
    }

    public final ReadOnlyObjectProperty<HDocument> documentProperty() {
        return document.getReadOnlyProperty();
    }

    private final void setDocument(HDocument value) {
        System.out.println("setting doc");
        setIsDocumentOpen(value != null); // spent about an hour trying to fix a bug:
        document.set(value);              // just had to switch these two lines
    }

    public final HDocument getDocument() {
        return document.get();
    }

    public final ReadOnlyBooleanProperty isDocumentOpenProperty() {
        return isDocumentOpen.getReadOnlyProperty();
    }

    private final void setIsDocumentOpen(boolean value) {
        System.out.println("DocumentManager: isDocumentOpen set to " + value);
        isDocumentOpen.set(value);
    }

    public final boolean getIsDocumentOpen() {
        return isDocumentOpen.get();
    }
    /**
     * Sets the document to {@code null}
     */
    private final void closeDocument() {
        setDocument(null);
    }

    /**
     * Attempts to create a new document. If there is no document open, or the
     * document is saved, then a new document will be created and set as the
     * document. The method will return {@code true}. If there is a document open
     * and it is not saved, then the user
     * will be prompted to save it. If the user cancels, then the document will
     * not change, and the method will return {@code false}. If the user either
     * saves
     * or does not save, then the new document will be created and the method will
     * return {@code true}.
     * 
     * @return {@code true} if and only if a new document was created and loaded.
     */
    public final boolean requestNewDocument() {
        System.out.println("DocumentManager: Requesting new document.");
        if (requestCloseDocument()) {
            System.out.println("DocumentManager: Document succesfully closed, setting to new blank document.");
            setDocument(new HDocument());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Precondition: The user must be prompted to save the current document before
     * opening.
     * 
     * @param file the file to open as a document
     * @return {@true} if and only if the document was successfully opened
     */
    private final boolean openDocument(File file) {
        try {
            System.out.println("Opening doc");
            String jsonString = Files.readString(Path.of(file.getAbsolutePath()));
            JSON parsedJSON = JSONParser.parseStringAsJSON(jsonString);
            HDocument openedDocument = JSONDeserializer.deserializeJSON(parsedJSON, HDocument.class);
            setDocument(openedDocument);
            return true;
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("File Error");
            alert.setContentText("Could not read file: " + file.getAbsolutePath());
            alert.showAndWait();
            return false;
        } catch (JSONParserException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Parsing Error");
            alert.setContentText("Could not parse JSON in file \"" + file.getName() + "\":" + e.getMessage());
            alert.showAndWait();
            return false;
        } catch (JSONDeserializerException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("JSON Error");
            alert.setContentText(
                    "Could not deserialize JSON data in file \"" + file.getName() + "\":" + e.getMessage());
            alert.showAndWait();
            return false;
        }
    }

    /**
     * True if successfully opened document, false otherwise
     * 
     * @return
     */
    public final boolean requestOpenDocument() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a HelixNavigator Document");
        fileChooser.getExtensionFilters().add(DOCUMENT_FILE_TYPE);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            if (file.canRead()) {
                if (requestCloseDocument()) {
                    return openDocument(file);
                } else {
                    return false;
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("File Error");
                alert.setContentText("Could not read file: " + file.getAbsolutePath());
                alert.showAndWait();
                return false;
            }
        } else {
            return false;
        }
    }

    // TODO: Save stuff here:

    /**
     * <p>
     * <b>Precondition: </b>The document must have a save location set before
     * running.
     * </p>
     * <p>
     * This method saves the document to the save location specified by the
     * document. It will display alerts to the user if there are errors.
     * </p>
     * 
     * @return {@code true} if and only if the document was successfully saved.
     */
    private final boolean saveDocument() {
        File saveLocation = getDocument().getSaveLocation();
        if (saveLocation.getParentFile().canWrite()) {
            JSON json = JSONSerializer.serializeJSON(getDocument());
            try {
                PrintWriter writer = new PrintWriter(saveLocation);
                writer.print(json.exportJSON());
                writer.close();
                // getDocument().setSaved(true);      -- implement later when state management is added as a feature
                return true;
            } catch (FileNotFoundException e) {
                // I don't think this could ever happen since we checked already with canWrite
                return false;
            }
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("File Error");
            alert.setContentText("Could not write to save location: " + saveLocation.getAbsolutePath()
                    + ". Please select a different save location.");
            Optional<ButtonType> response = alert.showAndWait();
            if (response.isPresent()) {
                ButtonType buttonType = response.orElseThrow();
                if (buttonType == ButtonType.OK) {
                    return requestSaveAsDocument();
                } else /* if (buttonType == ButtonType.CANCEL) */ {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * 
     * @return
     */
    public final boolean requestSaveDocument() {
        if (getIsDocumentOpen()) { // TODO: these checks should be removed soon, the menu items should just be grayyed out
            if (getDocument().isSaveLocationSet()) {
                return saveDocument();
            } else {
                return requestSaveAsDocument();
            }
        } else {
            return true;
        }
    }

    public final boolean requestSaveAsDocument() {
        if (getIsDocumentOpen()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(DOCUMENT_FILE_TYPE);
            File saveLocation = fileChooser.showSaveDialog(stage);
            if (saveLocation != null) {
                getDocument().setSaveLocation(saveLocation);
                return saveDocument();
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Attempts to close the document by prompting the user. The user can decide to
     * close and save, close without saving, or keep the current document open.
     * 
     * @return {@code true} if document was closed or no document was open,
     *         {@code false} if document was not closed
     */
    public final boolean requestCloseDocument() {
        System.out.println("DocumentManager: Document close reuqested.");
        if (!getIsDocumentOpen()) {
            System.out.println("DocumentManager: no document was open, already closed.");
            return true;
        } else if (getDocument().isSaved()) {
            System.out.println("DocumentManager: Document is saved, closing without prompt.");
            closeDocument();
            return true;
        } else {
            System.out.println("DocumentManager: Document is not saved, prompting user...");
            SavePrompt prompt = new SavePrompt();
            Optional<ButtonType> response = prompt.showAndWait();
            if (response.get() == ButtonType.YES) {
                requestSaveDocument();
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