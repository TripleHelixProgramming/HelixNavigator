// package org.team2363.helixnavigator.global;

// import java.io.File;
// import java.io.FileReader;
// import java.io.IOException;

// import org.team2363.helixnavigator.document.field.HField;
// import org.team2363.lib.file.TextFileReader;
// import org.team2363.lib.json.JSON;
// import org.team2363.lib.json.JSONParser;
// import org.team2363.lib.json.JSONParserException;

// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;

// public class BuiltInFields {
//     private static final File fieldDirectory = new File("/fields/");
//     private static final ObservableList<HField> fields = FXCollections.<HField>observableArrayList();
//     public static void loadPaths() {
//         String[] filePaths = fieldDirectory.list();
//         for (String filePath : filePaths) {
//             // try {
//             //     TextFileReader reader = new TextFileReader(filePath);
//                 //JSON json = JSONParser.fromString(reader.read());
//                 //HField field = HField.fromJSON(json);
//             // } catch(IOException | JSONParserException e) {
//             //     System.out.println(e);
//             // }
//         }
//     }
//     public ObservableList<HField> getFields() {
//         return fields;
//     }
// }
