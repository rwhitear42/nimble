package com.cloupia.feature.nimble.accounts;

import org.apache.log4j.Logger;

import com.cloupia.model.cIM.ConfigTableAction;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.service.cIM.inframgr.SystemScheduler;
import com.cloupia.service.cIM.inframgr.SystemStateEntry;
import com.cloupia.service.cIM.inframgr.forms.wizard.Page;
import com.cloupia.service.cIM.inframgr.forms.wizard.PageIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaPageAction;

public class AccountRunSystemTaskAction extends CloupiaPageAction {
	static Logger logger = Logger.getLogger(AccountRunSystemTaskAction.class);
	
	private static final String formId = "nimble.systemTask.run.form";
	private static final String ACTION_ID = "nimble.systemTask.run.action";
	private static final String label = "Run Now";
	/**
	 * Defines the form page to perform the action
	 */
	@Override
	public void definePage(Page page, ReportContext context) {
		
		String task = context.getId();        
        logger.info("Task Name is" + task);        
        SystemStateEntry entry = SystemScheduler.getInstance().getSystemStateEntryForTask(task);
        //entry.getValue();
        if(entry !=null && entry.getValue() !=null && entry.getValue().equalsIgnoreCase("In Progress")){        	
        	page.addLabel("Selected Task is in Progress, cannot perform run-now");
        	page.setEnableSubmit(false);  
        	
        }else{
        	page.addLabel("Are you sure you want to run task '"+ context.getId() + "' now?"); 
        }

	}
	/**
	 * Loads form data to the form
	 */
	@Override
	public void loadDataToPage(Page arg0, ReportContext arg1, WizardSession arg2)
			throws Exception {
		// TODO Auto-generated method stub

	}
	/**
	 * Validate the form page data
	 * @return returns form validation status
	 */
	@Override
	public int validatePageData(Page page, ReportContext context,
			WizardSession session) throws Exception {
		if (page.isPageSubmitted())
        {
            try
            {
                String task = context.getId();
                
                logger.info("Getting schedule task for " + task);
                
               SystemScheduler.getInstance().runNow(task);
               page.setPageMessage("Task is scheduled to run immediately");

               return PageIf.STATUS_OK;
                
            } catch (Exception ex)
            {
                return PageIf.STATUS_OK;
            }
        }

        return PageIf.STATUS_OK;
	}
	/**
	 * Defines action Id, should be unique across UCSD
	 * @return returns action Id 
	 */
	@Override
	public String getActionId() {
		// TODO Auto-generated method stub
		return ACTION_ID;
	}
	/**
	 * Defines action type e.g. popup, drilldown etc.
	 * @return returns action type 
	 */
	@Override
	public int getActionType() {
		// TODO Auto-generated method stub
		return ConfigTableAction.ACTION_TYPE_POPUP_FORM;
	}
	/**
	 * Defines action form Id, should be unique across UCSD
	 * @return returns action form Id 
	 */
	@Override
	public String getFormId() {
		// TODO Auto-generated method stub
		return formId;
	}
	/**
	 * Defines action button label to be shown in UI
	 * @return returns action button label 
	 */
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return label;
	}
	/**
	 * Defines form title
	 * @return returns form title 
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return label;
	}
	/**
	 * Defines action type, true if supports double click
	 * @return returns true if supports double click
	 */
	@Override
	public boolean isDoubleClickAction() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Defines action type, true if its a drill down action
	 * @return returns true if its a drill down action
	 */
	@Override
	public boolean isDrilldownAction() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Defines action type, true if report row selection required to perform action
	 * @return returns true if report row selection required
	 */
	@Override
	public boolean isSelectionRequired() {
		// TODO Auto-generated method stub
		return true;
	}

}
