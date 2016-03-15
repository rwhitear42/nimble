package com.cloupia.feature.nimble.accounts.inventory;

import org.apache.log4j.Logger;
import org.apache.log4j.Logger;

import com.cloupia.feature.nimble.accounts.DummyAccount;
import com.cloupia.fw.objstore.ObjStore;
import com.cloupia.fw.objstore.ObjStoreHelper;
import com.cloupia.service.cIM.inframgr.collector.controller.PersistenceListener;
import com.cloupia.service.cIM.inframgr.collector.model.ItemResponse;
/**
 * This is listener for persisting the inventory. 
 * 
 */
public class NimbleCollectorInventoryPersistenceListener extends
		PersistenceListener {
	static Logger logger = Logger.getLogger(NimbleCollectorInventoryPersistenceListener.class);
	
	@Override
	public void persistItem(ItemResponse arg0) throws Exception {
		logger.info(":: persist Item ::");
		/**
		 * This is for dummy implementation. 
		 * For real implementation you can persist according to your requirement.
		 */
		ObjStore<DummyAccount> store = ObjStoreHelper.getStore(DummyAccount.class);
		
		DummyAccount obj = new DummyAccount();
		obj.setAccountName("Nimble-Test-1");
		obj.setIp("182.28.23.34");
		obj.setStatus("Active");
		store.insert(obj);
		
		DummyAccount obj2 = new DummyAccount();
		obj2.setAccountName("Nimble-Test-1");
		obj2.setIp("182.28.23.34");
		obj2.setStatus("Active");
		store.insert(obj2);
		

	}

}
