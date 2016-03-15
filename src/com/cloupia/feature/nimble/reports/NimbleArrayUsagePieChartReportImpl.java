package com.cloupia.feature.nimble.reports;

import java.util.List;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.feature.nimble.accounts.NimbleAccountJsonObject;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.ReportNameValuePair;
import com.cloupia.model.cIM.SnapshotReport;
import com.cloupia.model.cIM.SnapshotReportCategory;
import com.cloupia.service.cIM.inframgr.SnapshotReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.rwhitear.nimbleRest.inventory.GetArrayInventory;
import com.rwhitear.nimbleRest.inventory.data.ArrayDataObject;

public class NimbleArrayUsagePieChartReportImpl implements SnapshotReportGeneratorIf {
	
	private static Logger logger = Logger.getLogger(NimbleArrayUsagePieChartReportImpl.class);
			
	@Override
	public SnapshotReport getSnapshotReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception { 
		
		SnapshotReport report = new SnapshotReport(); report.setContext(context);
	
		report.setReportName(reportEntry.getReportLabel());
	
		report.setNumericalData(true); 
		
		report.setDisplayAsPie(true);
	
		report.setPrecision(0);
	
		String accountName = "";
		String contextId = context.getId();
		
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
        String deviceIp = specificAcc.getDeviceIp();
		
        // Get array model and OS version level.
        GetArrayInventory gai = new GetArrayInventory(username, password, deviceIp);
        
        // Get array inventory.
        ArrayDataObject ado = gai.getInventory();
        
		List<Long>	volUsageBytesList 			= ado.getVolUsageBytes();
		List<Long> 	snapUsageBytesList			= ado.getSnapUsageBytes();
		List<Long>  usableCapacityBytesList 	= ado.getUsableCapacityBytes();
		
		Long volUsageBytes = (long) 0;
		Long snapUsageBytes = (long) 0;
		Long usableCapacityBytes = (long) 0;
		
		
		if( usableCapacityBytesList.size() > 0 ) {
			
			volUsageBytes = volUsageBytesList.get(0);
			snapUsageBytes = snapUsageBytesList.get(0);
			usableCapacityBytes = ( usableCapacityBytesList.get(0) - ( volUsageBytes + snapUsageBytes ) );
			
		}
		
		//creation of report name value pair goes 
		ReportNameValuePair[] rnv = new ReportNameValuePair[3]; 
		
		rnv[0] = new ReportNameValuePair("Volume Bytes", volUsageBytes );
		rnv[1] = new ReportNameValuePair("Snapshot Bytes", snapUsageBytes );
		rnv[2] = new ReportNameValuePair("Available Bytes", usableCapacityBytes );

		//setting of report category goes 
		SnapshotReportCategory cat = new SnapshotReportCategory();
	
		cat.setCategoryName(""); 
		cat.setNameValuePairs(rnv);
		
		report.setCategories(new SnapshotReportCategory[] { cat });
	
		return report;
		
	}
	
}