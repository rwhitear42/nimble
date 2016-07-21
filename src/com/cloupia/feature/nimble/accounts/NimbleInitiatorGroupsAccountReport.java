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
public class NimbleInitiatorGroupsAccountReport {
	
	@ReportField(label="Initiator Group")
	@Persistent
	private String initiatorGroup;
	
	@ReportField(label="Initiators")
	@Persistent
 	private String initiators;
	
	@ReportField(label="Associated Volumes")
	@Persistent
 	private String associatedVolumes;
	
	
	
	public NimbleInitiatorGroupsAccountReport() {
		super();
		
	}

	
	public String getInitiatorGroup() {
		return initiatorGroup;
	}

	public void setInitiatorGroup(String initiatorGroup) {
		this.initiatorGroup = initiatorGroup;
	}

	public String getInitiators() {
		return initiators;
	}

	public void setInitiators(String initiators) {
		this.initiators = initiators;
	}

	public String getAssociatedVolumes() {
		return associatedVolumes;
	}

	public void setAssociatedVolumes(String associatedVolumes) {
		this.associatedVolumes = associatedVolumes;
	}

}
