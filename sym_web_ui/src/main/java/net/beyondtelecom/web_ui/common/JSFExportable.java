package net.beyondtelecom.web_ui.common;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import net.beyondtelecom.gopay.bt_common.configuration.Configuration;
import net.beyondtelecom.gopay.bt_common.mail.EMailer;
import org.apache.poi.ss.usermodel.Workbook;

import javax.faces.application.FacesMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.*;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isValidEmail;
import static net.beyondtelecom.gopay.bt_common.configuration.ThreadPoolManager.schedule;
import static net.beyondtelecom.gopay.bt_common.mail.EMailer.createMultipartMessage;
import static net.beyondtelecom.gopay.bt_common.utilities.SymTransformer.dateToString;

/***************************************************************************
 *                                                                         *
 * Created:     17 / 09 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public abstract class JSFExportable {

    private static final Logger logger = Logger.getLogger(JSFExportable.class.getSimpleName());
    private static final String MIME_TYPE_EXCEL = "application/vnd.ms-excel";
    private static final String MIME_TYPE_PDF = "application/pdf";
    private String reportEmailRecipient;
    private FacesMessage reportEmailResponse;
    private boolean sendReportEmail = false;
    private ByteArrayOutputStream fileData;
    private PdfWriter pdfWriter;

    public abstract String getTableDescription();

    public String getReportEmailRecipient() { return reportEmailRecipient; }

    public void setReportEmailRecipient(String reportEmailRecipient) { this.reportEmailRecipient = reportEmailRecipient; }

    public void showExportMessage() {
        if (reportEmailResponse != null) { getCurrentInstance().addMessage(null, reportEmailResponse); }
    }

    public boolean isSendReportEmail() { return sendReportEmail; }

    public void setSendReportEmail(boolean sendReportEmail) { this.sendReportEmail = sendReportEmail; }

    public void postProcessXLS(Object document)
    {
        reportEmailResponse = null;
        logger.info("Exporting XLS document for email");
        if (!isValidEmail(reportEmailRecipient)) {
            logger.severe("Email failed. Invalid email address specified: " + reportEmailRecipient);

            reportEmailResponse = new FacesMessage(SEVERITY_WARN,
                "Cannot Email report to recipient '" + reportEmailRecipient + "'! Invalid email specified.",
                "Cannot Email report to recipient '" + reportEmailRecipient + "'! Invalid email specified.");
            return;
        }

        Workbook wb = (Workbook) document;
        fileData = new ByteArrayOutputStream();
        try {
            logger.info("Exporting document to output stream");
            wb.write(fileData);

            ByteArrayDataSource emailData = new ByteArrayDataSource(fileData.toByteArray(), MIME_TYPE_EXCEL);

            logger.info("Creating multipart message");
            String reportName = getTableDescription() + " - " + dateToString(new Date()) + ".xls";
            MimeMultipart multipartMessage = createMultipartMessage(
                emailData,"BTControlCenter " + getTableDescription() + " Report", reportName
            );

            reportEmailResponse = new FacesMessage(SEVERITY_INFO,
                format("Emailing report %s to %s", reportName, reportEmailRecipient),
                format("Emailing report %s to %s", reportName, reportEmailRecipient));

            logger.info("Sending multipart email");
            schedule(new EMailer(new String[]{reportEmailRecipient}, "BTControlCenterReport",
                    Configuration.getProperty("SupportEmail"), "beyondtelecoms", multipartMessage));
        }
        catch (Exception e) {
            e.printStackTrace();
            reportEmailResponse = new FacesMessage(SEVERITY_FATAL,
                    "Failed to email report to recipient '" + reportEmailRecipient + "': " + e.getMessage(),
                    "Failed to email report to recipient '" + reportEmailRecipient + "': " + e.getMessage());
        }
        finally {
            try { fileData.close(); }
            catch (IOException ignored) {}
        }
    }

    public void preProcessPDF(Object document) throws IOException, DocumentException {
        Document pdfDocument = (Document) document;
        fileData = new ByteArrayOutputStream();
        pdfWriter = PdfWriter.getInstance(pdfDocument, fileData);
        pdfDocument.open();
        pdfDocument.setPageSize(PageSize.A4);
    }

    public void postProcessPDF(Object document)
    {
        reportEmailResponse = null;
        logger.info("Exporting PDF document for email");
        if (!isValidEmail(reportEmailRecipient)) {
            logger.severe("Email failed. Invalid email address specified: " + reportEmailRecipient);
            reportEmailResponse = new FacesMessage(SEVERITY_WARN,
                "Cannot Email report to recipient '" + reportEmailRecipient + "'! Invalid email specified.",
                "Cannot Email report to recipient '" + reportEmailRecipient + "'! Invalid email specified.");
            return;
        }

        try {

            logger.info("Exporting document to output stream");
            byte[] pdfData = pdfWriter.getDirectContent().getInternalBuffer().toByteArray();
            ByteArrayDataSource emailData = new ByteArrayDataSource(pdfData, MIME_TYPE_PDF);

            logger.info("Creating multipart message");
            String reportName = getTableDescription() + " - " + dateToString(new Date()) + ".pdf";
            MimeMultipart multipartMessage = createMultipartMessage(
                emailData, "BTControlCenter " + getTableDescription() + " Report", reportName
            );

            reportEmailResponse = new FacesMessage(SEVERITY_INFO,
                    format("Emailing report %s to %s", reportName, reportEmailRecipient),
                    format("Emailing report %s to %s", reportName, reportEmailRecipient));


            logger.info("Sending multipart email");
            schedule(new EMailer(new String[]{reportEmailRecipient}, "BTControlCenterReport",
                    Configuration.getProperty("SupportEmail"), "beyondtelecoms", multipartMessage));
        }
        catch (Exception e) {
            reportEmailResponse = new FacesMessage(SEVERITY_FATAL,
                "Failed to email report to recipient '" + reportEmailRecipient + "': " + e.getMessage(),
                "Failed to email report to recipient '" + reportEmailRecipient + "': " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try { fileData.close(); }
            catch (IOException ignored) {}
        }
    }
}
