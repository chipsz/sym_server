package net.beyondtelecom.gopay.bt_common.configuration;

import net.beyondtelecom.gopay.bt_common.mail.EMailer;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;

import static net.beyondtelecom.gopay.bt_common.configuration.Configuration.getProperties;
import static net.beyondtelecom.gopay.bt_common.configuration.Configuration.getProperty;
import static net.beyondtelecom.gopay.bt_common.configuration.ThreadPoolManager.schedule;
import static net.beyondtelecom.gopay.bt_common.mail.EMailer.DEFAULT_CONTENT_TYPE;

/**
 * Created by photon on 2016/01/01.
 */
public class NetworkUtilities {

	private static Logger logger = Logger.getLogger(NetworkUtilities.class.getSimpleName());

    public static String execReadToString(String execCommand) throws IOException {
        Process proc = Runtime.getRuntime().exec(execCommand);
        try (InputStream stream = proc.getInputStream()) {
            try (Scanner s = new Scanner(stream).useDelimiter("\\A")) {
                return s.hasNext() ? s.next() : "";
            }
        }
    }

    public static String getHostName() {
        try {
            String hostname = execReadToString("hostname").trim();
            logger.info("Got hostname of the system as " + hostname);
            return hostname;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public static String getFQDN() {
        try {
            String hostname = java.net.InetAddress.getLocalHost().getHostName();
            logger.info("Got hostname of the system as " + hostname);
            return hostname;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getEmailFromName() {
        return "noreply-" + getHostName() + "@" + getProperty("DomainName");
    }

	public static void sendEmailAlert(String gopaySystem, String alertSubject, String alertMessage) {
		logger.info("Sending alert email from " + gopaySystem + " with subject: " + alertSubject);
		logger.info(alertMessage);
		schedule(new EMailer(
            getProperties("AlertEmail"), gopaySystem + " alert! " + alertSubject, alertMessage,
                getEmailFromName(), getHostName(), DEFAULT_CONTENT_TYPE));
	}

	public static void sendEmail(String gopaySystem, String recipient, String emailSubject, String emailMessage) {
		logger.info("Sending email from " + gopaySystem + " with subject: " + emailSubject);
		schedule(new EMailer(new String[] {recipient}, emailSubject, emailMessage,
            getEmailFromName(), getHostName(), DEFAULT_CONTENT_TYPE));
	}

	public static void sendEmail(String gopaySystem, String[] recipients, String emailSubject, String emailMessage,
	                             String contentType) {
		logger.info("Sending email from " + gopaySystem + " with subject: " + emailSubject);
		schedule(new EMailer(recipients, emailSubject, emailMessage,
            getEmailFromName(), getHostName(), contentType));
	}
}
