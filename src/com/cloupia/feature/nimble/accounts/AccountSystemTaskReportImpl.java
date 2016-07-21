/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.accounts;

import java.util.List;

import org.apache.log4j.Logger;

import com.cloupia.fw.scheduler.SystemTaskEntry;
import com.cloupia.fw.scheduler.SystemTaskRegistry;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.ScheduleTask;
import com.cloupia.service.cIM.inframgr.SystemScheduler;
import com.cloupia.service.cIM.inframgr.SystemStateEntry;
import com.cloupia.service.cIM.inframgr.SystemTaskUtil;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.TabularReportInternalModel;

public class AccountSystemTaskReportImpl implements TabularReportGeneratorIf{
	
	private static Logger logger = Logger.getLogger(AccountSystemTaskReportImpl.class);
	/**
	 * Provide Tabular report implementation
	 * @return returns tabular report
	 */
	@Override
	public TabularReport getTabularReportReport(ReportRegistryEntry reportEntry,
			ReportContext context) throws Exception {
		// TODO Auto-generated method stub
		TabularReport report = new TabularReport();

		report.setGeneratedTime(System.currentTimeMillis());
		report.setReportName(reportEntry.getReportLabel());
		report.setContext(context);
		
		String contextId = context.getId();
		if(contextId == null)
			return null;
		
		String accountName = contextId.split(";")[0];
		logger.info("###### Account Name for System Task report#####: "+accountName);
		TabularReportInternalModel model = new TabularReportInternalModel();

        model.addTextColumn("Name", "Name given to Task",true); 
        model.addTextColumn("Label", "Label given to Task");
        model.addTextColumn("Category", "Category of the Task");
        model.addTextColumn("Enabled", "Enabled");
       // model.addTextColumn("Frequency", "Frequency");
        model.addTextColumn("Execution Node Name", "Execution Node Name");
        model.addTextColumn("Execution Node Network Name", "Execution Node Network Name");
        model.addLongTextColumn("Execution Status", "Execution Status");    
        //model.addTextColumn("Execution Duration", "Execution Duration");
        //model.addTimeColumn("Start Time", "Start Time");
        model.addTimeColumn("Last Executed Time", "Last Executed Time");        
        model.addTimeColumn("Next Execution Time", "Next Execution Time"); 
        
        model.completedHeader();
        
        displayReport(model, report, accountName);
		
		return report;
	}

	private void displayReport(TabularReportInternalModel model,
			TabularReport report, String accountName) throws Exception {
		// TODO Auto-generated method stub
		List<SystemStateEntry> entries = SystemTaskUtil.getAllSystemStateEntriesByAccName(accountName);
		
		for (SystemStateEntry entry : entries)
    	{
			if (entry.getProperty()!=null && entry.getProperty().startsWith("task"))
    		{
				String taskName = entry.getProperty();    			
    	    	if (taskName.contains(".")) {
    	    		taskName = taskName.substring(taskName.indexOf(".") + 1);
    	    	}
    	    	
				String category = getCategory(taskName);
				
				//ScheduleTask task = SystemScheduler.getInstance().getTaskByName(taskName);
				
				model.addTextValue(taskName);
				model.addTextValue(entry.getLabel());    	    
		    	model.addTextValue(category);
		    	
		    	String status = (entry != null) ? (entry.isDisabled() ? "Disabled" : "Enabled") : "NA";
		    	model.addTextValue(status);
		    	
		    	//model.addTextValue(getFrequencyAsString(task.getFrequency(), entry.getFrequency()));
		    	model.addTextValue(entry.getNodeName());
	    		model.addTextValue(entry.getAgentIpAddress());
	    		model.addLongTextValue(entry.getValue());
	    		//model.addTextValue("");
	    		model.addTimeValue(entry.getStartTime());
	        	model.addTimeValue(entry.getEndTime());
	        	
	        	
	        	model.completedRow();
				
				
    		}
    	}
		model.updateReport(report);
		
	}

	private String getCategory(String taskName) {
		// TODO Auto-generated method stub
		String category = null;
		SystemTaskEntry taskDef = 
			SystemTaskRegistry.getInstance().getTaskEntry(taskName);
    	if (taskDef != null) {
    		category = taskDef.getCategory();
    	}
    	return category;
	}

}
