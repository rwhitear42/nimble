package com.cloupia.feature.nimble.accounts.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.lib.easyui.annotations.ReportField;
import com.cloupia.model.cIM.InventoryDBItemIf;

/**
 * A lot of the details of inventory POJOs is explained in the DummyInterface class.  Take a look there for
 * more info.  The only difference between this class and DummyInterface is I'm not implementing ReportableIf.
 * I could implement ReportableIf, but I'm choosing not to because I want to demonstrate how to develop a 
 * report using the alternate approach not POJO based.
 * 
 * This POJO will be used for binding and persisting purposes.
 *
 */
@PersistenceCapable(detachable = "true")
public class DummyVLAN implements InventoryDBItemIf {

	//it's really important you have a field tracking account name, because you will need this to
	//determine whether this instance belongs to a certain account or not
	@Persistent
	private String accountName;

	//The ReportField annotation is used to tag a particular field as a column to show in a report.
	//This is an easy way leverage an existing model POJO to display as a report in the UI.
	@ReportField(label="Name")
	@Persistent
	private String name;
	
	@ReportField(label="ID")
	@Persistent
	private String vlanID;

	@Override
	public String getAccountName() {
		return accountName;
	}

	@Override
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVlanID() {
		return vlanID;
	}

	public void setVlanID(String vlanID) {
		this.vlanID = vlanID;
	}

}
