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
import com.rwhitear.nimbleRest.constants.NimbleRESTConstants;
import com.rwhitear.nimbleRest.exceptions.VolumeIdException;
import com.rwhitear.nimbleRest.volumeCollections.AddVolumeToVolCollection;
import com.rwhitear.nimbleRest.volumes.GetVolumes;
import com.rwhitear.nimbleRest.volumes.json.ParseVolumeDetailResponse;
import com.rwhitear.nimbleRest.volumes.json.VolumesDetailJsonObject;


public class NimbleDisassocVolFromVolumeCollectionTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( NimbleDisassocVolFromVolumeCollectionTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		NimbleDisassocVolFromVolumeCollectionConfig config = (NimbleDisassocVolFromVolumeCollectionConfig) context.loadConfigObject();

		
		String ipAddress = config.getIpAddress();
		String username = config.getUsername();
		String password = config.getPassword(); 
		String volumeName = config.getVolumeName();
		

		// Retrieve Nimble array auth token.
		actionLogger.addInfo( "Retrieving authentication token." );
		
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();

		// Check that the volume exists and retrieve its id.
		actionLogger.addInfo( "Checking for the existence of volume[" + volumeName + "]." );
		
		// Retrieve JSON response for detailed Volume information.
		String volumesJsonData = new GetVolumes(ipAddress, token).getDetail();

		logger.info("Volumes Detail JSON: " + volumesJsonData );
		
		VolumesDetailJsonObject volumeDetail = new ParseVolumeDetailResponse(volumesJsonData).parse();
		
		String volID = "";
				
		for( int i=0; i < volumeDetail.getData().size(); i++ ) {
					
			if( volumeDetail.getData().get(i).getName().equals(volumeName) ) {
						
				actionLogger.addInfo("Found volume ["+volumeName+"] with id["+
						volumeDetail.getData().get(i).getId() +"].");
						
				volID = volumeDetail.getData().get(i).getId();
						
				break;
					
			}
					
		}
				
		if( volID == "") {
					
			throw new VolumeIdException("Failed to find volume ["+volumeName+"].");
		
		}
		
		actionLogger.addInfo( "Removing volume collection association for volume [" + volumeName + "]." );
		
		// All prerequisites satisfied. Build the volume to volume collection association.
		String removeVolFromVolCollResponse = new AddVolumeToVolCollection(ipAddress, token, volID, 
				NimbleRESTConstants.NO_VOLUME_COLLECTION_ID ).execute();
		
		logger.info("removeVolFromVolCollResponse: " +removeVolFromVolCollResponse );

	}
	

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new NimbleDisassocVolFromVolumeCollectionConfig();
	}

	@Override
	public String getTaskName() {
		return NimbleDisassocVolFromVolumeCollectionConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
