package com.cloupia.feature.nimble.resourceComputer;

import java.util.List;

import org.apache.log4j.Logger;

import com.cloupia.feature.nimble.actions.AssignDummyVLANToGroupForm;
import com.cloupia.fw.objstore.ObjStore;
import com.cloupia.fw.objstore.ObjStoreHelper;
import com.cloupia.model.cIM.Group;
import com.cloupia.service.cIM.inframgr.resourcecomputer.ResourceCountComputerIf;

/**
 * This demonstrates how you can write your own resource counter for resource limitation.  This is a very
 * simple example, where my dummy device has VLANs, I want to limit the usage of the VLANs per group.
 *
 */
public class DummyVLANResourceComputer implements ResourceCountComputerIf {
	
	private static Logger logger = Logger.getLogger(DummyVLANResourceComputer.class);

	/**
	 * This method use to compute number of groups assigned to vlan
	 * 
	 *@param This parameter is object of Group class which contains group information
	 *@return resource count
	 *
	 */
	@Override
	public double computeUsageByTenant(Group group) throws Exception {
		logger.info("NimbleNimble computing resources for " + group.getGroupId());
		logger.info("NimbleNimble computing resources for " + group.getGroupName());
		//my AssignDummyVLANToGroupAction makes sure all the data has been entered properly by the user, so
		//all i'm doing here is a very simple computation.
		//i'm just counting the number of vlans the user has associated to group passed in
		//you can view the results of the computation under Organization -> Summary -> Resource Limits
		ObjStore<AssignDummyVLANToGroupForm> store = ObjStoreHelper.getStore(AssignDummyVLANToGroupForm.class);
		List<AssignDummyVLANToGroupForm> objs = store.queryAll();
		double count = 0;
		for (AssignDummyVLANToGroupForm obj:objs) {
			if (group.getGroupId() == obj.getGroupId()) {
				count += 1;
			}
		}
		return count;
	}

}
