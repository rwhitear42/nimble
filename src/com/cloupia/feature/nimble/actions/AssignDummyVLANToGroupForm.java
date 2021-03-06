/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.actions;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.model.cIM.UserGroupsLOVProvider;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

/**
 * This form object demonstrates how you would go about developing a form to handle resource limiting.
 * I want to assign/associate a VLAN from my Dummy Device to a group I created in UCSD.
 *
 */
//note table name is prefixed with my module id, this is always good practice, also remember to add an
//entry for this in your jdo.files if you want it to be persistable!!!!
@PersistenceCapable(detachable = "true", table = "nimble_vlan_to_group")
public class AssignDummyVLANToGroupForm {
	
	//i need to identify which dummy vlan is being assigned, so this field will be used for that purpose
	@Persistent
	@FormField(label = "VLAN ID", help = "VLAN ID", mandatory = true, editable = false)
 	private String vlanID;

	//this is important, the UserGroupsLOVProvider.NAME LOV Provider is a built in provider that lists all the
	//groups in the system.  so you should use this whenever you are developing a form for the purposes of
	//resource limiting
	@Persistent
    @FormField(label = "Group", help = "Group Name", mandatory = true, type = FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, lovProvider = UserGroupsLOVProvider.NAME)
    private int groupId;
	
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getVlanID() {
		return vlanID;
	}

	public void setVlanID(String vlanID) {
		this.vlanID = vlanID;
	}

}
