package org.team2363.helixnavigator.document.drivetrain;
// package org.team2363.helixnavigator.document.drive;

// import org.team2363.helixnavigator.global.Standards.DefaultDifferentialDrive;

// import com.jlbabilino.json.DeserializedJSONConstructor;
// import com.jlbabilino.json.DeserializedJSONObjectValue;
// import com.jlbabilino.json.DeserializedJSONTarget;
// import com.jlbabilino.json.SerializedJSONObjectValue;

// import javafx.beans.property.DoubleProperty;
// import javafx.beans.property.SimpleDoubleProperty;

// public class HDifferentialDrive extends HDrive {

//     private final DoubleProperty wheelbase = new SimpleDoubleProperty(this, "wheelbase", DefaultDifferentialDrive.WHEELBASE);

//     @DeserializedJSONConstructor
//     public HDifferentialDrive() {
//     }

//     public DoubleProperty wheelbaseProperty() {
//         return wheelbase;
//     }
//     @DeserializedJSONTarget
//     public void setWheelbase(@DeserializedJSONObjectValue(key = "wheelbase") double value) {
//         wheelbase.set(value);
//     }
//     @SerializedJSONObjectValue(key = "wheelbase")
//     public double getWheelbase() {
//         return wheelbase.get();
//     }
// }