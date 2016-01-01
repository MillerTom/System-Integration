package controllers.util;

import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author tmiller tmiller
 *
 */
public class SendMail {

    public void send(String to, String subject, String body) {
        SendMail_New(to, subject, body);
    }

    public void SendMail_New(String to, String subject, String body) {
        try {
            String is = this.getClass().getClassLoader().getResource("configfile.xml").getPath();
            XMLParser xmlparser = new XMLParser(is);

            final String username = xmlparser.getSupportMail();
            final String password = xmlparser.getSupportPassword();
            final String server = xmlparser.getServer();
            final String port = xmlparser.getServerPort();


            Properties props = System.getProperties();
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(xmlparser.getSupportMail()));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, true));
            msg.setSubject(subject);
            msg.setText(body);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}