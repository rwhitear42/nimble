/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.multiselecttabularreports;

import com.cloupia.feature.nimble.lovs.SimpleTabularProvider;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

public class MultiSelectTabularForm {

	//for tabular types, multiline = true gives you a string[] but multiline = false gives you just a String
    @FormField(label = "Plain Tabular", type = FormFieldDefinition.FIELD_TYPE_TABULAR, table = SimpleTabularProvider.SIMPLE_TABULAR_PROVIDER, multiline = true)
    private String[] plainTabularValues;
    
    public String[] getPlainTabularValues() {
		return plainTabularValues;
	}

	public void setPlainTabularValues(String[] plainTabularValues) {
		this.plainTabularValues = plainTabularValues;
	}
}
