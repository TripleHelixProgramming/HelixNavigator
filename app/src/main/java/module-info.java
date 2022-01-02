module org.team2363.helixnavigator {
    requires transitive com.jlbabilino.json;
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    exports org.team2363.helixnavigator;
    exports org.team2363.helixnavigator.document to com.jlbabilino.json;
    exports org.team2363.helixnavigator.document.obstacle to com.jlbabilino.json;
    exports org.team2363.helixnavigator.document.waypoint to com.jlbabilino.json;
    exports org.team2363.helixnavigator.document.field.image to com.jlbabilino.json;

    exports org.team2363.helixnavigator.testcode;
}