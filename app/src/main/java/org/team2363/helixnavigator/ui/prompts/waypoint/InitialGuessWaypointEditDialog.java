package org.team2363.helixnavigator.ui.prompts.waypoint;

import java.util.List;

import org.team2363.helixnavigator.document.timeline.HInitialGuessWaypoint;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class InitialGuessWaypointEditDialog extends WaypointEditDialog {

    private HInitialGuessWaypoint initialGuessWaypoint;
    private HInitialGuessWaypoint backupInitialGuessWaypoint;

    private final Text headingText = new Text("Heading:");
    private final HeadingTextField headingTextField = new HeadingTextField();

    public InitialGuessWaypointEditDialog(HInitialGuessWaypoint initialGuessWaypoint) {
        super(initialGuessWaypoint, new HInitialGuessWaypoint());
        this.initialGuessWaypoint = (HInitialGuessWaypoint) super.waypoint;
        this.backupInitialGuessWaypoint = (HInitialGuessWaypoint) super.backupWaypoint;

        GridPane.setConstraints(headingText, 0, 3);
        GridPane.setConstraints(headingTextField, 1, 3);

        addGridItems(List.of(headingText, headingTextField));

        backupWaypoint();
        // Set ui to values of HWaypoint:
        initializeTextFields();
        // Now bind the Waypoint to the values of the ui
        bindWaypoint();
    }

    @Override
    protected void initializeTextFields() {
        super.initializeTextFields();
        headingTextField.setValue(initialGuessWaypoint.getHeading());
    }

    @Override
    protected void unbindWaypoint() {
        super.unbindWaypoint();
        initialGuessWaypoint.headingProperty().unbind();
    }

    @Override
    protected void bindWaypoint() {
        super.bindWaypoint();
        initialGuessWaypoint.headingProperty().bind(headingTextField.valueProperty());
    }

    @Override
    protected void backupWaypoint() {
        super.backupWaypoint();
        backupInitialGuessWaypoint.setHeading(initialGuessWaypoint.getHeading());
    }

    @Override
    protected void restoreBackup() {
        super.restoreBackup();
        initialGuessWaypoint.setHeading(backupInitialGuessWaypoint.getHeading());
    }
}