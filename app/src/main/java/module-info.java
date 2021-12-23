module org.team2363.helixnavigator {
    requires com.jlbabilino.json;
    requires javafx.controls;
    requires javafx.graphics;
    exports org.team2363.helixnavigator;
    exports org.team2363.helixnavigator.document to com.jlbabilino.json;
    exports org.team2363.helixnavigator.document.field to com.jlbabilino.json;
    exports org.team2363.helixnavigator.document.obstacle to com.jlbabilino.json;
    exports org.team2363.helixnavigator.document.waypoint to com.jlbabilino.json;
}