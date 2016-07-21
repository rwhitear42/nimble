/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.dummyOne.reports;

import org.apache.log4j.Logger;

import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.TabularReportInternalModel;

/**
 * This is an example demonstrating how to develop a report programmatically.  If you would rather
 * generate your report from scratch, this is how you would go about it.  This class is called when
 * a request to render the report is made, you need to compile all the data into a TabularReport
 * which will be returned to the UI for rednering.
 *
 */
public class DummyOneSampleReportImpl implements TabularReportGeneratorIf {
	
	private static Logger logger = Logger.getLogger(DummyOneSampleReportImpl.class);

	@Override
	public TabularReport getTabularReportReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		
		TabularReport report = new TabularReport();

		report.setGeneratedTime(System.currentTimeMillis());
		report.setReportName(reportEntry.getReportLabel());
		report.setContext(context);
		
		TabularReportInternalModel model = new TabularReportInternalModel();

		model.addTextColumn("Name", "Name");
		model.addTextColumn("VLAN ID", "VLAN ID");
		model.addTextColumn("Group", "Assigned To Group");
		model.completedHeader();

		for (int i=0; i<5; i++)
		{
			
			model.addTextValue("One");
			model.addTextValue("Two");
			model.addTextValue("Three");
			model.completedRow();
		}

		model.updateReport(report);

		return report;
	}

}
