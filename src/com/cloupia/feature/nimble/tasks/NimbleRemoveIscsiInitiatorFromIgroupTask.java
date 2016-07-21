/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.tasks;

import org.apache.log4j.Logger;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
import com.rwhitear.nimbleRest.exceptions.InitiatorGroupException;
import com.rwhitear.nimbleRest.initiatorGroups.GetInitiatorGroups;
import com.rwhitear.nimbleRest.initiatorGroups.ParseInitiatorGroupsDetailResponse;
import com.rwhitear.nimbleRest.initiatorGroups.json.GetInitiatorGroupsDetailObject;
import com.rwhitear.nimbleRest.initiators.DeleteInitiator;


public class NimbleRemoveIscsiInitiatorFromIgroupTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( NimbleRemoveIscsiInitiatorFromIgroupTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		NimbleRemoveIscsiInitiatorFromIgroupConfig config = (NimbleRemoveIscsiInitiatorFromIgroupConfig) context.loadConfigObject();

		
		String ipAddress = config.getIpAddress();
		String username = config.getUsername();
		String password = config.getPassword(); 
		String initiatorGroupName = config.getInitiatorGroupName();
		String initiatorLabel = config.getInitiatorName();

		
		// Retrieve Nimble array auth token.
		actionLogger.addInfo( "Retrieving authentication token." );
		
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		// Get iGroupID.
		String iGroupsResponse = new GetInitiatorGroups(ipAddress, token).getDetail();
		
		logger.info("Initiator Groups Response: " +iGroupsResponse );
		
		actionLogger.addInfo( "Retrieving initiator ID for initiator ["+initiatorLabel+"]." );
		
		GetInitiatorGroupsDetailObject iGroupObj = new ParseInitiatorGroupsDetailResponse(iGroupsResponse).parse();

		String initiatorID = "";
		
		for( int i=0; i < iGroupObj.getData().size(); i++ ) {
			
			if( iGroupObj.getData().get(i).getName().equals(initiatorGroupName) ) {
				
				for( int j = 0; j < iGroupObj.getData().get(i).getIscsi_initiators().size(); j++ ) {
					
					if( iGroupObj.getData().get(i).getIscsi_initiators().get(j).getLabel().equals(initiatorLabel) ) {
						
						initiatorID = iGroupObj.getData().get(i).getIscsi_initiators().get(j).getInitiator_id();
						
						break;
						
					}
				}			
			}		
		}
		
		if( initiatorID == "") {
			
			throw new InitiatorGroupException("Failed to find initiator ID for initiator [" +initiatorLabel+ "].");
			
		}
		
		// Initiator ID found. Go ahead and delete it.
		actionLogger.addInfo( "Removing initiator ["+initiatorLabel+"]." );
		
		String deleteInitiatorResponse = new DeleteInitiator(ipAddress, token, initiatorID).execute();
		
		logger.info("deleteInitiatorResponse: " + deleteInitiatorResponse );
		
		actionLogger.addInfo( "Task completed successfully." );
				
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new NimbleRemoveIscsiInitiatorFromIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return NimbleRemoveIscsiInitiatorFromIgroupConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
