// package com.team2363.helixnavigator.ui.editor;

// import java.io.InputStream;

// import com.team2363.helixnavigator.document.HDocument;
// import com.team2363.lib.base64.Base64Decoder;

// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;

// public class FieldImageView extends ImageView {
//     private HDocument document;
//     private Image image;
//     public FieldImageView() {
        
//     }
//     public void loadDocument(HDocument document) {
//         this.document = document;
//         image = b64ToImage(this.document.getField().getFieldImageB64()); // NOTE: Try removing these line and see if it affects program
//         setImage(image);                                                 // including this one
//         this.document.getField().fieldImageB64Property().addListener((currentVal, oldVal, newVal) -> {
//             image = b64ToImage(newVal);
//             setImage(image);
//         });
//         // translateXProperty().bind(document.getSelectedPath().zoomXOffsetProperty());
//         // translateYProperty().bind(document.getSelectedPath().zoomYOffsetProperty());
//         // scaleXProperty().bind(document.getSelectedPath().zoomScaleProperty());
//         // scaleYProperty().bind(document.getSelectedPath().zoomScaleProperty());
//     }

//     public static Image b64ToImage(String b64String) {
//         Base64Decoder decoder = new Base64Decoder(b64String);
//         InputStream decodedStream = decoder.getDecodedInputStream();
//         return new Image(decodedStream);
//     }
// }
