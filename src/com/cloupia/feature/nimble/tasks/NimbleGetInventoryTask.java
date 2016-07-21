/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.tasks;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.cloupia.feature.nimble.lovs.registration.RegisterInitiatorGroupsLOVs;
import com.cloupia.feature.nimble.lovs.registration.RegisterPerformancePoliciesLOVs;
import com.cloupia.feature.nimble.lovs.registration.RegisterVolumeCollectionsLOVs;
import com.cloupia.feature.nimble.lovs.registration.RegisterVolumesLOVs;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.arrays.GetArrays;
import com.rwhitear.nimbleRest.arrays.ParseArraysDetailResponse;
import com.rwhitear.nimbleRest.arrays.json.GetArraysDetailObject;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
import com.rwhitear.nimbleRest.exceptions.ArraysException;
import com.rwhitear.nimbleRest.initiatorGroups.GetInitiatorGroups;
import com.rwhitear.nimbleRest.initiatorGroups.ParseInitiatorGroupsDetailResponse;
import com.rwhitear.nimbleRest.initiatorGroups.json.GetInitiatorGroupsDetailObject;
import com.rwhitear.nimbleRest.performancePolicies.GetPerformancePolicies;
import com.rwhitear.nimbleRest.performancePolicies.json.ParsePerfPolicyDetailResponse;
import com.rwhitear.nimbleRest.performancePolicies.json.PerfPoliciesDetailJsonObject;
import com.rwhitear.nimbleRest.volumeCollections.GetVolumeCollections;
import com.rwhitear.nimbleRest.volumeCollections.json.ParseVolCollectionsDetailResponse;
import com.rwhitear.nimbleRest.volumeCollections.json.VolCollectionsDetailJsonObject;
import com.rwhitear.nimbleRest.volumes.GetVolumes;
import com.rwhitear.nimbleRest.volumes.json.ParseVolumeDetailResponse;
import com.rwhitear.nimbleRest.volumes.json.VolumesDetailJsonObject;


public class NimbleGetInventoryTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( NimbleGetInventoryTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		NimbleGetInventoryConfig config = (NimbleGetInventoryConfig) context.loadConfigObject();

		
		
		String username = config.getUsername();
		String password = config.getPassword();
		String ipAddress = config.getIpAddress();
		
		String arrayName = "";
		

		// Retrieve Nimble array auth token.
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		
		// Get array name for preamble to LOV name.
		String getArraysResponse = new GetArrays(ipAddress, token).getDetail();
		
		GetArraysDetailObject arraysObj = new ParseArraysDetailResponse(getArraysResponse).parse();
		
		logger.info("arrays size: " + arraysObj.getData().size() );
		
		if( arraysObj.getData().size() != 1 ) {
			
			throw new ArraysException("Failed to retrieve array name.");
			
		} else {
			
			arrayName = arraysObj.getData().get(0).getName();
			
		}
		
		
		// Get Performance Policies.
		
		// Retrieve JSON response for detailed Performance Policy information.
		String perfPolicyJsonData = new GetPerformancePolicies(ipAddress, token).getDetail();
		
		logger.info("Performance Policy Detail JSON: " + perfPolicyJsonData );
		
		PerfPoliciesDetailJsonObject perfPolicyDetail = new ParsePerfPolicyDetailResponse(perfPolicyJsonData).parse();

		logger.info("Perf Policy Size: " + perfPolicyDetail.getData().size());
			
	
		HashMap<String,String> pmap = new HashMap<>();
		
		for( int i = 0; i < perfPolicyDetail.getData().size(); i++ ) {
			
			pmap.put( perfPolicyDetail.getData().get(i).getName(), perfPolicyDetail.getData().get(i).getName() );
			
		}
		
		new RegisterPerformancePoliciesLOVs( pmap, arrayName ).registerWFInputs();
		
		
		// Get Volumes.
		
		// Retrieve JSON response for detailed Volume information.
		String volumeJsonData = new GetVolumes(ipAddress, token).getDetail();
						
		VolumesDetailJsonObject volDetail = new ParseVolumeDetailResponse(volumeJsonData).parse();
				
		HashMap<String,String> volMap = new HashMap<>();
		
		for( int i = 0; i < volDetail.getData().size(); i++ ) {
			
			volMap.put( volDetail.getData().get(i).getName(), volDetail.getData().get(i).getName() );
			
		}

		new RegisterVolumesLOVs( volMap, arrayName ).registerWFInputs();
		
		
		// Get Volume Collections.
		
		// Retrieve JSON response for detailed volume collections information.
		String volCollectionJsonData = new GetVolumeCollections(ipAddress, token).getDetail();

		VolCollectionsDetailJsonObject volCollDetail = new ParseVolCollectionsDetailResponse(volCollectionJsonData).parse();
		
		HashMap<String,String> volCollMap = new HashMap<>();
		
		for( int i = 0; i < volCollDetail.getData().size(); i++ ) {
			
			volCollMap.put( volCollDetail.getData().get(i).getName(), volCollDetail.getData().get(i).getName());
			
		}

		new RegisterVolumeCollectionsLOVs( volCollMap, arrayName ).registerWFInputs();
		
		
		// Get Initiator Groups.
		
		String iGroupsResponse = new GetInitiatorGroups(ipAddress, token).getDetail();
		
		logger.info("Initiator Groups Response: " +iGroupsResponse );
		
		GetInitiatorGroupsDetailObject iGroupObj = new ParseInitiatorGroupsDetailResponse(iGroupsResponse).parse();

		HashMap<String,String> iGroupsMap = new HashMap<>();
		
		for( int i = 0; i < iGroupObj.getData().size(); i++ ) {
			
			iGroupsMap.put( iGroupObj.getData().get(i).getName(), iGroupObj.getData().get(i).getName());
			
		}

		new RegisterInitiatorGroupsLOVs( iGroupsMap, arrayName ).registerWFInputs();
		
		
		
		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		
		/*
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Nimble Create Volume", 
				"Rollback Create Nimble Volume " + config.getVolumeName(), 
				new NimbleDeleteVolumeTask().getTaskName(), new NimbleDeleteVolumeConfig(config));
		
        try
        {
            context.saveOutputValue(NimbleConstants.NIMBLE_VOLUME_NAME, config.getVolumeName());

        } catch (Exception e)
        {
            actionLogger.addWarning("Failed to set output variable(s): " + e.getMessage());
        }
        */
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new NimbleGetInventoryConfig();
	}

	@Override
	public String getTaskName() {
		return NimbleGetInventoryConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		return null;
	}
	
	/*
	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		TaskOutputDefinition[] ops = new TaskOutputDefinition[1];
		//NOTE: If you want to use the output of this task as input to another task. Then the second argument 
		//of the output definition MUST MATCH the type of UserInputField in the config of the task that will
		//be receiving this output.  Take a look at HelloWorldConfig as an example.
		ops[0] = new TaskOutputDefinition( NimbleConstants.NIMBLE_VOLUME_NAME, NimbleConstants.GENERIC_TEXT_INPUT, "Three" );
		return ops;
	}
	*/
}
