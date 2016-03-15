package com.cloupia.feature.nimble.tasks;

import java.util.HashMap;

import org.apache.log4j.Logger;

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
import com.rwhitear.nimbleRest.constants.NimbleRESTConstants;
import com.rwhitear.nimbleRest.exceptions.ArraysException;
import com.rwhitear.nimbleRest.exceptions.VolumeIdException;
import com.rwhitear.nimbleRest.httpErrorHandling.json.ErrorResponseObject;
import com.rwhitear.nimbleRest.snapshots.OfflineSnapshot;
import com.rwhitear.nimbleRest.volumeCollections.AddVolumeToVolCollection;
import com.rwhitear.nimbleRest.volumes.DeleteVolume;
import com.rwhitear.nimbleRest.volumes.GetVolumes;
import com.rwhitear.nimbleRest.volumes.OfflineVolume;
import com.rwhitear.nimbleRest.volumes.json.ParseVolumeDetailResponse;
import com.rwhitear.nimbleRest.volumes.json.VolumesDetailJsonObject;


public class NimbleDeleteVolumeTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( NimbleDeleteVolumeTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		NimbleDeleteVolumeConfig config = (NimbleDeleteVolumeConfig) context.loadConfigObject();

		String ipAddress = config.getIpAddress();
		String username = config.getUsername();
		String password = config.getPassword();
		String volumeName = config.getVolumeName();
		

		// Retrieve Nimble array auth token.
		actionLogger.addInfo( "Retrieving authentication token." );
		
		String token = new GetSessionToken(ipAddress, username, password).getNewToken();
				
		// Retrieve JSON response for detailed Volume information.
		String volumeJsonData = new GetVolumes(ipAddress, token).getDetail();
						
		VolumesDetailJsonObject volDetail = new ParseVolumeDetailResponse(volumeJsonData).parse();
				
		if( volDetail.getData().size() < 1 ) {
					
			actionLogger.addError("Volume doesn't exist.");
					
			throw new Exception("No Volumes found.");

		}
				
		actionLogger.addInfo("Attempting to delete volume [" +volumeName+ "].");
			
		// Search for volumeName.
		for( int i = 0; i < volDetail.getData().size(); i++ ) {
					
			if( volDetail.getData().get(i).getName().equals(volumeName) ) {
						
				actionLogger.addInfo("Found volume [" +volumeName+ "] at index[" +i+ "]." );
						
				// Check for online snapshots and offline them if any found.
				if( volDetail.getData().get(i).getOnline_snaps() != null ) {

					actionLogger.addInfo("Number of snapshots found for volume ["+volumeName+"]: " + volDetail.getData().get(i).getOnline_snaps().size() );
							
					for( int j = 0; j < volDetail.getData().get(i).getOnline_snaps().size(); j++ ) {
								
						actionLogger.addInfo("Taking snapshot [" + volDetail.getData().get(i).getOnline_snaps().get(j).getSnap_name() 
								+ "][" + volDetail.getData().get(i).getOnline_snaps().get(j).getSnap_id() + "] offline.");
								
						// Snapshot offline logic here.
						String offlineSnapResponse = new OfflineSnapshot(ipAddress, token, 
								volDetail.getData().get(i).getOnline_snaps().get(j).getSnap_id() ).execute();
								
						logger.info("Snapshot ["+ volDetail.getData().get(i).getOnline_snaps().get(j).getSnap_name() 
								+"] Offline Response:\n" + offlineSnapResponse );

								
					}
							
				} else {
							
					actionLogger.addInfo("No online snapshots for volume [" + volumeName + "].");
							
				}
						
				// Check to see if the volume is online, and take offline if it is.
				if( volDetail.getData().get(i).isOnline() ) {

					actionLogger.addInfo("Volume [" +volumeName+ "] is ONLINE.");

					// Offline the volume.
					String response = new OfflineVolume( ipAddress, token, volDetail.getData().get(i).getId() ).execute();
							
					logger.info("Offline request response: " + response );
								
				} else {
					actionLogger.addInfo("Volume [" +volumeName+ "] is offline.");	
				}
						
				// Check to see if the volume is associated with a volume collection.
				if( !volDetail.getData().get(i).getVolcoll_name().equals("") ) {

					actionLogger.addInfo("Volume [" +volumeName+ "] IS protected.");
							
					// Remove volume collection association logic here.
					actionLogger.addInfo("Disassociating Volume Collection [" 
							+ volDetail.getData().get(i).getVolcoll_name() + "][" 
							+ volDetail.getData().get(i).getVolcoll_id() + "].");
							
					String addVolCollResponse = new AddVolumeToVolCollection(ipAddress, token, 
							volDetail.getData().get(i).getId(), NimbleRESTConstants.NO_VOLUME_COLLECTION_ID ).execute();
							
					logger.info("Add Vol to VolColl Response: \n" + addVolCollResponse );
							
				} else {
					actionLogger.addInfo("Volume [" +volumeName+ "] is not protected.");
				}
					
				// All Dependencies have now been addressed. Go ahead and delete the volume.
				actionLogger.addInfo("Deleting volume ["+volumeName+"]." ); 
				
				DeleteVolume dv = new DeleteVolume(ipAddress, token, volDetail.getData().get(i).getId() );
				
				String deleteVolResponse = dv.execute();
				
				
				if( (dv.getHttpStatusCode() != 201) && (dv.getHttpStatusCode() != 200) ) {
					
					ErrorResponseObject ero = dv.getErrorResponse();
					
					for( int j = 0; j < ero.getMessages().size(); j++ ) {
						
						actionLogger.addError("Error ["+ero.getMessages().get(j).getCode()+"]: " + ero.getMessages().get(j).getText() );
						
					}
					
					throw new VolumeIdException("Request failed.");
					
				}

				
				//String deleteVolResponse = new DeleteVolume(ipAddress, token, volDetail.getData().get(i).getId() ).execute();
						
				logger.info("Delete Volume Response:\n" + deleteVolResponse );
				
				
						
				// All done. Break the loop.
				break;
			}	
					
		}
				
		
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
		volumeJsonData = new GetVolumes(ipAddress, token).getDetail();
						
		volDetail = new ParseVolumeDetailResponse(volumeJsonData).parse();

		HashMap<String,String> volMap = new HashMap<>();
		
		for( int i = 0; i < volDetail.getData().size(); i++ ) {
			
			volMap.put( volDetail.getData().get(i).getName(), volDetail.getData().get(i).getName() );
			
		}

		new RegisterVolumesLOVs( volMap, arrayName ).registerWFInputs();			
		
		
		actionLogger.addInfo("All done.");
				

				
		//if user decides to rollback a workflow containing this task, then using the change tracker
		//we can take care of rolling back this task (i.e, disabling snmp)
		//NOTE: use the getTaskType() method in your handler to pass as the 5th argument
		//context.getChangeTracker().undoableResourceAdded("assetType", "idString", "SNMP enabled", 
		//		"SNMP enabled on " + config.getIpAddress(), 
		//		new DisableSNMPNexusTask().getTaskName(), new DisableSNMPNexusConfig(config));
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new NimbleDeleteVolumeConfig();
	}

	@Override
	public String getTaskName() {
		return NimbleDeleteVolumeConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
