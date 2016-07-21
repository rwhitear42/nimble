/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.accounts.inventory;

import java.util.Map;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.feature.nimble.accounts.NimbleAccount;
import com.cloupia.feature.nimble.accounts.api.NimbleAccountJSONBinder;
import com.cloupia.feature.nimble.accounts.api.NimbleJSONBinder;
import com.cloupia.lib.connector.AbstractInventoryItemHandler;
import com.cloupia.lib.connector.InventoryContext;
import com.cloupia.lib.connector.account.AbstractInfraAccount;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;
import com.cloupia.service.cIM.inframgr.collector.controller.PersistenceListener;
import com.cloupia.service.cIM.inframgr.collector.model.ItemResponse;

/**
 * This class is used to provide the  implementation for Nimble Account Inventory Item handlers.
 * 	While writing any new Item handler for any Nimble Account inventory task, that should extend this AbstractInventoryItemHandler Class. 
 * This provides the flexible way to override the url, binder and listener objects.
 * 
 *  This class provides the common logic to collect the inventory data and persist into the respective persistable POJO objects and cleanup 
 *  the data while deleting the Nimble account.
 */
public class NimbleInventoryItemHandler extends AbstractInventoryItemHandler {

	private static Logger logger = Logger.getLogger(NimbleInventoryItemHandler.class);
	
	/**
	 * This method used for cleanup Item Inventory.
	 * method of InventoryItemHandlerIf interface
	 * @param accountName
	 * @return void
	 * @exception Exception
	 */
	@Override
	public void cleanup(String accountName) throws Exception {
		// TODO Auto-generated method stub

	}
	/** 
	 * This method used for do Inventory of Account
	 * @Override method of IInventoryItemHandlerIf interface
	 * @param accountName ,InventoryContext
	 * @return void
	 * @exception Exception
	 */
	@Override
	public void doInventory(String accountName, InventoryContext inventoryCtxt)
			throws Exception {
		doInventory(accountName);

	}
	/**
	 * This method used for do Inventory of Account
	 * @Override method of IInventoryItemHandlerIf interface
	 * @param accountName ,Object
	 * @return void
	 * @exception Exception
	 */
	@Override
	public void doInventory(String accountName, Object obj) throws Exception {
		// TODO Auto-generated method stub

	}
	/** private Method used for doing Inventory of Account
	 * @param accountName
	 * @return void
	 * @exception Exception
	 */
	private void doInventory(String accountName) throws Exception {
		
		NimbleAccount acc = getNimbleCredential(accountName);
		
		
		//NimbleAccountAPI api = NimbleAccountAPI.getNimbleAccountAPI(acc);
		
		/**
		 * To provide the real implemntation , depends on the respond data binder for the requested item.
		 * To ensure the data collecting for the inventory via HTTP / SSH connection. 
		 * Response is converted as JSON Data, Json Data is binded with the 
		 */
		
		//String jsonData = api.getInventoryData(getUrl());
		String jsonData = null;
		ItemResponse bindableResponse = new ItemResponse();
		bindableResponse.setContext(getContext(accountName));
		bindableResponse.setCollectedData(jsonData);
		ItemResponse bindedResponse = null;
		logger.info("Before Callng bind");
		
		NimbleJSONBinder binder = getBinder();
		if(binder != null)
		{
			bindedResponse = binder.bind(bindableResponse);
			
		}
		
		logger.info("after Calling bind");
		
		
		 PersistenceListener listener = getListener();
		if(listener != null)
		{
			logger.info("Calling for Persistence");
			listener.persistItem(bindedResponse);
		}
		else
		{
			logger.info("Persistence is null");
		}
		
		
		
		
		
		
	}

	/** Method used for get Url
	 * @param No
	 * @return String
	 * @exception No
	 */
	public String getUrl() {
		// TODO Auto-generated method stub
		return "platform/1/protocols/smb/shares";
	}
	/** Method used to get object of  NimbleAccountJSONBinder
	 * 	Binder will bind the respective object as JSON. 
	 * @param No
	 * @return NimbleAccountJSONBinder
	 * @exception No
	 */
	public NimbleAccountJSONBinder getBinder() {
		// TODO Auto-generated method stub
		return new NimbleAccountJSONBinder();
	}
	/** Private Method used to get Map of Context
	 * @param accountName
	 * @return Map<String, Object>
	 * @exception No
	 */
	private Map<String, Object> getContext(String accountName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** Private Method used to get Object of PersistenceListener
	 * @param No
	 * @return PersistenceListener
	 * @exception No
	 */
	private PersistenceListener getListener() {
		// TODO Auto-generated method stub
		return new NimbleCollectorInventoryPersistenceListener();
	}
	/** Private Method used to get Credential of account Name
	 * @param accountName
	 * @return NimbleAccount
	 * @exception Exception
	 */
	private static NimbleAccount getNimbleCredential(String accountName) throws Exception{
		PhysicalInfraAccount acc = AccountUtil.getAccountByName(accountName);
		String json = acc.getCredential();
		AbstractInfraAccount specificAcc  =  (AbstractInfraAccount) JSON.jsonToJavaObject(json, NimbleAccount.class);
		specificAcc.setAccount(acc);
		
		return (NimbleAccount) specificAcc;
		
	}

}
