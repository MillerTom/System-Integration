/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author tmiller
 * 
 */
public class XMLParser {

    private String supportMail;
    private String supportPassword;
    private String server;
    private String serverPort;
    private String listingFilesPath;
    private String downloadFilesPath;
    private String reportType;
    private String reportFilesPath;
    private String schemaName;
    private String tableName;
    private String launchProgramName;
    private String openFilePath;

    public XMLParser() {
        
    }

    public XMLParser(String configFilePath) {
        this.parse(configFilePath);
    }

    public void parse(String configFilePath) {
        try {
            DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuilder = dfactory.newDocumentBuilder();
            Document xmldocument = dbuilder.parse(configFilePath);
            Element settingelement = xmldocument.getDocumentElement();
            Element supportMailElement = (Element) settingelement.getElementsByTagName("Support-Mail").item(0);
            Element supportPasswordElement = (Element) settingelement.getElementsByTagName("Support-Password").item(0);
            Element serverElement = (Element) settingelement.getElementsByTagName("Server").item(0);
            Element serverPortElement = (Element) settingelement.getElementsByTagName("Port").item(0);
            Element listingFilesPathElement = (Element) settingelement.getElementsByTagName("Listing-Files-Path").item(0);
            Element downloadFilesPathElement = (Element) settingelement.getElementsByTagName("download-Files-Path").item(0);
            Element reportFilesPathElement = (Element) settingelement.getElementsByTagName("Inventory-reports-path").item(0);
            Element reportTypeElement = (Element) settingelement.getElementsByTagName("report-type").item(0);
            Element schemaNameElement = (Element) settingelement.getElementsByTagName("Catalogname").item(0);
            Element tableNameElement = (Element) settingelement.getElementsByTagName("table-name").item(0);
            Element launchProgramNameElement = (Element) settingelement.getElementsByTagName("open-program-name").item(0);
            Element openFilePathElement = (Element) settingelement.getElementsByTagName("open-file-path").item(0);
            this.setSupportMail(supportMailElement.getTextContent());
            this.setSupportPassword(supportPasswordElement.getTextContent());
            this.setServer(serverElement.getTextContent());
            this.setServerPort(serverPortElement.getTextContent());
            this.setListingFilesPath(listingFilesPathElement.getTextContent());
            this.setDownloadFilesPath(downloadFilesPathElement.getTextContent());
            this.setReportFilesPath(reportFilesPathElement.getTextContent());
            this.setReportType(reportTypeElement.getTextContent());
            this.setSchemaName(schemaNameElement.getTextContent());
            this.setTableName(tableNameElement.getTextContent());
            this.setLaunchProgramName(launchProgramNameElement.getTextContent());
            this.setOpenFilePath(openFilePathElement.getTextContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getOpenFilePath() {
        return openFilePath;
    }

    public void setOpenFilePath(String openFilePath) {
        this.openFilePath = openFilePath;
    }

    public String getLaunchProgramName() {
        return launchProgramName;
    }

    public void setLaunchProgramName(String launchProgramName) {
        this.launchProgramName = launchProgramName;
    }

    
    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getReportFilesPath() {
        return reportFilesPath;
    }

    public void setReportFilesPath(String reportFilesPath) {
        this.reportFilesPath = reportFilesPath;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    
    public String getDownloadFilesPath() {
        return downloadFilesPath;
    }

    public void setDownloadFilesPath(String downloadFilesPath) {
        this.downloadFilesPath = downloadFilesPath;
    }

    
    public String getSupportMail() {
        return supportMail;
    }

    public void setSupportMail(String supportMail) {
        this.supportMail = supportMail;
    }

    public String getSupportPassword() {
        return supportPassword;
    }

    public void setSupportPassword(String supportPassword) {
        this.supportPassword = supportPassword;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getListingFilesPath() {
        return listingFilesPath;
    }

    public void setListingFilesPath(String listingFilesPath) {
        this.listingFilesPath = listingFilesPath;
    }
    
}