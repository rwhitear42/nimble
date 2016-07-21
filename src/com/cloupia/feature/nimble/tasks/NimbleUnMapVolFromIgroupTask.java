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


public class NimbleUnMapVolFromIgroupTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( NimbleUnMapVolFromIgroupTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		NimbleUnMapVolFromIgroupConfig config = (NimbleUnMapVolFromIgroupConfig) context.loadConfigObject();

		logger.info("Username: " +config.getUsername());
		logger.info("Password: " +config.getPassword());
		logger.info("IP Address: " +config.getIpAddress());
		logger.info("Volume Name: " +config.getVolumeName());
		logger.info("Initiator Group Name: " +config.getInitiatorGroupName());
		
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new NimbleUnMapVolFromIgroupConfig();
	}

	@Override
	public String getTaskName() {
		return NimbleUnMapVolFromIgroupConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
