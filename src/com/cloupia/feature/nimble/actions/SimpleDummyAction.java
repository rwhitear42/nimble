package com.cloupia.feature.nimble.actions;

import org.apache.log4j.Logger;

import com.cloupia.model.cIM.ConfigTableAction;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.service.cIM.inframgr.FileManagementUtil;
import com.cloupia.service.cIM.inframgr.forms.wizard.Page;
import com.cloupia.service.cIM.inframgr.forms.wizard.PageIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaPageAction;

/**
 * This is the controller portion of the action, where all the execution logic for the form is.
 *
 */
public class SimpleDummyAction extends CloupiaPageAction {

	private static Logger logger = Logger.getLogger(SimpleDummyAction.class);

	//need to provide a unique string to identify this form and action (note: prefix is module id, good practice)
	private static final String formId = "nimble.simple.dummy.form";
	private static final String ACTION_ID = "nimble.simple.dummy.action";
	//this is the label show in UI for this action
	private static final String label = "Dummy Action";
	
	/**
	 * @return this method return id for action field 
	 */
	@Override
	public String getActionId() {
		return ACTION_ID;
	}

	/**
	 * @return this method returns action form id 
	 */
	public String getFormId()
	{
		return formId;
	}
	
	/**
	 * @return this method  return label for action field 
	 */
	@Override
	public String getLabel() {
		return label;
	}
	
	/**
	 *@return action type for any UI actions        
	 */
	@Override
	public int getActionType() {
		
		return ConfigTableAction.ACTION_TYPE_POPUP_FORM;
	}
	/** 
	 * @return return true when a row needs to be selected for this action to proceed and false if row selection is not required
     */
	@Override
	public boolean isSelectionRequired() {
	
		return false;
	}
	/**
	 * @return return true when a double click action performed for this action to proceed and false if double click action performed not required
     */
	@Override
	public boolean isDoubleClickAction() {
		return false;
	}
	/** 
	 * @return return true when a double click a action and open a child report open  for this action to proceed and false if no child report or no click action  is required
     */
	@Override
	public boolean isDrilldownAction() {
		return false;
	}
	/**
	 * this is where you define the layout of the form page
	 * the easiest way to do this is to use this "bind" method
	 * 
	 * @param pagecontext
	 * @param reportcontext
	 */
	@Override
	public void definePage(Page page, ReportContext context) {
		
		page.bind(formId, SimpleDummyForm.class);
	}
	 /**
     *  This method loads the form fields and field data to the page.
	 *
	 * @param pagecontext
	 * @param reportcontext
	 * @param wizardsession
     */
	@Override
	public void loadDataToPage(Page page, ReportContext context, WizardSession session) throws Exception {
		
		String query = context.getId();
		logger.info("NimbleNimbleNimble = " + query);
		
		SimpleDummyForm form = new SimpleDummyForm();
		form.setName("dummy name - hardcoded for demo purposes");
		
		session.getSessionAttributes().put(formId, form);
		page.marshallFromSession(formId);
		
		String dir = FileManagementUtil.getDir(page.getSession());
	
		page.setSourceReport(formId + ".uploadFileName", dir);	
	}
	/**
	 * This method do the validation for the form fields.
	 * 
	 * @param pagecontext
	 * @param reportcontext
	 * @param wizardsession
	 * @return ststus 
	 */
	
	@Override
	public int validatePageData(Page page, ReportContext context, WizardSession session) throws Exception {
		
		Object obj = page.unmarshallToSession(formId);
		SimpleDummyForm form = (SimpleDummyForm) obj;
		logger.info("Nimble Nimble Nimble " + form.getName() + " : " + form.getValue());
		int number = form.getNumber();
		logger.info("Nimble Nimble Nimble number = " + number);
		boolean boolValue = form.getBoolType();
		logger.info("Nimble Nimble Nimble bool = " + boolValue);
		String password = form.getPassword();
		logger.info("Nimble Nimble Nimble password = " + password);
		long dateLong = form.getDateLong();
		logger.info("Nimble Nimble Nimble date long = " + dateLong);
		long dateTime = form.getDateTime();
		logger.info("Nimble Nimble Nimble date time = " + dateTime);
		String listValue = form.getListValue();
		logger.info("Nimble Nimble Nimble list value = " + listValue);

	
		if (form.getUploadFileName() != null && !form.getUploadFileName().equals("")) {
			String uploadFile = FileManagementUtil.getDir(page.getSession()) + form.getUploadFileName();
			try {
				byte[] fileData = FileManagementUtil.getBytesFromFile(uploadFile);
				logger.info("Nimble Nimble Nimble fileData.length = " + fileData.length);
				//when you're done with the file, make sure you clean up!
				FileManagementUtil.cleanupDirectory(page.getSession());
			} catch (Exception e) {
				throw new Exception("Error locating uploaded file.");
			}
		}
		
		String[] selectedTableValues = form.getPlainTabularValues();
		for (String selected:selectedTableValues) {
			logger.info("Nimble Nimble Nimble selected tabular value = " + selected);
		}

		String name = form.getName();
		if (name.equals("fail")) {
			//to signal an error you can throw an exception OR return error status
			page.setPageMessage("simple dummy action failed!");
			return PageIf.STATUS_ERROR;
		} else {
			//if you want to display a message to the user, use page.SetPageMessage(...)
			page.setPageMessage("simple dummy action was completed!");
			//return this constant so the UI will display to the user, the action has succeeded.
			return PageIf.STATUS_OK;
		}
	}
	/**
     * @return action title 
     */
	@Override
	public String getTitle() {
		return label;
	}


}
