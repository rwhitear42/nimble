package com.cloupia.feature.nimble.accounts;

import java.util.List;

import org.apache.log4j.Logger;

import com.cisco.cuic.api.client.JSON;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.TabularReportInternalModel;
import com.rwhitear.nimbleRest.inventory.GetVolumesInventory;
import com.rwhitear.nimbleRest.inventory.data.VolumeDataObject;


/**
 * This is an example demonstrating how to develop a report programmatically.  If you would rather
 * generate your report from scratch, this is how you would go about it.  This class is called when
 * a request to render the report is made, you need to compile all the data into a TabularReport
 * which will be returned to the UI for rednering.
 *
 */
public class NimbleVolumesReportImpl implements TabularReportGeneratorIf {
	
	private static Logger logger = Logger.getLogger(NimbleVolumesReportImpl.class);

	@Override
	public TabularReport getTabularReportReport(ReportRegistryEntry reportEntry, ReportContext context) throws Exception {
		
		TabularReport report = new TabularReport();

		report.setGeneratedTime(System.currentTimeMillis());
		report.setReportName(reportEntry.getReportLabel());
		report.setContext(context);
		
		String accountName = "";
		String contextId = context.getId();
		logger.info("###### Context at System Task report#####: "+contextId);
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
        
        GetVolumesInventory gvi = new GetVolumesInventory(username, password, deviceIp);
        
		VolumeDataObject vdo = gvi.getInventory();
		
		List<String> volNames = vdo.getVolNames();
		List<Long> volSizes = vdo.getVolSizes();
		List<Long> volUsageCompressedBytes = vdo.getVolUsageCompressedBytes();
		List<Long> snapUsageCompressedBytes = vdo.getSnapUsageCompressedBytes();
		List<Long> totalUsageBytes = vdo.getTotalUsageBytes();

        
		TabularReportInternalModel model = new TabularReportInternalModel();

		model.addTextColumn("Name", "Name");
		model.addTextColumn("Size GB", "Size GB");
		model.addTextColumn("Volume Usage Bytes", "Volume Usage Bytes");
		model.addTextColumn("Snapshot Usage Bytes", "Snapshot Usage Bytes");
		model.addTextColumn("Total Usage Bytes", "Total Usage Bytes");
		model.completedHeader();

		Double volSizeGB;
		
		for( int i = 0; i < volNames.size(); i++ ) {
			
			model.addTextValue( volNames.get(i) );
			
			volSizeGB = (double) ( volSizes.get(i) / 1024.0 );
			
			model.addTextValue( volSizeGB.toString() );
			model.addTextValue( volUsageCompressedBytes.get(i).toString() );
			model.addTextValue( snapUsageCompressedBytes.get(i).toString() );
			model.addTextValue( totalUsageBytes.get(i).toString() );
			model.completedRow();

		}
		
		model.updateReport(report);
		
		return report;
		
	}

}
