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
public class NimbleVolumeCollectionsAccountReport {
	
	@ReportField(label="Volume Collection")
	@Persistent
	private String volumeCollection;
	
	@ReportField(label="Synchronization")
	@Persistent
 	private String Synchronization;
	
	@ReportField(label="Last Snapshot")
	@Persistent
 	private String lastSnapshot;
	
	@ReportField(label="Replication Partner")
	@Persistent
 	private String replicationPartner;
	
	@ReportField(label="Last Replication")
	@Persistent
 	private String lastReplication;
	
	@ReportField(label="Total Snapshots to Retain")
	@Persistent
 	private String totalSnapsToRetain;
	
	
	public NimbleVolumeCollectionsAccountReport() {
		super();
		
	}

	
	public String getVolumeCollection() {
		return volumeCollection;
	}

	public void setVolumeCollection(String volumeCollection) {
		this.volumeCollection = volumeCollection;
	}

	public String getSynchronization() {
		return Synchronization;
	}

	public void setSynchronization(String synchronization) {
		Synchronization = synchronization;
	}

	public String getLastSnapshot() {
		return lastSnapshot;
	}

	public void setLastSnapshot(String lastSnapshot) {
		this.lastSnapshot = lastSnapshot;
	}

	public String getReplicationPartner() {
		return replicationPartner;
	}

	public void setReplicationPartner(String replicationPartner) {
		this.replicationPartner = replicationPartner;
	}

	public String getLastReplication() {
		return lastReplication;
	}

	public void setLastReplication(String lastReplication) {
		this.lastReplication = lastReplication;
	}

	public String getTotalSnapsToRetain() {
		return totalSnapsToRetain;
	}

	public void setTotalSnapsToRetain(String totalSnapsToRetain) {
		this.totalSnapsToRetain = totalSnapsToRetain;
	}

}
