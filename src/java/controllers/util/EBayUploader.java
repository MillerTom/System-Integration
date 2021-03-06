/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

import com.ebay.marketplace.services.*;
import daos.LogsFacade;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import model.BulkDataExchangeActions;
import model.CreateLMSParser;
import model.FileTransferActions;

/**
 *
 * @author tmiller
 */
public class EBayUploader {
    //variables Declarations

    private static Properties prop;
    public static String fileName;
    private static daos.LogsFacade logsFacade;

    //Generated Framework Methods
    /**
     * Constructor
     */
    public EBayUploader(String filePath, LogsFacade logFacade) {
        fileName = filePath;
        logsFacade = logFacade;
        LoggingManager logger = new LoggingManager(logsFacade);
        prop = new Properties();
        try {
            logger.log("trying to get Job Status Query Interval From the Configuration File ", "INFO");
            String CONFIG_PROPERTIES = this.getClass().getClassLoader().getResource("configuration.xml").getPath();
            if (CONFIG_PROPERTIES == null && CONFIG_PROPERTIES.length() == 0) {
                logger.log("can not find the Configuration File ", "ERROR");
            }
            prop.loadFromXML(new FileInputStream(CONFIG_PROPERTIES));
            prop.getProperty("getJobStatusQueryInterval");
            logger.log("Successfully retreived Job Status Query Interval From the Configuration File ,Interval= [" + prop.getProperty("getJobStatusQueryInterval") + "]", "INFO");
        } catch (Exception e) {
            logger.log("failed to retreive Job Status Query Interval from The Configuration File, due to this Exception[ " + ExceptionHandler.getStackTraceAsString(e) + " ]", "EXCEPTION");
        }
    }

    /**
     * Parent Method that is responsible for Uploading Ebay File
     *
     * @param uploadFile
     * @param downloadFile
     *
     * @return boolean that indicates upload result...
     */
    public boolean upload(String uploadFile, String downloadFile) {

        //local variables declarations...
        LoggingManager logger = new LoggingManager(logsFacade);
        boolean done = false;
        String JobStatusQueryIntervalLocal = "";
        String fileReferenceId = null;
        String jobId = null;
        String jobType = null;
        BaseServiceResponse baseRep = null;
        if (prop == null) {
            prop = new Properties();
            try {
                logger.log("trying to get Job Status Query Interval from the Configuration File ", "INFO");
                String CONFIG_PROPERTIES = this.getClass().getClassLoader().getResource("configuration.xml").getPath();
                if (CONFIG_PROPERTIES == null && CONFIG_PROPERTIES.length() == 0) {
                    logger.log("can not find the Configuration File ", "ERROR");
                }
                prop.loadFromXML(new FileInputStream(CONFIG_PROPERTIES));
                JobStatusQueryIntervalLocal = prop.getProperty("getJobStatusQueryInterval");
                logger.log("Successfully retreived Job Status Query Interval From the Configuration File ,Interval= [" + JobStatusQueryIntervalLocal + "]", "INFO");
            } catch (Exception e) {
                logger.log("failed to retreive Job Status Query Interval from The Configuration File, due to this Exception[ " + ExceptionHandler.getStackTraceAsString(e) + " ]", "EXCEPTION");
            }
        } else {
            JobStatusQueryIntervalLocal = prop.getProperty("getJobStatusQueryInterval");
            logger.log("Successfully retreived Job Status Query Interval From the Configuration File,Interval= [" + JobStatusQueryIntervalLocal + "]", "INFO");
        }

        //check File Existence on Hard Disk
        if (verifyFileForUploadJob(uploadFile)) {
            //get Job Type from the Uploaded XML File
            jobType = getJobTypeFromXML(uploadFile);
            if (jobType == null) {
                return false;
            }
        }

        logger.log("Now trying to create Upload Job in eBay System", "INFO");
        BulkDataExchangeActions bdeActions = new BulkDataExchangeActions(logsFacade);
        CreateUploadJobResponse createUploadJobresponse = bdeActions.createUploadJob(jobType);

        if (createUploadJobresponse != null) {
            baseRep = createUploadJobresponse;
            if (!isSuccess(baseRep)) {
                logger.log("failed to create Upload Job in eBay System.", "ERROR");
                return (done = false);
            } else {
                logger.log("Upload Job created Successfully in eBay System", "INFO");
            }
            fileReferenceId = createUploadJobresponse.getFileReferenceId();
            jobId = createUploadJobresponse.getJobId();
            logger.log("File Reference ID: [" + fileReferenceId + "] , And Job ID: [" + jobId + "]", "INFO");
        } else {
            logger.log("failed to create Upload Job in ebay System", "ERROR");
            return false;
        }

        logger.log("Now trying to upload The File ", "INFO");
        FileTransferActions ftActions = new FileTransferActions(logsFacade);
        UploadFileResponse uploadFileResp = ftActions.uploadFile2(uploadFile, jobId, fileReferenceId);
        if (uploadFileResp != null) {
            baseRep = uploadFileResp;
            if (!isSuccess(baseRep)) {
                logger.log("failed to upload File [" + uploadFile + "]", "ERROR");
                return (done = false);
            } else {
                logger.log("Response of The Upload File passed The Status Check Successfully", "INFO");
            }
        } else {
            logger.log("failed to upload The File [" + uploadFile + "]", "ERROR");
        }


        logger.log("Now Trying to start The Upload Job Operation", "INFO");
        StartUploadJobResponse startUploadJobResp = bdeActions.startUploadJob(jobId);
        baseRep = startUploadJobResp;
        if (!isSuccess(baseRep)) {
            logger.log("failed to start Upload Job", "ERROR");
            return (done = false);
        } else {
            logger.log("Upload Job Started Successfully", "INFO");
        }

        logger.log("will download The Response File after Job Status be Completed ", "INFO");
        done = doDownload(downloadFile, jobId, JobStatusQueryIntervalLocal);
        if (done) {
            logger.log("Download Operation completed Successfully ,Download File  [" + downloadFile + "],Listing File: [" + uploadFile + "]", "INFO");
        } else {
            logger.log("Download Operation failed ", "ERROR");
        }
        return done;
    }

    /**
     * Method that is responsible for Downloading Response File from eBay Server
     *
     * @param downloadFileName
     * @param jobId
     * @param JobStatusQueryInterval
     *
     * @return boolean
     */
    private boolean doDownload(String downloadFileName, String jobId, String JobStatusQueryInterval) {
        LoggingManager logger = new LoggingManager(logsFacade);
        BulkDataExchangeActions bdeActions = new BulkDataExchangeActions(logsFacade);
        boolean fileProcessIsDone = false;
        boolean downloadIsDone = false;

        if (JobStatusQueryInterval.length() == 0) {
            JobStatusQueryInterval = "10000";
        }

        do {
            JobProfile job = null;

            //get Job Status...
            GetJobStatusResponse getJobStatusResp = bdeActions.getJobStatus(jobId);
            BaseServiceResponse baseRep = getJobStatusResp;
            if (!isSuccess(baseRep)) {
                logger.log("failed to retrieve Job Status", "ERROR");
                return false;
            }

            job = retrieveOneJobStatus(getJobStatusResp);
            if (job != null) {
                if (job.getJobStatus().equals(JobStatus.COMPLETED) && job.getPercentComplete() == 100.0) {
                    logger.log("JobId=" + job.getJobId() + " : " + "Job Type " + job.getJobType() + " : JobStatus= " + job.getJobStatus(), "INFO");
                    fileProcessIsDone = true;
                    try {
                        logger.log("Now Downloading the Response File ", "INFO");
                        downloadIsDone = downloadJob(jobId, job.getFileReferenceId(), downloadFileName);
                        logger.log("Successfully downloaded eBay Response File ", "INFO");

                    } catch (Exception e) {
                        fileProcessIsDone = false;
                        downloadIsDone = false;
                        logger.log("faced this Exception [" + ExceptionHandler.getStackTraceAsString(e) + "] , while downloading The Response File ", "EXCEPTION");
                    }
                } else if (job.getJobStatus().equals(JobStatus.FAILED) || job.getJobStatus().equals(JobStatus.ABORTED)) {
                    logger.log("JobId=" + job.getJobId() + ": " + "Job Type " + job.getJobType() + " : JobStatus= " + job.getJobStatus(), "ERROR");
                    return false;
                } else {
                    logger.log("JobId=" + job.getJobId() + ": " + "Job Type " + job.getJobType() + " : JobStatus= " + job.getJobStatus(), "INFO");
                    try {
                        Thread.sleep(Integer.parseInt(JobStatusQueryInterval));
                    } catch (InterruptedException x) {
                        fileProcessIsDone = false;
                        downloadIsDone = false;
                        logger.log("faced this Exception[" + ExceptionHandler.getStackTraceAsString(x) + "] while waiting for retrieving Job Status to be Finished ", "EXCEPTION");
                    }
                }
            } else {
                logger.log("can not retreive Job Status,Please try Again Later ", "ERROR");
                return false;
            }
        } while (!fileProcessIsDone);
        return downloadIsDone;
    }

    /**
     * Method that downloads response data and writes it to the response file
     *
     * @param jobId
     * @param fileReferenceId
     * @param downloadFileName
     *
     * @return boolean
     *
     * @throws Exception
     */
    public boolean downloadJob(String jobId, String fileReferenceId, String downloadFileName) throws Exception {
        LoggingManager logger = new LoggingManager(logsFacade);
        boolean done = false;
        FileTransferActions ftActions = new FileTransferActions(logsFacade);

        DownloadFileResponse downloadFileResp = ftActions.downloadFile(downloadFileName, jobId, fileReferenceId);
        if (downloadFileResp != null) {
            BaseServiceResponse baseRep = downloadFileResp;
            if (isSuccess(baseRep)) {
                logger.log("Now Writing Response File ", "INFO");
                done = true;
                FileAttachment attachment = downloadFileResp.getFileAttachment();
                DataHandler dh = attachment.getData();
                try {
                    InputStream in = dh.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(in);

                    FileOutputStream fo = new FileOutputStream(new File(downloadFileName));
                    BufferedOutputStream bos = new BufferedOutputStream(fo);
                    int bytes_read = 0;
                    byte[] dataBuf = new byte[4096];
                    while ((bytes_read = bis.read(dataBuf)) != -1) {
                        bos.write(dataBuf, 0, bytes_read);
                    }
                    bis.close();
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    logger.log("Exception caught while trying to save the attachement File to [" + downloadFileName + "],Exceptionis: [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
                }
                logger.log("File attachment has been saved successfully to [" + downloadFileName + "]", "INFO");
            }
        }
        return done;
    }

    /**
     * Method to retrieve One Job Status from GetJobStatusReponse
     *
     * @param jobStatusResp
     *
     * @return JobProfile
     */
    private static JobProfile retrieveOneJobStatus(GetJobStatusResponse jobStatusResp) {
        try {
            JobProfile job = null;
            if (jobStatusResp != null) {
                List<JobProfile> listOfJobs = jobStatusResp.getJobProfile();
                if (listOfJobs.size() == 1) {
                    Iterator itr = listOfJobs.iterator();
                    job = (JobProfile) itr.next();
                }
            }
            return job;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method that retrieves the Job ID from the XML file that will be Uploaded
     * to eBay Server...
     *
     * @param uploadFile File that Will be Uploaded to eBay Server...
     *
     * @return String holds the Job ID that will be Used to Upload the Passed
     * File...
     */
    private String getJobTypeFromXML(String uploadFile) {
        LoggingManager logger = new LoggingManager(logsFacade);
        logger.log("Now trying to get Job Type form the eBay Listing File", "INFO");
        File uploadXMLFile = null;
        uploadXMLFile = new File(uploadFile);

        CreateLMSParser parser = new CreateLMSParser();
        boolean parseResult = parser.parse(uploadXMLFile);
        if (!parseResult) {
            logger.log("failed to extract the JobType from the file [" + uploadFile + "]", "ERROR");
            return null;
        }
        // extract the JObType String successfully
        String jobType = parser.getJobType();
        if (jobType == null) {
            logger.log("Invalid job type in the XML file [" + uploadFile + "]", "ERROR");
        } else {
            logger.log("Successfully retrieved Job Type form eBay Listing File [" + uploadFile + "], Job Type: [" + jobType + "]", "INFO");
        }
        return jobType;
    }

    /**
     * Method to Test response of Any Operation Occurred on eBay Server while
     * uploading or Downloading Files to/from it...
     *
     * @param response Operation result that will be tested...
     * @return boolean Test Result...
     */
    private boolean isSuccess(BaseServiceResponse response) {
        LoggingManager logger = new LoggingManager(logsFacade);
        boolean done = true;
        ErrorMessage errorMsg = null;
        if (response != null) {
            if (!response.getAck().equals(AckValue.SUCCESS)) {
                errorMsg = new ErrorMessage();
                if (errorMsg != null) {
                    ErrorData error = response.getErrorMessage().getError().get(0);
                    if (response.getAck().equals(AckValue.WARNING)) {
                        logger.log("Response Passed Status Check with this Warning [" + error.getMessage() + "]", "WARNING");
                    } else if (response.getAck().equals(AckValue.FAILURE) || response.getAck().equals(AckValue.PARTIAL_FAILURE)) {
                        done = false;
                        logger.log("Response failed to Pass Status Check due to this Error [" + error.getMessage() + "]", "ERROR");
                    }
                }
            }
        }
        return done;
    }

    /**
     * This Method checks the Existence of the Listing File
     *
     * @param uploadFileName
     *
     * @return boolean
     */
    private boolean verifyFileForUploadJob(String uploadFileName) {
        LoggingManager logger = new LoggingManager(logsFacade);
        boolean found = false;
        BufferedReader file;
        try {
            file = new BufferedReader(new FileReader(uploadFileName));
            String st = file.readLine();
            logger.log("File existence check passed Successfully ", "INFO");
            found = true;
        } catch (FileNotFoundException e) {
            logger.log("File [" + uploadFileName + "] not found, and This Exception has been thrown [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            return false;
        } catch (IOException e) {
            logger.log("File existence check failed due to This Exception thrown [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            return false;
        }
        return found;
    }
}
