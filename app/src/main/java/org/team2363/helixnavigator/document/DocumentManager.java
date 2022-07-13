package org.team2363.helixnavigator.document;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONParserException;
import com.jlbabilino.json.JSONSerializer;

import org.team2363.helixnavigator.global.Standards;
import org.team2363.lib.ui.prompts.SavePrompt;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class DocumentManager {

    private static final ExtensionFilter WAYPOINT_BUNDLE_FILE_TYPE = new ExtensionFilter("Waypoint Bundle (*.json)", "*.json");

    private static final Logger logger = Logger.getLogger("org.team2363.helixnavigator.document");

    private final DocumentActions actions;
    private final Stage stage;
    private final DoubleProperty pathAreaWidth = new SimpleDoubleProperty(this, "pathAreaWidth", 0.0);
    private final DoubleProperty pathAreaHeight = new SimpleDoubleProperty(this, "pathAreaHeight", 0.0);
    private final ReadOnlyObjectWrapper<HDocument> document = new ReadOnlyObjectWrapper<HDocument>(this, "document", null);
    private final ReadOnlyBooleanWrapper isDocumentOpen = new ReadOnlyBooleanWrapper(this, "isDocumentOpen", false);

    public DocumentManager(Stage stage) {
        this.actions = new DocumentActions(this);
        this.stage = stage;
    }

    public final ReadOnlyObjectProperty<HDocument> documentProperty() {
        return document.getReadOnlyProperty();
    }

    private final void setDocument(HDocument value) {
        logger.info("Setting document");
        setIsDocumentOpen(value != null); // spent about an hour trying to fix a bug:
        document.set(value);              // just had to switch these two lines
        if (actions.getLockZoom()) {
            actions.zoomToFit();
        }
        updateStageTitle();
    }

    private final void updateStageTitle() {
        String title;
        if (getIsDocumentOpen()) {
            String docName;
            if (getDocument().getSaveLocation() != null) {
                docName = getDocument().getSaveLocation().getName();
            } else {
                docName = "New Document";
            }
            title = Standards.APPLICATION_NAME + " \u2014 " + docName; // escaped em dash
        } else {
            title = Standards.APPLICATION_NAME;
        }
        stage.setTitle(title);
    }

    public final HDocument getDocument() {
        return document.get();
    }

    public final ReadOnlyBooleanProperty isDocumentOpenProperty() {
        return isDocumentOpen.getReadOnlyProperty();
    }

    private final void setIsDocumentOpen(boolean value) {
        isDocumentOpen.set(value);
    }

    public final boolean getIsDocumentOpen() {
        return isDocumentOpen.get();
    }

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
        logger.info("User requested \"New Document\".");
        if (requestCloseDocument()) {
            logger.info("Document succesfully closed, setting to new blank document.");
            setDocument(new HDocument());
            actions.zoomToFit();
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
            logger.info("Opening file: " + file.getAbsolutePath());
            HDocument openedDocument = JSONDeserializer.deserialize(file, HDocument.class);
            openedDocument.setSaveLocation(file);
            setDocument(openedDocument);
            logger.info("File \"" + file.getAbsolutePath() + "\" succesffully opened.");
            return true;
        } catch (IOException e) {
            logger.finer("Could not read file " + file.getAbsolutePath() + ": " + e.getMessage());
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("File Error");
            alert.setContentText("Could not read file " + file.getAbsolutePath() + ": " + e.getMessage());
            alert.showAndWait();
            return false;
        } catch (JSONParserException e) {
            logger.finer("Could not parse JSON in file \"" + file.getName() + "\":" + e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Parsing Error");
            alert.setContentText("Could not parse JSON in file \"" + file.getName() + "\":" + e.getMessage());
            alert.showAndWait();
            return false;
        } catch (JSONDeserializerException e) {
            logger.finer("Could not deserialize JSON data in file \"" + file.getName() + "\":" + e.getMessage());
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
        fileChooser.setTitle("Select a Document");
        fileChooser.getExtensionFilters().add(Standards.DOCUMENT_FILE_TYPE);
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
        try {
            JSONSerializer.serializeFile(getDocument(), saveLocation);
            return true;
        } catch (IOException e) {
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
     * <p>
     * <b>Precondition: </b> A document should be open.
     * </p>
     * <p>
     * Prompts the user to save the document. If the save location is already set, then
     * it will be saved there, but it it isn't set, then the user will be prompted to select
     * a save location.
     * </p>
     * 
     * @return {@code true} if and only if there is no document open or the open document was saved successfully
     */
    public final boolean requestSaveDocument() {
        if (getIsDocumentOpen()) {
            if (getDocument().isSaveLocationSet()) {
                return saveDocument();
            } else {
                return requestSaveAsDocument();
            }
        } else {
            return true;
        }
    }

    /**
     * <p>
     * <b>Precondition: </b> A document should be open.
     * </p>
     * <p>
     * Prompts the user to save the document to a specific location.
     * </p>
     * 
     * @return {@code true} if and only if there is no document open or the open document was saved successfully
     */
    public final boolean requestSaveAsDocument() {
        if (getIsDocumentOpen()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(Standards.DOCUMENT_FILE_TYPE);
            File saveLocation = fileChooser.showSaveDialog(stage);
            if (saveLocation != null) {
                getDocument().setSaveLocation(saveLocation);
                updateStageTitle();
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
        logger.info("Document close reuqested.");
        if (!getIsDocumentOpen()) {
            logger.info("no document was open, already closed.");
            return true;
        } else if (getDocument().isSaved()) {
            logger.info("Document is saved, closing without prompt.");
            closeDocument();
            return true;
        } else {
            logger.info("Document is not saved, prompting user...");
            SavePrompt prompt = new SavePrompt();
            Optional<ButtonType> response = prompt.showAndWait();
            if (response.isPresent()) {
                if (response.orElseThrow() == ButtonType.YES) {
                    requestSaveDocument();
                    closeDocument();
                    return true;
                } else if (response.orElseThrow() == ButtonType.NO) {
                    closeDocument();
                    return true;
                } else {
                    return false;
                }
            } else { // pressed x to close stage
                return false;
            }
        }
    }

    public final boolean requestExportWaypointBundle() {
        logger.info("Export waypoint bundle requested.");
        if (getIsDocumentOpen() && getDocument().isPathSelected()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(WAYPOINT_BUNDLE_FILE_TYPE);
            File saveLocation = fileChooser.showSaveDialog(stage);
            if (saveLocation != null) {
                HPath selectedPath = getDocument().getSelectedPath();
                HWaypointBundle waypointBundle = new HWaypointBundle(selectedPath);
                try {
                    JSONSerializer.serializeFile(waypointBundle, saveLocation);
                    return true;
                } catch (IOException e) {
                    logger.finer("Error while saving waypoint bundle");
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public DocumentActions actions() {
        return actions;
    }

    public Stage getStage() {
        return stage;
    }

    public DoubleProperty pathAreaWidthProperty() {
        return pathAreaWidth;
    }

    public void setPathAreaWidth(double value) {
        pathAreaWidth.set(value);
    }
    
    public double getPathAreaWidth() {
        return pathAreaWidth.get();
    }

    public DoubleProperty pathAreaHeightProperty() {
        return pathAreaHeight;
    }

    public void setPathAreaHeight(double value) {
        pathAreaHeight.set(value);
    }
    
    public double getPathAreaHeight() {
        return pathAreaHeight.get();
    }
}