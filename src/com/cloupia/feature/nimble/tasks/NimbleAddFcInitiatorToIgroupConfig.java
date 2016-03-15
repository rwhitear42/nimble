package com.cloupia.feature.nimble.tasks;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.feature.nimble.constants.NimbleConstants;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

/**
 * Nimble Add Fibre Channel Initiator to Initiator Group configuration class. Persists database entries
 * for Nimble array ipAddress, username, password, FC initiator group name, FC initiator alias and WWPN
 * inputs.
 * 
 * @author rwhitear@cisco.com
 *
 * @version 1.0
 */
@PersistenceCapable(detachable = "true", table = "nimble_addfcinitiatortoigrouptask")
public class NimbleAddFcInitiatorToIgroupConfig implements TaskConfigIf {

	public static final String displayLabel = "Nimble Add Fibre Channel Initiator to Initiator Group";
	@Persistent
	private long configEntryId;
	@Persistent
	private long actionId;
	
	@FormField(label = "Nimble Array IP", help = "Nimble Array IP Address", mandatory = true)
	@UserInputField(type = WorkflowInputFieldTypeDeclaration.IPADDRESS)
	@Persistent
	private String	ipAddress = "";

	@FormField(label = "Nimble Username", help = "Nimble username", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String	username;

	@FormField(label = "Password", help = "Password", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_PASSWORD)
	@UserInputField(type = NimbleConstants.PASSWORD)
	@Persistent
	private String	password;

	@FormField(label = "Nimble Initiator Group Name", help = "Enter Nimble Initiator Group Name", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String	initiatorGroupName;

	@FormField(label = "Nimble Initiator Alias", help = "Enter Nimble Initiator Alias", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String	alias;
	
	@FormField(label = "Nimble WWPN", help = "Enter Nimble WWPN (e.g. 11:22:33:44:55:66:77:88)", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String	wwpn;
	
	
	@Override
	public long getActionId() {
		return actionId;
	}

	@Override
	public long getConfigEntryId() {
		return configEntryId;
	}

	@Override
	public String getDisplayLabel() {
		return displayLabel;
	}

	@Override
	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

	@Override
	public void setConfigEntryId(long configEntryId) {
		this.configEntryId = configEntryId;
	}

	/**
	 * @return Nimble array username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username Set Nimble array username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return Nimble array password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password Set Nimble array password.
	 */

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Nimble array IP address.
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress Set Nimble array IP address.
	 */

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	/**
	 * @return Nimble array fibre channel initiator group name.
	 */
	public String getInitiatorGroupName() {
		return initiatorGroupName;
	}

	/**
	 * @param initiatorGroupName Set Nimble array initiator group name.
	 */
	public void setInitiatorGroupName(String initiatorGroupName) {
		this.initiatorGroupName = initiatorGroupName;
	}

	/**
	 * @return Nimble array fibre channel initiator alias.
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias Set Nimble array fibre channel initiator alias.
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return Nimble array fibre channel initiator worldwide port name.
	 */
	public String getWwpn() {
		return wwpn;
	}

	public void setWwpn(String wwpn) {
		this.wwpn = wwpn;
	}
	
}
