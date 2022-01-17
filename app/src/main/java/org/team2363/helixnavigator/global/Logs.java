package org.team2363.helixnavigator.global;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public final class Logs {

    private static final Logger GLOBAL_LOGGER = Logger.getLogger("org.team2363.helixnavigator");
    private static final File LOG_FILE = new File(Standards.USER_DOCUMENTS_DIR, "HelixNavigator/helixnavigator.log");
    private static FileOutputStream fileOut = null;

    private Logs() {
    }

    public static void initialize() {
        try {
            if (!LOG_FILE.exists()) {
                LOG_FILE.getParentFile().mkdirs();
                LOG_FILE.createNewFile();
            }
            fileOut = new FileOutputStream(LOG_FILE);
            if (LOG_FILE.canWrite() && LOG_FILE.canRead()) {
                GLOBAL_LOGGER.addHandler(new StreamHandler(fileOut, new SimpleFormatter()));
            } else {
                GLOBAL_LOGGER.warning("Could not log to file \"" + LOG_FILE.getAbsolutePath() + "\"");
            }
        } catch (IOException e) {
            GLOBAL_LOGGER.warning("Could not log to file \"" + LOG_FILE.getAbsolutePath() + "\" -- IO exception.");
        }
    }
}