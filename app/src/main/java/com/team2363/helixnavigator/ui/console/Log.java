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
package com.team2363.helixnavigator.ui.console;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * This class takes the text printed to the console and additional information
 * and logs it.
 * 
 * @author Justin Babilino
 */
public class Log {
    private static final File logFile = new File("/log.txt");
    private static Timestamp timestamp;
    private static PrintWriter writer;
    
    static {
        timestamp = new Timestamp(System.currentTimeMillis());
        try {
            writer = new PrintWriter(logFile);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to print text to log file");
        }
    }
    public static void log(String text) {
        timestamp = new Timestamp(System.currentTimeMillis());
        StringBuilder logString = new StringBuilder()
                .append("[").append(timestamp.toString()).append("]")
                .append(" ").append(text);
        writer.println(logString);
    }
    public static void close() {
        writer.close();
    }
}
