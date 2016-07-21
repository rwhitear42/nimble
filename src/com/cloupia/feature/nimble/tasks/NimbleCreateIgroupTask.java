/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.tasks;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.cloupia.feature.nimble.constants.NimbleConstants;
import com.cloupia.feature.nimble.lovs.registration.RegisterInitiatorGroupsLOVs;
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
import com.rwhitear.nimbleRest.exceptions.InitiatorGroupException;
import com.rwhitear.nimbleRest.httpErrorHandling.json.ErrorResponseObject;
import com.rwhitear.nimbleRest.initiatorGroups.CreateInitiatorGroup;
import com.rwhitear.nimbleRest.initiatorGroups.GetInitiatorGroups;
import com.rwhitear.nimbleRest.initiatorGroups.ParseInitiatorGroupsDetailResponse;
import com.rwhitear.nimbleRest.initiatorGroups.json.GetInitiatorGroupsDetailObject;


public class NimbleCreateIgroupTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( NimbleCreateIgroupTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		NimbleCreateIgroupConfig config = (NimbleCreateIgroupConfig) context.loadConfigObject();

		
		String ipAddress 			= config.getIpAddress();
		String username 			= config.getUsername();
		String password 			= config.getPassword(); 
		String initiatorGroupName 	= config.getInitiatorGroupName();
		String description			= config.getDescription();
		String sanProtocol 			= config.getSanProtocol();
		
		// Retrieve Nimble array auth token.
		actionLogger.addInfo( "Retrieving authentication token." );
		
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		// Check that the initiator group doesn't already exist.
		String iGroupsResponse = new GetInitiatorGroups(ipAddress, token).getDetail();
		
		logger.info("Initiator Groups Response: " +iGroupsResponse );
		
		actionLogger.addInfo( "Checking that initiator group ["+ initiatorGroupName +"] doesn't already exist." );
		
		GetInitiatorGroupsDetailObject iGroupObj = new ParseInitiatorGroupsDetailResponse(iGroupsResponse).parse();

		for( int i=0; i < iGroupObj.getData().size(); i++ ) {
			
			if( iGroupObj.getData().get(i).getName().equals(initiatorGroupName) ) {
				
				throw new InitiatorGroupException("Initiator group [" +initiatorGroupName+ "] already exists." );
				
			}
			
		}

		// Initiator group doesn't exist. Go ahead and create the initiator group.
		actionLogger.addInfo( "Creating initiator group ["+ initiatorGroupName +"]." );
		
		CreateInitiatorGroup cig = new CreateInitiatorGroup(ipAddress, token);
		
		String createResp = cig.create(initiatorGroupName, description, sanProtocol);
		
		logger.info("Create Initiator Group Response: " + createResp);

		actionLogger.addInfo("HTTP status code: " + cig.getHttpStatusCode() );
		
		if( (cig.getHttpStatusCode() != 201) && (cig.getHttpStatusCode() != 200) ) {
			
			ErrorResponseObject ero = cig.getErrorResponse();
			
			for( int i = 0; i < ero.getMessages().size(); i++ ) {
				
				actionLogger.addError("Error ["+ero.getMessages().get(i).getCode()+"]: " + ero.getMessages().get(i).getText() );
				
			}
			
			throw new InitiatorGroupException("Request failed.");
			
		}

		actionLogger.addInfo( "Task completed successfully." );
		
		
		
		// Update initiator groups LOVs.
		
		actionLogger.addInfo("Re-registering dynamic LOVs.");
		
		// Get array name for preamble to LOV name.
		
		String arrayName = "";
		
		String getArraysResponse = new GetArrays(ipAddress, token).getDetail();
		
		GetArraysDetailObject arraysObj = new ParseArraysDetailResponse(getArraysResponse).parse();
		
		logger.info("arrays size: " + arraysObj.getData().size() );
		
		if( arraysObj.getData().size() != 1 ) {
			
			throw new ArraysException("Failed to retrieve array name.");
			
		} else {
			
			arrayName = arraysObj.getData().get(0).getName();
			
		}

		// Get Initiator Groups.
		
		iGroupsResponse = new GetInitiatorGroups(ipAddress, token).getDetail();
		
		logger.info("Initiator Groups Response: " +iGroupsResponse );
		
		iGroupObj = new ParseInitiatorGroupsDetailResponse(iGroupsResponse).parse();

		HashMap<String,String> iGroupsMap = new HashMap<>();
		
		for( int i = 0; i < iGroupObj.getData().size(); i++ ) {
			
			iGroupsMap.put( iGroupObj.getData().get(i).getName(), iGroupObj.getData().get(i).getName());
			
		}

		new RegisterInitiatorGroupsLOVs( iGroupsMap, arrayName ).registerWFInputs();

		
		
		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Nimble Create Initiator Group", 
				"Rollback Create Nimble Initiator Group " + config.getInitiatorGroupName(), 
				new NimbleDeleteIgroupTask().getTaskName(), new NimbleDeleteIgroupConfig(config));
		
        try
        {
            context.saveOutputValue(NimbleConstants.NIMBLE_INITIATOR_GROUP_NAME, config.getInitiatorGroupName());

        } catch (Exception e)
        {
            actionLogger.addWarning("Failed to set output variable(s): " + e.getMessage());
        }
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new NimbleCreateIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return NimbleCreateIgroupConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		TaskOutputDefinition[] ops = new TaskOutputDefinition[1];
		//NOTE: If you want to use the output of this task as input to another task. Then the second argument 
		//of the output definition MUST MATCH the type of UserInputField in the config of the task that will
		//be receiving this output.  Take a look at HelloWorldConfig as an example.
		ops[0] = new TaskOutputDefinition( NimbleConstants.NIMBLE_INITIATOR_GROUP_NAME, NimbleConstants.GENERIC_TEXT_INPUT, "Three" );
		return ops;
	}
}
