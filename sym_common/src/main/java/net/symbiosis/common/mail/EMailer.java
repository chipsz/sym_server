package net.symbiosis.common.mail;

import net.symbiosis.common.configuration.Configuration;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class EMailer implements Runnable {

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    public static final String DEFAULT_CONTENT_TYPE = "text/plain";
    public static final String CONTENT_TYPE_HTML = "text/html";
    String host = "null";
    String recipients[];
    String subject;
    String message;
    String from;
    String contentType;
    String imageLocation;
    String contentID;
    boolean isMultipart = false;
    String attachmentFilenames[] = null;
    MimeMultipart multipart;

    public EMailer(String recipients[], String subject, String message, String from, String host, String contentType) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.from = from;
        this.host = host;
        this.contentType = contentType;
    }

    /**
     * This constructor creates a HTML email with a logo and attachments
     *
     * @param recipients
     * @param subject
     * @param message
     * @param from
     * @param host
     * @param contentType
     * @param imageLocation
     * @param contentID
     * @param attachmentFilenames
     */
    public EMailer(String recipients[], String subject, String message, String from, String host, String contentType, String imageLocation, String contentID, String attachmentFilenames[]) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.from = from;
        this.host = host;
        this.contentType = contentType;
        this.imageLocation = imageLocation;
        this.contentID = contentID;
        this.attachmentFilenames = attachmentFilenames;
        isMultipart = true;
    }

    /**
     * Creates HTML email with logo but no attachment
     *
     * @param recipients
     * @param subject
     * @param message
     * @param from
     * @param host
     * @param contentType
     * @param imageLocation
     * @param contentID
     */
    public EMailer(String recipients[], String subject, String message, String from, String host, String contentType, String imageLocation, String contentID) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.from = from;
        this.host = host;
        this.contentType = contentType;
        this.imageLocation = imageLocation;
        this.contentID = contentID;
        isMultipart = true;
    }

    public EMailer(String recipients[], String subject, String from, String host, MimeMultipart multipart) {
        this.recipients = recipients;
        this.subject = subject;
        this.from = from;
        this.host = host;
        this.multipart = multipart;
        isMultipart = true;
    }

    public EMailer(String recipients[], String subject, String message) {
        this(recipients, subject, message, DEFAULT_CONTENT_TYPE);
    }

    public EMailer(String recipients[], String subject, String message, String contentType) {
        //get alarm email address
        String from = Configuration.getProperty("mail", "mail.smtp.submitter");
        String host = Configuration.getProperty("mail", "mail.smtp.host");

        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.from = from;
        this.host = host;
        this.contentType = contentType;
    }

    public EMailer(String recipients[], String subject, String message, String contentType, String imageLocation) {
        //get alarm email address
        String from = Configuration.getProperty("mail", "mail.smtp.submitter");
        String host = Configuration.getProperty("mail", "mail.smtp.host");

        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.from = from;
        this.host = host;
        this.contentType = contentType;
        this.imageLocation = imageLocation;
        isMultipart = true;
    }

    //Send the email
    public void run() {
        try {
            //Set the host smtp address
            Properties props = Configuration.resourceBundleToProperties(ResourceBundle.getBundle("properties/mail"));

            // create some properties and get the default Session
            Authenticator auth = new PopupAuthenticator(
                    props.getProperty("mail.smtp.submitter"),
                    props.getProperty("mail.smtp.submitter.password"));

            Session session = Session.getInstance(props, auth);

            // create a message
            Message msg = new MimeMessage(session);

            // set the from and to address
            InternetAddress addressFrom = new InternetAddress(from);
            msg.setFrom(addressFrom);

            InternetAddress[] addressTo = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                addressTo[i] = new InternetAddress(recipients[i]);
                addressTo[i].validate();
            }

            String addresses = "{";
            for (int c = 0; c < addressTo.length; c++) {
                addresses += addressTo[c].getAddress();
                if (c + 1 != addressTo.length)
                    addresses += ',';
            }
            addresses += "}";

            logger.info("Sending email with subject: " + subject + " to addresses " + addresses + " using host: " + host);

            msg.setRecipients(Message.RecipientType.TO, addressTo);
            msg.setSubject(subject);

            if (isMultipart)
                if (multipart == null)
                    msg = setMultiPartMessage(msg);
                else
                    msg.setContent(multipart);
            else
                msg.setContent(message, contentType);

            Transport.send(msg);

            String allRecipients = "";
            for (String recipient : recipients) {
                allRecipients += recipient + ",";
            }

            logger.info("Email with subject: " + subject + " sent to: {" +
                    allRecipients.substring(0, allRecipients.length() - 1) + "}");
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.severe("An error occurred sending mail. Message: " + ex.getMessage());
        }
    }

    public Message setMultiPartMessage(Message msg) {
        try {
            // This HTML mail has to 2 parts, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // First add the html part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, contentType);
            multipart.addBodyPart(messageBodyPart);

            // Add the image part
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(imageLocation);
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", contentID);
            multipart.addBodyPart(messageBodyPart);

            if (attachmentFilenames != null)
                for (int j = 0; j < attachmentFilenames.length; j++) {
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachmentFilenames[j]);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    String filename = attachmentFilenames[j].substring(attachmentFilenames[j].lastIndexOf(File.separatorChar) + 1);
                    messageBodyPart.setFileName(filename);
                    multipart.addBodyPart(messageBodyPart);
                }

            msg.setContent(multipart);
            return msg;
        } catch (Exception ex) {
            ex.printStackTrace();
            return msg;
        }
    }

    public static MimeMultipart createMultipartMessage(ByteArrayDataSource dataSource, String message, String fileName) {
        try {
            MimeMultipart multipart = new MimeMultipart("related");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html");
            multipart.addBodyPart(messageBodyPart);

            // Add the image part
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(dataSource));
            messageBodyPart.setHeader("Content-ID", fileName);
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            return multipart;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static MimeMultipart createMultipartMessage(byte[] attachment, String message, String fileName, String attachmentFileType) {
        try {
            MimeMultipart multipart = new MimeMultipart("related");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html");
            multipart.addBodyPart(messageBodyPart);


            //Add attachment
            messageBodyPart = new MimeBodyPart();
            DataSource att = new javax.mail.util.ByteArrayDataSource(attachment, attachmentFileType);
            messageBodyPart.setDataHandler(new DataHandler(att));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);


            // Add the image part
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(fileName);
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", fileName);
            multipart.addBodyPart(messageBodyPart);

            return multipart;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public class PopupAuthenticator extends Authenticator {
        String username;
        String password;

        public PopupAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }
}
