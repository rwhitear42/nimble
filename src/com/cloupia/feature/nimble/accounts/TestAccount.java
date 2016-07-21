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
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

@PersistenceCapable(detachable = "true", table = "test_account")
public class TestAccount extends AbstractInfraAccount implements ConnectorCredential {

	static Logger logger = Logger.getLogger(TestAccount.class);
	
	@Persistent
	private boolean isCredentialPolicy = false;
	
	@Persistent
	@FormField(label = "Device IP", help = "Device IP", mandatory = true)
	private String deviceIp;
	
	@Persistent
	@FormField(label = "Login", help = "Login")
	private String login;
	
	@Persistent
	@FormField(label = "Password", help = "Password", type = FormFieldDefinition.FIELD_TYPE_PASSWORD)
	private String password;
	
	
	public TestAccount() {
		
	}
	
	
	@Override
	public boolean isCredentialPolicy() {
		return false;
	}

	@Override
	public void setCredentialPolicy(boolean isCredentialPolicy) {
		this.isCredentialPolicy = isCredentialPolicy;
	}
	
	public String getDeviceIp() {
		return this.deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public InfraAccount toInfraAccount() {
		
		try {
			
			ObjStore<InfraAccount> store = ObjStoreHelper.getStore(InfraAccount.class);
			
			String cquery = "server == '" + deviceIp + "' && userID == '" 
					+ login + "' && transport == 'https' && port == "
					+ Integer.parseInt("5392"); // 5392 is the TCP port for Nimble REST API.
			
			logger.info("query = " + cquery);
			
			List<InfraAccount> accList = store.query(cquery);
			
			if (accList != null && accList.size() > 0)
				return accList.get(0);
			else
				return null;
			
			
		} catch(Exception e) {
			logger.error("Exception while mapping DeviceCredential to InfraAccount for server: "
					+ deviceIp + ": " + e.getMessage());
		}
		
		return null;
		
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
	public void setUsername(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPort(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
