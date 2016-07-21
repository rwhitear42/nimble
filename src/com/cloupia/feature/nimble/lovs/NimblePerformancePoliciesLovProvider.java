/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.lovs;

import java.util.HashMap;

import com.cloupia.model.cIM.FormLOVPair;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;

public class NimblePerformancePoliciesLovProvider implements LOVProviderIf {

	public static final String NAME = "nimblePerfPoliciesLOV";
	
	private HashMap<String,String> perfPolLOVs = new HashMap<>();
	
	// Constructor.
	public NimblePerformancePoliciesLovProvider( HashMap<String, String> perfPolLOVs ) {
		
		this.perfPolLOVs = perfPolLOVs;	
		
	}
	
	
    @Override
    public FormLOVPair[] getLOVs(WizardSession session)
    {
        try
        {
        	
        	int i = 0;
        	
        	int mapSize = perfPolLOVs.size();
        	
        	FormLOVPair[] pairs = new FormLOVPair[ mapSize ];
        	
        	for( String key : perfPolLOVs.keySet() ) {
        		
        		pairs[i] = new FormLOVPair( key , perfPolLOVs.get(key) );
        		
        		i++;
        		
        	}

            return pairs;
            
        } catch (Exception ex)
        {
            return new FormLOVPair[0];
        }
        
    }

	public HashMap<String, String> getPerfPolLOVs() {
		return perfPolLOVs;
	}

	public void setPerfPolLOVs(HashMap<String, String> perfPolLOVs) {
		this.perfPolLOVs = perfPolLOVs;
	}

}
