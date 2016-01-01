/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

import daos.LogsFacade;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import pojos.Logs;

/**
 *
 * @author tmiller
 */
public class LoggingManager implements Serializable {

    public static LogsFacade logFacade;

    public LoggingManager(LogsFacade facade) {
        logFacade = facade;
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
     * Get user Name from the Session...
     *
     * @return String
     */
    public String getUserNameFromSession() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            String userName = session.getValue("userName").toString();
            return userName;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * logging any general action occurs like for example user login , logout
     *
     * @param message
     * @param messageType
     */
    public void log(String message, String messageType) {
//        System.out.println("Log Facade :" + logFacade);
        Logs log = new Logs();

        if (getUserNameFromSession() != null) {
            log.setUserName(getUserNameFromSession());
        } else {
            log.setUserName("System");
        }
        log.setMessage(message);
        log.setMessageType(messageType);

        String[] temp = this.formatDateTime();
        log.setActionDate(new Date(temp[0]));
        log.setActionTime(temp[1]);
        try {
            logFacade.create(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
