// package org.team2363.helixnavigator.document.field;

// import com.jlbabilino.json.DeserializedJSONConstructor;
// import com.jlbabilino.json.DeserializedJSONObjectValue;
// import com.jlbabilino.json.JSONSerializable;
// import com.jlbabilino.json.SerializedJSONObjectValue;

// import org.team2363.helixnavigator.global.DefaultFieldImages;

// import javafx.scene.image.Image;

// @JSONSerializable
// public class HFieldImageDefault implements HFieldImage {

//     private final String name;

//     @DeserializedJSONConstructor
//     public HFieldImageDefault(@DeserializedJSONObjectValue(key = "name") String name) {
//         this.name = name;
//     }

//     @SerializedJSONObjectValue(key = "reference_type")
//     @Override
//     public ReferenceType getReferenceType() {
//         return ReferenceType.DEFAULT;
//     }

//     @SerializedJSONObjectValue(key = "name")
//     @Override
//     public String getName() {
//         return name;
//     }

//     @Override
//     public Image getImage() {
//         return DefaultFieldImages.fromName(name);
//     }
// }