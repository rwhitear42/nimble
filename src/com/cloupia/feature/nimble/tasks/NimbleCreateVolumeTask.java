package com.cloupia.feature.nimble.tasks;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.cloupia.feature.nimble.constants.NimbleConstants;
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
import com.rwhitear.nimbleRest.performancePolicies.GetPerformancePolicies;
import com.rwhitear.nimbleRest.performancePolicies.json.ParsePerfPolicyDetailResponse;
import com.rwhitear.nimbleRest.performancePolicies.json.PerfPoliciesDetailJsonObject;
import com.rwhitear.nimbleRest.volumes.CreateVolume;
import com.rwhitear.nimbleRest.volumes.GetVolumes;
import com.rwhitear.nimbleRest.volumes.json.ParseVolumeDetailResponse;
import com.rwhitear.nimbleRest.volumes.json.VolumesDetailJsonObject;



public class NimbleCreateVolumeTask extends AbstractTask {

	private static Logger logger = Logger.getLogger( NimbleCreateVolumeTask.class );
			
	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		NimbleCreateVolumeConfig config = (NimbleCreateVolumeConfig) context.loadConfigObject();

		
		// nimbleREST library testing.
		
		String username = config.getUsername();
		String password = config.getPassword();
		String ipAddress = config.getIpAddress();
		String volumeName = config.getVolumeName();
		String	volumeSizeGB	= config.getVolumeSizeGB();
		String	description		= config.getVolumeDescription();
		String	perfPolicy		= config.getVolumePerfPolicy();
		boolean	dataEncryption	= config.getVolumeDataEncryption();
		boolean	cachePinning	= config.getVolumeCachePinning();
		
	
		// Retrieve the performance policy ID for perfPolicy.
		String perfPolicyID = "";

		// Retrieve Nimble array auth token.
		actionLogger.addInfo( "Retrieving authentication token." );
		
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
		
		// Retrieve JSON response for detailed Performance Policy information.
		String perfPolicyJsonData = new GetPerformancePolicies(ipAddress, token).getDetail();
		
		actionLogger.addInfo("Verifying that performance policy [" + perfPolicy + "] exists.");
		
		//System.out.println("Performance Policy Detail JSON: " + perfPolicyJsonData );
		//actionLogger.addInfo("Performance Policy Detail JSON: " + perfPolicyJsonData );
		logger.info( "Performance Policy Detail JSON: " + perfPolicyJsonData );
		
		PerfPoliciesDetailJsonObject perfPolicyDetail = new ParsePerfPolicyDetailResponse(perfPolicyJsonData).parse();

		for( int i=0; i < perfPolicyDetail.getData().size(); i++ ) {
			
			if( perfPolicyDetail.getData().get(i).getName().equals(perfPolicy) ) {
				
				//System.out.println("Found performance policy id [" + 
				//		perfPolicyDetail.getData().get(i).getId() + 
				//		"] for Performance Policy [" + perfPolicy + "].");
				actionLogger.addInfo("Found performance policy id [" + 
						perfPolicyDetail.getData().get(i).getId() + 
						"] for Performance Policy [" + perfPolicy + "].");
				
				perfPolicyID = perfPolicyDetail.getData().get(i).getId();
				
				break;
			}
		}
		
		// Go ahead and create the volume.
		
		actionLogger.addInfo("Attempting to create volume [" + volumeName + "].");
		
		CreateVolume cv = new CreateVolume(ipAddress, token);
		
		String createResp = cv.create(volumeName, description, perfPolicyID, dataEncryption, volumeSizeGB, cachePinning);
		
		//System.out.println("Create Volume Response: " + createResp);
		//actionLogger.addInfo("Create Volume Response: " + createResp);
		logger.info( "Create Volume Response: " + createResp );
	
		actionLogger.addInfo("Volume [" + volumeName + "] successfully created.");
		
		
		// Update Volumes LOVs.
			
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

		// Retrieve JSON response for detailed Volume information.
		String volumeJsonData = new GetVolumes(ipAddress, token).getDetail();
						
		VolumesDetailJsonObject volDetail = new ParseVolumeDetailResponse(volumeJsonData).parse();
				
		HashMap<String,String> volMap = new HashMap<>();
		
		for( int i = 0; i < volDetail.getData().size(); i++ ) {
			
			volMap.put( volDetail.getData().get(i).getName(), volDetail.getData().get(i).getName() );
			
		}

		new RegisterVolumesLOVs( volMap, arrayName ).registerWFInputs();			
		
		
		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
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
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new NimbleCreateVolumeConfig();
	}

	@Override
	public String getTaskName() {
		return NimbleCreateVolumeConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		TaskOutputDefinition[] ops = new TaskOutputDefinition[1];
		//NOTE: If you want to use the output of this task as input to another task. Then the second argument 
		//of the output definition MUST MATCH the type of UserInputField in the config of the task that will
		//be receiving this output.  Take a look at HelloWorldConfig as an example.
		ops[0] = new TaskOutputDefinition( NimbleConstants.NIMBLE_VOLUME_NAME, NimbleConstants.GENERIC_TEXT_INPUT, "Three" );
		return ops;
	}
}
