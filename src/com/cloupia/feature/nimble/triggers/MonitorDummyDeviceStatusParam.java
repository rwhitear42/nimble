package com.cloupia.feature.nimble.triggers;

import com.cloupia.feature.nimble.constants.NimbleConstants;
import com.cloupia.model.cIM.DynReportContext;
import com.cloupia.model.cIM.FormLOVPair;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.service.cIM.inframgr.thresholdmonitor.MonitoredParameterIf;

/**
 * This goes together with MonitorDummyDeviceType, we want to monitor the status of the
 * dummy device, and that is what this class should do.
 *
 */
public class MonitorDummyDeviceStatusParam implements MonitoredParameterIf {

	@Override
	public String getParamLabel() {
		//this is the label of this parameter shown in the ui
		return "Dummy Device Status";
	}

	@Override
	public String getParamName() {
		//each parameter needs a unique string, it's a good idea to prefix each parameter
		//with your module id, this way it basically guarantees uniqueness
		return "Nimble.dummy.device.status";
	}

	@Override
	public FormLOVPair[] getSupportedOps() {
		//this should return all the supported operations that can be applied to this parameter
		FormLOVPair isOp = new FormLOVPair("is", "is");
		FormLOVPair[] ops = { isOp };
		return ops;
	}

	@Override
	public int getValueConstraintType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FormLOVPair[] getValueLOVs() {
		//this should return all the values you want to compare against e.g. threshold values
		FormLOVPair valueUP = new FormLOVPair("Up", "up");
		FormLOVPair valueDOWN = new FormLOVPair("Down", "down");
		FormLOVPair valueUNKNOWN = new FormLOVPair("Unknown", "unknown");
		FormLOVPair[] statuses = { valueDOWN, valueUNKNOWN, valueUP };
		return statuses;
	}

	@Override
	public int getApplicableContextType() {
		//this parameter is binded to MonitorDummyDeviceType, so it needs to return the same
		//value returned by MonitorDummyDeviceType.getContextType()
		DynReportContext dummyContextOneType = ReportContextRegistry.getInstance().getContextByName(NimbleConstants.DUMMY_CONTEXT_ONE);
		return dummyContextOneType.getType();
	}

	@Override
	public String getApplicableCloudType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int checkTrigger(StringBuffer messageBuf, int contextType,
			String objects, String param, String op, String values) {
		//you want to basically do if (objects.param op values) { activate } else { not activate }
		
		//first step, you'd look up what objects is pointing to, usually objects should be an identifier
		//for some other object you actually want
		//in this example, objects is either ddOne (dummy device) or ddTwo, for simplicity's sake, we'll
		//say ddOne is always up and ddTwo is always down
		if (objects.equals("ddOne")) {
			if (op.equals("is")) {
				//ddOne is always up, so trigger only gets activated when "ddOne is up"
				if (values.equals("up")) {
					return RULE_CHECK_TRIGGER_ACTIVATED;
				} else {
					return RULE_CHECK_TRIGGER_NOT_ACTIVATED;
				}
			} else {
				return RULE_CHECK_ERROR;
			}
		} else {
			if (op.equals("is")) {
				//ddTwo is always down, so trigger only gets activated when "ddTwo is not up"
				if (values.equals("up")) {
					return RULE_CHECK_TRIGGER_NOT_ACTIVATED;
				} else {
					return RULE_CHECK_TRIGGER_ACTIVATED;
				}
			} else {
				return RULE_CHECK_ERROR;
			}
		}
	}

}
