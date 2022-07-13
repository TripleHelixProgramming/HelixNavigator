package org.team2363.lib.base64;

import java.io.File;
import java.io.IOException;

public class FileEncodeTest {
    public static void main(String[] args) throws IOException {
        File file = new File("2020_field_image.png");
        Base64Encoder encoder = new Base64Encoder(file);
        System.out.println(encoder.getString());
    }
}