/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.accounts;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.apache.log4j.Logger;

import com.cloupia.fw.objstore.ObjStore;
import com.cloupia.fw.objstore.ObjStoreHelper;
import com.cloupia.lib.connector.account.AbstractInfraAccount;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.model.cIM.InfraAccount;
import com.cloupia.service.cIM.inframgr.collector.view2.ConnectorCredential;
import com.cloupia.service.cIM.inframgr.forms.wizard.FieldValidation;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;
import com.cloupia.service.cIM.inframgr.forms.wizard.HideFieldOnCondition;

@PersistenceCapable(detachable = "true", table = "nimble_account_backup")
public class NimbleAccountBackup extends AbstractInfraAccount implements
ConnectorCredential {

static Logger logger = Logger.getLogger(NimbleAccountBackup.class);

@Persistent
@FormField(label = "Device IP", help = "Device IP", mandatory = true)
private String deviceIp;

@Persistent
@FormField(label = "Use Credential Policy", validate = true, help = "Select if you want to use policy to give the credentials.", type = FormFieldDefinition.FIELD_TYPE_BOOLEAN)
private boolean isCredentialPolicy = false;

@Persistent
@FormField(label = "Protocol", help = "Protocol", type = FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, validate = true, lov = {
	"http", "https" })
@HideFieldOnCondition(field = "isCredentialPolicy", op = FieldValidation.OP_EQUALS, value = "false")
private String protocol="http";

@FormField(label = "Port", help = "Port Number", type = FormFieldDefinition.FIELD_TYPE_TEXT)
@Persistent
@HideFieldOnCondition(field = "isCredentialPolicy", op = FieldValidation.OP_EQUALS, value = "false")
private String port = "8080";

@Persistent
@FormField(label = "Login", help = "Login")
@HideFieldOnCondition(field = "isCredentialPolicy", op = FieldValidation.OP_EQUALS, value = "false")
private String login;

@Persistent
@FormField(label = "Password", help = "Password", type = FormFieldDefinition.FIELD_TYPE_PASSWORD)
@HideFieldOnCondition(field = "isCredentialPolicy", op = FieldValidation.OP_EQUALS, value = "false")
private String password;


public NimbleAccountBackup() {

}

/**
* @return the deviceIp
*/
public String getDeviceIp() {
return deviceIp;
}

/**
* @param deviceIp
*            the deviceIp to set
*/
public void setDeviceIp(String deviceIp) {
this.deviceIp = deviceIp;
}

/**
* @return the isCredentialPolicy
*/
public boolean isCredentialPolicy() {
return isCredentialPolicy;
}

/**
* @param isCredentialPolicy
*            the isCredentialPolicy to set
*/
public void setCredentialPolicy(boolean isCredentialPolicy) {
this.isCredentialPolicy = isCredentialPolicy;
}

/**
* @return the protocol
*/
public String getProtocol() {
return protocol;
}

/**
* @param protocol
*            the protocol to set
*/
public void setProtocol(String protocol) {
this.protocol = protocol;
}

/**
* @return the port
*/
public String getPort() {
return port;
}

/**
* @param port
*            the port to set
*/
public void setPort(String port) {
this.port = port;
}

/**
* @return the login
*/
public String getLogin() {
return login;
}

/**
* @param login
*            the login to set
*/
public void setLogin(String login) {
this.login = login;
}

/**
* @return the password
*/
public String getPassword() {
return password;
}

/**
* @param password
*            the password to set
*/
public void setPassword(String password) {
this.password = password;
}



@Override
public InfraAccount toInfraAccount() {
try {
	ObjStore<InfraAccount> store = ObjStoreHelper
			.getStore(InfraAccount.class);
	String cquery = "server == '"
			+ deviceIp + "' && userID == '" + login
			+ "' && transport == '" + protocol + "' && port == "
			+ Integer.parseInt(port);
	logger.debug("query = " + cquery);

	List<InfraAccount> accList = store.query(cquery);
	if (accList != null && accList.size() > 0)
		return accList.get(0);
	/*else if (deviceCategory.equalsIgnoreCase("Nimble Account")) {
		InfraAccount acc = new InfraAccount();
		acc.setServer(deviceIp);
		acc.setDcName(datacenter);
		acc.setAccountName(this.getEnablePassword());
		acc.setUserID(login);
		acc.setPassword(this.getPassword());
		acc.setPort(Integer.valueOf(port));
		acc.setTransport(protocol);
		return acc;
	} */else
		return null;
} catch (Exception e) {
	logger.error("Exception while mapping DeviceCredential to InfraAccount for server: "
			+ deviceIp + ": " + e.getMessage());
}

return null;
}

@Override
public String getServerAddress() {
// TODO Auto-generated method stub
return getDeviceIp();
}



@Override
public String getPolicy() {
// TODO Auto-generated method stub
return null;
}

@Override
public void setPolicy(String arg0) {
// TODO Auto-generated method stub

}

@Override
public void setPort(int arg0) {
// TODO Auto-generated method stub

}

@Override
public void setUsername(String arg0) {
// TODO Auto-generated method stub

}


}
