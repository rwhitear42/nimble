package com.cloupia.feature.nimble.reports;

import org.apache.log4j.Logger;

import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.TabularReportInternalModel;

public class MyFirstReportImpl implements TabularReportGeneratorIf {

	private static Logger logger = Logger.getLogger(MyFirstReportImpl.class);
	
	
	@Override
	public TabularReport getTabularReportReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		
		TabularReport report = new TabularReport();
		
		// Current system time is taken as report generated time.
		report.setGeneratedTime( System.currentTimeMillis() );
		
		// Setting unique report name.
		report.setReportName( reportEntry.getReportLabel() );
		
		// Set the context of the report.
		report.setContext( context );

		// TabularReportInternalModel contains all the data that you want to show
		// in a report.
		TabularReportInternalModel model = new TabularReportInternalModel();
		
		model.addTextColumn("Name", "Name");
		model.addTextColumn("VLAN ID", "VLAN ID");
		model.addTextColumn("Group", "Assigned To Group");
		model.completedHeader();
		
		model.addTextValue("My");
		model.addTextValue("First");
		model.addTextValue("Report");
		model.completedRow();
		
		model.updateReport( report );
		
		return report;
	}

}
