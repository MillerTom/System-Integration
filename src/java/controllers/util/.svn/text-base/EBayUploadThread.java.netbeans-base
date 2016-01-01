/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

import controllers.FilesController;
import daos.FilesFacade;
import daos.LogsFacade;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import pojos.Files;
import pojos.ValidEntry;

/**
 *
 * @author tmiller
 */
public class EBayUploadThread extends Thread {

    private List<Files> files;
    private static daos.LogsFacade logsFacade;
    private static daos.FilesFacade filesFacade;
    private String parentPath;

    public List<Files> getFiles() {
        return files;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public EBayUploadThread(FilesFacade filefacade, LogsFacade logfacade) {
        filesFacade = filefacade;
        logsFacade = logfacade;
    }

    @Override
    public void run() {
        LoggingManager logger = new LoggingManager(logsFacade);
        List<Files> selectedFiles = getFiles();
        for (int i = 0; i < selectedFiles.size(); i++) {
            Files file = selectedFiles.get(i);
            logger.log("Upload Thread of File [" + file.getFilePath() + "]  started ", "INFO");
            logger.log("Current File Info==== File Name: [" + file.getFileName() + "], File Date: [" + file.getFileDate() + "], File Time: [" + file.getFileTime() + "]", "INFO");
            logger.log("Now will start upload Steps", "INFO");
            String downloadPathlocal = this.getParentPath();
            String downloadFilePath = createDownloadFolder(downloadPathlocal);
            String uploadFileName = file.getFileName();
            String[] fileNameParts = uploadFileName.split("\\.(?=[^\\.]+$)");
            String downloadFileName = "Resp_" + fileNameParts[0] + ".zip";
            File downloadFile = new File(downloadFilePath + File.separator + downloadFileName);
            logger.log("Response File Path: [" + downloadFile.getAbsolutePath() + "]", "INFO");

            logger.log("started in Uploading Steps.", "INFO");

            EBayUploader eBUploader = new EBayUploader(file.getFilePath(), logsFacade);
            boolean uploadresult = eBUploader.upload(file.getFilePath(), downloadFile.getAbsolutePath());

            if (uploadresult) {
                ValidEntry ve = new ValidEntry();
                ve.setVeId(UploadFiles.SUCCESS.value());
                ve.setVeName(UploadFiles.SUCCESS.name());
                file.setRequestStatus(ve);
                ValidEntry ve1 = new ValidEntry();
                ve1.setVeId(UploadFiles.SUCCESS.value());
                ve1.setVeName(UploadFiles.SUCCESS.name());
                file.setResponseStatus(ve1);
                file.setDownloadFilePath(downloadFile.getAbsolutePath());
                try {
                    Files DBFile = null;
                    try {
                        DBFile = filesFacade.findByFileName(file.getFileName());
                    } catch (Exception e) {
                    }
                    if (DBFile != null) {
                        if (file.getFileTime().equalsIgnoreCase(DBFile.getFileTime()) && file.getFileDate().compareTo(DBFile.getFileDate()) == 0) {
                            logger.log("Data of File [" + file.getFilePath() + "] saved into Database before", "INFO");
                        } else {
                            filesFacade.create(file);
                            logger.log("Data of File [" + file.getFilePath() + "] saved into Database Successfully", "INFO");
                        }
                    } else {
                        filesFacade.create(file);
                        logger.log("Data of File [" + file.getFilePath() + "] saved into Database Successfully ", "INFO");
                    }
                } catch (Exception e) {
                    logger.log("failed to save Data of File [" + file.getFilePath() + "] into Database, due to that Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
                }
                logger.log("Upload Operation of File [" + file.getFilePath() + "] finished Sccessfully", "INFO");
            } else {
                ValidEntry ve = new ValidEntry();
                ve.setVeId(UploadFiles.FAILURE.value());
                ve.setVeName(UploadFiles.FAILURE.name());
                file.setRequestStatus(ve);
                ValidEntry ve1 = new ValidEntry();
                ve1.setVeId(UploadFiles.FAILURE.value());
                ve1.setVeName(UploadFiles.FAILURE.name());
                file.setResponseStatus(ve1);
                filesFacade.create(file);
                logger.log("Upload Operation of File [" + file.getFilePath() + "] failed", "ERROR");
            }
            try {
                logger.log("Sleeping One Minute ", "INFO");
                Thread.sleep(60000);
                logger.log("Sleep Finished ", "INFO");
            } catch (Exception e) {
                logger.log("Exception Occurred While Sleeping One Minute Between Files [" + ExceptionHandler.getStackTraceAsString(e) + "]", "INFO");
            }
        }
    }

    /**
     * Creating Download Folder for the Response File
     *
     * @param parentPath
     *
     * @return String represents the Folder Path
     */
    public String createDownloadFolder(String parentPath) {
        LoggingManager logger = new LoggingManager(logsFacade);
        logger.log("Parent Download Path: [" + parentPath + "]", "INFO");
        try {
            Date d = new Date();
            TimeZone timeZone = TimeZone.getTimeZone("Europe/Copenhagen");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            sdf.setTimeZone(timeZone);
            String dateString = sdf.format(d);
            String[] temp = dateString.split(" ");
            File downloadPathlocal = new File(parentPath + File.separator + temp[0]);
            FilesController fc = new FilesController();
            fc.setTimeNow(temp[1]);
            boolean mkdirsResult = false;
            if (downloadPathlocal.exists()) {
                logger.log("Directory[" + downloadPathlocal.getAbsolutePath() + "] already exists ", "INFO");
                return downloadPathlocal.getAbsolutePath();
            } else {
                mkdirsResult = downloadPathlocal.mkdir();
                if (mkdirsResult) {
                    logger.log("Successfully created Dowload Path [" + downloadPathlocal.getAbsolutePath() + "]", "INFO");
                    return downloadPathlocal.getAbsolutePath();
                } else {
                    logger.log("failed to create Download Path: [" + downloadPathlocal.getAbsolutePath() + "], So Response File will be saved in [" + parentPath + "]", "ERROR");
                    return parentPath;
                }
            }
        } catch (Exception e) {
            logger.log("failed to create download Path due to this Exception [" + ExceptionHandler.getStackTraceAsString(e) + "],So Response File will be saved in [" + parentPath + "]", "EXCEPTION");
            return parentPath;
        }
    }
}
