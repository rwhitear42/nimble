/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.lovs.registration;

import java.util.HashMap;

import com.cloupia.feature.nimble.lovs.NimbleVolumesLovProvider;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputTypeRegistry;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderRegistry;

public class RegisterVolumesLOVs {
	
	private HashMap<String,String> volMap = new HashMap<>();
	
	private String arrayName = "";
	
	public RegisterVolumesLOVs( HashMap<String,String> volMap, String arrayName ) {
		this.volMap = volMap;
		this.arrayName = arrayName;
	}
	
	public void registerWFInputs() {
	
		WorkflowInputTypeRegistry volumesInputType = WorkflowInputTypeRegistry.getInstance();
	
		volumesInputType.addDeclaration( new WorkflowInputFieldTypeDeclaration( this.arrayName + "_NimbleVolumesLOV", 
																				this.arrayName + " Nimble Volumes LOV", 
																				FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, 
																				this.arrayName + NimbleVolumesLovProvider.NAME ) );
		
		NimbleVolumesLovProvider nvlp = new NimbleVolumesLovProvider( this.volMap );
		
		LOVProviderRegistry.getInstance().registerProvider(	this.arrayName + NimbleVolumesLovProvider.NAME, nvlp );

	}

	

	public HashMap<String, String> getVolMap() {
		return volMap;
	}

	public void setVolMap(HashMap<String, String> volMap) {
		this.volMap = volMap;
	}

	public String getArrayName() {
		return arrayName;
	}

	public void setArrayName(String arrayName) {
		this.arrayName = arrayName;
	}	

}
