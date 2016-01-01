/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

import controllers.FilesController;
import java.io.File;
import pojos.Files;
import pojos.ValidEntry;

/**
 *
 * @author tmiller
 */
public class MyThread implements Runnable{

    private Files file;
    private String userName;
    FilesController filesController;
    
    public MyThread(){
        filesController=new FilesController();
    }
    
    public Files getFile() {
        return file;
    }

    public void setFile(Files file) {
        this.file = file;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Override
    public void run() {
        Files current=file;
//        filesController.userLog(userName, "Now will start in uploading Steps", "INFO", current.getFileName(), current.getFileDate(), current.getFileTime());
        String uploadFilePath = current.getFilePath();
        String downloadPathlocal = current.getDownloadFilePath();
        String downloadFilePath =filesController.createDownloadFolder(downloadPathlocal);
        String uploadFileName = current.getFileName();
        String[] fileNameParts = uploadFileName.split("\\.(?=[^\\.]+$)");
        String downloadFileName = "Resp_" + fileNameParts[0] + "_" + filesController.getTimeNow() + ".zip";
        File downloadFile = new File(downloadFilePath + File.separator + downloadFileName);
        try {
            downloadFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        filesController.userLog(userName, "Response File Path: [" + downloadFile.getAbsolutePath() + "]", "INFO", current.getFileName(), current.getFileDate(), current.getFileTime());

//        filesController.userLog(userName, "Started in Uploading Steps.", "INFO", current.getFileName(), current.getFileDate(), current.getFileTime());
//        EBayUploader eBUploader = new EBayUploader(current.getFilePath());
//        eBUploader.upload(current.getFilePath(), downloadFile.getAbsolutePath());

        current.setDownloadFilePath(downloadFile.getAbsolutePath());
        ValidEntry ve = new ValidEntry();
        ve.setVeId(UploadFiles.SUCCESS.value());
        ve.setVeName(UploadFiles.SUCCESS.name());
        current.setRequestStatus(ve);
        filesController.prepareList();
    }
}
