/**
 * This program is licensed under the terms of the eBay Common Development and
 * Distribution License (CDDL) Version 1.0 (the "License") and any subsequent
 * version thereof released by eBay. The then-current version of the License can
 * be found at http://www.opensource.org/licenses/cddl1.php
 */
package model;

import com.ebay.marketplace.services.*;
import controllers.util.EBayUploader;
import controllers.util.ExceptionHandler;
import controllers.util.LoggingManager;
import daos.LogsFacade;
import java.io.*;
import java.util.Properties;
import java.util.zip.GZIPOutputStream;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

/**
 *
 * @author zhuyang
 */
public class FileTransferActions {

    FileTransferCall call;
    private static daos.LogsFacade logsFacade;

    public FileTransferActions(LogsFacade logFacade) {
        logsFacade = logFacade;
        LoggingManager logger = new LoggingManager(logsFacade);
        Properties prop = new Properties();
        try {
            String CONFIG_PROPERTIES = this.getClass().getClassLoader().getResource("configuration.xml").getPath();
            if (CONFIG_PROPERTIES == null && CONFIG_PROPERTIES.length() == 0) {
                logger.log("can not find the Configuration File ", "ERROR");
            }
            prop.loadFromXML(new FileInputStream(CONFIG_PROPERTIES));
            call = new FileTransferCall(prop.getProperty("fileTransferURL"), prop.getProperty("userToken"),logsFacade);
            logger.log("Successfully retreived File Transfer URL: [" + prop.getProperty("fileTransferURL") + "] from The Configurattion File", "INFO");
        } catch (IOException ioe) {
            logger.log("faced this Exception[ " + ioe + " ] while trying to retrieve File Transfer URL from the Configuration File ", "EXCEPTION");
        }
    }

    public boolean uploadFile(String xmlFile, String jobId, String fileReferenceId) {
        String callName = "uploadFile";
        boolean uploadFileOK = false;
        try {

            String compressedFileName = compressFileToGzip(xmlFile);
            if (compressedFileName == null) {
//                System.out.println("\nFailed to compress your XML file into gzip file. Aborted.");
                return (uploadFileOK = false);
            }
            FileTransferServicePort port = call.setFTSMessageContext(callName);
            UploadFileRequest request = new UploadFileRequest();
            FileAttachment attachment = new FileAttachment();
            File fileToUpload = new File(compressedFileName);
            DataHandler dh = new DataHandler(new FileDataSource(fileToUpload));
            attachment.setData(dh);
            attachment.setSize(fileToUpload.length());
            String fileFormat = "gzip";
            request.setFileFormat(fileFormat);
            /*
             * For instance, the Bulk Data Exchange Service uses a job ID as a
             * primary identifier, so, if you're using the Bulk Data Exchange
             * Service, enter the job ID as the taskReferenceId.
             */

            request.setTaskReferenceId(jobId);
            request.setFileReferenceId(fileReferenceId);
            request.setFileAttachment(attachment);
            //request.
            if (port != null && request != null) {
                UploadFileResponse response = port.uploadFile(request);
                if (response.getAck().equals(AckValue.SUCCESS)) {
                    return (uploadFileOK = true);
                } else {
//                    logger.severe(response.getErrorMessage().getError().get(0).getMessage());
                    return (uploadFileOK = false);
                }
            }

        } catch (Exception e) {

//            logger.severe(e.getMessage());
            return (uploadFileOK = false);
        }
        return uploadFileOK;
    }

    /**
     * Method to Upload The Listing File after Compressing it
     *
     * @param xmlFile
     * @param jobId
     * @param fileReferenceId
     *
     * @return UploadFileResponse
     */
    public UploadFileResponse uploadFile2(String xmlFile, String jobId, String fileReferenceId) {
        LoggingManager logger = new LoggingManager(logsFacade);
        String callName = "uploadFile";
        UploadFileResponse response = null;

        try {
            String compressedFileName = compressFileToGzip(xmlFile);
            if (compressedFileName == null) {
                logger.log("failed to compress file ["+xmlFile+"] into gzip file.", "INFO");
                return null;
            } else {
                logger.log("File ["+xmlFile+"] compressed Successfully into file [" + compressedFileName + "]", "INFO");
            }

            FileTransferServicePort port = call.setFTSMessageContext(callName);
            UploadFileRequest request = new UploadFileRequest();
            FileAttachment attachment = new FileAttachment();
            File fileToUpload = new File(compressedFileName);
            DataHandler dh = new DataHandler(new FileDataSource(fileToUpload));
            attachment.setData(dh);
            attachment.setSize(fileToUpload.length());
            String fileFormat = "gzip";
            request.setFileFormat(fileFormat);

            /*
             * For instance, the Bulk Data Exchange Service uses a job ID as a
             * primary identifier, so, if you're using the Bulk Data Exchange
             * Service, enter the job ID as the taskReferenceId.
             */
            request.setTaskReferenceId(jobId);
            request.setFileReferenceId(fileReferenceId);
            request.setFileAttachment(attachment);
            //request.
            if (port != null && request != null) {
                response = port.uploadFile(request);
            }

        } catch (Exception e) {
            logger.log("encountered This Exception [" + ExceptionHandler.getStackTraceAsString(e) + "] while trying to upload File ["+xmlFile+"]", "EXCEPTION");
            return null;
        }
        return response;
    }

    public boolean downloadFile2(String fileName, String jobId, String fileReferenceId) {
        boolean downloadOK = false;
        String callName = "downloadFile";
        try {
            FileTransferServicePort port = call.setFTSMessageContext(callName);
            com.ebay.marketplace.services.DownloadFileRequest request = new com.ebay.marketplace.services.DownloadFileRequest();
            request.setFileReferenceId(fileReferenceId);
            request.setTaskReferenceId(jobId);
            DownloadFileResponse response = port.downloadFile(request);
            if (response.getAck().equals(AckValue.SUCCESS)) {
//                System.out.println(response.getAck().SUCCESS);
                downloadOK = true;
            } else {
//                System.out.println(response.getErrorMessage().getError().get(0).getMessage());
                return (downloadOK = false);
            }
            FileAttachment attachment = response.getFileAttachment();
            DataHandler dh = attachment.getData();
            try {
                InputStream in = dh.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(in);

                FileOutputStream fo = new FileOutputStream(new File(fileName)); // "C:/myDownLoadFile.gz"
                BufferedOutputStream bos = new BufferedOutputStream(fo);
                int bytes_read = 0;
                byte[] dataBuf = new byte[4096];
                while ((bytes_read = bis.read(dataBuf)) != -1) {
                    bos.write(dataBuf, 0, bytes_read);
                }
                bis.close();
                bos.flush();
                bos.close();
//                System.out.println("File attachment has been saved successfully to " + fileName);

            } catch (IOException e) {
                return (downloadOK = false);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            return (downloadOK = false);
        }
        return downloadOK;
    }

    /**
     * Method to download eBay Response File
     *
     * @param fileName
     * @param jobId
     * @param fileReferenceId
     *
     * @return DownloadFileResponse
     */
    public DownloadFileResponse downloadFile(String fileName, String jobId, String fileReferenceId) {
        LoggingManager logger = new LoggingManager(logsFacade);
        String callName = "downloadFile";
        DownloadFileResponse response = null;
        try {
            FileTransferServicePort port = call.setFTSMessageContext(callName);
            com.ebay.marketplace.services.DownloadFileRequest request = new com.ebay.marketplace.services.DownloadFileRequest();
            request.setFileReferenceId(fileReferenceId);
            request.setTaskReferenceId(jobId);
            response = port.downloadFile(request);
        } catch (Exception e) {
            logger.log("Faced this Exception [" + ExceptionHandler.getStackTraceAsString(e) + "] while Downloading response File ", "EXCEPTION");
            return null;
        }
        return response;
    }

    /**
     * Method to download eBay Report Response File
     *
     * @param fileName
     * @param jobId
     * @param fileReferenceId
     *
     * @return DownloadFileResponse
     */
    public DownloadFileResponse downloadReportFile(String fileName, String jobId, String fileReferenceId, LogsFacade logFacade) {
        LoggingManager logger = new LoggingManager(logFacade);
        String callName = "downloadFile";
        DownloadFileResponse response = null;
        try {
            FileTransferServicePort port = call.setFTSMessageContext(callName);
            com.ebay.marketplace.services.DownloadFileRequest request = new com.ebay.marketplace.services.DownloadFileRequest();
            request.setFileReferenceId(fileReferenceId);
            request.setTaskReferenceId(jobId);
            response = port.downloadFile(request);
        } catch (Exception e) {
            logger.log("faced this Exception [" + e + "] while Downloading  Report response File to File [" + fileName + "]", "EXCEPTION");
            return null;
        }
        return response;
    }

    /**
     * Method to Compress The Listing File(.XML)
     *
     * @param inFilename XML File Name
     *
     * @return String Compressed File Name
     */
    private static String compressFileToGzip(String inFilename) {
        LoggingManager logger = new LoggingManager(logsFacade);
        logger.log("Now Trying to compress the xml file into gz file", "INFO");
        String outFilename = null;
        String usingPath = inFilename.substring(0, inFilename.lastIndexOf(File.separator) + 1);
        String fileName = inFilename.substring(inFilename.lastIndexOf(File.separator) + 1);
        outFilename = usingPath + fileName + ".gz";

        try {
            BufferedReader in = new BufferedReader(new FileReader(inFilename));
            BufferedOutputStream out = new BufferedOutputStream(
                    new GZIPOutputStream(new FileOutputStream(outFilename)));
            logger.log("Writing gz file...", "INFO");
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            in.close();
            out.close();
            logger.log("Compress Operation of File ["+inFilename+"] completed Successfully !","INFO");
        } catch (FileNotFoundException e) {
            logger.log("cannot find file: [" + inFilename + "]", "ERROR");
        } catch (IOException e) {
            logger.log("This Exception occurred while trying to compress File [ " + inFilename + "],Exception is:[ " + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
        }
        return outFilename;
    }
}
