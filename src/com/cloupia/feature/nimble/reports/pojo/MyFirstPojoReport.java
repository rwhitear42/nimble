package com.cloupia.feature.nimble.reports.pojo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.lib.easyui.annotations.ReportField;
import com.cloupia.service.cIM.inframgr.reports.simplified.ReportableIf;

@PersistenceCapable(detachable = "true")
public class MyFirstPojoReport implements ReportableIf {
	
	@ReportField(label="Name")
	@Persistent
	private String name;
	
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getInstanceQuery() {
		
		return "name == '" + name + "'";
		
	}

}
