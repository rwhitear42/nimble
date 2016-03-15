package com.cloupia.feature.nimble.lovs.registration;

import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputTypeRegistry;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderRegistry;

import java.util.HashMap;

import com.cloupia.feature.nimble.lovs.NimblePerformancePoliciesLovProvider;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;

public class PerformancePoliciesLOVWorkflowInputType {

	public static void registerWFInputs(){
		
		registerPerformancePoliciesLOVWorkflowInputType();
		
	}
	
	private static void registerPerformancePoliciesLOVWorkflowInputType(){
		
		WorkflowInputTypeRegistry perfPolInputType = WorkflowInputTypeRegistry.getInstance();
		
		perfPolInputType.addDeclaration( new WorkflowInputFieldTypeDeclaration( "NimblePerfPoliciesLOV", 
																				"Nimble Performance Policies LOV", 
																				FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, 
																				NimblePerformancePoliciesLovProvider.NAME ) );
		
		HashMap<String,String> myMap = new HashMap<>();
		
		myMap.put("one", "one");
		myMap.put("two", "two");
		myMap.put("three", "three");
		myMap.put("four", "four");
		
		
		NimblePerformancePoliciesLovProvider nppp = new NimblePerformancePoliciesLovProvider(myMap);
		
		LOVProviderRegistry.getInstance().registerProvider(	NimblePerformancePoliciesLovProvider.NAME, nppp );
		
	}
}
