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
import com.rwhitear.nimbleRest.inventory.GetPerformancePoliciesInventory;
import com.rwhitear.nimbleRest.inventory.data.PerformancePolicyDataObject;

public class NimblePerformancePoliciesReportImpl implements TabularReportGeneratorIf {
	
	private static Logger logger = Logger.getLogger(NimblePerformancePoliciesReportImpl.class);

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
        
        GetPerformancePoliciesInventory gppi = new GetPerformancePoliciesInventory(username, password, deviceIp);
		
		PerformancePolicyDataObject ppdo = gppi.getInventory();
		
		List<String> perfPolicyNames = ppdo.getPerfPolicyNames();
		
		TabularReportInternalModel model = new TabularReportInternalModel();

		model.addTextColumn("Performance Policy", "Performance Policy");
		model.addTextColumn("Associated Volumes", "Associated Volumes");
		model.completedHeader();
		
		for( int i = 0; i < perfPolicyNames.size(); i++ ) {
			
			model.addTextValue( perfPolicyNames.get(i) );
			model.addTextValue("N/A");
			model.completedRow();
			
		}

		model.updateReport(report);

		return report;
		
	}

}
