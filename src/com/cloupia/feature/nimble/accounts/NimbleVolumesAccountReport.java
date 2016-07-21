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
public class NimbleVolumesAccountReport {
	
	@ReportField(label="Name")
	@Persistent
	private String name;
	
	@ReportField(label="Size")
	@Persistent
 	private String size;
	
	@ReportField(label="Volume Usage")
	@Persistent
 	private String volumeUsage;

	@ReportField(label="Snapshot Usage")
	@Persistent
 	private String snapshotUsage;
	
	@ReportField(label="Total Usage")
	@Persistent
 	private String totalUsage;


	
	public NimbleVolumesAccountReport() {
		super();
		
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getVolumeUsage() {
		return volumeUsage;
	}

	public void setVolumeUsage(String volumeUsage) {
		this.volumeUsage = volumeUsage;
	}

	public String getSnapshotUsage() {
		return snapshotUsage;
	}

	public void setSnapshotUsage(String snapshotUsage) {
		this.snapshotUsage = snapshotUsage;
	}

	public String getTotalUsage() {
		return totalUsage;
	}

	public void setTotalUsage(String totalUsage) {
		this.totalUsage = totalUsage;
	}

}
