package org.team2363.helixnavigator.ui.prompts.waypoint;

import org.team2363.helixnavigator.document.timeline.HInitialGuessWaypoint;

public class InitialGuessWaypointEditDialog extends WaypointEditDialog {

    // private HInitialGuessWaypoint initialGuessWaypoint;
    // private HInitialGuessWaypoint backupInitialGuessWaypoint;

    public InitialGuessWaypointEditDialog(HInitialGuessWaypoint initialGuessWaypoint) {
        super(initialGuessWaypoint, new HInitialGuessWaypoint());
        // this.initialGuessWaypoint = (HInitialGuessWaypoint) super.waypoint;
        // this.backupInitialGuessWaypoint = (HInitialGuessWaypoint) super.backupWaypoint;

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