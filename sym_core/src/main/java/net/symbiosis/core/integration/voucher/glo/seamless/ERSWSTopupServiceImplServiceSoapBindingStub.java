package net.symbiosis.core.integration.voucher.glo.seamless;

import org.apache.axis.AxisEngine;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;

import javax.xml.namespace.QName;

import static java.lang.Boolean.FALSE;

public class ERSWSTopupServiceImplServiceSoapBindingStub extends org.apache.axis.client.Stub implements ERSTopupService {
	private java.util.Vector cachedSerClasses = new java.util.Vector();
	private java.util.Vector cachedSerQNames = new java.util.Vector();
	private java.util.Vector cachedSerFactories = new java.util.Vector();
	private java.util.Vector cachedDeserFactories = new java.util.Vector();

	static org.apache.axis.description.OperationDesc[] _operations;

	static {
		_operations = new org.apache.axis.description.OperationDesc[23];
		_initOperationDesc1();
		_initOperationDesc2();
		_initOperationDesc3();
	}

	private static void _initOperationDesc1() {
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("customOperation");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "clientContext"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "customerPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "targetPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "senderAccountSpecifier"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"), PrincipalAccountSpecifier.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "operationId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "operationParameters"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "ersMap"), ErsMap.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSResponse"));
		oper.setReturnClass(ERSWSResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[0] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("registerSubReseller");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "resellerId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "resellerMsisdn"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "resellerType"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "parentResellerId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "firstName"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "lastName"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "defaultPin"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSRegisterSubResellerResponse"));
		oper.setReturnClass(ERSWSRegisterSubResellerResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[1] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("deLinkSubReseller");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "parentResellerId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "principalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSDeLinkSubResellerResponse"));
		oper.setReturnClass(ERSWSDeLinkSubResellerResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[2] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("commitTopup");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "senderPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "topupPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "senderAccountSpecifier"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"), PrincipalAccountSpecifier.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "topupAccountSpecifier"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"), PrincipalAccountSpecifier.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "topupAmount"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "amount"), Amount.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "ersReference"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "reasonCode"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "comment"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "fetchsubscriberBalance"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", ">commitTopupResponse"));
		oper.setReturnClass(CommitTopupResponse.class);
		oper.setReturnQName(new QName("http://external.interfaces.ers.seamless.com/", "commitTopupResponse"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[3] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("getTransactionStatus");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "resellerPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSGetTransactionStatusResponse"));
		oper.setReturnClass(ERSWSGetTransactionStatusResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[4] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("requestPrincipalInformation");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "principalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "nativeReslutCode"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSPrincipalInformationResponse"));
		oper.setReturnClass(ERSWSPrincipalInformationResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[5] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("requestTopup");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "senderPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "topupPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "senderAccountSpecifier"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"), PrincipalAccountSpecifier.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "topupAccountSpecifier"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"), PrincipalAccountSpecifier.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "productId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "amount"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "amount"), Amount.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSTopupResponse"));
		oper.setReturnClass(ERSWSTopupResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[6] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("requestTransfer");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "senderPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "receiverPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "senderAccountSpecifier"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"), PrincipalAccountSpecifier.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "receiverAccountSpecifier"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"), PrincipalAccountSpecifier.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "productId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "amount"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "amount"), Amount.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSTransferResponse"));
		oper.setReturnClass(ERSWSTransferResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[7] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("cancelPurchase");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "cancelPurchaseOrder"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "cancelPurchaseOrderRow"), CancelPurchaseOrderRow[].class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSCancelPurchaseResponse"));
		oper.setReturnClass(ERSWSCancelPurchaseResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[8] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("unFreezeReseller");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "targetReseller"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "targetPrincipal"), TargetPrincipal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSUnFreezeResellerResponse"));
		oper.setReturnClass(ERSWSUnFreezeResellerResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[9] = oper;

	}

	private static void _initOperationDesc2() {
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("freezeReseller");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "targetReseller"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "targetPrincipal"), TargetPrincipal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSFreezeResellerResponse"));
		oper.setReturnClass(ERSWSFreezeResellerResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[10] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("linkSubReseller");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "parentResellerId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "principalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSLinkSubResellerResponse"));
		oper.setReturnClass(ERSWSLinkSubResellerResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[11] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("unBlockReseller");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "targetReseller"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "targetPrincipal"), TargetPrincipal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSUnBlockResellerResponse"));
		oper.setReturnClass(ERSWSUnBlockResellerResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[12] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("getVouchersDenomination");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSGetVouchersDenominationResponse"));
		oper.setReturnClass(ERSWSGetVouchersDenominationResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[13] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("requestPurchase");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "senderPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "receiverPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "senderAccountSpecifier"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"), PrincipalAccountSpecifier.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "receiverAccountSpecifier"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"), PrincipalAccountSpecifier.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "purchaseOrder"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "purchaseOrderRow"), PurchaseOrderRow[].class, false, false);
		param.setOmittable(true);
		param.setNillable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "extraFields"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "map"), Entry[].class, false, false);
		param.setItemQName(new QName("", "entry"));
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSPurchaseResponse"));
		oper.setReturnClass(ERSWSPurchaseResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[14] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("redeemVoucher");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "transactionProperties"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "mapArray"), Parameter[].class, false, false);
		param.setItemQName(new QName("", "entry"));
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "voucherSerial"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "voucherVerificationCode"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "topupPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "sendSmsNotification"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSTopupResponse"));
		oper.setReturnClass(ERSWSTopupResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[15] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("cancelTopup");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "senderPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "topupPrincipalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "senderAccountSpecifier"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"), PrincipalAccountSpecifier.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "topupAccountSpecifier"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier"), PrincipalAccountSpecifier.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "ersReference"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "reasonCode"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "comments"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", ">cancelTopupResponse"));
		oper.setReturnClass(CancelTopupResponse.class);
		oper.setReturnQName(new QName("http://external.interfaces.ers.seamless.com/", "cancelTopupResponse"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[16] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("updateReseller");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "targetReseller"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "targetPrincipal"), TargetPrincipal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "updateResellerProfile"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "updateResellerParams"), UpdateResellerParams.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSUpdateResellerResponse"));
		oper.setReturnClass(ERSWSUpdateResellerResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[17] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("deActivateReseller");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "targetReseller"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "targetPrincipal"), TargetPrincipal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSDeActivateResellerResponse"));
		oper.setReturnClass(ERSWSDeActivateResellerResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[18] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("activateReseller");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "activationCode"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSActivateResellerResponse"));
		oper.setReturnClass(ERSWSActivateResellerResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[19] = oper;

	}

	private static void _initOperationDesc3() {
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("executeReport");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "reportId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "language"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "parameterMap"), ParameterMap.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSExecuteReportResponse"));
		oper.setReturnClass(ERSWSExecuteReportResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[20] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("blockReseller");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "targetReseller"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "targetPrincipal"), TargetPrincipal.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSBlockResellerResponse"));
		oper.setReturnClass(ERSWSBlockResellerResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[21] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("changePrincipalPassword");
		param = new org.apache.axis.description.ParameterDesc(new QName("", "context"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "clientContext"), ClientContext.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "principalId"), org.apache.axis.description.ParameterDesc.IN, new QName("http://external.interfaces.ers.seamless.com/", "principalId"), PrincipalId.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new QName("", "newPassword"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://external.interfaces.ers.seamless.com/", "ERSWSResponse"));
		oper.setReturnClass(ERSWSResponse.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[22] = oper;

	}

	public ERSWSTopupServiceImplServiceSoapBindingStub() throws org.apache.axis.AxisFault {
		this(null);
	}

	public ERSWSTopupServiceImplServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}

	public ERSWSTopupServiceImplServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		} else {
			super.service = service;
		}
		((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
		Class cls;
		QName qName;
		QName qName2;
		Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
		Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
		Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
		Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
		Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
		Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
		Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
		Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
		Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
		Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
		qName = new QName("http://external.interfaces.ers.seamless.com/", ">cancelTopupResponse");
		cachedSerQNames.add(qName);
		cls = CancelTopupResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", ">commitTopupResponse");
		cachedSerQNames.add(qName);
		cls = CommitTopupResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", ">principal>accounts");
		cachedSerQNames.add(qName);
		cls = PrincipalAccount[].class;
		cachedSerClasses.add(cls);
		qName = new QName("http://external.interfaces.ers.seamless.com/", "principalAccount");
		qName2 = new QName("", "account");
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new QName("http://external.interfaces.ers.seamless.com/", "addressData");
		cachedSerQNames.add(qName);
		cls = AddressData.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "amount");
		cachedSerQNames.add(qName);
		cls = Amount.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "cancelPurchaseOrderResultRow");
		cachedSerQNames.add(qName);
		cls = CancelPurchaseOrderResultRow.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "cancelPurchaseOrderRow");
		cachedSerQNames.add(qName);
		cls = CancelPurchaseOrderRow.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "clientContext");
		cachedSerQNames.add(qName);
		cls = ClientContext.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "entry");
		cachedSerQNames.add(qName);
		cls = Entry.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSCancelTopupResponse");
		cachedSerQNames.add(qName);
		cls = ERSCancelTopupResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSCommitTopupResponse");
		cachedSerQNames.add(qName);
		cls = ERSCommitTopupResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ersMap");
		cachedSerQNames.add(qName);
		cls = ErsMap.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSActivateResellerResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSActivateResellerResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSBlockResellerResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSBlockResellerResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSCancelPurchaseResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSCancelPurchaseResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSDeActivateResellerResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSDeActivateResellerResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSDeLinkSubResellerResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSDeLinkSubResellerResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSExecuteReportResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSExecuteReportResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSFreezeResellerResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSFreezeResellerResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSGetTransactionStatusResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSGetTransactionStatusResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSGetVouchersDenominationResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSGetVouchersDenominationResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSLinkSubResellerResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSLinkSubResellerResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSPrincipalInformationResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSPrincipalInformationResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSPurchaseResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSPurchaseResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSRegisterSubResellerResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSRegisterSubResellerResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSTopupResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSTopupResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSTransferResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSTransferResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSUnBlockResellerResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSUnBlockResellerResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSUnFreezeResellerResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSUnFreezeResellerResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "ERSWSUpdateResellerResponse");
		cachedSerQNames.add(qName);
		cls = ERSWSUpdateResellerResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "map");
		cachedSerQNames.add(qName);
		cls = Entry[].class;
		cachedSerClasses.add(cls);
		qName = new QName("http://external.interfaces.ers.seamless.com/", "entry");
		qName2 = new QName("", "entry");
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new QName("http://external.interfaces.ers.seamless.com/", "mapArray");
		cachedSerQNames.add(qName);
		cls = Parameter[].class;
		cachedSerClasses.add(cls);
		qName = new QName("http://external.interfaces.ers.seamless.com/", "parameter");
		qName2 = new QName("", "entry");
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new QName("http://external.interfaces.ers.seamless.com/", "parameter");
		cachedSerQNames.add(qName);
		cls = Parameter.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "parameterMap");
		cachedSerQNames.add(qName);
		cls = ParameterMap.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "personData");
		cachedSerQNames.add(qName);
		cls = PersonData.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "principal");
		cachedSerQNames.add(qName);
		cls = Principal.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "principalAccount");
		cachedSerQNames.add(qName);
		cls = PrincipalAccount.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "principalAccountSpecifier");
		cachedSerQNames.add(qName);
		cls = PrincipalAccountSpecifier.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "principalId");
		cachedSerQNames.add(qName);
		cls = PrincipalId.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "productDetail");
		cachedSerQNames.add(qName);
		cls = ProductDetail.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "productSpecifier");
		cachedSerQNames.add(qName);
		cls = ProductSpecifier.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "purchaseOrderResultRow");
		cachedSerQNames.add(qName);
		cls = PurchaseOrderResultRow.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "purchaseOrderRow");
		cachedSerQNames.add(qName);
		cls = PurchaseOrderRow.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "report");
		cachedSerQNames.add(qName);
		cls = Report.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "targetPrincipal");
		cachedSerQNames.add(qName);
		cls = TargetPrincipal.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "updateResellerParams");
		cachedSerQNames.add(qName);
		cls = UpdateResellerParams.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new QName("http://external.interfaces.ers.seamless.com/", "voucherProduct");
		cachedSerQNames.add(qName);
		cls = VoucherProduct.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

	}

	protected Call createCall() throws java.rmi.RemoteException {
		try {
			Call _call = super._createCall();
			if (super.maintainSessionSet) {
				_call.setMaintainSession(super.maintainSession);
			}
			if (super.cachedUsername != null) {
				_call.setUsername(super.cachedUsername);
			}
			if (super.cachedPassword != null) {
				_call.setPassword(super.cachedPassword);
			}
			if (super.cachedEndpoint != null) {
				_call.setTargetEndpointAddress(super.cachedEndpoint);
			}
			if (super.cachedTimeout != null) {
				_call.setTimeout(super.cachedTimeout);
			}
			if (super.cachedPortName != null) {
				_call.setPortName(super.cachedPortName);
			}
			java.util.Enumeration keys = super.cachedProperties.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				_call.setProperty(key, super.cachedProperties.get(key));
			}
			// All the type mapping information is registered
			// when the first call is made.
			// The type mapping information is actually registered in
			// the TypeMappingRegistry of the service, which
			// is the reason why registration is only needed for the first call.
			synchronized (this) {
				if (firstCall()) {
					// must set encoding style before registering serializers
					_call.setEncodingStyle(null);
					for (int i = 0; i < cachedSerFactories.size(); ++i) {
						Class cls = (Class) cachedSerClasses.get(i);
						QName qName =
								(QName) cachedSerQNames.get(i);
						Object x = cachedSerFactories.get(i);
						if (x instanceof Class) {
							Class sf = (Class)
									cachedSerFactories.get(i);
							Class df = (Class)
									cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						} else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
							org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
									cachedSerFactories.get(i);
							org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
									cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						}
					}
				}
			}
			return _call;
		} catch (Throwable _t) {
			throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
		}
	}

	public ERSWSResponse customOperation(ClientContext clientContext, PrincipalId customerPrincipalId, PrincipalId targetPrincipalId, PrincipalAccountSpecifier senderAccountSpecifier, String operationId, ErsMap operationParameters) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "customOperation"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{clientContext, customerPrincipalId, targetPrincipalId, senderAccountSpecifier, operationId, operationParameters});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSResponse) JavaUtils.convert(_resp, ERSWSResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSRegisterSubResellerResponse registerSubReseller(ClientContext context, String resellerId, String resellerMsisdn, String resellerType, String parentResellerId, String firstName, String lastName, String defaultPin) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "registerSubReseller"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, resellerId, resellerMsisdn, resellerType, parentResellerId, firstName, lastName, defaultPin});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSRegisterSubResellerResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSRegisterSubResellerResponse) JavaUtils.convert(_resp, ERSWSRegisterSubResellerResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSDeLinkSubResellerResponse deLinkSubReseller(ClientContext context, String parentResellerId, PrincipalId principalId) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[2]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "deLinkSubReseller"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, parentResellerId, principalId});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSDeLinkSubResellerResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSDeLinkSubResellerResponse) JavaUtils.convert(_resp, ERSWSDeLinkSubResellerResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public CommitTopupResponse commitTopup(ClientContext context, PrincipalId senderPrincipalId, PrincipalId topupPrincipalId, PrincipalAccountSpecifier senderAccountSpecifier, PrincipalAccountSpecifier topupAccountSpecifier, Amount topupAmount, String ersReference, String reasonCode, String comment, boolean fetchsubscriberBalance) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[3]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "commitTopup"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, senderPrincipalId, topupPrincipalId, senderAccountSpecifier, topupAccountSpecifier, topupAmount, ersReference, reasonCode, comment, new Boolean(fetchsubscriberBalance)});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (CommitTopupResponse) _resp;
				} catch (Exception _exception) {
					return (CommitTopupResponse) JavaUtils.convert(_resp, CommitTopupResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSGetTransactionStatusResponse getTransactionStatus(ClientContext context, PrincipalId resellerPrincipalId) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[4]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "getTransactionStatus"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, resellerPrincipalId});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSGetTransactionStatusResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSGetTransactionStatusResponse) JavaUtils.convert(_resp, ERSWSGetTransactionStatusResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSPrincipalInformationResponse requestPrincipalInformation(ClientContext context, PrincipalId principalId, String nativeReslutCode) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[5]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "requestPrincipalInformation"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, principalId, nativeReslutCode});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSPrincipalInformationResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSPrincipalInformationResponse) JavaUtils.convert(_resp, ERSWSPrincipalInformationResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSTopupResponse requestTopup(ClientContext context, PrincipalId senderPrincipalId, PrincipalId topupPrincipalId, PrincipalAccountSpecifier senderAccountSpecifier, PrincipalAccountSpecifier topupAccountSpecifier, String productId, Amount amount) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[6]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "requestTopup"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, senderPrincipalId, topupPrincipalId, senderAccountSpecifier, topupAccountSpecifier, productId, amount});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSTopupResponse) ((ERSWSResponse) _resp)
						.setRequestSoapXML(_call.getMessageContext().getRequestMessage().getSOAPPartAsString())
						.setResponseSoapXML(_call.getMessageContext().getResponseMessage().getSOAPPartAsString());
				} catch (Exception _exception) {
					return (ERSWSTopupResponse) ((ERSWSResponse) JavaUtils.convert(_resp, ERSWSTopupResponse.class))
						.setRequestSoapXML(_call.getMessageContext().getRequestMessage().getSOAPPartAsString())
						.setResponseSoapXML(_call.getMessageContext().getResponseMessage().getSOAPPartAsString());
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSTransferResponse requestTransfer(ClientContext context, PrincipalId senderPrincipalId, PrincipalId receiverPrincipalId, PrincipalAccountSpecifier senderAccountSpecifier, PrincipalAccountSpecifier receiverAccountSpecifier, String productId, Amount amount) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[7]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "requestTransfer"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, senderPrincipalId, receiverPrincipalId, senderAccountSpecifier, receiverAccountSpecifier, productId, amount});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSTransferResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSTransferResponse) JavaUtils.convert(_resp, ERSWSTransferResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSCancelPurchaseResponse cancelPurchase(ClientContext context, CancelPurchaseOrderRow[] cancelPurchaseOrder) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[8]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "cancelPurchase"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, cancelPurchaseOrder});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSCancelPurchaseResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSCancelPurchaseResponse) JavaUtils.convert(_resp, ERSWSCancelPurchaseResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSUnFreezeResellerResponse unFreezeReseller(ClientContext context, TargetPrincipal targetReseller) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[9]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "unFreezeReseller"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, targetReseller});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSUnFreezeResellerResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSUnFreezeResellerResponse) JavaUtils.convert(_resp, ERSWSUnFreezeResellerResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSFreezeResellerResponse freezeReseller(ClientContext context, TargetPrincipal targetReseller) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[10]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "freezeReseller"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, targetReseller});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSFreezeResellerResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSFreezeResellerResponse) JavaUtils.convert(_resp, ERSWSFreezeResellerResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSLinkSubResellerResponse linkSubReseller(ClientContext context, String parentResellerId, PrincipalId principalId) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[11]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "linkSubReseller"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, parentResellerId, principalId});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSLinkSubResellerResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSLinkSubResellerResponse) JavaUtils.convert(_resp, ERSWSLinkSubResellerResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSUnBlockResellerResponse unBlockReseller(ClientContext context, TargetPrincipal targetReseller) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[12]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "unBlockReseller"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, targetReseller});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSUnBlockResellerResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSUnBlockResellerResponse) JavaUtils.convert(_resp, ERSWSUnBlockResellerResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSGetVouchersDenominationResponse getVouchersDenomination(ClientContext context) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[13]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "getVouchersDenomination"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSGetVouchersDenominationResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSGetVouchersDenominationResponse) JavaUtils.convert(_resp, ERSWSGetVouchersDenominationResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSPurchaseResponse requestPurchase(ClientContext context, PrincipalId senderPrincipalId, PrincipalId receiverPrincipalId, PrincipalAccountSpecifier senderAccountSpecifier, PrincipalAccountSpecifier receiverAccountSpecifier, PurchaseOrderRow[] purchaseOrder, Entry[] extraFields) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[14]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "requestPurchase"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, senderPrincipalId, receiverPrincipalId, senderAccountSpecifier, receiverAccountSpecifier, purchaseOrder, extraFields});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSPurchaseResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSPurchaseResponse) JavaUtils.convert(_resp, ERSWSPurchaseResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSTopupResponse redeemVoucher(Parameter[] transactionProperties, String voucherSerial, String voucherVerificationCode, PrincipalId topupPrincipalId, String sendSmsNotification) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[15]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "redeemVoucher"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{transactionProperties, voucherSerial, voucherVerificationCode, topupPrincipalId, sendSmsNotification});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSTopupResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSTopupResponse) JavaUtils.convert(_resp, ERSWSTopupResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public CancelTopupResponse cancelTopup(ClientContext context, PrincipalId senderPrincipalId, PrincipalId topupPrincipalId, PrincipalAccountSpecifier senderAccountSpecifier, PrincipalAccountSpecifier topupAccountSpecifier, String ersReference, String reasonCode, String comments) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[16]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "requestCancelTopup"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, senderPrincipalId, topupPrincipalId, senderAccountSpecifier, topupAccountSpecifier, ersReference, reasonCode, comments});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (CancelTopupResponse) _resp;
				} catch (Exception _exception) {
					return (CancelTopupResponse) JavaUtils.convert(_resp, CancelTopupResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSUpdateResellerResponse updateReseller(ClientContext context, TargetPrincipal targetReseller, UpdateResellerParams updateResellerProfile) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[17]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "updateReseller"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, targetReseller, updateResellerProfile});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSUpdateResellerResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSUpdateResellerResponse) JavaUtils.convert(_resp, ERSWSUpdateResellerResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSDeActivateResellerResponse deActivateReseller(ClientContext context, TargetPrincipal targetReseller) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[18]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "deActivateReseller"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, targetReseller});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSDeActivateResellerResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSDeActivateResellerResponse) JavaUtils.convert(_resp, ERSWSDeActivateResellerResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSActivateResellerResponse activateReseller(ClientContext context, String activationCode) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[19]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "activateReseller"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, activationCode});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSActivateResellerResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSActivateResellerResponse) JavaUtils.convert(_resp, ERSWSActivateResellerResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSExecuteReportResponse executeReport(ClientContext context, String reportId, String language, ParameterMap parameters) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[20]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "executeReport"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, reportId, language, parameters});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSExecuteReportResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSExecuteReportResponse) JavaUtils.convert(_resp, ERSWSExecuteReportResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSBlockResellerResponse blockReseller(ClientContext context, TargetPrincipal targetReseller) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[21]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "blockReseller"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, targetReseller});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSBlockResellerResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSBlockResellerResponse) JavaUtils.convert(_resp, ERSWSBlockResellerResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public ERSWSResponse changePrincipalPassword(ClientContext context, PrincipalId principalId, String newPassword) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[22]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://external.interfaces.ers.seamless.com/", "changePrincipalPassword"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[]{context, principalId, newPassword});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (ERSWSResponse) _resp;
				} catch (Exception _exception) {
					return (ERSWSResponse) JavaUtils.convert(_resp, ERSWSResponse.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

}
