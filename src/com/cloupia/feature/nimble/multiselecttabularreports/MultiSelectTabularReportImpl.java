/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.multiselecttabularreports;

import org.apache.log4j.Logger;

import com.cloupia.feature.nimble.dummyOne.reports.DummyOneSampleReportImpl;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.TabularReportInternalModel;

public class MultiSelectTabularReportImpl implements TabularReportGeneratorIf {
	private static Logger logger = Logger.getLogger(MultiSelectTabularReportImpl.class);

	@Override
	public TabularReport getTabularReportReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		TabularReport report = new TabularReport();

		report.setGeneratedTime(System.currentTimeMillis());
		report.setReportName(reportEntry.getReportLabel());
		report.setContext(context);
		
		TabularReportInternalModel model = new TabularReportInternalModel();
	
		model.addTextColumn("Report Name", "Report Name");
		model.addTextColumn("Report Column 1", "Report Column 1");
		model.addTextColumn("Report Column 2", "Report Column 2");
		model.completedHeader();

		for (int i=0; i<5; i++)
		{
			
			model.addTextValue("N/A");
			model.addTextValue("N/A");
			model.addTextValue("N/A");
			model.completedRow();
		}

		model.updateReport(report);

		return report;
	}

}
