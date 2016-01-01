/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

import daos.LogsFacade;
import java.io.File;
import java.io.FileOutputStream;

public class BrowserControl {

    // The default browser under unix.
    private static String UNIX_PATH;
    private static String commandFilePath;
    LogsFacade logsFacade;

    public BrowserControl(LogsFacade logfacade) {
        logsFacade = logfacade;
        String CONFIG_PROPERTIES = this.getClass().getClassLoader().getResource("configfile.xml").getPath();
        XMLParser parser = new XMLParser(CONFIG_PROPERTIES);
        UNIX_PATH = parser.getLaunchProgramName();
        commandFilePath = parser.getOpenFilePath() + File.separator + "Open.sh";
    }

    public void openFile(String filePath) {
        LoggingManager logger = new LoggingManager(logsFacade);
        try {
            logger.log("Now Opening Extracted Response File[" + filePath + "]", "INFO");
            String openCommand = UNIX_PATH + " " + filePath;
            logger.log("Opening File Command: [" + openCommand + "]", "INFO");
            File executer = new File(commandFilePath);
            FileOutputStream fos = new FileOutputStream(executer);
            fos.write(openCommand.getBytes());
            fos.close();
            logger.log("Changing File Mode to be in Executing Mode ","INFO");
            Runtime.getRuntime().exec("chmod 775 " + executer.getAbsolutePath());
            logger.log("Executing the File Now ","INFO");
            Runtime.getRuntime().exec("/bin/sh " + executer.getAbsolutePath());
            logger.log("File [" + filePath + "] opened Successfully ", "INFO");
        } catch (Exception e) {
            logger.log("Error occurred while Opening File [" + filePath + "], due to this Exception [" + e+ "]", "EXCEPTION");
        }
    }
}