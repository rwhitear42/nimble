package com.cloupia.feature.nimble.accounts.util;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.feature.nimble.accounts.NimbleAccount;
import com.cloupia.lib.connector.account.AbstractInfraAccount;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;

/**
 * This is the sample persistent util class for account. 
 * You can write your methods to expose your persistence
 * 
 *
 */

public class NimbleAccountPersistenceUtil {

	static Logger logger = Logger.getLogger(NimbleAccountPersistenceUtil.class);
	
	public static void persistCollectedInventory(String accountName) throws Exception {
		logger.debug("Call in persistCollectedInventory :: inventory  ");
		logger.debug("Account Name "+accountName);
		
		
		NimbleAccount acc = getNimbleCredential(accountName);
		
		if(acc != null)
		{
			logger.debug("Remote Host Ip "+acc.getServerAddress());
			
		}
		
		//Persists the collected inventory from the server / cevice for the particular account name by
		//using ObjStoreHelper
		//
		
	}
	
	/**
	 * To get the  object of Nimble by passing the AccountName.
	 * 
	 * @param accountName
	 * @return
	 * @throws Exception
	 */
	public static NimbleAccount getNimbleCredential(String accountName) throws Exception{
		PhysicalInfraAccount acc = AccountUtil.getAccountByName(accountName);
		String json = acc.getCredential();
		AbstractInfraAccount specificAcc  =  (AbstractInfraAccount) JSON.jsonToJavaObject(json, NimbleAccount.class);
		specificAcc.setAccount(acc);
		
		return (NimbleAccount) specificAcc;
		
	}

}
