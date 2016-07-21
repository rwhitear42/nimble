/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.accounts;

import com.cloupia.feature.nimble.accounts.pagination.NimbleAccountReportHandler;
import com.cloupia.feature.nimble.constants.NimbleConstants;
import com.cloupia.model.cIM.DynReportContext;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.model.cIM.ReportDefinition;
import com.cloupia.service.cIM.inframgr.reportengine.ContextMapRule;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportAction;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportWithActions;

/**
 * This is just a dummy report I'm using to demonstrate how you would place a report under a new menu location
 * for a specific context.  In this case, I have a new menu "dummy_menu_1" and this menu also has a new context
 * "dummy context one", this report does a couple of things to make sure it gets placed into that specific
 * location.
 *
 */
public class NimblePerformancePoliciesReport extends CloupiaReportWithActions {
	
	private static final String NAME = "nimble.performance.policies.report";
	private static final String LABEL = "Performance Policies";
	
	public NimblePerformancePoliciesReport() {	
		super();		
		//IMPORTANT: this tells the framework which column of this report you want to pass as the report context id
		//when there is a UI action being launched in this report
		this.setMgmtColumnIndex(0);
		
		//you can technically speaking set the proper menu id and context map rules here in the
		//the constructor BUT i think it's better to override the functions and hardcode this logic into the function 
		//instead (as shown below), just to guarantee it can't be tweaked by anyone else!
		
		
	}

	@Override
	public CloupiaReportAction[] getActions() {
		return null;
	}

	@Override
	public Class<NimblePerformancePoliciesReportImpl> getImplementationClass() {
		//this class handles all the report generation logic, look here for more details
		return NimblePerformancePoliciesReportImpl.class;
	}

	@Override
	public String getReportLabel() {
		return LABEL;
	}

	@Override
	public String getReportName() {
		return NAME;
	}

	//this is important, make sure this returns false.  isEasyReport should ONLY return
	//true if and only if you're using the POJO approach, which we aren't in this case.
	@Override
	public boolean isEasyReport() {
		return false;
	}

	//make sure to return true in this case, as there are no drill down reports
	@Override
	public boolean isLeafReport() {
		return false;
	}
	
	//basically hardcoding it so this report will show in the location i want it to
	//Physcial -> Storage -> LH Menu Tree Provider is 51.
	//Physcial -> Compute -> LH Menu Tree Provider is 50.
	//Physcial -> Network -> LH Menu Tree Provider is 52
	@Override
	public int getMenuID() {
		return 51;// NimbleConstants.DUMMY_MENU_1;
	}
	
	@Override
	public ContextMapRule[] getMapRules() {
		//i'm using an autogenerated report context (which I registered in NimbleModule), as mentioned in documentation
		//the type may vary depending on deployments, so the safest way to retrieve the auto generated type value
		//is to use the getContextByName api!
		DynReportContext dummyContextOneType = ReportContextRegistry.getInstance().getContextByName(NimbleConstants.INFRA_ACCOUNT_TYPE);
		ContextMapRule rule = new ContextMapRule();
		rule.setContextName(dummyContextOneType.getId());
		rule.setContextType(dummyContextOneType.getType());
		
		ContextMapRule[] rules = new ContextMapRule[1];
		rules[0] = rule;
		
		return rules;
	}
	
}
