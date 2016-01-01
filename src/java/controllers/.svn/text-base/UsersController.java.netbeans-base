package controllers;

import controllers.util.*;
import daos.LogsFacade;
import daos.UsersFacade;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
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
import pojos.Users;

@ManagedBean(name = "usersController")
@SessionScoped
public class UsersController implements Serializable {

    private Users current;
    private DataModel items = null;
    @EJB
    private daos.UsersFacade ejbFacade;
    @EJB
    private daos.LogsFacade ejbLogsFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String userName;
    private String password;
    private String userMail;
    private SecureRandom random;

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UsersController() {
    }

    public Users getSelected() {
        if (current == null) {
            current = new Users();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UsersFacade getFacade() {
        return ejbFacade;
    }

    private LogsFacade getLogsFacade() {
        return ejbLogsFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List?faces-redirect=true";
    }

    public String prepareView() {
        current = (Users) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View?faces-redirect=true";
    }

    public String prepareCreate() {
        current = new Users();
        selectedItemIndex = -1;
        return "Create?faces-redirect=true";
    }

    /**
     * Creating New User ...
     *
     * @return String represents Page Name
     */
    public String create() {
        LoggingManager logger= new LoggingManager(getLogsFacade());
        try {
            String encryptedPassword = this.encryptPass(current.getPassword());
            current.setPassword(encryptedPassword);
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsersCreated"));
            logger.log("New User [" + current.getUserName() + "] created Successfully", "INFO");
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            logger.log("Operation of Creating New User [" + current.getUserName() + "] failed, due to that Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            return null;
        }
    }

    public String prepareEdit() {
        current = (Users) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit?faces-redirect=true";
    }

    public String update() {
        LoggingManager logger= new LoggingManager(getLogsFacade());
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try {
            String newPass = current.getPassword();
            String oldPass = session.getValue("userPass").toString();
            if (oldPass.equalsIgnoreCase(newPass)) {
                getFacade().edit(current);
            } else {
                String encryptedPass = this.encryptPass(newPass);
                current.setPassword(encryptedPass);
                getFacade().edit(current);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsersUpdated"));
            logger.log("updating Data of User [" + current.getUserName() + "] Completed Successfully.", "INFO");
            return "View?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
           logger.log("updating Data of User [" + current.getUserName() + "] failed, due to That Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            return null;
        }
    }

    public String destroy() {
        current = (Users) getItems().getRowData();
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
            // all items were removed - go back to list
            recreateModel();
            return "List?faces-redirect=true";
        }
    }

    private void performDestroy() {
        LoggingManager logger= new LoggingManager(getLogsFacade());
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsersDestroyd"));
            logger.log("User [" + current.getUserName() + "] deleted Successfully", "INFO");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            logger.log("Operation of deleting User [" + current.getUserName() + "] failed , due to that Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
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

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
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

    @FacesConverter(forClass = Users.class)
    public static class UsersControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsersController controller = (UsersController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usersController");
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
            if (object instanceof Users) {
                Users o = (Users) object;
                return getStringKey(o.getUId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UsersController.class.getName());
            }
        }
    }

    
    /**
     * Check User Name and Password to Authorize Login...
     *
     * @return String represents Name of the page that will goto it ...
     */
    public String loginCheck() {
        LoggingManager logger= new LoggingManager(getLogsFacade());
        String localUserName = this.userName.trim();
        String localPassword = this.password;

        //check if user entered data into the two fields or no...
        if (!localUserName.isEmpty()) {
            try {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

                Users loginuser = new Users();
                loginuser.setUserName(localUserName);

                //encrypt user password...
                String encryptedPassword = this.encryptPass(localPassword);
                loginuser.setPassword(encryptedPassword);

                Users dbuser = getFacade().findByUserNameAndPassword(loginuser);
                if (dbuser != null) {
                    session.putValue("userName", dbuser.getUserName());
                    session.putValue("userEmail", dbuser.getEmail());
                    session.putValue("userPass", dbuser.getPassword());
                    session.putValue("userType", dbuser.getType().getVeName());
                    session.putValue("userID", dbuser.getUId());
                    logger.log(" user ["+dbuser.getUserName()+"]logged in successfully ", "INFO");
                    return "/home?faces-redirect=true";
                } else {
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("InvalidLogin"));
                    logger.log("User ["+loginuser.getUserName()+"] failed to Login ", "WARNING");
                    return null;
                }
            } catch (Exception e) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("InvalidLogin"));
                logger.log("User ["+localUserName+"] failed to Login due to That Exception: [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
                return "/login?faces-redirect=true";
            }
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("InvalidLogin"));
            return "/login?faces-redirect=true";
        }
    }

    
    /**
     * trying to logging user out and dropping his session
     *
     * @return String represents Login Page Name
     */
    public String logOut() {
        LoggingManager logger= new LoggingManager(getLogsFacade());
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try {
            logger.log("User ["+getUserNameFromSession()+"] Successfully logged out", "INFO");
            session.invalidate();
            return "/login?faces-redirect=true";
        } catch (Exception e) {
            logger.log("User ["+getUserNameFromSession()+"]failed to Log out due to that Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            return null;
        }

    }

    
    /**
     * goto page that user can enter his mail to send him the new password to
     * the entered mail...
     *
     * @return page name that takes the mail from the user...
     */
    public String forgetpassword() {

        return "/forgetpassword?faces-redirect=true";

    }

    
    /**
     * This Method generates new password and
     *
     * @return String represents the result of resetting password operation...
     */
    public String resetPassword() {
        LoggingManager logger= new LoggingManager(getLogsFacade());
        String mail = userMail;

        Users user = getFacade().findByEmail(mail);
        if (user != null) {
            //generate Random password
            String generatedPassword = this.generateRandomPassword();
            try {
                //encrypting password...
                String encryptedPass = this.encryptPass(generatedPassword);
                user.setPassword(encryptedPass);
                logger.log("Password Encryption Operation Completed Successfully ", "INFO");
            } catch (Exception e) {
                logger.log("Password Encryption Operation failed, due to that Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            }
            try {
                //saving new password in Database...
                this.performResetting(user);
                //sending mail with the New password to the User Mail...
                this.sendMail(generatedPassword, mail);
                logger.log("Password of User [" + user.getUserName() + "] has been reset Successfully", "INFO");
                return "/forgetpasswordresult?faces-redirect=true";
            } catch (Exception e) {
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("ErrorWhileResettingPassword"));
                logger.log("User [" + user.getUserName() + "] tried to reset his Password but operation Faild, due to this Exception [" +ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
                return "/forgetpassword?faces-redirect=true";
            }
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("InvalidEmailAddress"));
            return "/forgetpassword?faces-redirect=true";
        }
    }

    
    /**
     * Updating the Employee Password in the Database...
     */
    private void performResetting(Users user) {
        LoggingManager logger= new LoggingManager(getLogsFacade());
        try {
            getFacade().edit(user);
            logger.log("New Password of User [" + user.getUserName() + "] saved Successfully in Database After reset Operation. ", "INFO");
        } catch (Exception e) {
            logger.log("New Password of User [" + user.getUserName() + "] didn't save in the Database , due to that Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
        }
    }

    
    /**
     * Generating new Random Password...
     *
     * @return String represents the New Password
     */
    public String generateRandomPassword() {
        random = new SecureRandom();
        return new BigInteger(40, random).toString(12);
    }

    
    /**
     * Send Confirmation Mail to the Employee with the New Password...
     *
     * @param newPassword
     * @param userMail
     */
    public void sendMail(String newPassword, String userMail) {
        LoggingManager logger= new LoggingManager(getLogsFacade());
//        System.out.println("Sending");
        String to = userMail;
        String subject = "New Password";
        String message = "Dear MR/MS " + userMail + "\n" + "Your New Login Password is [" + newPassword + "]" + "\n" + "Thanks" + "\n" + "Internal System" + "\n" + "El-Kotob.com";
        logger.log("trying to send Mail to [" + to + "] with the New Password.", "INFO");
        SendMail sm = new SendMail();
        sm.send(to, subject, message);
        logger.log("sent Mail successfully to [" + to + "] contains the New Password.", "INFO");
//        System.out.println("Sent.");
    }
    
    
    /**
     * Formatting Date and Time to Specific Formate...
     *
     * @return String[] represents Date and Time
     */
    public String[] formatDateTime() {
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Copenhagen");
        DateFormat datetimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        datetimeFormat.setTimeZone(timeZone);
        Date datetimenow = new Date();
        String datetimeafterformat = datetimeFormat.format(datetimenow);
        String delimeter = " ";
        return datetimeafterformat.split(delimeter);
    }

    
    /**
     * Encrypt Password and return the Encrypted Password
     *
     * @param password
     * @return EncryptedPassword
     */
    public String encryptPass(String password) throws Exception {
        EncryptPassword ep = new EncryptPassword();
        ep.setPassword(password);
        return ep.getPassword();
    }

    
    /**
     * View User Profile
     *
     * @return String represents View Profile Page
     */
    public String viewProfile() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String userEmail = session.getAttribute("userEmail").toString();
        Users usr = getFacade().findByEmail(userEmail);
        current = usr;
        return "/viewprofile?faces-redirect=true";
    }

    
    /**
     * return page that user can use it to edit in his profile...
     *
     * @return String
     */
    public String editProfile() {
        return "/editprofile?faces-redirect=true";
    }

    
    /**
     * Save user updates to his Profile
     *
     * @return String
     */
    public String saveProfile() {
        LoggingManager logger= new LoggingManager(getLogsFacade());
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try {
            String newPass = current.getPassword();
            String oldPass = session.getValue("userPass").toString();
            if (oldPass.equalsIgnoreCase(newPass)) {
                getFacade().edit(current);
            } else {
                String encryptedPass = this.encryptPass(newPass);
                current.setPassword(encryptedPass);
                getFacade().edit(current);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProfileUpdatedSuccessfully"));
            logger.log("User [" + current.getUserName() + "] changed his Profile Data Successfully ", "INFO");
            recreateModel();
            return "/viewprofile?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            logger.log("User [" + current.getUserName() + "] failed to update his Profile Data due to That Exception [" + ExceptionHandler.getStackTraceAsString(e) + "]", "EXCEPTION");
            return null;
        }
    }

    
    /**
     * get user name from Session to be used in different cases later.
     *
     * @return userName
     */
    public String getUserNameFromSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String userName = session.getValue("userName").toString();
        return userName;
    }
}
