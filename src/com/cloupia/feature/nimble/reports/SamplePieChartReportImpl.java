package com.cloupia.feature.nimble.reports;

import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.ReportNameValuePair;
import com.cloupia.model.cIM.SnapshotReport;
import com.cloupia.model.cIM.SnapshotReportCategory;
import com.cloupia.service.cIM.inframgr.SnapshotReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;

public class SamplePieChartReportImpl implements SnapshotReportGeneratorIf {
	
	@Override
	public SnapshotReport getSnapshotReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception { 
		
		SnapshotReport report = new SnapshotReport(); report.setContext(context);
	
		report.setReportName(reportEntry.getReportLabel());
	
		report.setNumericalData(true); 
		
		report.setDisplayAsPie(true);
	
		report.setPrecision(0);
	
		//creation of report name value pair goes 
		ReportNameValuePair[] rnv = new ReportNameValuePair[5]; 
		
		rnv[0] = new ReportNameValuePair("Pure", 5 );
		rnv[1] = new ReportNameValuePair("SolidFire", 10 );
		rnv[2] = new ReportNameValuePair("NetApp", 10 );
		rnv[3] = new ReportNameValuePair("EMC", 5 );
		rnv[4] = new ReportNameValuePair("Nimble", 70 );


		//setting of report category goes 
		SnapshotReportCategory cat = new SnapshotReportCategory();
	
		cat.setCategoryName(""); 
		cat.setNameValuePairs(rnv);
		
		report.setCategories(new SnapshotReportCategory[] { cat });
	
		return report;
		
	}
	
}