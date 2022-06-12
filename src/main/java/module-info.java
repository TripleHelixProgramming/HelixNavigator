module org.team2363.helixnavigator {
    requires transitive java.logging;
    requires transitive com.jlbabilino.json;
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    // requires java.measure;
    // requires tech.units.indriya;
    // requires systems.uom.common;
    // requires systems.uom.quantity;
    // requires systems.uom.unicode;
    // requires djunits;
    exports org.team2363.helixnavigator;
    exports org.team2363.helixnavigator.document;
    exports org.team2363.helixnavigator.document.obstacle;
    exports org.team2363.helixnavigator.document.waypoint;
    exports org.team2363.helixnavigator.document.field.image;

    exports org.team2363.helixnavigator.testcode;
    exports org.team2363.helixnavigator.testcode.jenkov;
}