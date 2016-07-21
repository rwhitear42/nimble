/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.tasks;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.feature.nimble.constants.NimbleConstants;
import com.cloupia.feature.nimble.lovs.NimbleSanProtocolLovProvider;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

@PersistenceCapable(detachable = "true", table = "nimble_nimblecreateigrouptask")
public class NimbleCreateIgroupConfig implements TaskConfigIf {

	public static final String displayLabel = "Nimble Create Initiator Group";
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
	
	@FormField(label = "Protocol", help = "iSCSI or Fibre Channel", type=FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, lovProvider=NimbleSanProtocolLovProvider.NIMBLE_SAN_PROTOCOL_LOV_PROVIDER)
	@UserInputField(type = NimbleConstants.Nimble_NIMBLE_SAN_PROTOCOL_LOV_NAME)
	@Persistent
	private String	sanProtocol;

	@FormField(label = "Nimble Initiator Group Name", help = "Enter Nimble Initiator Group Name", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String	initiatorGroupName;

	@FormField(label = "Nimble Initiator Group Description", help = "Enter Nimble Initiator Group Description", mandatory = false, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String	description;
	
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public String getSanProtocol() {
		return sanProtocol;
	}

	public void setSanProtocol(String sanProtocol) {
		this.sanProtocol = sanProtocol;
	}

	public String getInitiatorGroupName() {
		return initiatorGroupName;
	}

	public void setInitiatorGroupName(String initiatorGroupName) {
		this.initiatorGroupName = initiatorGroupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
