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
import com.rwhitear.nimbleRest.initiatorGroups.DeleteInitiatorGroup;
import com.rwhitear.nimbleRest.initiatorGroups.GetInitiatorGroups;
import com.rwhitear.nimbleRest.initiatorGroups.ParseInitiatorGroupsDetailResponse;
import com.rwhitear.nimbleRest.initiatorGroups.json.GetInitiatorGroupsDetailObject;


public class NimbleDeleteIgroupTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( NimbleDeleteIgroupTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		NimbleDeleteIgroupConfig config = (NimbleDeleteIgroupConfig) context.loadConfigObject();

		
		String ipAddress = config.getIpAddress();
		String username = config.getUsername();
		String password = config.getPassword();
		String initiatorGroupName = config.getInitiatorGroupName(); 

		// Retrieve Nimble array auth token.
		actionLogger.addInfo( "Retrieving authentication token." );
		
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		// Check that iGroup exists.
		String iGroupsResponse = new GetInitiatorGroups(ipAddress, token).getDetail();
		
		logger.info("Initiator Groups Response: " +iGroupsResponse );
		
		actionLogger.addInfo( "Checking that initiator group ["+ initiatorGroupName +"] currently exists." );
		
		GetInitiatorGroupsDetailObject iGroupObj = new ParseInitiatorGroupsDetailResponse(iGroupsResponse).parse();

		String iGroupID = "";
		
		for( int i=0; i < iGroupObj.getData().size(); i++ ) {
			
			if( iGroupObj.getData().get(i).getName().equals(initiatorGroupName) ) {
				
				iGroupID = iGroupObj.getData().get(i).getId();
				
				break;
				
			}
			
		}
		
		// If iGroupID didn't get set in the previous for loop, then error.
		if( iGroupID == "" ) {
			
			throw new InitiatorGroupException("Initiator group [" +initiatorGroupName+ "] doesn't exist." );
			
		}

		// Initiator group exists, go ahead and delete it.
		actionLogger.addInfo( "Deleting initiator group ["+ initiatorGroupName +"]." );
		
		DeleteInitiatorGroup dig = new DeleteInitiatorGroup(ipAddress, token, iGroupID);
		
		String deleteResponse = dig.execute();
		
		logger.info("Initiator Group Delete Response: " + deleteResponse );
		
		actionLogger.addInfo("HTTP status code: " + dig.getHttpStatusCode() );
		
		if( (dig.getHttpStatusCode() != 201) && (dig.getHttpStatusCode() != 200) ) {
			
			ErrorResponseObject ero = dig.getErrorResponse();
			
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
		
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new NimbleDeleteIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return NimbleDeleteIgroupConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
