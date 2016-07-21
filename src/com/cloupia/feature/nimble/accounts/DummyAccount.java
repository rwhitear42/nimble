/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.accounts;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;



import com.cloupia.lib.easyui.annotations.ReportField;
import com.cloupia.model.cIM.InventoryDBItemIf;
import com.cloupia.service.cIM.inframgr.reports.simplified.ReportableIf;


/**
 * This is dummy class for POJO based approach for Account Type implementation. 
 * Data populating from DummyAccountData for this account.
 * For real implementation similar POJO based approach can be refer.
 * 
 *
 */
@PersistenceCapable(detachable = "true")
public class DummyAccount {
	
	@ReportField(label="Sniffles")
	@Persistent
	private String accountName;
	
	@ReportField(label="Bingle")
	@Persistent
 	private String status;
	
	@ReportField(label="Poop")
	@Persistent
 	private String ip;

	
	
	public DummyAccount() {
		super();
		
	}

	public String getAccountName() {
		// TODO Auto-generated method stub
		return accountName;
	}

	public void setAccountName(String accountName) {
		// TODO Auto-generated method stub
		this.accountName = accountName;
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
