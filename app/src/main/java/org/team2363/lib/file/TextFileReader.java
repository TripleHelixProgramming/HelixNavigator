/*
 * Copyright (C) 2021 Triple Helix Robotics - FRC Team 2363
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
package org.team2363.lib.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class imports a text file as a Java String. It is instantly read on
 * construction, then the String can be retrieved through the
 * <code>read()</code> method.
 *
 * @author Justin Babilino
 */
public class TextFileReader {

    /**
     * The path of the file, as a String
     */
    private final String filePath;
    /**
     * The text extracted from the text file, as a String
     */
    private final String text;

    /**
     * Constructs a <code>TextFileReader</code> with a String file path. The
     * text is imported during construction.
     *
     * @param stringFilePath the String file path
     * @throws IOException if there is an error in either the file path or the
     * reading of the file
     */
    public TextFileReader(String stringFilePath) throws IOException {
        if (stringFilePath == null) {
            throw new IOException("File path is null.");
        }
        filePath = stringFilePath;
        Path path = Path.of(filePath);
        text = Files.readString(path);
    }

    /**
     * Retrieves the text from the file. This is essentially a getter method. If
     * it is null, then there was an error in importing.
     *
     * @return the text
     */
    public String read() {
        return text;
    }
}
