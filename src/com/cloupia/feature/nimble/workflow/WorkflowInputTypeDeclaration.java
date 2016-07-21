/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.workflow;

import org.apache.log4j.Logger;


import com.cloupia.feature.nimble.accounts.NimbleAccountSampleReportImpl;
import com.cloupia.feature.nimble.lovs.NimbleVolumesLOVProviderOLD;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionHandlerIf;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionRegistry;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.cloupia.service.cIM.inframgr.customactions.WFTaskDefinition;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputTypeRegistry;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderRegistry;
import com.cloupia.service.cIM.inframgr.forms.wizard.TabularFieldRegistry;

/**
 * 
 * @author anadipin
 * Using this class we can create custom workflow input types.
 *
 */
public class WorkflowInputTypeDeclaration {
	
	private static Logger logger = Logger.getLogger(WorkflowInputTypeDeclaration.class);
	
	public WorkflowInputTypeDeclaration(){}
	
  /**
	* This method is used to register Workflow Input Types.
	* @return void This returns sum of numA and numB.
	*/
	public static void registerWFInputs(){
		
		registerSampleLOVWorkflowInputType();
		registerSampleTabularWorkflowInputType();
		registerNimbleVolumesLOVWorkflowInputType();
			
	}
	
  /**
	* This method is used to register Workflow LOV Type Inputs.
	* @return void
	*/
	private static void registerSampleLOVWorkflowInputType(){
		
		WorkflowInputTypeRegistry sampleInputType = WorkflowInputTypeRegistry.getInstance();
		sampleInputType.addDeclaration(new WorkflowInputFieldTypeDeclaration(
				"SampleLOVInput", "Sample LOV Input",
				FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, "sampleInputTypeLOV"));
				
		 LOVProviderRegistry.getInstance()
         .registerProvider(SampleLOVProvider.NAME,
         new SampleLOVProvider());	
	}
	
	// Register Nimble volumes LOV as a test. If this works, then it will
	// need to be modified to prepend the LOV with the array name for 
	// uniqueness.
	private static void registerNimbleVolumesLOVWorkflowInputType(){
		
		WorkflowInputTypeRegistry sampleInputType = WorkflowInputTypeRegistry.getInstance();
		sampleInputType.addDeclaration(new WorkflowInputFieldTypeDeclaration(
				"NimbleVolumesLOV", "Nimble Volumes LOV",
				FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, "nimbleVolumesLOV"));
				
		 LOVProviderRegistry.getInstance()
         .registerProvider(NimbleVolumesLOVProviderOLD.NAME,
         new NimbleVolumesLOVProviderOLD());	
	}
	
	
	/**
	* This method is used to register Workflow input of type tabular. 
	* @return void
	*/
	
	private static void registerSampleTabularWorkflowInputType(){
		String fieldName = "SampleInputTypeTabuler";
		
		WorkflowInputTypeRegistry sampleInputType = WorkflowInputTypeRegistry.getInstance();
		sampleInputType.addDeclaration(new WorkflowInputFieldTypeDeclaration(
                "SampleTabulerInput", "Sample Tabuler Input",
                FormFieldDefinition.FIELD_TYPE_TABULAR,fieldName));
		
		TabularFieldRegistry.getInstance().registerTabularField(fieldName,
				NimbleAccountSampleReportImpl.class, "0", "2");
	}
	
}
