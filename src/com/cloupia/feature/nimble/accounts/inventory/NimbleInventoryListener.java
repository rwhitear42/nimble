/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.accounts.inventory;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.feature.nimble.accounts.NimbleAccount;
import com.cloupia.feature.nimble.accounts.util.NimbleAccountPersistenceUtil;
import com.cloupia.lib.connector.InventoryContext;
import com.cloupia.lib.connector.InventoryEventListener;
import com.cloupia.lib.connector.account.AbstractInfraAccount;
import com.cloupia.lib.connector.account.AccountTypeEntry;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalAccountManager;
import com.cloupia.lib.connector.account.PhysicalConnectivityStatus;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;

public class NimbleInventoryListener implements InventoryEventListener {
	private static Logger logger = Logger.getLogger(NimbleInventoryListener.class);

	@Override
	public void afterInventoryDone(String accountName, InventoryContext context)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Call in NimbleInventoryListener afterInventoryDone");
		
		NimbleAccountPersistenceUtil.persistCollectedInventory(accountName);
		
		AccountTypeEntry entry = PhysicalAccountManager.getInstance().getAccountTypeEntryByName(accountName);
		PhysicalConnectivityStatus connectivityStatus =null;
		if(entry != null)
		{
			connectivityStatus = entry.getTestConnectionHandler().testConnection(accountName);
		}
		
		NimbleAccount acc = getNimbleCredential(accountName);
		
		if(acc != null && connectivityStatus != null)
		{
			
			
			logger.info("Inventory collected successfully");
		}


	}

	@Override
	public void beforeInventoryStart(String accountName, InventoryContext arg1)
			throws Exception {
		logger.info("Call in NimbleInventoryListener beforeInventoryStart");
		
	}
	
	private static NimbleAccount getNimbleCredential(String accountName) throws Exception{
		PhysicalInfraAccount acc = AccountUtil.getAccountByName(accountName);
		String json = acc.getCredential();
		AbstractInfraAccount specificAcc  =  (AbstractInfraAccount) JSON.jsonToJavaObject(json, NimbleAccount.class);
		specificAcc.setAccount(acc);
		
		return (NimbleAccount) specificAcc;
		
	}

}
