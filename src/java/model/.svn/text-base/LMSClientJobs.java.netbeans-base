/**
 * This program is licensed under the terms of the eBay Common Development and
 * Distribution License (CDDL) Version 1.0 (the "License") and any subsequent
 * version thereof released by eBay. The then-current version of the License can
 * be found at http://www.opensource.org/licenses/cddl1.php
 */
package model;

import com.ebay.marketplace.services.*;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.activation.DataHandler;

/**
 *
 * @author zhuyang
 */
public class LMSClientJobs {
//
//    private static Logger logger = Logger.getLogger("LMSClientJobs.logger");
//
//    public static boolean end2EndUploadJob(String uploadFileName,
//            String downloadFileName, String JobStatusQueryInterval) throws Exception {
//        logger.info("\n******\nUploadJobEnd2End Job: uploadFileName=" + uploadFileName + " ;;; downloadFileName" + downloadFileName);
//        boolean done = false;
//        BulkDataExchangeActions bdeActions = new BulkDataExchangeActions();
//        FileTransferActions ftActions = new FileTransferActions();
//        String jobType = null;
//        String fileReferenceId = null;
//        String jobId = null;
//        BaseServiceResponse baseRep = null;
//        // verify file to upload
//        if (verifyFileForUploadJob(uploadFileName)) {
//            jobType = getJobTypeFromXML(uploadFileName);
//            if (jobType == null) {
//                return false;
//            }
//        }
//
//        CreateUploadJobResponse createUploadJobresponse = bdeActions.createUploadJob(jobType);
//        if (createUploadJobresponse != null) {
//            baseRep = createUploadJobresponse;
//            if (!isSuccess(baseRep)) {
//                return (done = false);
//            }
//
//            fileReferenceId = createUploadJobresponse.getFileReferenceId();
//            jobId = createUploadJobresponse.getJobId();
//        }
//
//        UploadFileResponse uploadFileResp = ftActions.uploadFile2(uploadFileName, jobId, fileReferenceId);
//        if (uploadFileResp != null) {
//            baseRep = uploadFileResp;
//            if (!isSuccess(baseRep)) {
//                return (done = false);
//            }
//        }
//
//        StartUploadJobResponse startUploadJobResp = bdeActions.startUploadJob(jobId);
//        baseRep = startUploadJobResp;
//        if (!isSuccess(baseRep)) {
//            return (done = false);
//        }
//        done = doDownload(downloadFileName, jobId, JobStatusQueryInterval);
//        return done;
//    }
//
//    public static boolean end2EndDownloadJob(String downloadJobType,String downloadFileName, String JobStatusQueryInterval) throws Exception {
//        logger.info("\n******\n DownloadJobEnd2End : downloadJobType=" + downloadJobType + " ; downloadFileName=" + downloadFileName);
//        boolean done = false;
//        String jobid = "";
//
//        StartDownloadJobResponse sdljResp = doStartDownloadJob(downloadJobType, null);
//        BaseServiceResponse baseRep = sdljResp;
//        if (!isSuccess(baseRep)) {
//            return done;
//        }
//        jobid = sdljResp.getJobId();
//        done = doDownload(downloadFileName, jobid, JobStatusQueryInterval);
//        return done;
//    }
//
//    private static boolean doDownload(String downloadFileName, String jobId, String JobStatusQueryInterval) {
//        BulkDataExchangeActions bdeActions = new BulkDataExchangeActions();
//        FileTransferActions ftActions = new FileTransferActions();
//        boolean fileProcessIsDone = false;
//        boolean downloadIsDone = false;
//        logger.info("JobStatusQueryInterval =========" + JobStatusQueryInterval);
//        if (JobStatusQueryInterval.length() == 0) {
//            JobStatusQueryInterval = "10000";
//        }
//        do {
//            JobProfile job = null;
//            GetJobStatusResponse getJobStatusResp = bdeActions.getJobStatus(jobId);
//            BaseServiceResponse baseRep = getJobStatusResp;
//            if (!isSuccess(baseRep)) {
//                return false;
//            }
//            job = retrieveOneJobStatus(getJobStatusResp);
//
//            if (job.getJobStatus().equals(JobStatus.COMPLETED) && job.getPercentComplete() == 100.0) {
//                logger.info(job.getJobId() + " : " + job.getJobType() + " : " + job.getJobStatus());
//                fileProcessIsDone = true;
//                DownloadFileResponse downloadFileResp = ftActions.downloadFile(downloadFileName, jobId, job.getFileReferenceId());
////                DownloadFileResponse downloadFileResp = ftActions.downloadFile(downloadFileName, jobId, fileReferenceId);
////        if (downloadFileResp != null) {
////            BaseServiceResponse baseRep = downloadFileResp;
////            if (isSuccess(baseRep)) {
////                done = true;
////                FileAttachment attachment = downloadFileResp.getFileAttachment();
////                DataHandler dh = attachment.getData();
////                try {
////                    InputStream in = dh.getInputStream();
////                    BufferedInputStream bis = new BufferedInputStream(in);
////
////                    FileOutputStream fo = new FileOutputStream(new File(downloadFileName)); // "C:/myDownLoadFile.gz"
////                    BufferedOutputStream bos = new BufferedOutputStream(fo);
////                    int bytes_read = 0;
////                    byte[] dataBuf = new byte[4096];
////                    while ((bytes_read = bis.read(dataBuf)) != -1) {
////                        bos.write(dataBuf, 0, bytes_read);
////                    }
////                    bis.close();
////                    bos.flush();
////                    bos.close();
////            }
////        }
//                if (downloadFileResp != null) {
//                    baseRep = downloadFileResp;
//                    if (isSuccess(baseRep)) {
//                        downloadIsDone = true;
//                        FileAttachment attachment = downloadFileResp.getFileAttachment();
//                        DataHandler dh = attachment.getData();
//                        try {
//                            InputStream in = dh.getInputStream();
//                            BufferedInputStream bis = new BufferedInputStream(in);
//
//                            FileOutputStream fo = new FileOutputStream(new File(downloadFileName)); // "C:/myDownLoadFile.gz"
//                            BufferedOutputStream bos = new BufferedOutputStream(fo);
//                            int bytes_read = 0;
//                            byte[] dataBuf = new byte[4096];
//                            while ((bytes_read = bis.read(dataBuf)) != -1) {
//                                bos.write(dataBuf, 0, bytes_read);
//                            }
//                            bis.close();
//                            bos.flush();
//                            bos.close();
//                        } catch (IOException e) {
//                            logger.severe("\nException caught while trying to save the attachement.");
//                        }
//                    }
//                }
//            } else if (job.getJobStatus().equals(JobStatus.FAILED) || job.getJobStatus().equals(JobStatus.ABORTED)) {
//                logger.severe("JobId=" + job.getJobId() + ": " + "Job Type " + job.getJobType() + " : JobStatus= " + job.getJobStatus());
//                return false;
//            } else {
//                logger.info("JobId=" + job.getJobId() + ": " + "Job Type " + job.getJobType() + " : JobStatus= " + job.getJobStatus());
//                try {
//                    logger.info(" SLEEP FOR " + JobStatusQueryInterval);
//                    Thread.sleep(Integer.parseInt(JobStatusQueryInterval));
//                } catch (InterruptedException x) {
//                    fileProcessIsDone = false;
//                    downloadIsDone = false;
//                }
//            }
//
//        } while (!fileProcessIsDone);
//        return downloadIsDone;
//    }
//
//    private static JobProfile retrieveOneJobStatus(GetJobStatusResponse jobStatusResp) {
//        // boolean jobIsCompleted = false;
//        JobProfile job = null;
//        if (jobStatusResp != null) {
//            List<JobProfile> listOfJobs = jobStatusResp.getJobProfile();
//            if (listOfJobs.size() == 1) {
//                Iterator itr = listOfJobs.iterator();
//                job = (JobProfile) itr.next();
//            }
//        }
//        return job;
//    }
//
//    public static boolean uploadJob(String jobId, String fileReferenceId, String uploadFileName) throws Exception {
//        boolean done = false;
//        // verify file to upload
//        if (verifyFileForUploadJob(uploadFileName)) {
//            // get JobType from the XML file
//            if (getJobTypeFromXML(uploadFileName) == null) {
//                return done;
//            }
//        }
//        FileTransferActions ftActions = new FileTransferActions();
//        UploadFileResponse uploadFileResp = ftActions.uploadFile2(uploadFileName, jobId, fileReferenceId);
//        if (uploadFileResp != null) {
//            BaseServiceResponse baseRep = uploadFileResp;
//            if (isSuccess(baseRep)) {
//                done = true;
//                System.out.println("uploadFile Request: jobId=" + jobId);
//                System.out.println("    uploadFile Response: \n {");
//                System.out.println("    Ack: " + uploadFileResp.getAck());
//                System.out.println("ResponseTime: " + uploadFileResp.getTimestamp() + "\n } \n");
//            }
//        }
//        return done;
//    }
//
//    public static boolean downloadJob2(String jobId, String fileReferenceId, String downloadFileName) throws Exception {
//        boolean done = false;
//        BaseServiceResponse baseRep = null;
//        FileTransferActions ftActions = new FileTransferActions();
//        DownloadFileResponse downloadFileResp = ftActions.downloadFile(downloadFileName, jobId, fileReferenceId);
//        if (downloadFileResp != null) {
//            baseRep = downloadFileResp;
//            if (isSuccess(baseRep)) {
//                done = true;
//            }
//        }
//        return done;
//    }
//
//    public static boolean downloadJob(String jobId, String fileReferenceId, String downloadFileName) throws Exception {
//        boolean done = false;
//        FileTransferActions ftActions = new FileTransferActions();
//        DownloadFileResponse downloadFileResp = ftActions.downloadFile(downloadFileName, jobId, fileReferenceId);
//        if (downloadFileResp != null) {
//            BaseServiceResponse baseRep = downloadFileResp;
//            if (isSuccess(baseRep)) {
//                done = true;
//                FileAttachment attachment = downloadFileResp.getFileAttachment();
//                DataHandler dh = attachment.getData();
//                try {
//                    InputStream in = dh.getInputStream();
//                    BufferedInputStream bis = new BufferedInputStream(in);
//
//                    FileOutputStream fo = new FileOutputStream(new File(downloadFileName)); // "C:/myDownLoadFile.gz"
//                    BufferedOutputStream bos = new BufferedOutputStream(fo);
//                    int bytes_read = 0;
//                    byte[] dataBuf = new byte[4096];
//                    while ((bytes_read = bis.read(dataBuf)) != -1) {
//                        bos.write(dataBuf, 0, bytes_read);
//                    }
//                    bis.close();
//                    bos.flush();
//                    bos.close();
//
//                } catch (IOException e) {
//                    logger.severe("\nException caught while trying to save the attachement.");
//                }
//                logger.info("File attachment has been saved successfully to " + downloadFileName);
//            }
//        }
//
//        return done;
//    }
//
//    public static boolean abortJob(String jobId) throws Exception {
//        boolean done = false;
//        BulkDataExchangeActions bdeActions = new BulkDataExchangeActions();
//        AbortJobResponse response = bdeActions.abortJobs(jobId);
//        if (response != null) {
//            BaseServiceResponse baseRep = response;
//            if (isSuccess(baseRep)) {
//                done = true;
//                System.out.println("abortJob Request: jobId=" + jobId);
//                System.out.println("abortJob Response::: \n {");
//                System.out.println("    Ack: " + response.getAck());
//                System.out.println("    ResponseTime: " + response.getTimestamp() + "\n } \n");
//            }
//
//        }
//        return (done);
//    }
//
//    private static boolean isSuccess(BaseServiceResponse response) {
//        boolean done = true;
//        ErrorMessage errorMsg = null;
//        if (!response.getAck().equals(AckValue.SUCCESS)) {
//
//            //} else if (response.getAck().equals(AckValue.WARNING)) {
//            errorMsg = new ErrorMessage();
//            if (errorMsg != null) {
//                ErrorData error = response.getErrorMessage().getError().get(0);
//                if (response.getAck().equals(AckValue.WARNING)) {
//                    logger.warning(error.getSeverity() + " --> ErrorID=" + error.getErrorId() + " ; ErrorMessage=\"" + error.getMessage() + "\"");
//                } else if (response.getAck().equals(AckValue.FAILURE) || response.getAck().equals(AckValue.PARTIAL_FAILURE)) {
//                    done = false;
//                    logger.severe(error.getSeverity() + " --> ErrorID=" + error.getErrorId() + " ; ErrorMessage=\"" + error.getMessage() + "\"");
//                }
//            }
//        }
//        return done;
//    }
//
//    public static boolean createUploadJob(String jobType) throws Exception {
//        boolean done = false;
//        BulkDataExchangeActions bdeActions = new BulkDataExchangeActions();
//        CreateUploadJobResponse response = bdeActions.createUploadJob(jobType);
//        BaseServiceResponse baseRep = response;
//        if (isSuccess(baseRep)) {
//            done = true;
//            System.out.println("createUploadJob Request: jobType=" + jobType);
//            System.out.println("createUploadJob Response::: \n {");
//            System.out.println("JobId: " + response.getJobId());
//            System.out.println("FileReferenceId: " + response.getFileReferenceId());
//            System.out.println("MaxFileSize: " + response.getMaxFileSize() + "\n }\n");
//        }
//        return (done);
//    }
//
//    public static boolean startUploadJob(String jobId) throws Exception {
//        boolean done = false;
//        BulkDataExchangeActions bdeActions = new BulkDataExchangeActions();
//        StartUploadJobResponse response = bdeActions.startUploadJob(jobId);
//        if (response != null) {
//            BaseServiceResponse baseRep = response;
//            if (isSuccess(baseRep)) {
//                System.out.println("startUploadJob Request: jobId=" + jobId);
//                System.out.println("startUploadJob Response: \n {");
//                System.out.println("    Ack: " + response.getAck());
//                System.out.println("    ResponseTime: " + response.getTimestamp() + "\n } \n");
//            }
//        }
//        return (done);
//    }
//
//    public static boolean getJobStatus(String jobId) throws Exception {
//        boolean done = false;
//        BulkDataExchangeActions bdeActions = new BulkDataExchangeActions();
//        GetJobStatusResponse response = bdeActions.getJobStatus(jobId);
//        BaseServiceResponse baseRep = response;
//        if (isSuccess(baseRep)) {
//            done = true;
//            JobProfile job = retrieveOneJobStatus(response);
//            System.out.println("getJobStatus Request: jobId=" + jobId);
//            System.out.println("getJobStatus Response: \n {");
//            System.out.println("getJobStatus for jobId=" + jobId);
//            System.out.println("jobId: " + job.getJobId());
//            System.out.println("jobType: " + job.getJobType());
//            System.out.println("jobStatus: " + job.getJobStatus());
//            if (job.getJobStatus().compareTo(JobStatus.ABORTED) == 0 || job.getJobStatus().compareTo(JobStatus.ABORTED) == 0) {
//                System.out.println("PercentComplete: " + job.getPercentComplete());
//            }
//            System.out.println("} \n");
//        }
//        return done;
//    }
//
//    public static boolean getJobs(String conditionsStr) throws Exception {
//        System.out.println("/////////in getJobs///////");
//        BulkDataExchangeActions bdeActions = new BulkDataExchangeActions();
//        GetJobsResponse response = bdeActions.getJobs(conditionsStr);
//        System.out.println("////////after getting jobs///////");
//        List<JobProfile> jobs=response.getJobProfile();
//        System.out.println("///////////Jobs Size: "+jobs.size());
//        Iterator itr = jobs.iterator();
//        while (itr.hasNext()) {
//            JobProfile job = (JobProfile) itr.next();
//            System.out.println(job.getJobId() + " : " + job.getJobType() + " : " + job.getJobStatus());
//            if (job.getJobStatus().equals(JobStatus.CREATED)) {
//                System.out.println("Created Job");
//            }
//            if (job.getJobStatus().equals(JobStatus.COMPLETED)) {
//                System.out.println("Completed Job");
//            }
//            if (job.getJobStatus().equals(JobStatus.ABORTED)) {
//                System.out.println("Aborted Job");
//            }
//        }
//        BaseServiceResponse baseRep = response;
//        return (isSuccess(baseRep));
//    }
//
//    public static boolean startDownloadJob(String downloadJobType, String startTimeString) throws Exception {
//        boolean done = false;
//        StartDownloadJobResponse response = doStartDownloadJob(downloadJobType, startTimeString);
//        if (response != null) {
//            BaseServiceResponse baseRep = response;
//            if (isSuccess(baseRep)) {
//                done = true;
//                System.out.println("startDownloadJob for jobType=" + downloadJobType);
//                System.out.println("startDownloadJobResponse : \n {");
//                System.out.println("jobId: " + response.getJobId());
//                System.out.println("Response Timestamp: " + response.getTimestamp());
//                System.out.println("} \n");
//            }
//        }
//        return done;
//
//    }
//
//    private static StartDownloadJobResponse doStartDownloadJob(String downloadJobType, String startTimeString) throws Exception {
//        BulkDataExchangeActions bdeActions = new BulkDataExchangeActions();
//        return (bdeActions.startDownloadJob(downloadJobType, startTimeString));
//    }
//
//    private static boolean verifyFileForUploadJob(String uploadFileName) {
//        // verify file to upload
//        boolean found = false;
//        BufferedReader file;
//        try {
//            file = new BufferedReader(new FileReader(uploadFileName));
//            String st = file.readLine();
//            //logger.info("The first line of " + uploadFileName + " is:" + st);
//            logger.info("File existence check passed.");
//            found = true;
//        } catch (FileNotFoundException e) {
//            logger.severe("File '" + uploadFileName + "' not found.");
//            return false;
//        } catch (IOException e) {
//            return false;
//        }
//
//        return found;
//    }
//
//    private static String getJobTypeFromXML(String inFilename) {
//        // get the JobType from the input xml file
//        File inputXml = null;
//        inputXml = new File(inFilename);
//        CreateLMSParser parser = new CreateLMSParser();
//        boolean parseOk = parser.parse(inputXml);
//        if (!parseOk) {
//            logger.severe("Failed to extract the JobType from the file [" + inFilename + "]");
//            return null;
//        }
//        // extract the JObType String successfully
//        String jobType = parser.getJobType();
//        if (jobType == null) {
//            logger.severe("Invalid job type in the XML file.");
//        } else {
//            logger.info("Found the job type from the XML file, it is " + jobType);
//        }
//        return jobType;
//    }
}
