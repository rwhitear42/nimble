package com.cloupia.feature.nimble.multiselecttabularreports;

import org.apache.log4j.Logger;

import com.cloupia.model.cIM.ConfigTableAction;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.service.cIM.inframgr.forms.wizard.Page;
import com.cloupia.service.cIM.inframgr.forms.wizard.PageIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaPageAction;

public class MultiSelectTabularReportAction extends CloupiaPageAction {

	private static Logger logger = Logger.getLogger(MultiSelectTabularReportAction.class);

	// need to provide a unique string to identify this form and action (note:
	// prefix is module id, good practice)
	private static final String formId = "Nimble.multiselect.tabular.form";
	private static final String ACTION_ID = "Nimble.multiselect.tabular.action";
	// this is the label show in UI for this action
	private static final String label = "Multi Select Action";

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
	public String getFormId() {
		return formId;
	}

	/**
	 * @return this method return label for action field
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * @return action type for any UI actions
	 */
	@Override
	public int getActionType() {

		return ConfigTableAction.ACTION_TYPE_POPUP_FORM;
	}

	/**
	 * @return return true when a row needs to be selected for this action to
	 *         proceed and false if row selection is not required
	 */
	@Override
	public boolean isSelectionRequired() {

		return false;
	}

	/**
	 * @return return true when a double click action performed for this action
	 *         to proceed and false if double click action performed not
	 *         required
	 */
	@Override
	public boolean isDoubleClickAction() {
		return false;
	}

	/**
	 * @return return true when a double click a action and open a child report
	 *         open for this action to proceed and false if no child report or
	 *         no click action is required
	 */
	@Override
	public boolean isDrilldownAction() {
		return false;
	}

	/**
	 * this is where you define the layout of the form page the easiest way to
	 * do this is to use this "bind" method
	 * 
	 * @param pagecontext
	 * @param reportcontext
	 */
	@Override
	public void definePage(Page page, ReportContext context) {

		page.bind(formId, MultiSelectTabularForm.class);
	}

	/**
	 * This method loads the form fields and field data to the page.
	 *
	 * @param pagecontext
	 * @param reportcontext
	 * @param wizardsession
	 */
	@Override
	public void loadDataToPage(Page page, ReportContext context, WizardSession session) throws Exception {

		String query = context.getId();
		logger.info("NimbleNimbleNimble = " + query);

		MultiSelectTabularForm form = new MultiSelectTabularForm();

		session.getSessionAttributes().put(formId, form);
		page.marshallFromSession(formId);

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
		MultiSelectTabularForm form = (MultiSelectTabularForm) obj;
		// logger.info("Nimble Nimble Nimble " + form.getName() + " : " +
		// form.getValue());

		// if you want to display a message to the user, use
		// page.SetPageMessage(...)
		page.setPageMessage("multi select action was completed!");
		// return this constant so the UI will display to the user, the action
		// has succeeded.
		return PageIf.STATUS_OK;

	}

	/**
	 * @return action title
	 */
	@Override
	public String getTitle() {
		return label;
	}
}
