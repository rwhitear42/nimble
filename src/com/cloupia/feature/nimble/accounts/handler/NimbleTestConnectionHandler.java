/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.accounts.handler;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.feature.nimble.accounts.NimbleAccount;
import com.cloupia.feature.nimble.accounts.api.NimbleAccountAPI;
import com.cloupia.feature.nimble.accounts.status.storage.StorageAccountStatusSummary;
import com.cloupia.lib.connector.account.AbstractInfraAccount;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalConnectivityStatus;
import com.cloupia.lib.connector.account.PhysicalConnectivityTestHandler;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;
import com.cloupia.model.cIM.InfraAccountTypes;

/**
 * This class is used to test the connection of the real device reachable from the UCSD.
 * 
 * 
 *
 */
public class NimbleTestConnectionHandler extends PhysicalConnectivityTestHandler{
	static Logger logger = Logger.getLogger(NimbleTestConnectionHandler.class);
	
	@Override
	public PhysicalConnectivityStatus testConnection(String accountName)
			throws Exception {
		
		NimbleAccount acc = getNimbleCredential(accountName);
		PhysicalInfraAccount infraAccount = AccountUtil.getAccountByName(accountName);
		PhysicalConnectivityStatus status = new PhysicalConnectivityStatus(infraAccount);
		status.setConnectionOK(false);
		if(infraAccount != null)
		{
			if(infraAccount.getAccountType() != null)
			{
				logger.info(infraAccount.getAccountType());
				
				
				
				
				//Instead of Nimble Account , real account type needs to specified.
				if(infraAccount.getAccountType().equals("Nimble Account"))
				{
					logger.info("Inside if condition");
					/**
					 * The below snippet will be used for the real device connection.
					 * 
					 */
					/*try
					{
						NimbleAccountAPI.getNimbleAccountAPI(acc);	
						logger.info("*******Setting test Connection as true*****");
					}
					catch(Exception e)
					{
						status.setConnectionOK(false);
						status.setErrorMsg("Failed to establish connection with the Device.");
						logger.debug("Test Connection is failed");
						return status;
					}*/
					
					status.setConnectionOK(true);
					StorageAccountStatusSummary.accountSummary(accountName);
					logger.debug("Connection is verified");
					
				}
			}
			
			
		}
		logger.info("Returning status "+status.isConnectionOK());
		return status;
	}
	
	private static NimbleAccount getNimbleCredential(String accountName) throws Exception{
		PhysicalInfraAccount acc = AccountUtil.getAccountByName(accountName);
		String json = acc.getCredential();
		AbstractInfraAccount specificAcc  =  (AbstractInfraAccount) JSON.jsonToJavaObject(json, NimbleAccount.class);
		specificAcc.setAccount(acc);
		
		return (NimbleAccount) specificAcc;
		
	}
}
