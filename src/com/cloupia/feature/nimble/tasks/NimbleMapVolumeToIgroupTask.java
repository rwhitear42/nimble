package com.cloupia.feature.nimble.tasks;

import org.apache.log4j.Logger;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.accessControlRecords.CreateAccessControlRecord;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
import com.rwhitear.nimbleRest.exceptions.InitiatorGroupException;
import com.rwhitear.nimbleRest.initiatorGroups.GetInitiatorGroups;
import com.rwhitear.nimbleRest.initiatorGroups.ParseInitiatorGroupsDetailResponse;
import com.rwhitear.nimbleRest.initiatorGroups.json.GetInitiatorGroupsDetailObject;
import com.rwhitear.nimbleRest.volumes.GetVolumes;
import com.rwhitear.nimbleRest.volumes.json.GetVolumesSummaryResponse;


public class NimbleMapVolumeToIgroupTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( NimbleMapVolumeToIgroupTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		NimbleMapVolumeToIgroupConfig config = (NimbleMapVolumeToIgroupConfig) context.loadConfigObject();

		
		String ipAddress = config.getIpAddress();		
		String username = config.getUsername();
		String password = config.getPassword();
		String volumeName = config.getVolumeName();
		String initiatorGroupName = config.getInitiatorGroupName();
		
		
		// Retrieve Nimble array auth token.
		actionLogger.addInfo( "Retrieving authentication token." );
		
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		
		// Get volume ID for 'volumeName'.
		actionLogger.addInfo("Retrieving volume ID for volume ["+ volumeName + "].");
		
		String volumeJsonData = new GetVolumes(ipAddress, token).getSummary();
				
		String volID = new GetVolumesSummaryResponse(volumeJsonData).getVolumeID(volumeName);

		
		// Initiator Groups
		actionLogger.addInfo("Retrieving Initiator Group ID for iGroup ["+ initiatorGroupName + "].");
		
		String iGroupsResponse = new GetInitiatorGroups(ipAddress, token).getDetail();
		
		logger.info("iGroupsResponse: " + iGroupsResponse);
		
		GetInitiatorGroupsDetailObject iGroupObj = new ParseInitiatorGroupsDetailResponse(iGroupsResponse).parse();
		
		logger.info("Initiator Groups size: " + iGroupObj.getData().size() );
		

		String iGroupID = "";
		
		for( int i=0; i < iGroupObj.getData().size(); i++ ) {
			
			if( iGroupObj.getData().get(i).getName().equals(initiatorGroupName)) {
				
				iGroupID = iGroupObj.getData().get(i).getId();
				
				System.out.println("iGroup ID ["+iGroupID+"] found for iGroup name [" +iGroupObj.getData().get(i).getName()+ "]." );
				
				break;
				
			}
			
		}
		
		// If iGroupID is unset, then the previous for loop failed to match the iGroup name.
		if( iGroupID == "" ) {
			
			throw new InitiatorGroupException("Initiator group [" +initiatorGroupName+ "] doesn't exist." );
			
		}

		
		
		// Create new ACR.
		if( !volID.equals(null) ) {
			
			actionLogger.addInfo("Creating new Access Control Record for volume ["+ volumeName + "] and Initiator Group [" + initiatorGroupName + "].");
			
			logger.info("Create ACR JSON Response:\n"  + new CreateAccessControlRecord(ipAddress, token, volID, iGroupID).create());
		}

		
		/*
		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Nimble Map Volume to Initiator Group", 
				"Rollback Create Nimble Map Volume to Initiator Group " + config.getInitiatorGroupName(), 
				new NimbleUnMapVolFromIgroupTask().getTaskName(), new NimbleUnMapVolFromIgroupConfig(config));
	
        try
        {
            context.saveOutputValue(NimbleConstants.NIMBLE_INITIATOR_GROUP_NAME, config.getInitiatorGroupName());

        } catch (Exception e)
        {
            actionLogger.addWarning("Failed to set output variable(s): " + e.getMessage());
        }
        */
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new NimbleMapVolumeToIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return NimbleMapVolumeToIgroupConfig.displayLabel;
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
		ops[0] = new TaskOutputDefinition( NimbleConstants.NIMBLE_INITIATOR_GROUP_NAME, NimbleConstants.GENERIC_TEXT_INPUT, "Three" );
		return ops;
	}
	*/
}
