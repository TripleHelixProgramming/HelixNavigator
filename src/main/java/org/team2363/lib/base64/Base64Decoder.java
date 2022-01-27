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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 *
 * @author Justin Babilino
 */
public class Base64Decoder {
    private byte[] decodedBytes;
    private InputStream inputStream;
    public Base64Decoder(byte[] bytes) {
        decodedBytes = Base64.getDecoder().decode(bytes);
    }
    public Base64Decoder(InputStream input) throws IOException {
        this(input.readAllBytes());
    }
    public Base64Decoder(String base64String) throws FileNotFoundException {
        decodedBytes = Base64.getDecoder().decode(base64String);
    }
    public byte[] getDecodedBytes() {
        return decodedBytes;
    }
    public InputStream getDecodedInputStream() {
        if (inputStream == null) {
            inputStream = new ByteArrayInputStream(decodedBytes);
        }
        return inputStream;
    }
}
