// package org.team2363.helixnavigator.global;

// import java.io.File;
// import java.io.FileReader;
// import java.io.IOException;

// import org.team2363.helixnavigator.document.field.HField;
// import org.team2363.lib.file.TextFileReader;

// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;

// public class BuiltInFields {

//     private static final File fieldsDirectory = new File(BuiltInFields.class.getResource("/fields/").getFile());
//     private static final File[] fieldDirectories;
//     private static final ObservableList<HField> fields = FXCollections.<HField>observableArrayList();

//     static {
//         fieldDirectories = fieldsDirectory.listFiles();
//         loadPaths();
//     }
//     public static void loadPaths() {

//     }
//     public ObservableList<HField> getFields() {
//         return fields;
//     }
// }
