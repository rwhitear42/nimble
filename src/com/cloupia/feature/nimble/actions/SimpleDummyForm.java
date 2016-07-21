/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.actions;

import com.cloupia.feature.nimble.lovs.SimpleLovProvider;
import com.cloupia.feature.nimble.lovs.SimpleTabularProvider;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

/**
 * A super simple example of a form object.
 *
 */
public class SimpleDummyForm {
	
	@FormField(label = "Name", help = "Name")
 	private String name;

    @FormField(label = "Value", help = "Value", type = FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, lovProvider = SimpleLovProvider.SIMPLE_LOV_PROVIDER)
    private String value;
    
    @FormField(label = "Number", type = FormFieldDefinition.FIELD_TYPE_NUMBER, minValue = 1 , maxValue = 65535)
    private int number;
    
    @FormField(label = "Bool", type = FormFieldDefinition.FIELD_TYPE_BOOLEAN)
    private boolean boolType;
    
    @FormField(label = "Password", type = FormFieldDefinition.FIELD_TYPE_PASSWORD)
    private String password;
        
    @FormField(label = "Date", type = FormFieldDefinition.FIELD_TYPE_DATE)
    private long dateLong;
    
    @FormField(label = "Date Time", type = FormFieldDefinition.FIELD_TYPE_DATE_TIME)
    private long dateTime;
    
    @FormField(label = "Multi Select List", type = FormFieldDefinition.FIELD_TYPE_MULTI_SELECT_LIST, lovProvider = SimpleLovProvider.SIMPLE_LOV_PROVIDER)
    private String listValue;
    
    @FormField(label = "Upload File", type = FormFieldDefinition.FIELD_TYPE_FILE_UPLOAD)
    private String uploadFileName;
    
    @FormField(label = "Tabular Popup", type = FormFieldDefinition.FIELD_TYPE_TABULAR_POPUP, table = SimpleTabularProvider.SIMPLE_TABULAR_PROVIDER)
    private String tabularPopup;
    
    //for tabular types, multiline = true gives you a string[] but multiline = false gives you just a String
    @FormField(label = "Plain Tabular", type = FormFieldDefinition.FIELD_TYPE_TABULAR, table = SimpleTabularProvider.SIMPLE_TABULAR_PROVIDER, multiline = true)
    private String[] plainTabularValues;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean getBoolType() {
		return boolType;
	}

	public void setBoolType(boolean boolType) {
		this.boolType = boolType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getDateLong() {
		return dateLong;
	}

	public void setDateLong(long dateLong) {
		this.dateLong = dateLong;
	}

	public long getDateTime() {
		return dateTime;
	}

	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}

	public String getListValue() {
		return listValue;
	}

	public void setListValue(String listValue) {
		this.listValue = listValue;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getTabularPopup() {
		return tabularPopup;
	}

	public void setTabularPopup(String tabularPopup) {
		this.tabularPopup = tabularPopup;
	}

	public String[] getPlainTabularValues() {
		return plainTabularValues;
	}

	public void setPlainTabularValues(String[] plainTabularValues) {
		this.plainTabularValues = plainTabularValues;
	}

}
