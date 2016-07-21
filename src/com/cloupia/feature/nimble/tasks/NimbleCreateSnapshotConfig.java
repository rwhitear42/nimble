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

@PersistenceCapable(detachable = "true", table = "nimble_nimblecreatesnapshottask")
public class NimbleCreateSnapshotConfig implements TaskConfigIf {

	public static final String displayLabel = "Nimble Create Snapshot";
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

	@FormField(label = "Nimble Snapshot Name", help = "Enter Nimble Snapshot Name", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             snapshotName;
	
	@FormField(label = "Nimble Snapshot Description", help = "Enter Nimble Snapshot Description", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@UserInputField(type = NimbleConstants.GENERIC_TEXT_INPUT)
	@Persistent
	private String             description;
	
	@FormField(label = "Online", help = "Should Volume be Encrypted?", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_BOOLEAN)
	@UserInputField(type = NimbleConstants.BOOLEAN)
	@Persistent
	private boolean            online;
	
	@FormField(label = "Writable", help = "Should Volume be Pinned in Cache?", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_BOOLEAN)
	@UserInputField(type = NimbleConstants.BOOLEAN)
	@Persistent
	private boolean            writable;

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
	
	public String getSnapshotName() {
		return snapshotName;
	}

	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
	}
	
	public String getSnapshotDescription() {
		return description;
	}

	public void setSnapshotDescription(String description) {
		this.description = description;
	}
	
	public boolean getSnapshotOnline() {
		return online;
	}

	public void setSnapshotOnline(boolean online) {
		this.online = online;
	}
	
	public boolean getSnapshotWritable() {
		return writable;
	}

	public void setSnapshotWritable(boolean writable) {
		this.writable = writable;
	}

}
