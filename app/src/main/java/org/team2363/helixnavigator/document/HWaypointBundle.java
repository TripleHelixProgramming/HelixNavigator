// package org.team2363.helixnavigator.document;

// import java.util.ArrayList;
// import java.util.List;

// import com.jlbabilino.json.JSONEntry.JSONType;
// import com.jlbabilino.json.JSONDeserializable;
// import com.jlbabilino.json.JSONSerializable;
// import com.jlbabilino.json.SerializedJSONEntry;

// import org.team2363.helixnavigator.document.timeline.HHardWaypoint;
// import org.team2363.helixnavigator.document.timeline.HWaypoint;

// @JSONSerializable(JSONType.ARRAY)
// @JSONDeserializable({JSONType.ARRAY})
// public class HWaypointBundle {
    
//     private List<double[]> bundleWaypoints = new ArrayList<>();

//     public HWaypointBundle(HPath path) {
//         for (HWaypoint waypoint : path.getTimeline()) {
//             if (waypoint.isHard()) {
//                 HHardWaypoint hardWaypoint = (HHardWaypoint) waypoint;
//                 double convertedX = hardWaypoint.getX() * 0.0254;
//                 double convertedY = hardWaypoint.getY() * 0.0254;
//                 double convertedHeading = Math.toRadians(hardWaypoint.getHeading());
//                 double[] bundleWaypoint = {convertedX, convertedY, convertedHeading};
//                 bundleWaypoints.add(bundleWaypoint);
//             }
//         }
//     }

//     @SerializedJSONEntry
//     public List<double[]> getWaypointBundle() {
//         return bundleWaypoints;
//     }
// }