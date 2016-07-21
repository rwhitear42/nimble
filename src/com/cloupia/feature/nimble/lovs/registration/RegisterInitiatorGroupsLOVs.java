/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.lovs.registration;

import java.util.HashMap;

import com.cloupia.feature.nimble.lovs.NimbleInitiatorGroupsLovProvider;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputTypeRegistry;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderRegistry;

public class RegisterInitiatorGroupsLOVs {

	private HashMap<String,String> iGroupMap = new HashMap<>();
	
	private String arrayName = "";
	
	public RegisterInitiatorGroupsLOVs( HashMap<String,String> iGroupMap, String arrayName ) {
		this.iGroupMap = iGroupMap;
		this.arrayName = arrayName;
	}
	
	public void registerWFInputs() {
	
		WorkflowInputTypeRegistry iGroupInputType = WorkflowInputTypeRegistry.getInstance();
	
		iGroupInputType.addDeclaration( new WorkflowInputFieldTypeDeclaration( this.arrayName + "_NimbleInitiatorGroupsLOV", 
																			this.arrayName + " Nimble Initiator Groups LOV", 
																			FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, 
																			this.arrayName + NimbleInitiatorGroupsLovProvider.NAME ) );
		
		NimbleInitiatorGroupsLovProvider niglp = new NimbleInitiatorGroupsLovProvider( this.iGroupMap );
		
		LOVProviderRegistry.getInstance().registerProvider(	this.arrayName + NimbleInitiatorGroupsLovProvider.NAME, niglp );

	}


	public HashMap<String, String> getiGroupMap() {
		return iGroupMap;
	}

	public void setiGroupMap(HashMap<String, String> iGroupMap) {
		this.iGroupMap = iGroupMap;
	}

	public String getArrayName() {
		return arrayName;
	}

	public void setArrayName(String arrayName) {
		this.arrayName = arrayName;
	}	

}
