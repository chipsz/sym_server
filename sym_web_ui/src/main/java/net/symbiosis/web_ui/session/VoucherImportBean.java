package net.symbiosis.web_ui.session;

import net.symbiosis.core.contract.SymImportBatchList;
import net.symbiosis.core.contract.symbiosis.SymImportBatch;
import net.symbiosis.core.service.VoucherProcessor;
import net.symbiosis.persistence.entity.complex_type.log.sym_import_batch;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;
import net.symbiosis.web_ui.common.JSFLoggable;
import net.symbiosis.web_ui.common.JSFReportable;
import net.symbiosis.web_ui.common.UpdateOptions;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static javax.faces.application.FacesMessage.SEVERITY_FATAL;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.symbiosis.core_lib.enumeration.SymEventType.VOUCHER_IMPORT;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.GENERAL_ERROR;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     28 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class VoucherImportBean extends JSFReportable implements JSFLoggable {

    private static final Logger logger = Logger.getLogger(VoucherImportBean.class.getSimpleName());
    private static final String TABLE_NAME = "Voucher Imports";
    private final SessionBean sessionBean;
    private final UpdateOptions updateOptions;
    private List<sym_import_batch> voucherImports = null;

    private sym_voucher_provider selectedVoucherProvider;
    private Integer voucherValue;

    private final VoucherProcessor voucherProcessor;

    @Autowired
    public VoucherImportBean(SessionBean sessionBean, VoucherProcessor voucherProcessor, UpdateOptions updateOptions) {
        this.sessionBean = sessionBean;
        this.voucherProcessor = voucherProcessor;
        this.updateOptions = updateOptions;
    }

    public void initializeImports() {
        voucherImports = getEntityManagerRepo().findAll(sym_import_batch.class, true);
    }

    public void handleFileUpload(FileUploadEvent event) {
        Date requestTime = new Date();
        logger.info("Uploading file for VP " + selectedVoucherProvider.getName() + " with amount " + voucherValue);

        try {
            SymImportBatchList uploadResponse = voucherProcessor.loadVoucherProviderVouchers(selectedVoucherProvider.getId(),
                event.getFile().getInputstream(), event.getFile().getFileName(), event.getFile().getContentType(),
                voucherValue == null ? null : new BigDecimal(voucherValue));

            if (uploadResponse.getSymResponse().getResponse().equals(SUCCESS)) {
                SymImportBatch importBatch = uploadResponse.getImportBatchData().get(0);
                String result = event.getFile().getFileName() + " uploaded successfully.\r\n" + "\r\n" +
                    "Voucher Provider: " + importBatch.getVoucherProvider() + "\r\n" + "\r\n" +
                    "Service Provider: " + importBatch.getServiceProvider() + "\r\n" + "\r\n" +
                    "Provider BatchID: " + importBatch.getVoucherProviderBatchId() + "\r\n" + "\r\n" +
                    "Voucher Value: " + importBatch.getVoucherValue() + "\r\n" + "\r\n" +
                    "Voucher Type: " + importBatch.getVoucherType() + "\r\n" +"\r\n" +
                    "Num Vouchers: " + importBatch.getNumberOfVouchers() + "\r\n" + "\r\n";
                voucherImports = getEntityManagerRepo().findAll(sym_import_batch.class, true);

                getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                    "File Upload Successful", result));

                log(fromEnum(VOUCHER_IMPORT), sessionBean.getSymbiosisAuthUser(),
                        fromEnum(SUCCESS), requestTime, new Date(),
                        format("UPLOAD VOUCHER FILE %s (%s %s %s %s)",
                            event.getFile().getFileName(),
                            importBatch.getVoucherProvider(),
                            importBatch.getVoucherValue(),
                            importBatch.getServiceProvider(),
                            importBatch.getVoucherType()),
                        result);
                voucherValue = null;
            }
            else {
                getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_FATAL,
                    "File Upload Failed", uploadResponse.getSymResponse().getResponse_message()));
                log(fromEnum(VOUCHER_IMPORT), sessionBean.getSymbiosisAuthUser(),
                    fromEnum(uploadResponse.getSymResponse().getResponse()),
                    requestTime, new Date(), format("UPLOAD VOUCHER FILE %s", event.getFile().getFileName()),
                    uploadResponse.getSymResponse().getResponse_message());
            }
        }
        catch (IOException e) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_FATAL,
                "File Upload Failed", "Upload failed! " + e.getMessage()));
            log(fromEnum(VOUCHER_IMPORT), sessionBean.getSymbiosisAuthUser(),
                    fromEnum(GENERAL_ERROR),
                    requestTime, new Date(), format("UPLOAD VOUCHER FILE %s", event.getFile().getFileName()),
                    GENERAL_ERROR.getMessage());
            e.printStackTrace();
        }
    }

    public List<sym_import_batch> getVoucherImports() { initializeImports(); return voucherImports; }

    public void setVoucherImports(List<sym_import_batch> voucherImports) { this.voucherImports = voucherImports; }

    public void setSelectedVoucherProvider(sym_voucher_provider selectedVoucherProvider) {
        this.selectedVoucherProvider = selectedVoucherProvider;
    }

    public sym_voucher_provider getSelectedVoucherProvider() {
        if (selectedVoucherProvider == null) {
            selectedVoucherProvider = updateOptions.getVoucherProviders().get(0);
        }
        return selectedVoucherProvider;
    }

    public void changeVoucherProvider(AjaxBehaviorEvent event) {
        selectedVoucherProvider = (sym_voucher_provider)((UIInput) event.getSource()).getValue();
    }

    public Integer getVoucherValue() { return voucherValue; }

    public void setVoucherValue(Integer voucherValue) { this.voucherValue = voucherValue; }

    public void updateAmount(AjaxBehaviorEvent event) {
        Object value = ((UIInput) event.getSource()).getValue();
        this.voucherValue = (value == null || value.toString().equals("")) ? null : parseInt(valueOf(value));
    }

    public Double getVoucherTotalAmount(Long voucherImportBatchId) {

        if (voucherImportBatchId == null) {
            return null;
        }

        sym_import_batch importBatch = getEntityManagerRepo().findById(sym_import_batch.class, voucherImportBatchId);

        return importBatch.getVoucher().getVoucher_value().multiply(
                new BigDecimal(importBatch.getNumber_of_vouchers().intValue())).doubleValue();


    }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}
