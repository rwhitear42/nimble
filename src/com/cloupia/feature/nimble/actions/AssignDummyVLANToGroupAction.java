package com.cloupia.feature.nimble.actions;

import java.util.List;

import org.apache.log4j.Logger;

import com.cloupia.fw.objstore.ObjStore;
import com.cloupia.fw.objstore.ObjStoreHelper;
import com.cloupia.model.cIM.ConfigTableAction;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.service.cIM.inframgr.forms.wizard.Page;
import com.cloupia.service.cIM.inframgr.forms.wizard.PageIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaPageAction;

/**
 * This is the controller portion of the action, where all the execution logic for the form is.  This form simply
 * populates UI fields with data from DB if available and updates DB tables if required.
 *
 */
public class AssignDummyVLANToGroupAction extends CloupiaPageAction {
	
	private static Logger logger = Logger.getLogger(AssignDummyVLANToGroupAction.class);

	//need to provide a unique string to identify this form and action
	private static final String formId = "nimble.assign.vlan.group.form";
	private static final String ACTION_ID = "nimble.assign.vlan.group.action";
	//this is the label show in UI for this action
	private static final String label = "Assign To Group";
	
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
		
		return true;
	}
	/**
	 * @return return true if action required to perform when double clicking a row
	 */
	@Override
	public boolean isDoubleClickAction() {
		return false;
	}
	/** 
	 * @return return true if child report has to be drilled down when double clicking a row
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
		
		page.bind(formId, AssignDummyVLANToGroupForm.class);
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
		
		AssignDummyVLANToGroupForm form = new AssignDummyVLANToGroupForm();
		form.setVlanID(query);
		
		session.getSessionAttributes().put(formId, form);
		page.marshallFromSession(formId);
	}
	/**
	 * This method do the validation for the form fields.
	 * 
	 * @param pagecontext
	 * @param reportcontext
	 * @param wizardsession
	 * @return status 
	 */
	
	@Override
	public int validatePageData(Page page, ReportContext context, WizardSession session) throws Exception {
		ObjStore<AssignDummyVLANToGroupForm> store = ObjStoreHelper.getStore(AssignDummyVLANToGroupForm.class);
        
		Object obj = page.unmarshallToSession(formId);
		AssignDummyVLANToGroupForm form = (AssignDummyVLANToGroupForm) obj;
		
		AssignDummyVLANToGroupForm modded = null;
		List<AssignDummyVLANToGroupForm> objs = store.queryAll();
		for (AssignDummyVLANToGroupForm o:objs) {
			if (o.getVlanID().equals(form.getVlanID())) {
				o.setGroupId(form.getGroupId());
				modded = o;
				break;
			}
		}
		
		if (modded != null) {
			store.modifySingleObject("vlanID == '" + form.getVlanID() + "'", modded);
		} else {
			store.insert(form);
		}
		
		return PageIf.STATUS_OK;
	}
	/**
     * @return action title 
     */
	@Override
	public String getTitle() {
		return this.label;
	}

	
}
