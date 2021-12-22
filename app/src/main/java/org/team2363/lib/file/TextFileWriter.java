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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This class writes text to files
 * 
 * @author Justin Babilino
 */
public class TextFileWriter {
    private final File file;
    private final PrintWriter writer;
    
    public TextFileWriter(File file) throws FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("File object passed in constructor is null");
        }
        this.file = file;
        writer = new PrintWriter(this.file);
    }
    public void printString(String str) {
        System.out.println("Printing to file: " + file.getAbsolutePath());
        writer.print(str);
    }
    public void close() {
        writer.close();
    }
}
