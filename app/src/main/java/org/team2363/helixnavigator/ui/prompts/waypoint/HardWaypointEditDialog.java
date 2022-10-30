package org.team2363.helixnavigator.ui.prompts.waypoint;

import java.util.List;

import org.team2363.helixnavigator.document.timeline.HHardWaypoint;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class HardWaypointEditDialog extends WaypointEditDialog {
    
    private HHardWaypoint hardWaypoint;
    private HHardWaypoint backupHardWaypoint;

    private final Text headingText = new Text("Heading:");
    private final HeadingTextField headingTextField = new HeadingTextField();

    public HardWaypointEditDialog(HHardWaypoint hardWaypoint) {
        super(hardWaypoint, new HHardWaypoint());
        this.hardWaypoint = (HHardWaypoint) waypoint;
        this.backupHardWaypoint = (HHardWaypoint) backupWaypoint;
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
        headingTextField.setValue(hardWaypoint.getHeading());
    }

    @Override
    protected void unbindWaypoint() {
        super.unbindWaypoint();
        hardWaypoint.headingProperty().unbind();
    }

    @Override
    protected void bindWaypoint() {
        super.bindWaypoint();
        hardWaypoint.headingProperty().bind(headingTextField.valueProperty());
    }

    @Override
    protected void backupWaypoint() {
        super.backupWaypoint();
        backupHardWaypoint.setHeading(hardWaypoint.getHeading());
    }

    @Override
    protected void restoreBackup() {
        super.restoreBackup();
        hardWaypoint.setHeading(backupHardWaypoint.getHeading());
    }
}