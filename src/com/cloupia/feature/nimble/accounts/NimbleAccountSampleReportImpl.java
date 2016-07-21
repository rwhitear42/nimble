/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.accounts;

import java.util.List;

import org.apache.log4j.Logger;

import com.cloupia.fw.objstore.ObjStore;
import com.cloupia.fw.objstore.ObjStoreHelper;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.TabularReportInternalModel;

/**
 * This is an example demonstrating how to develop a report programmatically. If
 * you would rather generate your report from scratch, this is how you would go
 * about it. This class is called when a request to render the report is made,
 * you need to compile all the data into a TabularReport which will be returned
 * to the UI for rendering.
 * 
 */
public class NimbleAccountSampleReportImpl implements TabularReportGeneratorIf {

	private static Logger logger = Logger
			.getLogger(NimbleAccountSampleReportImpl.class);

	@Override
	public TabularReport getTabularReportReport(
			ReportRegistryEntry reportEntry, ReportContext context)
			throws Exception {

		TabularReport report = new TabularReport();

		report.setGeneratedTime(System.currentTimeMillis());
		report.setReportName(reportEntry.getReportLabel());
		report.setContext(context);
		/**
		 * This is dummy account retrieval from database. Data populating from
		 * DummyAccountData for this account.
		 */
		ObjStore<DummyAccount> dummyAssignStore = ObjStoreHelper
				.getStore(DummyAccount.class);
		
		List<DummyAccount> objs = dummyAssignStore.queryAll();

		TabularReportInternalModel model = new TabularReportInternalModel();

		model.addTextColumn("Device IP", "Device IP");
		model.addTextColumn("Protocol", "Protocol");
		model.addTextColumn("Port", "Port");
		model.completedHeader();

		for (int i = 0; i < objs.size(); i++) {
			DummyAccount pojo = objs.get(i);

			logger.info("[RUSS] model.addTextValue(pojo.getDeviceIp())" + pojo.getIp());
			
			model.addTextValue(pojo.getIp());
			
			logger.info("[RUSS] model.addTextValue(pojo.getProtocol())" + pojo.getAccountName());

			model.addTextValue(pojo.getAccountName());
			
			logger.info("[RUSS] model.addTextValue(pojo.getPort())" + pojo.getStatus());

			model.addTextValue(pojo.getStatus());
			
			model.completedRow();
		}

		model.updateReport(report);

		return report;
	}

}
