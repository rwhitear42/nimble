/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.reports;

import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.ReportNameValuePair;
import com.cloupia.model.cIM.SnapshotReport;
import com.cloupia.model.cIM.SnapshotReportCategory;
import com.cloupia.service.cIM.inframgr.SnapshotReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;

public class SamplePieChartReportImpl2 implements SnapshotReportGeneratorIf {
	
	@Override
	public SnapshotReport getSnapshotReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception { 
		
		SnapshotReport report = new SnapshotReport(); report.setContext(context);
	
		report.setReportName(reportEntry.getReportLabel());
	
		report.setNumericalData(true); report.setDisplayAsPie(true);
	
		report.setPrecision(0);
	
		//creation of report name value pair goes 
		ReportNameValuePair[] rnv = new ReportNameValuePair[2]; 
		
		rnv[0] = new ReportNameValuePair("Steve will give a shit", 2 );
		rnv[1] = new ReportNameValuePair("Steve won't give a shit", 98 );


		//setting of report category goes 
		SnapshotReportCategory cat = new SnapshotReportCategory();
	
		cat.setCategoryName(""); cat.setNameValuePairs(rnv);
		
		report.setCategories(new SnapshotReportCategory[] { cat });
	
		return report;
		
	}
	
}