

package com.cloupia.feature.nimble.tasks;

import javax.jdo.annotations.PersistenceCapable;


import javax.jdo.annotations.Persistent;

import com.cloupia.feature.nimble.constants.NimbleConstants;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;
import com.cloupia.feature.nimble.lovs.NimbleSanProtocolLovProvider;
import com.cloupia.feature.nimble.lovs.SimpleTabularProvider;
import com.cloupia.model.cIM.FormFieldDefinition;

/**
 * This class is used for multiselect workflow inputs and task inputs ,user can select multiple inputs whenever they want.
 */
@PersistenceCapable(detachable = "true", table = "nimble_multiselecttabularconfig")
public class NimbleMultiSelectTabularConfig implements TaskConfigIf {

	public static final String displayLabel = "NimbleMultiSelectTabularConfig";
	@Persistent
	private long configEntryId;
	@Persistent
	private long actionId;
	
	/**
	 * This field is multi select field user can select multi no of tabular values as input.
	 */
	
	@FormField(label = "TabularValues", help = "TabularValues ", multiline = true,type=FormFieldDefinition.FIELD_TYPE_TABULAR_POPUP, table=SimpleTabularProvider.SIMPLE_TABULAR_PROVIDER)
	@UserInputField(type = NimbleConstants.Nimble_MULTI_SELECT_TABULARVALUE_NAME)
	@Persistent
	private String tabularvalues;
	
	@FormField(label = "LovValues", help = "LOVValues ", multiline = false,type=FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, lovProvider=NimbleSanProtocolLovProvider.NIMBLE_SAN_PROTOCOL_LOV_PROVIDER)
	@UserInputField(type = NimbleConstants.Nimble_NIMBLE_SAN_PROTOCOL_LOV_NAME)
	@Persistent
	private String lovValues;
	
	public String getLovValues() {
		return lovValues;
	}

	public void setLovValues(String lovValues) {
		this.lovValues = lovValues;
	}

	public String getTabularValues() {
		return tabularvalues;
	}

	public void setTabularValues(String tabularvalues) {
		this.tabularvalues = tabularvalues;
	}

	/**
	 * This method return actionId usiing which specific action can performed.
	 */
	@Override
	public long getActionId() {
		return actionId;
	}
	/**
	 * This method returns configentryId which is unique for each task.
	 */
	@Override
	public long getConfigEntryId() {
		return configEntryId;
	}
	
  
	/**
	 * This method set the actionId.
	 */
	@Override
	public void setActionId(long actionId) {
		this.actionId = actionId;
	}
	/**
	 * This method set the configentryId.
	 */
	@Override
	public void setConfigEntryId(long configEntryId) {
		this.configEntryId = configEntryId;
	}
	@Override
	public String getDisplayLabel() {
		// TODO Auto-generated method stub
		return displayLabel;
	}
	public NimbleMultiSelectTabularConfig(){
		
	}

	

}
