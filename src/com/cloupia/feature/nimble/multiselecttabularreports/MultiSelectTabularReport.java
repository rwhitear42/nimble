package com.cloupia.feature.nimble.multiselecttabularreports;

import com.cloupia.feature.nimble.constants.NimbleConstants;
import com.cloupia.model.cIM.DynReportContext;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.service.cIM.inframgr.reportengine.ContextMapRule;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportAction;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportWithActions;

public class MultiSelectTabularReport extends CloupiaReportWithActions {
	private static final String NAME = "Nimble.dummy.multiselect.tabular.report";
	private static final String LABEL = "Multi Select Tabular Report";

	@Override
	public CloupiaReportAction[] getActions() {
		
		MultiSelectTabularReportAction multiSelectTabularReportAction = new MultiSelectTabularReportAction();
		
		CloupiaReportAction[] actions = new CloupiaReportAction[1];
		
		actions[0] = multiSelectTabularReportAction;
		
		return actions;

	}

	@Override
	public Class<MultiSelectTabularReportImpl> getImplementationClass() {
		
		return MultiSelectTabularReportImpl.class;
	}

	@Override
	public String getReportLabel() {
		return LABEL;
	}

	@Override
	public String getReportName() {
		return NAME;
	}

	// this is important, make sure this returns false. isEasyReport should ONLY
	// return
	// true if and only if you're using the POJO approach, which we aren't in
	// this case.
	@Override
	public boolean isEasyReport() {
		return false;
	}

	// make sure to return true in this case, as there are no drill down reports
	@Override
	public boolean isLeafReport() {
		return true;
	}

	// basically hardcoding it so this report will show in the location i want
	// it to
	@Override
	public int getMenuID() {
		//return NimbleConstants.DUMMY_MENU_1;
		return 51;
	}

	@Override
	public ContextMapRule[] getMapRules() {
		// i'm using an autogenerated report context (which I registered in
		// NimbleModule), as mentioned in documentation
		// the type may vary depending on deployments, so the safest way to
		// retrieve the auto generated type value
		// is to use the getContextByName api!
		DynReportContext dummyContextOneType = ReportContextRegistry.getInstance()
				.getContextByName(NimbleConstants.DUMMY_CONTEXT_ONE);
		ContextMapRule rule = new ContextMapRule();
		rule.setContextName(dummyContextOneType.getId());
		rule.setContextType(dummyContextOneType.getType());

		ContextMapRule[] rules = new ContextMapRule[1];
		rules[0] = rule;

		return rules;
	}

}
