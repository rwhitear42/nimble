package com.cloupia.feature.nimble.reports;

import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.ReportNameValuePair;
import com.cloupia.model.cIM.SnapshotReport;
import com.cloupia.model.cIM.SnapshotReportCategory;
import com.cloupia.service.cIM.inframgr.SnapshotReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;

public class SampleBarChartReportImpl implements SnapshotReportGeneratorIf {
	
	// In this example , defines the number of bars should be in chart as bar1 and bar2 
	// like shown in above snapshot
	private final int NUM_BARS = 2; 
	private final String BAR_1 = "bar1"; 
	private final String BAR_2 = "bar2";
	
	@Override
	public SnapshotReport getSnapshotReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
	
		SnapshotReport report = new SnapshotReport(); 
		
		report.setContext(context);	
		
		report.setReportName(reportEntry.getReportLabel());
		
		report.setNumericalData(true); 
		
		report.setValueAxisName("Value Axis Name");
		
		report.setPrecision(0);

		//chart
		// setting the report name value pair for the bar
		ReportNameValuePair[] rnv1 = new ReportNameValuePair[NUM_BARS];
	
		rnv1[0] = new ReportNameValuePair(BAR_1, 5); 
		rnv1[1] = new ReportNameValuePair(BAR_2, 10);
	
		// setting category of report 
		SnapshotReportCategory cat1 = new SnapshotReportCategory();
	
		cat1.setCategoryName("cat1"); 
		cat1.setNameValuePairs(rnv1);
	
		report.setCategories(new SnapshotReportCategory[] { cat1 });
	
		return report;
		
	}
	
}
