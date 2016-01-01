/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

import com.ebay.marketplace.services.*;
import daos.LogsFacade;
import daos.ReportsFacade;
import daos.UsersFacade;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import javax.activation.DataHandler;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.BulkDataExchangeActions;
import model.FileTransferActions;
import pojos.Reports;
import pojos.Users;
import pojos.ValidEntry;

/**
 *
 * @author tmiller
 */
public class GenerateReportThread extends Thread {

    private static daos.LogsFacade logsFacade;
    private static daos.ReportsFacade reportsFacade;
    private static daos.UsersFacade usersFacade;

    public GenerateReportThread(ReportsFacade ejbFacade ,UsersFacade ejbUsersFacade , LogsFacade ejblogfacade) {
        reportsFacade = ejbFacade;
        usersFacade = ejbUsersFacade;
        logsFacade = ejblogfacade;
    }

    @Override
    public void run() {
       this.generateReport();
    }
    
    /**
     * Generate Report and Saves its Data in Database
     *
     * @return String
     */
    public void generateReport() {
        LoggingManager logger = new LoggingManager(logsFacade);
        logger.log("trying to generate Report from eBay Stock", "INFO");
        String CONFIG_PROPERTIES = this.getClass().getClassLoader().getResource("configfile.xml").getPath();
        XMLParser parser = new XMLParser(CONFIG_PROPERTIES);
        String jobType = parser.getReportType();
        logger.log("Successfully retreivd Report Type from Configuration File [" + jobType + "]", "INFO");

        String[] array = createDownloadPath();
        String reportFilePath = array[2];
        boolean done = this.generateinvenotryReport(jobType, reportFilePath);
        if (done) {
            Reports report = new Reports();
            report.setFileName(reportFilePath);

            File reportFile = new File(reportFilePath);
            String[] temp = null;
            if (reportFile.exists()) {
                temp = fileDateTime(reportFilePath);
                report.setReportDate(new Date(temp[0]));
                report.setReportTime(temp[1]);
            } else {
                report.setReportDate(new Date(array[0].replace("-", "/")));
                report.setReportTime(array[1]);
            }

            Users user = usersFacade.findByUserName(getUserNameFromSession());
            report.setUser(user);

            ValidEntry ve = new ValidEntry();
            ve.setVeId(DownloadFiles.SUCCESS.value());
            ve.setVeName(DownloadFiles.SUCCESS.name());
            report.setStatus(ve);

            ValidEntry ve1 = new ValidEntry();
            ve1.setVeId(ReportTypes.INVENTORY.value());
            ve1.setVeName(ReportTypes.INVENTORY.name());
            report.setReportType(ve1);

            ValidEntry ve2 = new ValidEntry();
            ve2.setVeId(ReportLoadStatus.NOT_YET.value());
            ve2.setVeName(ReportLoadStatus.NOT_YET.name());
            report.setLoadStatus(ve2);

            try {
                reportsFacade.create(report);
                logger.log("Successfully saved Data of Report [" + report.getFileName() + "] into Database", "INFO");
                logger.log("Report generated Successfully", "INFO");
            } catch (Exception e) {
                logger.log("failed to save Data of Report [" + report.getFileName() + "] into Database, due to This Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            }
        } else {
            logger.log("failed to generate Report from eBay Stock", "ERROR");
        }
    }
    
    
    /**
     * Method to Generate Inventory Report from eBay Stock
     *
     * @param jobType
     * @param downloadFileName
     *
     * @return boolean
     */
    public boolean generateinvenotryReport(String jobType, String downloadFileName) {
        LoggingManager logger = new LoggingManager(logsFacade);
        boolean done = false;
        String jobid = "";
        try {
            logger.log("excuting Report Steps", "INFO");

            BulkDataExchangeActions bdeActions = new BulkDataExchangeActions(logsFacade);
            logger.log("trying to start Report Download Job", "INFO");
            StartDownloadJobResponse sdljResp = bdeActions.startDownloadJob(jobType, null);

            String JobStatusQueryInterval = bdeActions.getJobQueryInterval();
            logger.log("Query Interval [" + JobStatusQueryInterval + "]", "INFO");

            BaseServiceResponse baseRep = sdljResp;
            if (!isSuccess(baseRep)) {
                logger.log("Report Start Download Job Response didn't pass Response Check", "ERROR");
                return done;
            } else {
                logger.log("Report Start Download Job Response passed Response Check Successfully", "INFO");
                logger.log("Report Download Job started Successfully", "INFO");
            }

            jobid = sdljResp.getJobId();
            done = doDownload(downloadFileName, jobid, JobStatusQueryInterval);

            return done;
        } catch (Exception ex) {
            logger.log("failed to Download The Report due to This Exception [" + ExceptionHandler.getStackTraceAsString(ex) + "]", "EXCEPTION");
            return done;
        }
    }
    
    /**
     * Method to Test responses is it succeeded or no...
     *
     * @param response
     *
     * @return boolean
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
                        logger.log("Response passed Status Check with this Warning [" + error.getMessage() + "]", "WARNING");
                    } else if (response.getAck().equals(AckValue.FAILURE) || response.getAck().equals(AckValue.PARTIAL_FAILURE)) {
                        done = false;
                        logger.log("Response failed to pass Status Check due to this Error [" + error.getMessage() + "]", "ERROR");
                    }
                }
            }
        } else {
            done = false;
        }
        return done;
    }
    
    
    /**
     *
     *
     * @param downloadFileName
     * @param jobId
     * @param JobStatusQueryInterval
     *
     * @return boolean
     */
    private boolean doDownload(String downloadFileName, String jobId, String JobStatusQueryInterval) {
        LoggingManager logger = new LoggingManager(logsFacade);
        logger.log("Now downloading Inventory Report ", "INFO");
        BulkDataExchangeActions bdeActions = new BulkDataExchangeActions(logsFacade);
        FileTransferActions ftActions = new FileTransferActions(logsFacade);
        boolean fileProcessIsDone = false;
        boolean downloadIsDone = false;
        if (JobStatusQueryInterval.length() == 0) {
            JobStatusQueryInterval = "10000";
        }
        do {
            JobProfile job = null;
            GetJobStatusResponse getJobStatusResp = bdeActions.getJobStatus(jobId);
            BaseServiceResponse baseRep = getJobStatusResp;
            if (!isSuccess(baseRep)) {
                logger.log("can not retrieve Job Status from Job Status Response", "ERROR");
                return false;
            }

            job = retrieveOneJobStatus(getJobStatusResp);

            if (job.getJobStatus().equals(JobStatus.COMPLETED) && job.getPercentComplete() == 100.0) {
                logger.log("Report Job Status [" + job.getJobStatus() + "] Successfully", "INFO");
                fileProcessIsDone = true;
                DownloadFileResponse downloadFileResp = ftActions.downloadReportFile(downloadFileName, jobId, job.getFileReferenceId(), logsFacade);
                if (downloadFileResp != null) {
                    baseRep = downloadFileResp;
                    if (isSuccess(baseRep)) {
                        logger.log("Writing Report Response to The Compressed File ", "INFO");
                        downloadIsDone = true;
                        FileAttachment attachment = downloadFileResp.getFileAttachment();
                        DataHandler dh = attachment.getData();
                        try {
                            InputStream in = dh.getInputStream();
                            BufferedInputStream bis = new BufferedInputStream(in);

                            FileOutputStream fo = new FileOutputStream(new File(downloadFileName)); // "C:/myDownLoadFile.gz"
                            BufferedOutputStream bos = new BufferedOutputStream(fo);
                            int bytes_read = 0;
                            byte[] dataBuf = new byte[4096];
                            while ((bytes_read = bis.read(dataBuf)) != -1) {
                                bos.write(dataBuf, 0, bytes_read);
                            }
                            bis.close();
                            bos.flush();
                            bos.close();
                            logger.log("finished Writing Report Response to The Compressed File ", "INFO");
                        } catch (IOException e) {
                            logger.log("failed to write Report Response to the Compressed File due to this Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
                        }
                    }
                }
            } else if (job.getJobStatus().equals(JobStatus.FAILED) || job.getJobStatus().equals(JobStatus.ABORTED)) {
                logger.log("Report Job Status[" + job.getJobStatus() + "]", "ERROR");
                return false;
            } else {
                try {
                    Thread.sleep(Integer.parseInt(JobStatusQueryInterval));
                } catch (InterruptedException x) {
                    logger.log("Job Status Waiting period iterrupted due to this Exception [" + ExceptionHandler.getStackTraceAsString(x) + "]", "EXCEPTION");
                    fileProcessIsDone = false;
                    downloadIsDone = false;
                }
            }
        } while (!fileProcessIsDone);

        return downloadIsDone;
    }
    
    /**
     *
     * @param jobStatusResp
     *
     * @return JopProfile
     */
    private static JobProfile retrieveOneJobStatus(GetJobStatusResponse jobStatusResp) {
        JobProfile job = null;
        if (jobStatusResp != null) {
            List<JobProfile> listOfJobs = jobStatusResp.getJobProfile();
            if (listOfJobs.size() == 1) {
                Iterator itr = listOfJobs.iterator();
                job = (JobProfile) itr.next();
            }
        }
        return job;
    }
    
    /**
     * Get user Name from the Session...
     *
     * @return String
     */
    public String getUserNameFromSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String userName = session.getValue("userName").toString();
        return userName;
    }
    
    /**
     *
     * This Method gets Date and time of the Passed File Name
     *
     * @param filePath
     *
     * @return String Array of Date and Time...
     */
    public String[] fileDateTime(String filePath) {
        LoggingManager logger = new LoggingManager(logsFacade);
        try {
            File f = new File(filePath);
            long datetime = f.lastModified();
            Date d = new Date(datetime);
            TimeZone timeZone = TimeZone.getTimeZone("Europe/Copenhagen");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            sdf.setTimeZone(timeZone);
            String dateString = sdf.format(d);
            String[] temp = dateString.split(" ");
            return temp;
        } catch (Exception e) {
            logger.log("failed to get Modified Date of File [" + filePath + "] due to this Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            return new String[]{"1970-12-31", "========="};
        }
    }

    /**
     *
     * This Method gets the Current Date and time
     *
     * @return String Array of Date and Time...
     */
    public String[] currentDateTime() {
        Date d = new Date();
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Copenhagen");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        String dateString = sdf.format(d);
        return dateString.split(" ");
    }
    
    /**
     * Creating Download Folder for the Report Files
     *
     * @return String represents the Folder Path
     */
    public String[] createDownloadPath() {
        LoggingManager logger = new LoggingManager(logsFacade);
        try {
            String CONFIG_PROPERTIES = this.getClass().getClassLoader().getResource("configfile.xml").getPath();
            String[] temp = currentDateTime();
            XMLParser parser = new XMLParser(CONFIG_PROPERTIES);
            File reportsPath = new File(parser.getReportFilesPath() + temp[0]);
            String zipFilePath = "";
            boolean mkdirsResult = false;
            String[] output = null;
            if (reportsPath.exists()) {
                logger.log("Directory[" + reportsPath.getAbsolutePath() + "] already exists ", "INFO");
                File zipFile = new File(reportsPath.getAbsolutePath() + File.separator + "Report-" + temp[1] + ".zip");
                zipFilePath = zipFile.getAbsolutePath();
                output = new String[]{temp[0], temp[1], zipFilePath};
                logger.log("Report File [" + zipFilePath + "]", "INFO");
                return output;
            } else {
                mkdirsResult = reportsPath.mkdirs();
                if (mkdirsResult) {
                    File zipFile = new File(reportsPath.getAbsolutePath() + File.separator + "Report-" + temp[1] + ".zip");
                    zipFilePath = zipFile.getAbsolutePath();
                    output = new String[]{temp[0], temp[1], zipFilePath};
                    logger.log("Report File [" + zipFilePath + "]", "INFO");
                    return output;
                } else {
                    logger.log("failed to create Report File [" + zipFilePath + "], So New File Report File [" + parser.getReportFilesPath() + "]", "ERROR");
                    output = new String[]{temp[0], temp[1], parser.getReportFilesPath()};
                    return output;
                }
            }
        } catch (Exception ex) {
            logger.log("failed to create Report File Path due to this Exception [" + ExceptionHandler.getStackTraceAsString(ex) + "]", "EXCEPTION");
            return null;
        }
    }
}
