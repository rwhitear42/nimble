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
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

@PersistenceCapable(detachable = "true", table = "nimble_nimblecreatevolumetask")
public class NimbleCreateVolumeConfig implements TaskConfigIf {

	public static final String displayLabel = "Nimble Create Volume";
	@Persistent
	private long configEntryId;
	@Persistent
	private long actionId;
	
	@FormField(label = "Nimble Array IP", help = "Nimble Array IP Address", mandatory = true)
	@UserInputField(type = WorkflowInputFieldTypeDeclaration.IPADDRESS)
	@Persistent
	private String             ipAddress         = "";

	@FormField(label = "Nimble Username", help = "Nimble username", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             username;

	@FormField(label = "Password", help = "Password", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_PASSWORD)
	@UserInputField(type = NimbleConstants.PASSWORD)
	@Persistent
	private String             password;

	@FormField(label = "Nimble Volume Name", help = "Enter Nimble Volume Name", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             volumeName;

	@FormField(label = "Nimble Volume Size GB", help = "Enter Nimble Volume Size in GB", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             volumeSizeGB;
	
	@FormField(label = "Nimble Volume Description", help = "Enter Nimble Volume Description", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             description;
	
	@FormField(label = "Nimble Volume Performance Policy", help = "Select an Appropriate Performance Policy", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             perfPolicy;
	
	@FormField(label = "Nimble Volume Data Encryption", help = "Should Volume be Encrypted", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_BOOLEAN)
	@UserInputField(type = NimbleConstants.BOOLEAN)
	@Persistent
	private boolean            dataEncryption;
	
	@FormField(label = "Nimble Volume Cache Pinning", help = "Should Volume be Pinned in Cache", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_BOOLEAN)
	@UserInputField(type = NimbleConstants.BOOLEAN)
	@Persistent
	private boolean            cachePinning; 
	
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
	
	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}
	
	public String getVolumeSizeGB() {
		return volumeSizeGB;
	}

	public void setVolumeSizeGB(String volumeSizeGB) {
		this.volumeSizeGB = volumeSizeGB;
	}
	
	public String getVolumeDescription() {
		return description;
	}

	public void setVolumeDescription(String description) {
		this.description = description;
	}
	
	public String getVolumePerfPolicy() {
		return perfPolicy;
	}

	public void setVolumePerfPolicy(String perfPolicy) {
		this.perfPolicy = perfPolicy;
	}
	
	public boolean getVolumeDataEncryption() {
		return dataEncryption;
	}

	public void setVolumeDataEncryption(boolean dataEncryption) {
		this.dataEncryption = dataEncryption;
	}
	
	public boolean getVolumeCachePinning() {
		return cachePinning;
	}

	public void setVolumeDataCachePinning(boolean cachePinning) {
		this.cachePinning = cachePinning;
	}

}
