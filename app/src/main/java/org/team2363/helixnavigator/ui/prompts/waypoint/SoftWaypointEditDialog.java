package org.team2363.helixnavigator.ui.prompts.waypoint;

import org.team2363.helixnavigator.document.waypoint.HSoftWaypoint;

public class SoftWaypointEditDialog extends WaypointEditDialog {

    // private HSoftWaypoint softWaypoint;
    // private HSoftWaypoint backupSoftWaypoint;

    public SoftWaypointEditDialog(HSoftWaypoint softWaypoint) {
        super(softWaypoint, new HSoftWaypoint());
        // this.softWaypoint = (HSoftWaypoint) super.waypoint;
        // this.backupSoftWaypoint = (HSoftWaypoint) super.backupWaypoint;

        backupWaypoint();
        // Set ui to values of HWaypoint:
        initializeTextFields();
        // Now bind the Waypoint to the values of the ui
        bindWaypoint();
    }

    // @Override
    // protected void initializeTextFields() {
    //     super.initializeTextFields();
    // }

    // @Override
    // protected void unbindWaypoint() {
    //     super.unbindWaypoint();
    // }

    // @Override
    // protected void bindWaypoint() {
    //     super.bindWaypoint();
    // }

    // @Override
    // protected void backupWaypoint() {
    //     super.backupWaypoint();
    // }

    // @Override
    // protected void restoreBackup() {
    //     super.restoreBackup();
    // }
}