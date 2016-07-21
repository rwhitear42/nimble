/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.lovs.registration;

import java.util.HashMap;

import com.cloupia.feature.nimble.lovs.NimblePerformancePoliciesLovProvider;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputTypeRegistry;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderRegistry;

public class RegisterPerformancePoliciesLOVs {
	
	private HashMap<String,String> pmap = new HashMap<>();
	
	private String arrayName = "";
	
	public RegisterPerformancePoliciesLOVs( HashMap<String,String> pmap, String arrayName ) {
		this.pmap = pmap;
		this.arrayName = arrayName;
	}
	
	public void registerWFInputs() {
	
		WorkflowInputTypeRegistry perfPolInputType = WorkflowInputTypeRegistry.getInstance();
	
		perfPolInputType.addDeclaration( new WorkflowInputFieldTypeDeclaration( this.arrayName + "_NimblePerfPoliciesLOV", 
																				this.arrayName + " Nimble Performance Policies LOV", 
																				FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, 
																				this.arrayName + NimblePerformancePoliciesLovProvider.NAME ) );
		
		NimblePerformancePoliciesLovProvider nppp = new NimblePerformancePoliciesLovProvider( this.pmap );
		
		LOVProviderRegistry.getInstance().registerProvider(	this.arrayName + NimblePerformancePoliciesLovProvider.NAME, nppp );

	}

	public HashMap<String, String> getPmap() {
		return pmap;
	}

	public void setPmap(HashMap<String, String> pmap) {
		this.pmap = pmap;
	}

	public String getArrayName() {
		return arrayName;
	}

	public void setArrayName(String arrayName) {
		this.arrayName = arrayName;
	}	

}
