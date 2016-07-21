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

/**
 * This is a simple task that just prints out multiselect tabular values  in the logs.
 * It also demonstrates how you can consume output from another task.
 *
 */
public class NimbleMultiSelectTabularTask extends AbstractTask {
	
	private static Logger logger = Logger.getLogger( NimbleMultiSelectTabularTask.class );

	@Override
	public void executeCustomAction(CustomActionTriggerContext context, CustomActionLogger actionLogger) throws Exception {
		long configEntryId = context.getConfigEntry().getConfigEntryId();
		NimbleMultiSelectTabularConfig config = (NimbleMultiSelectTabularConfig) context.loadConfigObject();

		if (config == null)
		{
			throw new Exception("No multiselect tabular values configuration found for custom action " + context.getActionDef().getName()
					+ " entryId " + configEntryId);
		}

		logger.info("MultiSelect Tabular Values: " + config.getTabularValues());
		
		logger.info("the task type is: " + this.getTaskType());
		
		logger.info("LOV Values: " + config.getLovValues());
		
	}
  /**
   * This method register/enable the config 
   */
	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new NimbleMultiSelectTabularConfig();
	}
     /**
	   * This method returns the task name  
	   */
	@Override
	public String getTaskName() {
		return NimbleMultiSelectTabularConfig.displayLabel;
	}
	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		return null;
	}

}
