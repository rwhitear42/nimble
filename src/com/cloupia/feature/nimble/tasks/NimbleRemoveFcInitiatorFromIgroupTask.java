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


public class NimbleRemoveFcInitiatorFromIgroupTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( NimbleRemoveFcInitiatorFromIgroupTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		NimbleRemoveFcInitiatorFromIgroupConfig config = (NimbleRemoveFcInitiatorFromIgroupConfig) context.loadConfigObject();


		String ipAddress = config.getIpAddress();
		String username = config.getUsername();
		String password = config.getPassword(); 
		String initiatorGroupName = config.getInitiatorGroupName();
		String alias = config.getAlias();

		
		// Retrieve Nimble array auth token.
		actionLogger.addInfo( "Retrieving authentication token." );
		
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		// Get iGroupID.
		String iGroupsResponse = new GetInitiatorGroups(ipAddress, token).getDetail();
		
		logger.info("Initiator Groups Response: " +iGroupsResponse );
		
		GetInitiatorGroupsDetailObject iGroupObj = new ParseInitiatorGroupsDetailResponse(iGroupsResponse).parse();

		actionLogger.addInfo( "Retrieving initiator ID for alias ["+alias+"]." );
		
		String initiatorID = "";
		
		for( int i=0; i < iGroupObj.getData().size(); i++ ) {
			
			if( iGroupObj.getData().get(i).getName().equals(initiatorGroupName) ) {
				
				for( int j = 0; j < iGroupObj.getData().get(i).getFc_initiators().size(); j++ ) {
					
					if( iGroupObj.getData().get(i).getFc_initiators().get(j).getAlias().equals(alias) ) {
						
						initiatorID = iGroupObj.getData().get(i).getFc_initiators().get(j).getInitiator_id();
						
						break;
						
					}
				}			
			}		
		}
		
		if( initiatorID == "") {
			
			throw new InitiatorGroupException("Failed to find initiator ID for initiator [" +alias+ "].");
			
		}
		
		// Initiator ID found. Go ahead and delete it.
		actionLogger.addInfo( "Removing initiator ["+alias+"]." );
		
		String deleteInitiatorResponse = new DeleteInitiator(ipAddress, token, initiatorID).execute();
		
		logger.info("deleteInitiatorResponse: " + deleteInitiatorResponse );
		
		actionLogger.addInfo( "Task completed successfully." );
				
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new NimbleRemoveFcInitiatorFromIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return NimbleRemoveFcInitiatorFromIgroupConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
