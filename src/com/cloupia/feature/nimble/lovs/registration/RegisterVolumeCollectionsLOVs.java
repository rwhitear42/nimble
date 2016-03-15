package com.cloupia.feature.nimble.lovs.registration;

import java.util.HashMap;

import com.cloupia.feature.nimble.lovs.NimbleVolumeCollectionsLovProvider;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputFieldTypeDeclaration;
import com.cloupia.service.cIM.inframgr.customactions.WorkflowInputTypeRegistry;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderRegistry;

public class RegisterVolumeCollectionsLOVs {

	
	private HashMap<String,String> volCollMap = new HashMap<>();
	
	private String arrayName = "";
	
	public RegisterVolumeCollectionsLOVs( HashMap<String,String> volCollMap, String arrayName ) {
		this.volCollMap = volCollMap;
		this.arrayName = arrayName;
	}
	
	public void registerWFInputs() {
	
		WorkflowInputTypeRegistry volumeCollectionsInputType = WorkflowInputTypeRegistry.getInstance();
	
		volumeCollectionsInputType.addDeclaration( new WorkflowInputFieldTypeDeclaration( 
																		this.arrayName + "_NimbleVolumeCollectionsLOV", 
																		this.arrayName + " Nimble Volume Collections LOV", 
																		FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, 
																		this.arrayName + NimbleVolumeCollectionsLovProvider.NAME ) );
		
		NimbleVolumeCollectionsLovProvider nvclp = new NimbleVolumeCollectionsLovProvider( this.volCollMap );
		
		LOVProviderRegistry.getInstance().registerProvider(	this.arrayName + NimbleVolumeCollectionsLovProvider.NAME, nvclp );

	}

	

	public HashMap<String, String> getVolCollMap() {
		return volCollMap;
	}

	public void setVolCollMap(HashMap<String, String> volCollMap) {
		this.volCollMap = volCollMap;
	}

	public String getArrayName() {
		return arrayName;
	}

	public void setArrayName(String arrayName) {
		this.arrayName = arrayName;
	}	

}
