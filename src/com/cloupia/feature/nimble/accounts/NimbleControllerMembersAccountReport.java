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
public class NimbleControllerMembersAccountReport {
	
	@ReportField(label="Name")
	@Persistent
	private String name;
	
	@ReportField(label="Version")
	@Persistent
 	private String version;
	
	@ReportField(label="Role")
	@Persistent
 	private String role;
	
	@ReportField(label="Model")
	@Persistent
 	private String model;
	
	@ReportField(label="Pool")
	@Persistent
 	private String pool;
	
	@ReportField(label="Status")
	@Persistent
 	private String status;

	
	public NimbleControllerMembersAccountReport() {
		super();
		
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
