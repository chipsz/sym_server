/**
 * ERSWSTopupServiceImplServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT)
 */
package net.symbiosis.core.integration.voucher.glo;


/**
 *  ERSWSTopupServiceImplServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class ERSWSTopupServiceImplServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public ERSWSTopupServiceImplServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public ERSWSTopupServiceImplServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for customOperation method
     * override this method for handling normal response from customOperation operation
     */
    public void receiveResultcustomOperation(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.CustomOperationResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from customOperation operation
     */
    public void receiveErrorcustomOperation(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for registerSubReseller method
     * override this method for handling normal response from registerSubReseller operation
     */
    public void receiveResultregisterSubReseller(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.RegisterSubResellerResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from registerSubReseller operation
     */
    public void receiveErrorregisterSubReseller(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for deLinkSubReseller method
     * override this method for handling normal response from deLinkSubReseller operation
     */
    public void receiveResultdeLinkSubReseller(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.DeLinkSubResellerResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from deLinkSubReseller operation
     */
    public void receiveErrordeLinkSubReseller(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for commitTopup method
     * override this method for handling normal response from commitTopup operation
     */
    public void receiveResultcommitTopup(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.CommitTopupResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from commitTopup operation
     */
    public void receiveErrorcommitTopup(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for cancelPurchase method
     * override this method for handling normal response from cancelPurchase operation
     */
    public void receiveResultcancelPurchase(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.CancelPurchaseResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from cancelPurchase operation
     */
    public void receiveErrorcancelPurchase(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for unFreezeReseller method
     * override this method for handling normal response from unFreezeReseller operation
     */
    public void receiveResultunFreezeReseller(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.UnFreezeResellerResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from unFreezeReseller operation
     */
    public void receiveErrorunFreezeReseller(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for freezeReseller method
     * override this method for handling normal response from freezeReseller operation
     */
    public void receiveResultfreezeReseller(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.FreezeResellerResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from freezeReseller operation
     */
    public void receiveErrorfreezeReseller(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getTransactionStatus method
     * override this method for handling normal response from getTransactionStatus operation
     */
    public void receiveResultgetTransactionStatus(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.GetTransactionStatusResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getTransactionStatus operation
     */
    public void receiveErrorgetTransactionStatus(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for requestPrincipalInformation method
     * override this method for handling normal response from requestPrincipalInformation operation
     */
    public void receiveResultrequestPrincipalInformation(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.RequestPrincipalInformationResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from requestPrincipalInformation operation
     */
    public void receiveErrorrequestPrincipalInformation(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for requestTopup method
     * override this method for handling normal response from requestTopup operation
     */
    public void receiveResultrequestTopup(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.RequestTopupResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from requestTopup operation
     */
    public void receiveErrorrequestTopup(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for requestTransfer method
     * override this method for handling normal response from requestTransfer operation
     */
    public void receiveResultrequestTransfer(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.RequestTransferResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from requestTransfer operation
     */
    public void receiveErrorrequestTransfer(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for redeemVoucher method
     * override this method for handling normal response from redeemVoucher operation
     */
    public void receiveResultredeemVoucher(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.RedeemVoucherResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from redeemVoucher operation
     */
    public void receiveErrorredeemVoucher(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for linkSubReseller method
     * override this method for handling normal response from linkSubReseller operation
     */
    public void receiveResultlinkSubReseller(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.LinkSubResellerResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from linkSubReseller operation
     */
    public void receiveErrorlinkSubReseller(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for unBlockReseller method
     * override this method for handling normal response from unBlockReseller operation
     */
    public void receiveResultunBlockReseller(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.UnBlockResellerResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from unBlockReseller operation
     */
    public void receiveErrorunBlockReseller(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getVouchersDenomination method
     * override this method for handling normal response from getVouchersDenomination operation
     */
    public void receiveResultgetVouchersDenomination(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.GetVouchersDenominationResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getVouchersDenomination operation
     */
    public void receiveErrorgetVouchersDenomination(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for requestPurchase method
     * override this method for handling normal response from requestPurchase operation
     */
    public void receiveResultrequestPurchase(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.RequestPurchaseResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from requestPurchase operation
     */
    public void receiveErrorrequestPurchase(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for activateReseller method
     * override this method for handling normal response from activateReseller operation
     */
    public void receiveResultactivateReseller(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.ActivateResellerResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from activateReseller operation
     */
    public void receiveErroractivateReseller(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for executeReport method
     * override this method for handling normal response from executeReport operation
     */
    public void receiveResultexecuteReport(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.ExecuteReportResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from executeReport operation
     */
    public void receiveErrorexecuteReport(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for blockReseller method
     * override this method for handling normal response from blockReseller operation
     */
    public void receiveResultblockReseller(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.BlockResellerResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from blockReseller operation
     */
    public void receiveErrorblockReseller(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for changePrincipalPassword method
     * override this method for handling normal response from changePrincipalPassword operation
     */
    public void receiveResultchangePrincipalPassword(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.ChangePrincipalPasswordResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from changePrincipalPassword operation
     */
    public void receiveErrorchangePrincipalPassword(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for cancelTopup method
     * override this method for handling normal response from cancelTopup operation
     */
    public void receiveResultcancelTopup(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.CancelTopupResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from cancelTopup operation
     */
    public void receiveErrorcancelTopup(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for updateReseller method
     * override this method for handling normal response from updateReseller operation
     */
    public void receiveResultupdateReseller(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.UpdateResellerResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from updateReseller operation
     */
    public void receiveErrorupdateReseller(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for deActivateReseller method
     * override this method for handling normal response from deActivateReseller operation
     */
    public void receiveResultdeActivateReseller(
        net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.DeActivateResellerResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from deActivateReseller operation
     */
    public void receiveErrordeActivateReseller(java.lang.Exception e) {
    }
}
