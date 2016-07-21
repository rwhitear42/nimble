/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.workflow;

import org.apache.log4j.Logger;


import com.cloupia.feature.nimble.accounts.NimbleAccountSampleReportImpl;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionHandlerIf;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionRegistry;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.cloupia.service.cIM.inframgr.customactions.WFTaskDefinition;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputTypeRegistry;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderRegistry;
import com.cloupia.service.cIM.inframgr.forms.wizard.TabularFieldRegistry;
import com.cloupia.feature.nimble.workflow.InputTypeDeclaration;
import com.cloupia.feature.nimble.constants.NimbleConstants;
import com.cloupia.feature.nimble.lovs.SimpleTabularProvider;

/**
 * 
 * @author anadipin
 * Using this class we can create custom workflow input types.
 *
 */
public class InputTypeDeclaration {
	
	private static Logger logger = Logger.getLogger(InputTypeDeclaration.class);
	
	public InputTypeDeclaration(){}
	
	/**
	 * This method is use to register the workflow Input Types
	 */
	public static void registerWFInputs(){
		
		registerSampleLOVWorkflowInputType();
		registerSampleTabularWorkflowInputType();
		registerSampleTabularMultiWorkflowInputType();
		
		
	}
/**
 * This method is use to create workflow Input Type of LOV(list of values)
 */
	private static void registerSampleLOVWorkflowInputType(){
		
		WorkflowInputTypeRegistry sampleInputType = WorkflowInputTypeRegistry.getInstance();
		sampleInputType.addDeclaration(new WorkflowInputFieldTypeDeclaration(
				"SampleLOVInput", "Sample LOV Input",
				FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, "sampleInputTypeLOV"));
		
		
		 LOVProviderRegistry.getInstance()
         .registerProvider(UserGroupsLOVProvider.NAME,
         new UserGroupsLOVProvider());
		 
		
		
	}
	/**
	 * This method is use to create workflow Input Type of Tabular
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
	private static void registerSampleTabularMultiWorkflowInputType(){
		String fieldName = SimpleTabularProvider.SIMPLE_TABULAR_PROVIDER;
		
		WorkflowInputTypeRegistry sampleInputType = WorkflowInputTypeRegistry.getInstance();
		sampleInputType.addDeclaration(new WorkflowInputFieldTypeDeclaration(
				NimbleConstants.Nimble_MULTI_SELECT_TABULARVALUE_NAME,
				"sample multi select",
				FormFieldDefinition.FIELD_TYPE_TABULAR_POPUP,
				fieldName, true));
		
		TabularFieldRegistry.getInstance().registerTabularField(fieldName,
				SimpleTabularProvider.class, "0", "0");
	}
	
}
