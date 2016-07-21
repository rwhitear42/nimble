/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.accounts.inventory;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.feature.nimble.accounts.NimbleAccountJsonObject;
import com.cloupia.feature.nimble.constants.NimbleConstants;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;
import com.cloupia.model.cIM.ConvergedStackComponentDetail;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.service.cIM.inframgr.reports.contextresolve.ConvergedStackComponentBuilderIf;
import com.rwhitear.nimbleRest.inventory.GetArrayInventory;
import com.rwhitear.nimbleRest.inventory.data.ArrayDataObject;

/**
 * NimbleConvergedStackBuilder.java is the base class for providing account details in converged view.
 */
public class NimbleConvergedStackBuilder implements ConvergedStackComponentBuilderIf{
	
	static Logger logger = Logger.getLogger(NimbleConvergedStackBuilder.class);
	
	/**
	 * Need to override this method providing account details to be shown in converged view.
	 * 
	 * @param : account context Id
	 * 
	 * @return: returns ConvergedStackComponentDetail instance
	 */
	@Override
	public ConvergedStackComponentDetail buildConvergedStackComponent(
			String contextId) throws Exception {
		
		String accountName = "";
		String deviceIp = "127.0.0.1";

		logger.info("contextId: "+contextId);
		
		if(contextId != null)
			//As the contextId returns as: "account Name;POD Name"
			accountName = contextId.split(";")[0];
		
        PhysicalInfraAccount acc = AccountUtil.getAccountByName(accountName);
        if (acc == null)
        {
            throw new Exception("Unable to find the account:" + accountName);
        }
		
        String json = acc.getCredential();
        
        NimbleAccountJsonObject specificAcc = (NimbleAccountJsonObject) JSON.jsonToJavaObject(json, NimbleAccountJsonObject.class);
        
        String username = specificAcc.getLogin();
        String password = specificAcc.getPassword();
        deviceIp = specificAcc.getDeviceIp();
		
        // Get array model and OS version level.
        GetArrayInventory gai = new GetArrayInventory(username, password, deviceIp);
        
        // Get array inventory.
        ArrayDataObject ado = gai.getInventory();
        
		List<String>	arrayNames 			= ado.getArrayNames();
		List<String> 	modelNames 			= ado.getModelNames();
        
		
        
		ConvergedStackComponentDetail detail = new ConvergedStackComponentDetail();
		
		if( modelNames.size() > 0 )
			detail.setModel( modelNames.get(0) );
		else 
			detail.setModel("Nimble");
		
		detail.setOsVersion("N/A");
		detail.setVendorLogoUrl("/app/uploads/openauto/NimbleArrayIcon_150x75.png");
		detail.setMgmtIPAddr(deviceIp);
		detail.setStatus("OK");
		detail.setVendorName("Nimble");
		
		if( arrayNames.size() > 0 )
			detail.setLabel("ArrayName: " + arrayNames.get(0) );
		
		detail.setIconUrl("/app/uploads/openauto/NimbleArrayIcon_150x75.png");
		//setting account context type
		detail.setContextType(ReportContextRegistry.getInstance().getContextByName(NimbleConstants.INFRA_ACCOUNT_TYPE).getType());
		//setting context value that should be passed to report implementation
		detail.setContextValue(contextId);
		detail.setLayerType(3);
		detail.setComponentSummaryList(getSummaryReports());
		return detail;
	}

	private List<String> getSummaryReports()
			throws Exception {
		
		 List<String> rpSummaryList = new ArrayList<String>();
		 rpSummaryList.add("test");
		 rpSummaryList.add("test2");
				return rpSummaryList;
	
	}
}
