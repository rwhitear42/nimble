/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.triggers;

import com.cloupia.feature.nimble.constants.NimbleConstants;
import com.cloupia.model.cIM.DynReportContext;
import com.cloupia.model.cIM.FormLOVPair;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;
import com.cloupia.service.cIM.inframgr.thresholdmonitor.MonitoredContextIf;

/**
 * Introducing a new type I want to monitor,  I'm calling it a dummy device type.
 *
 */
public class MonitorDummyDeviceType implements MonitoredContextIf {

	@Override
	public int getContextType() {
		//each monitored type is uniquely identified by an integer
		//we usually use the report context type
		DynReportContext dummyContextOneType = ReportContextRegistry.getInstance().getContextByName(NimbleConstants.DUMMY_CONTEXT_ONE);
		return dummyContextOneType.getType();
	}

	@Override
	public String getContextLabel() {
		//this is the label shown in the ui
		return "Dummy Device";
	}

	@Override
	public FormLOVPair[] getPossibleLOVs(WizardSession session) {
		//this should return all the dummy devices that could potentially be monitored
		//in this example i only have two dummy devices, usually the value should be an identifier you can use
		//to reference back to the actual object
		FormLOVPair deviceOne = new FormLOVPair("ddOne", "ddOne");
		FormLOVPair deviceTwo = new FormLOVPair("ddTwo", "ddTwo");
		FormLOVPair[] dummyDevices = { deviceOne, deviceTwo };
		return dummyDevices;
	}

	@Override
	public String getContextValueDetail(String selectedContextValue) {
		//this is additional info to display in the ui, i'm just returning a dummy string
		return "you picked " + selectedContextValue;
	}

	@Override
	public String getCloudType(String selectedContextValue) {
		// TODO Auto-generated method stub
		return null;
	}

}
