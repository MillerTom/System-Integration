package controllers;

import controllers.util.*;
import daos.FilesFacade;
import daos.LogsFacade;
import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import model.FilesComparator;
import pojos.Files;
import pojos.ValidEntry;

@ManagedBean(name = "filesController")
@SessionScoped
public class FilesController implements Serializable {

    //variables declarations...
    private Files current;
    private DataModel items = null;
    private DataModel addItems = null;
    private DataModel reviseItems = null;
    private DataModel endItems = null;
    @EJB
    private daos.FilesFacade ejbFacade;
    @EJB
    private daos.LogsFacade ejbLogsFacade;
    private PaginationHelper pagination;
    private PaginationHelper addPagination;
    private PaginationHelper revisePagination;
    private PaginationHelper endPagination;
    private int selectedItemIndex;
    private String folderName;
    private String fullListingFilesPath;
    private char filesType;
    private String downloadPath;
    private String timeNow;
    private Map<Long, Boolean> checked = new HashMap<Long, Boolean>();
    //end of Variables Declarations

    //getter and setter methods
    public Map<Long, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Long, Boolean> checked) {
        this.checked = checked;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(String timeNow) {
        this.timeNow = timeNow;
    }

    public char getFilesType() {
        return filesType;
    }

    public void setFilesType(char filesType) {
        this.filesType = filesType;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getFullListingFilesPath() {

        return fullListingFilesPath;

    }

    public void setFullListingFilesPath(String fullListingFilesPath) {

        this.fullListingFilesPath = fullListingFilesPath;

    }
    //end of setter and getter Methods

    /**
     * Constructor Declaration...
     */
    public FilesController() {
    }

    //Generated Framework Methods
    private LogsFacade getLogsFacade() {
        return ejbLogsFacade;
    }

    private FilesFacade getFacade() {
        return ejbFacade;
    }

    public Files getSelected() {
        if (current == null) {
            current = new Files();
            selectedItemIndex = -1;
        }
        return current;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(30) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(finder(fullListingFilesPath, filesType));
                }
            };
        }
        return pagination;
    }

    public PaginationHelper getAddPagination() {
        if (addPagination == null) {
            addPagination = new PaginationHelper(30) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(finder(fullListingFilesPath, 'I'));
                }
            };
        }
        return addPagination;
    }

    public PaginationHelper getRevisePagination() {
        if (revisePagination == null) {
            revisePagination = new PaginationHelper(30) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(finder(fullListingFilesPath, 'R'));
                }
            };
        }
        return revisePagination;
    }

    public PaginationHelper getEndPagination() {
        if (endPagination == null) {
            endPagination = new PaginationHelper(30) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(finder(fullListingFilesPath, 'E'));
                }
            };
        }
        return endPagination;
    }

    public String prepareList() {
        recreateModel();
        return "List?faces-redirect=true";
    }

    public String prepareAddList() {
        recreateAddModel();
        return "addlist?faces-redirect=true";
    }

    public String prepareReviseList() {
        recreateReviseModel();
        return "reviselist?faces-redirect=true";
    }

    public String prepareEndList() {
        recreateEndModel();
        return "endlist?faces-redirect=true";
    }

    public String prepareView() {
        current = (Files) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View?faces-redirect=true";
    }

    public String prepareCreate() {
        current = new Files();
        selectedItemIndex = -1;
        return "Create?faces-redirect=true";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FilesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Files) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit?faces-redirect=true";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FilesUpdated"));
            return "View?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Files) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List?faces-redirect=true";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View?faces-redirect=true";
        } else {
            recreateModel();
            return "List?faces-redirect=true";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FilesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            selectedItemIndex = count - 1;
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    public DataModel getAddItems() {
        if (addItems == null) {
            addItems = getAddPagination().createPageDataModel();
        }
        return addItems;
    }

    public DataModel getReviseItems() {
        if (reviseItems == null) {
            reviseItems = getRevisePagination().createPageDataModel();
        }
        return reviseItems;
    }

    public DataModel getEndItems() {
        if (endItems == null) {
            endItems = getEndPagination().createPageDataModel();
        }
        return endItems;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreateAddModel() {
        addItems = null;
    }

    private void recreateReviseModel() {
        reviseItems = null;
    }

    private void recreateEndModel() {
        endItems = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    private void recreateAddPagination() {
        addPagination = null;
    }

    private void recreateRevisePagination() {
        revisePagination = null;
    }

    private void recreateEndPagination() {
        endPagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List?faces-redirect=true";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List?faces-redirect=true";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Files.class)
    public static class FilesControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FilesController controller = (FilesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "filesController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Files) {
                Files o = (Files) object;
                return getStringKey(o.getfId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FilesController.class.getName());
            }
        }
    }

    //Business Methods...
    /**
     * List All Add Files from the Listing Directory
     *
     * @return String represents the List Page
     */
    public String listAddFiles() {
        String fn = this.folderName;
        String is = this.getClass().getClassLoader().getResource("configfile.xml").getPath();
        XMLParser xmlparser = new XMLParser(is);
        String listingParentPath = xmlparser.getListingFilesPath();
        String downloadFilePath = xmlparser.getDownloadFilesPath();
        this.setDownloadPath(downloadFilePath);
        this.setFullListingFilesPath(listingParentPath + fn);
        this.setFilesType('I');
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.putValue("addFilesCount", finder(fullListingFilesPath, filesType).size());
        recreateAddModel();
        return "addlist?faces-redirect=true";
    }

    /**
     * List All Revise Files from the Listing Directory
     *
     * @return String represents the List Page
     */
    public String listReviseFiles() {
        String fn = this.folderName;
        String is = this.getClass().getClassLoader().getResource("configfile.xml").getPath();
        XMLParser xmlparser = new XMLParser(is);
        String listingParentPath = xmlparser.getListingFilesPath();
        String downloadFilePath = xmlparser.getDownloadFilesPath();
        this.setDownloadPath(downloadFilePath);
        this.setFullListingFilesPath(listingParentPath + fn);
        this.setFilesType('R');
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.putValue("reviseFilesCount", finder(fullListingFilesPath, filesType).size());
        recreateReviseModel();
        return "reviselist?faces-redirect=true";
    }

    /**
     * List All End Files from the Listing Directory
     *
     * @return String represents the List Page
     */
    public String listEndFiles() {
        String fn = this.folderName;
        String is = this.getClass().getClassLoader().getResource("configfile.xml").getPath();
        XMLParser xmlparser = new XMLParser(is);
        String listingParentPath = xmlparser.getListingFilesPath();
        String downloadFilePath = xmlparser.getDownloadFilesPath();
        this.setDownloadPath(downloadFilePath);
        this.setFullListingFilesPath(listingParentPath + fn);
        this.setFilesType('E');
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.putValue("endFilesCount", finder(fullListingFilesPath, filesType).size());
        recreateEndModel();
        return "endlist?faces-redirect=true";
    }

    /**
     * List All Listing Files from the Listing Directory
     *
     * @return String represents the List Page
     */
    public String listAllFiles() {
        String fn = this.folderName;
        String is = this.getClass().getClassLoader().getResource("configfile.xml").getPath();
        XMLParser xmlparser = new XMLParser(is);
        String listingParentPath = xmlparser.getListingFilesPath();
        String downloadFilePath = xmlparser.getDownloadFilesPath();
        this.setDownloadPath(downloadFilePath);
        this.setFullListingFilesPath(listingParentPath + fn);
        this.setFilesType('A');
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.putValue("allFilesCount", finder(fullListingFilesPath, filesType).size());
        recreateModel();
        return "/List?faces-redirect=true";
    }

    /**
     * Find All Files in the Passed Folder Name according to passed File Type
     *
     * @param dirName Directory Name
     * @param type File Type(Add, Revise, End, All)
     *
     * @return List of Files
     */
    public List<Files> finder(String dirName, final char type) {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        List<Files> filesList = new ArrayList<Files>();
        File dir = new File(dirName);
        if (dir.exists()) {
            File[] filesArray = dir.listFiles(new FilenameFilter() {

                public boolean accept(File dir, String filename) {
                    switch (type) {
                        case 'I':
                            return filename.startsWith("Add") && filename.endsWith(".xml");
                        case 'R':
                            return filename.startsWith("Revise") && filename.endsWith(".xml");
                        case 'E':
                            return filename.startsWith("End") && filename.endsWith(".xml");
                        default:
                            return filename.endsWith(".xml");
                    }
                }
            });

            if (filesArray.length > 0) {
                for (int i = 0; i < filesArray.length; i++) {
                    File diskFile = filesArray[i];
                    Files file = new Files();
                    String fn = diskFile.getName();
                    file.setfId(i + 1);
                    file.setFileName(diskFile.getName());
                    file.setFilePath(diskFile.getAbsolutePath());

                    String[] temp = fileDateTime(diskFile.getAbsolutePath());

                    ValidEntry ve = new ValidEntry();
                    if (fn.startsWith("Add")) {
                        ve.setVeId(FileTypes.Add.value());
                        ve.setVeName(FileTypes.Add.name());
                    }
                    if (fn.startsWith("Revise")) {
                        ve.setVeId(FileTypes.Revise.value());
                        ve.setVeName(FileTypes.Revise.name());
                    }
                    if (fn.startsWith("End")) {
                        ve.setVeId(FileTypes.End.value());
                        ve.setVeName(FileTypes.End.name());
                    }
                    file.setFileType(ve);

                    Date fileDate = new Date(temp[0]);
                    file.setFileDate(fileDate);
                    file.setFileTime(temp[1]);
                    Files DBFile = null;
                    try {
                        DBFile = getFacade().findByFileName(file.getFileName());
                    } catch (Exception e) {
                    }
                    ValidEntry ve1 = new ValidEntry();
                    if (DBFile != null) {
                        if (file.getFileTime().equalsIgnoreCase(DBFile.getFileTime()) && file.getFileDate().compareTo(DBFile.getFileDate()) == 0) {
                            if (DBFile.getRequestStatus().getVeId() == 9) {
                                ve1.setVeId(UploadFiles.FAILURE.value());
                                ve1.setVeName(UploadFiles.FAILURE.name());
                                file.setDownloadFilePath(DBFile.getDownloadFilePath());
                            } else {
                                ve1.setVeId(UploadFiles.SUCCESS.value());
                                ve1.setVeName(UploadFiles.SUCCESS.name());
                                file.setDownloadFilePath(DBFile.getDownloadFilePath());
                            }
                        } else {
                            ve1.setVeId(UploadFiles.FAILURE.value());
                            ve1.setVeName(UploadFiles.FAILURE.name());
                            file.setDownloadFilePath(this.getDownloadPath());
                        }
                    } else {
                        ve1.setVeId(UploadFiles.FAILURE.value());
                        ve1.setVeName(UploadFiles.FAILURE.name());
                        file.setDownloadFilePath(this.getDownloadPath());
                    }
                    file.setRequestStatus(ve1);
                    filesList.add(file);
                }
            } else {
                logger.log("No Listing Files found Under Directory [" + this.fullListingFilesPath + "]", "Warning");
            }
            //arranging the Files List by File Name
            if (filesList.size() > 0) {
                FilesComparator comparator = new FilesComparator();
                Collections.sort(filesList, comparator);
            }
            logger.log("retrieved Listing Files from Directory: [" + this.fullListingFilesPath + "]", "INFO");
            return filesList;
        } else {
            logger.log("specified Directory [" + this.fullListingFilesPath + "] Not Found !", "Warning");
            return filesList;
        }
    }

    /**
     * Uploads Different File Types to eBay Servers...
     *
     * @return String represents the Page Name
     */
    public String uploadFiles() {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        current = (Files) getItems().getRowData();
        logger.log("Selected File INFO== Name: [" + current.getFilePath() + "], Date: [" + current.getFileDate() + "], Time: [" + current.getFileTime() + "]", "INFO");
        String downloadPathlocal = this.getDownloadPath();
        String downloadFilePath = createDownloadFolder(downloadPathlocal);
        String uploadFileName = current.getFileName();
        String[] fileNameParts = uploadFileName.split("\\.(?=[^\\.]+$)");
        String downloadFileName = "Resp_" + fileNameParts[0] + ".zip";
        File downloadFile = new File(downloadFilePath + File.separator + downloadFileName);
        logger.log("Response File Path [" + downloadFile.getAbsolutePath() + "]", "INFO");

        logger.log("started in Uploading Steps.", "INFO");

        EBayUploader eBUploader = new EBayUploader(current.getFilePath(), getLogsFacade());
        boolean uploadresult = eBUploader.upload(current.getFilePath(), downloadFile.getAbsolutePath());

        if (uploadresult) {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FileUploadedSuccessfully"));
            logger.log("finished Upload Operation Sccessfully", "INFO");
            current.setDownloadFilePath(downloadFile.getAbsolutePath());
            ValidEntry ve = new ValidEntry();
            ve.setVeId(UploadFiles.SUCCESS.value());
            ve.setVeName(UploadFiles.SUCCESS.name());
            current.setRequestStatus(ve);
            ValidEntry ve1 = new ValidEntry();
            ve1.setVeId(UploadFiles.SUCCESS.value());
            ve1.setVeName(UploadFiles.SUCCESS.name());
            current.setResponseStatus(ve1);
            try {
                Files DBFile = null;
                try {
                    DBFile = getFacade().findByFileName(current.getFileName());
                } catch (Exception e) {
                }
                if (DBFile != null) {
                    if (current.getFileTime().equalsIgnoreCase(DBFile.getFileTime()) && current.getFileDate().compareTo(DBFile.getFileDate()) == 0) {
                        logger.log("Data of File [" + current.getFilePath() + "] saved into Database before", "INFO");
                    } else {
                        getFacade().create(current);
                        logger.log("Data of File [" + current.getFilePath() + "] saved into Database Successfully", "INFO");
                    }
                } else {
                    getFacade().create(current);
                    logger.log("Data of File [" + current.getFilePath() + "] saved into Database Successfully ", "INFO");
                }
            } catch (Exception e) {
                logger.log("failed to save Data of File [" + current.getFilePath() + "] into Database, due to that Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            }
        } else {
            ValidEntry ve = new ValidEntry();
            ve.setVeId(UploadFiles.FAILURE.value());
            ve.setVeName(UploadFiles.FAILURE.name());
            current.setRequestStatus(ve);
            ValidEntry ve1 = new ValidEntry();
            ve1.setVeId(UploadFiles.FAILURE.value());
            ve1.setVeName(UploadFiles.FAILURE.name());
            current.setResponseStatus(ve1);
            getFacade().create(current);
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("FileUploadFailed"));
            logger.log("failed to upload File [" + current.getFilePath() + "]", "ERROR");
        }
        return prepareList();
    }

    /**
     * Uploads Add Files to eBay Servers...
     *
     * @return String represents the Page Name
     */
    public String uploadAddFiles() {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        current = (Files) getAddItems().getRowData();
        logger.log("Selected File INFO== Name: [" + current.getFilePath() + "], Date: [" + current.getFileDate() + "], Time: [" + current.getFileTime() + "]", "INFO");
        String downloadPathlocal = this.getDownloadPath();
        String downloadFilePath = createDownloadFolder(downloadPathlocal);
        String uploadFileName = current.getFileName();
        String[] fileNameParts = uploadFileName.split("\\.(?=[^\\.]+$)");
        String downloadFileName = "Resp_" + fileNameParts[0] + ".zip";
        File downloadFile = new File(downloadFilePath + File.separator + downloadFileName);
        logger.log("Response File Path [" + downloadFile.getAbsolutePath() + "]", "INFO");

        logger.log("started in Uploading Steps.", "INFO");

        EBayUploader eBUploader = new EBayUploader(current.getFilePath(), getLogsFacade());
        boolean uploadresult = eBUploader.upload(current.getFilePath(), downloadFile.getAbsolutePath());

        if (uploadresult) {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FileUploadedSuccessfully"));
            logger.log("finished Upload Operation Sccessfully", "INFO");
            current.setDownloadFilePath(downloadFile.getAbsolutePath());
            ValidEntry ve = new ValidEntry();
            ve.setVeId(UploadFiles.SUCCESS.value());
            ve.setVeName(UploadFiles.SUCCESS.name());
            current.setRequestStatus(ve);
            ValidEntry ve1 = new ValidEntry();
            ve1.setVeId(UploadFiles.SUCCESS.value());
            ve1.setVeName(UploadFiles.SUCCESS.name());
            current.setResponseStatus(ve1);
            try {
                Files DBFile = null;
                try {
                    DBFile = getFacade().findByFileName(current.getFileName());
                } catch (Exception e) {
                }
                if (DBFile != null) {
                    if (current.getFileTime().equalsIgnoreCase(DBFile.getFileTime()) && current.getFileDate().compareTo(DBFile.getFileDate()) == 0) {
                        logger.log("Data of File [" + current.getFilePath() + "] saved into Database before", "INFO");
                    } else {
                        getFacade().create(current);
                        logger.log("Data of File [" + current.getFilePath() + "] saved into Database Successfully", "INFO");
                    }
                } else {
                    getFacade().create(current);
                    logger.log("Data of File [" + current.getFilePath() + "] saved into Database Successfully ", "INFO");
                }
            } catch (Exception e) {
                logger.log("failed to save Data of File [" + current.getFilePath() + "] into Database, due to that Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            }
        } else {
            ValidEntry ve = new ValidEntry();
            ve.setVeId(UploadFiles.FAILURE.value());
            ve.setVeName(UploadFiles.FAILURE.name());
            current.setRequestStatus(ve);
            ValidEntry ve1 = new ValidEntry();
            ve1.setVeId(UploadFiles.FAILURE.value());
            ve1.setVeName(UploadFiles.FAILURE.name());
            current.setResponseStatus(ve1);
            getFacade().create(current);
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("FileUploadFailed"));
            logger.log("failed to upload File [" + current.getFilePath() + "]", "ERROR");
        }
        return prepareAddList();
    }

    /**
     * Uploads Revise Files to eBay Servers...
     *
     * @return String represents the Page Name
     */
    public String uploadReviseFiles() {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        current = (Files) getReviseItems().getRowData();
        logger.log("Selected File INFO== Name: [" + current.getFilePath() + "], Date: [" + current.getFileDate() + "], Time: [" + current.getFileTime() + "]", "INFO");
        String downloadPathlocal = this.getDownloadPath();
        String downloadFilePath = createDownloadFolder(downloadPathlocal);
        String uploadFileName = current.getFileName();
        String[] fileNameParts = uploadFileName.split("\\.(?=[^\\.]+$)");
        String downloadFileName = "Resp_" + fileNameParts[0] + ".zip";
        File downloadFile = new File(downloadFilePath + File.separator + downloadFileName);
        logger.log("Response File Path [" + downloadFile.getAbsolutePath() + "]", "INFO");

        logger.log("started in Uploading Steps.", "INFO");

        EBayUploader eBUploader = new EBayUploader(current.getFilePath(), getLogsFacade());
        boolean uploadresult = eBUploader.upload(current.getFilePath(), downloadFile.getAbsolutePath());

        if (uploadresult) {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FileUploadedSuccessfully"));
            logger.log("finished Upload Operation Sccessfully", "INFO");
            current.setDownloadFilePath(downloadFile.getAbsolutePath());
            ValidEntry ve = new ValidEntry();
            ve.setVeId(UploadFiles.SUCCESS.value());
            ve.setVeName(UploadFiles.SUCCESS.name());
            current.setRequestStatus(ve);
            ValidEntry ve1 = new ValidEntry();
            ve1.setVeId(UploadFiles.SUCCESS.value());
            ve1.setVeName(UploadFiles.SUCCESS.name());
            current.setResponseStatus(ve1);
            try {
                Files DBFile = null;
                try {
                    DBFile = getFacade().findByFileName(current.getFileName());
                } catch (Exception e) {
                }
                if (DBFile != null) {
                    if (current.getFileTime().equalsIgnoreCase(DBFile.getFileTime()) && current.getFileDate().compareTo(DBFile.getFileDate()) == 0) {
                        logger.log("Data of File [" + current.getFilePath() + "] saved into Database before", "INFO");
                    } else {
                        getFacade().create(current);
                        logger.log("Data of File [" + current.getFilePath() + "] saved into Database Successfully", "INFO");
                    }
                } else {
                    getFacade().create(current);
                    logger.log("Data of File [" + current.getFilePath() + "] saved into Database Successfully ", "INFO");
                }
            } catch (Exception e) {
                logger.log("failed to save Data of File [" + current.getFilePath() + "] into Database, due to that Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            }
        } else {
            ValidEntry ve = new ValidEntry();
            ve.setVeId(UploadFiles.FAILURE.value());
            ve.setVeName(UploadFiles.FAILURE.name());
            current.setRequestStatus(ve);
            ValidEntry ve1 = new ValidEntry();
            ve1.setVeId(UploadFiles.FAILURE.value());
            ve1.setVeName(UploadFiles.FAILURE.name());
            current.setResponseStatus(ve1);
            getFacade().create(current);
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("FileUploadFailed"));
            logger.log("failed to upload File [" + current.getFilePath() + "]", "ERROR");
        }
        return prepareReviseList();
    }

    /**
     * Uploads End Files to eBay Servers...
     *
     * @return String represents the Page Name
     */
    public String uploadEndFiles() {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        current = (Files) getEndItems().getRowData();
        logger.log("Selected File INFO== Name: [" + current.getFilePath() + "], Date: [" + current.getFileDate() + "], Time: [" + current.getFileTime() + "]", "INFO");
        String downloadPathlocal = this.getDownloadPath();
        String downloadFilePath = createDownloadFolder(downloadPathlocal);
        String uploadFileName = current.getFileName();
        String[] fileNameParts = uploadFileName.split("\\.(?=[^\\.]+$)");
        String downloadFileName = "Resp_" + fileNameParts[0] + ".zip";
        File downloadFile = new File(downloadFilePath + File.separator + downloadFileName);
        logger.log("Response File Path [" + downloadFile.getAbsolutePath() + "]", "INFO");

        logger.log("started in Uploading Steps.", "INFO");

        EBayUploader eBUploader = new EBayUploader(current.getFilePath(), getLogsFacade());
        boolean uploadresult = eBUploader.upload(current.getFilePath(), downloadFile.getAbsolutePath());

        if (uploadresult) {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FileUploadedSuccessfully"));
            logger.log("finished Upload Operation Sccessfully", "INFO");
            current.setDownloadFilePath(downloadFile.getAbsolutePath());
            ValidEntry ve = new ValidEntry();
            ve.setVeId(UploadFiles.SUCCESS.value());
            ve.setVeName(UploadFiles.SUCCESS.name());
            current.setRequestStatus(ve);
            ValidEntry ve1 = new ValidEntry();
            ve1.setVeId(UploadFiles.SUCCESS.value());
            ve1.setVeName(UploadFiles.SUCCESS.name());
            current.setResponseStatus(ve1);
            try {
                Files DBFile = null;
                try {
                    DBFile = getFacade().findByFileName(current.getFileName());
                } catch (Exception e) {
                }
                if (DBFile != null) {
                    if (current.getFileTime().equalsIgnoreCase(DBFile.getFileTime()) && current.getFileDate().compareTo(DBFile.getFileDate()) == 0) {
                        logger.log("Data of File [" + current.getFilePath() + "] saved into Database before", "INFO");
                    } else {
                        getFacade().create(current);
                        logger.log("Data of File [" + current.getFilePath() + "] saved into Database Successfully", "INFO");
                    }
                } else {
                    getFacade().create(current);
                    logger.log("Data of File [" + current.getFilePath() + "] saved into Database Successfully ", "INFO");
                }
            } catch (Exception e) {
                logger.log("failed to save Data of File [" + current.getFilePath() + "] into Database, due to that Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            }
        } else {
            ValidEntry ve = new ValidEntry();
            ve.setVeId(UploadFiles.FAILURE.value());
            ve.setVeName(UploadFiles.FAILURE.name());
            current.setRequestStatus(ve);
            ValidEntry ve1 = new ValidEntry();
            ve1.setVeId(UploadFiles.FAILURE.value());
            ve1.setVeName(UploadFiles.FAILURE.name());
            current.setResponseStatus(ve1);
            getFacade().create(current);
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("FileUploadFailed"));
            logger.log("failed to upload File [" + current.getFilePath() + "]", "ERROR");
        }
        return prepareEndList();
    }

    public void openResponseFile() {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        current = (Files) getItems().getRowData();
        logger.log("Now trying to open Response File [" + current.getDownloadFilePath() + "]", "INFO");
        String responseFile = current.getDownloadFilePath();
        String extractedFile = CompressFiles.unZip(responseFile, getLogsFacade());
        BrowserControl browserControl = new BrowserControl(getLogsFacade());
        browserControl.openFile(extractedFile);
    }

    public void openAddResponseFile() {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        current = (Files) getAddItems().getRowData();
        logger.log("Now trying to open Response File [" + current.getDownloadFilePath() + "]", "INFO");
        String responseFile = current.getDownloadFilePath();
        String extractedFile = CompressFiles.unZip(responseFile, getLogsFacade());
        BrowserControl browserControl = new BrowserControl(getLogsFacade());
        browserControl.openFile(extractedFile);
    }

    public void openReviseResponseFile() {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        current = (Files) getReviseItems().getRowData();
        logger.log("Now trying to open Response File [" + current.getDownloadFilePath() + "]", "INFO");
        String responseFile = current.getDownloadFilePath();
        String extractedFile = CompressFiles.unZip(responseFile, getLogsFacade());
        BrowserControl browserControl = new BrowserControl(getLogsFacade());
        browserControl.openFile(extractedFile);
    }

    public void openEndResponseFile() {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        current = (Files) getEndItems().getRowData();
        logger.log("Now trying to open Response File [" + current.getDownloadFilePath() + "]", "INFO");
        String responseFile = current.getDownloadFilePath();
        String extractedFile = CompressFiles.unZip(responseFile, getLogsFacade());
        BrowserControl browserControl = new BrowserControl(getLogsFacade());
        browserControl.openFile(extractedFile);
    }

    /**
     * Creating Download Folder for the Response File
     *
     * @param parentPath
     *
     * @return String represents the Folder Path
     */
    public String createDownloadFolder(String parentPath) {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        logger.log("Parent Download Path: [" + parentPath + "]", "INFO");
        try {
            Date d = new Date();
            TimeZone timeZone = TimeZone.getTimeZone("Europe/Copenhagen");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            sdf.setTimeZone(timeZone);
            String dateString = sdf.format(d);
            String[] temp = dateString.split(" ");
            File downloadPathlocal = new File(parentPath + File.separator + temp[0]);
            this.setTimeNow(temp[1]);
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

    /**
     *
     * This Method gets Date and time of the Passed File Name
     *
     * @param filePath
     *
     * @return String Array of Date and Time...
     */
    public String[] fileDateTime(String filePath) {
        LoggingManager logger = new LoggingManager(getLogsFacade());
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
            logger.log("failed to retreive modified Date and Time of File [" + filePath + "] due to this Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            return new String[]{"1970-12-31", "========="};
        }
    }

    /**
     * Upload Selected Files from any Type to eBay...
     *
     * @return String page Name.
     */
    public String uploadSelected() {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        try {
            logger.log("Now trying to upload Selected Files", "INFO");
            List<Files> checkedFiles = new ArrayList<Files>();
            Iterator<Files> itr = items.iterator();
            while (itr.hasNext()) {
                Files file = itr.next();
                if (getChecked().get(file.getfId())) {
                    checkedFiles.add(file);
                }
            }
            logger.log("Number of Selected Files: [" + checkedFiles.size() + "]", "INFO");
            checked.clear();
            if (checkedFiles.isEmpty()) {
                logger.log("No Files Selected to be uploaded ", "WARNING");
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("NoFilesSelected"));
                return "List?faces-redirect=true";
            } else {
                EBayUploadThread thread = new EBayUploadThread(getFacade(), getLogsFacade());
                thread.setFiles(checkedFiles);
                thread.setParentPath(this.getDownloadPath());
                thread.start();
                while (true) {
                    if (!thread.isAlive()) {
                        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FilesUploadedSuccessfully"));
                        return prepareAddList();
                    }
                }
            }
        } catch (Exception e) {
            logger.log("Upload Operation failed due to This Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("UploadingError"));
            return prepareList();
        }
    }

    /**
     * Upload Selected Add Files to eBay...
     *
     * @return String page Name.
     */
    public String uploadSelectedAdd() {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        try {
            logger.log("Now trying to upload Selected Files", "INFO");
            List<Files> checkedFiles = new ArrayList<Files>();
            Iterator<Files> itr = addItems.iterator();
            while (itr.hasNext()) {
                Files file = itr.next();
                if (getChecked().get(file.getfId())) {
                    checkedFiles.add(file);
                }
            }
            logger.log("Number of Selected Files: [" + checkedFiles.size() + "]", "INFO");
            checked.clear();
            if (checkedFiles.isEmpty()) {
                logger.log("No Files Selected to be uploaded ", "WARNING");
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("NoFilesSelected"));
                return prepareAddList();
            } else {
                EBayUploadThread thread = new EBayUploadThread(getFacade(), getLogsFacade());
                thread.setFiles(checkedFiles);
                thread.setParentPath(this.getDownloadPath());
                thread.start();
                while (true) {
                    if (!thread.isAlive()) {
                        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FilesUploadedSuccessfully"));
                        return prepareAddList();
                    }
                }
            }
        } catch (Exception e) {
            logger.log("Upload Operation failed due to This Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("UploadingError"));
            return prepareAddList();
        }
    }

    /**
     * Upload Selected Revise Files to eBay...
     *
     * @return String page Name.
     */
    public String uploadSelectedRevise() {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        try {
            logger.log("Now trying to upload Selected Files", "INFO");
            List<Files> checkedFiles = new ArrayList<Files>();
            Iterator<Files> itr = reviseItems.iterator();
            while (itr.hasNext()) {
                Files file = itr.next();
                if (getChecked().get(file.getfId())) {
                    checkedFiles.add(file);
                }
            }
            logger.log("Number of Selected Files: [" + checkedFiles.size() + "]", "INFO");
            checked.clear();
            if (checkedFiles.isEmpty()) {
                logger.log("No Files Selected to be uploaded ", "WARNING");
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("NoFilesSelected"));
                return prepareReviseList();
            } else {
                EBayUploadThread thread = new EBayUploadThread(getFacade(), getLogsFacade());
                thread.setFiles(checkedFiles);
                thread.setParentPath(this.getDownloadPath());
                thread.start();
                while (true) {
                    if (!thread.isAlive()) {
                        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FilesUploadedSuccessfully"));
                        return prepareAddList();
                    }
                }
            }
        } catch (Exception e) {
            logger.log("Upload Operation failed due to This Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("UploadingError"));
            return prepareReviseList();
        }
    }

    /**
     * Upload Selected End Files to eBay...
     *
     * @return String page Name.
     */
    public String uploadSelectedEnd() {
        LoggingManager logger = new LoggingManager(getLogsFacade());
        try {
            logger.log("Now trying to upload Selected Files", "INFO");
            List<Files> checkedFiles = new ArrayList<Files>();
            Iterator<Files> itr = endItems.iterator();
            while (itr.hasNext()) {
                Files file = itr.next();
                if (getChecked().get(file.getfId())) {
                    checkedFiles.add(file);
                }
            }
            logger.log("Number of Selected Files: [" + checkedFiles.size() + "]", "INFO");
            checked.clear();
            if (checkedFiles.isEmpty()) {
                logger.log("No Files Selected to be uploaded ", "WARNING");
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("NoFilesSelected"));
                return prepareEndList();
            } else {
                EBayUploadThread thread = new EBayUploadThread(getFacade(), getLogsFacade());
                thread.setFiles(checkedFiles);
                thread.setParentPath(this.getDownloadPath());
                thread.start();
                while (true) {
                    if (!thread.isAlive()) {
                        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FilesUploadedSuccessfully"));
                        return prepareAddList();
                    }
                }
            }
        } catch (Exception e) {
            logger.log("Upload Operation failed due to This Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("UploadingError"));
            return prepareEndList();
        }
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
}
