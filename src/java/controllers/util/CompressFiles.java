/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

import daos.LogsFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author tmiller
 */
public class CompressFiles {

    /**
     * Method to Extract Compressed Response Files ...
     * 
     * @param zipFile
     * @param logfacade
     * @return 
     */
    public static String unZip(String zipFile, LogsFacade logFacade) {
        LoggingManager logger= new LoggingManager(logFacade);
        String extendedFileName = "";
        File file = null;
        try {
            File archiveFile = new File(zipFile);
            ZipFile zipArchive = new ZipFile(archiveFile);
            Enumeration e = zipArchive.getEntries();
            logger.log("extracting Response File ["+zipFile+"]", "INFO");
            while (e.hasMoreElements()) {
                ZipArchiveEntry entry = (ZipArchiveEntry) e.nextElement();
                String outPath = zipFile.substring(0, zipFile.lastIndexOf("/"));
                extendedFileName = entry.getName();
                File outFilePath = new File(outPath);
                file = new File(outFilePath, extendedFileName);
                if (entry.isDirectory()) {
                    FileUtils.forceMkdir(file);
                } else {
                    InputStream is = zipArchive.getInputStream(entry);
                    FileOutputStream os = FileUtils.openOutputStream(file);
                    try {
                        IOUtils.copy(is, os);
                    } finally {
                        os.close();
                        is.close();
                    }
                }
            }
            logger.log("finished extracting Response File [" + zipFile + "], to File [" + file.getAbsolutePath() + "]", "INFO");
        } catch (Exception ex) {
            logger.log("failed to extract Response File [" + zipFile + "], due to this Exception [" + ex + "]", "EXCEPTION");
        }
        return file.getAbsolutePath();
    }
}
