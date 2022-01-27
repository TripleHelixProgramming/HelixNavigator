/*
 * Copyright (C) 2021 Justin Babilino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.team2363.lib.base64;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Encoder {
    private byte[] encodedBytes;
    private InputStream encodedInputStream;
    private String base64String;
    public Base64Encoder(byte[] data) {
        encodedBytes = Base64.getEncoder().encode(data);
    }
    public Base64Encoder(InputStream input) throws IOException {
        this(input.readAllBytes());
    }
    public Base64Encoder(File file) throws IOException {
        this(new FileInputStream(file));
    }
    public Base64Encoder(String filePath) throws IOException {
        this(new File(filePath));
    }
    public byte[] getEncodedBytes() {
        return encodedBytes;
    }
    public InputStream getEncodedInputStream() {
        if (encodedInputStream == null) {
            encodedInputStream = new ByteArrayInputStream(encodedBytes);
        }
        return encodedInputStream;
    }
    public String getString() {
        if (base64String == null) {
            base64String = new String(encodedBytes, StandardCharsets.ISO_8859_1);
        }
        return base64String;
    }
}
