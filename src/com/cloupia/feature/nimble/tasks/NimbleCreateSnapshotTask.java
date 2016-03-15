package com.cloupia.feature.nimble.tasks;

import org.apache.log4j.Logger;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;
import com.rwhitear.nimbleRest.authenticate.GetSessionToken;
import com.rwhitear.nimbleRest.exceptions.SnapshotIdException;
import com.rwhitear.nimbleRest.exceptions.VolumeIdException;
import com.rwhitear.nimbleRest.snapshots.CreateSnapshot;
import com.rwhitear.nimbleRest.snapshots.GetSnapshots;
import com.rwhitear.nimbleRest.snapshots.json.GetSnapshotDetailResponse;
import com.rwhitear.nimbleRest.volumes.GetVolumes;
import com.rwhitear.nimbleRest.volumes.json.ParseVolumeDetailResponse;
import com.rwhitear.nimbleRest.volumes.json.VolumesDetailJsonObject;


public class NimbleCreateSnapshotTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( NimbleCreateSnapshotTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		NimbleCreateSnapshotConfig config = (NimbleCreateSnapshotConfig) context.loadConfigObject();
		
		
		String  ipAddress 		= config.getIpAddress();
		String  username 		= config.getUsername();
		String  password 		= config.getPassword(); 
		String  volumeName 		= config.getVolumeName(); 	
		String  snapShotName	= config.getSnapshotName();
		String  description 	= config.getSnapshotDescription();
		boolean online			= config.getSnapshotOnline();
		boolean writable		= config.getSnapshotWritable();
		
		
		// Get token.
		// Retrieve Nimble array auth token.
		actionLogger.addInfo( "Retrieving authentication token." );
		
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();

		
		// Get volumeID for volumeName.
		actionLogger.addInfo( "Retrieving volume ID for volume ["+ volumeName +"]." );
		
		String gvResponse = new GetVolumes(ipAddress, token).getDetail();
		
		logger.info("GetVolumes JSON: " + gvResponse );		
		
		// Parse JSON for volumeID for volumeName.
		VolumesDetailJsonObject pvsr = new ParseVolumeDetailResponse(gvResponse).parse();
		
		String volumeID = "";
		
		for( int i=0; i<pvsr.getData().size(); i++ ) {
			
			if( pvsr.getData().get(i).getName().equals(volumeName)) {
				
				volumeID = pvsr.getData().get(i).getId();
				
				break;
				
			}
			
		}
		
		if( volumeID == "" ) {
			
			throw new VolumeIdException("Failed to find volume ID for volume [" +volumeName+ "].");
				
		} else {
			
			actionLogger.addInfo("VolID for [" +volumeName+ "] is [" +volumeID+ "].");
			
		}


		// Check that the snapshot doesn't already exist.
		// Now that we have the volumeID, we can go ahead and get the snapshot ID for snapShotName.
		actionLogger.addInfo( "Checking that snapshot ["+snapShotName+"] doesn't already exist for volume ["+volumeName+"]." );
		
		String gsResponse = new GetSnapshots(ipAddress, token, volumeID).getDetail();

		logger.info("GetSnapshots JSON: " +gsResponse );
		
		GetSnapshotDetailResponse gsdr = new GetSnapshotDetailResponse(gsResponse);
		
		String snapID = gsdr.getSnapshotID(snapShotName);
		
		if( snapID != null ) {
			
			throw new SnapshotIdException("The snapshot [" +snapShotName+ "] already exists." );

		}

		// Snapshot doesn't exist, go ahead and create it.
		actionLogger.addInfo( "Creating snapshot." );
		
		CreateSnapshot cs = new CreateSnapshot(ipAddress, token, snapShotName, volumeID, description, online, writable);
		
		String csResponse = cs.execute();
		
		logger.info("Create Snapshot Response: " + csResponse );

		actionLogger.addInfo( "Task completed successfully." );
		
			
		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Nimble Create Snapshot", 
				"Rollback Create Nimble Snapshot " + config.getSnapshotName(), 
				new NimbleDeleteSnapshotTask().getTaskName(), new NimbleDeleteSnapshotConfig(config));
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new NimbleCreateSnapshotConfig();
	}

	@Override
	public String getTaskName() {
		return NimbleCreateSnapshotConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
