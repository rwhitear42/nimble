/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.scheduledTasks;

import org.apache.log4j.Logger;

import com.cloupia.feature.nimble.NimbleModule;
import com.cloupia.model.cIM.ChangeRecord;
import com.cloupia.model.cIM.FormLOVPair;
import com.cloupia.service.cIM.inframgr.AbstractScheduleTask;
import com.cloupia.service.cIM.inframgr.FeatureContainer;
import com.cloupia.service.cIM.inframgr.cmdb.ChangeTrackingAPI;

/**
 * This is a simple example demonstrating how to implement a scheduled task.  This task is executed
 * every 5 mins and simply makes a logging statement and increments the number of times it's been
 * executed.  It removes itself from the system once it has been executed twice.  It also
 * demonstrates how you can use the change tracking APIs to track changes made to the system.
 * 
 *
 */

public class DummyScheduleTask extends AbstractScheduleTask {
	
	private static Logger logger = Logger.getLogger(DummyScheduleTask.class);
	
	private int numTimesExecuted = 0;
	
	private static final long TWO_MINS = 60*1000*2;
	private static final int MAX_TIMES_EXECUTED = 2;

	public DummyScheduleTask() {
		super("Nimble");
	}
	
    /**
     * Execute the scheduled task in the given interval of time.
     * Here removeScheduleTask method call used to remove scheduled task
     * 
     * @param lastExecution ,shows last execution time 
     * 
     */
	@Override
	public void execute(long lastExecution) throws Exception {
		logger.info("vxvxvxvxvx - dummyTask has been executed " + numTimesExecuted + " times.");
		numTimesExecuted++;
		
		if (numTimesExecuted == MAX_TIMES_EXECUTED) {
			logger.info("vxvxvxvxvx - removing dummyTask");
			NimbleModule module = (NimbleModule) FeatureContainer.getInstance().getModuleById("Nimble");
			//NOTE: i am using getTaskName() and NOT getScheduleTaskName(), it's really important
			//we distinguish the two, getTaskName is used internally by the system, where we do 
			//some extra stuff to ensure uniqueness of the task name (prepend moduleID), so we need to
			//make sure to use this when removing tasks!
			module.removeScheduleTask(this.getTaskName());
			//use the static ChangeTrackingAPI to create an instance of ChangeRecord, these are just values you'd like have
			//tracked and store in the changes DB
			ChangeRecord rec = ChangeTrackingAPI.create("openAutoDeveloper", ChangeRecord.CHANGE_TYPE_DELETE, "Dummy Task removed from System",
	                "Nimble dummy task");
			//insert the record like so
			ChangeTrackingAPI.insertRecord(rec);
		}
	}

	/**
	 * Returns the interval time in which time of interval scheduled task getting executed
	 * 
	 * @return long  ,frequency of task execution in minute
	 */
	@Override
	public long getFrequency() {
		return TWO_MINS;
	}

	/**
	 * Returns the scheduled task name
	 *  
	 * @return task name
	 */
	@Override
	protected String getScheduleTaskName() {
		//usually good idea to name your task something descriptive
		return "dummyTask";
	}
	
	/**
	 * Returns the array of FormLOVPair values
	 * 
	 * @return the  specified list of values, otherwise an empty array will be returned.
	 */
	@Override
	public FormLOVPair[] getFrequencyHoursLov() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Returns list of values in minutes
	 * 
	 * @return the  specified list of values, otherwise an empty array will be returned.
	 */
	@Override
	public FormLOVPair[] getFrequencyMinsLov() {
		// TODO Auto-generated method stub
		return null;
	}

}
