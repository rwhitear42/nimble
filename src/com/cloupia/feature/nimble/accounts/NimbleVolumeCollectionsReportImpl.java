/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.accounts;

import java.util.List;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.TabularReportInternalModel;
import com.rwhitear.nimbleRest.inventory.GetVolumeCollectionsInventory;
import com.rwhitear.nimbleRest.inventory.data.VolumeCollectionsDataObject;

/**
 * This is an example demonstrating how to develop a report programmatically.  If you would rather
 * generate your report from scratch, this is how you would go about it.  This class is called when
 * a request to render the report is made, you need to compile all the data into a TabularReport
 * which will be returned to the UI for rednering.
 *
 */
public class NimbleVolumeCollectionsReportImpl implements TabularReportGeneratorIf {
	
	private static Logger logger = Logger.getLogger(NimbleVolumeCollectionsReportImpl.class);

	@Override
	public TabularReport getTabularReportReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		
		TabularReport report = new TabularReport();

		report.setGeneratedTime(System.currentTimeMillis());
		report.setReportName(reportEntry.getReportLabel());
		report.setContext(context);
		
		String accountName = "";
		String contextId = context.getId();
		logger.info("###### Context at System Task report#####: "+contextId);
		if(contextId != null)
			//As the contextId returns as: "account Name;POD Name"
			accountName = contextId.split(";")[0];
		
        PhysicalInfraAccount acc = AccountUtil.getAccountByName(accountName);
        if (acc == null)
        {
            throw new Exception("Unable to find the account:" + accountName);
        }
		
        String json = acc.getCredential();
        
        NimbleAccountJsonObject specificAcc = (NimbleAccountJsonObject) JSON.jsonToJavaObject(json, NimbleAccountJsonObject.class);
        
        String username = specificAcc.getLogin();
        String password = specificAcc.getPassword();
        String deviceIp = specificAcc.getDeviceIp();

        GetVolumeCollectionsInventory gvci = new GetVolumeCollectionsInventory(username, password, deviceIp);
		
		VolumeCollectionsDataObject vcdo = gvci.getInventory();
		
		List<String> 	volCollectionNames 	= vcdo.getVolCollectionNames();
		List<String> 	synchronization 	= vcdo.getSynchronization();

		
		TabularReportInternalModel model = new TabularReportInternalModel();

		model.addTextColumn("Volume Collection", "Volume Collection");
		model.addTextColumn("Synchronization", "Synchronization");
		model.addTextColumn("Last Snapshot", "Last Snapshot");
		model.addTextColumn("Replication Partner", "Replication Partner");
		model.addTextColumn("Last Replication", "Last Replication");
		model.addTextColumn("Total Snapshots to Retain", "Total Snapshots to Retain");
		model.completedHeader();
		
		for( int i = 0; i < volCollectionNames.size(); i++ ) {
			
			model.addTextValue( volCollectionNames.get(i) );
			model.addTextValue( synchronization.get(i) );
			model.addTextValue("N/A");
			model.addTextValue("N/A");
			model.addTextValue("N/A");
			model.addTextValue("N/A");
			model.completedRow();
			
		}

		model.updateReport(report);

		return report;
	}

}
