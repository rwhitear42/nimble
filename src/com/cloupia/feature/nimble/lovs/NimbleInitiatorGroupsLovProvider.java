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

public class NimbleInitiatorGroupsLovProvider implements LOVProviderIf {

	public static final String NAME = "nimbleInitiatorGroupsLOV";
	
	private HashMap<String,String> InitiatorGroupsLOVs = new HashMap<>();
	
	// Constructor.
	public NimbleInitiatorGroupsLovProvider( HashMap<String, String> InitiatorGroupsLOVs ) {
		
		this.InitiatorGroupsLOVs = InitiatorGroupsLOVs;	
		
	}
	
	
    @Override
    public FormLOVPair[] getLOVs(WizardSession session)
    {
        try
        {
        	
        	int i = 0;
        	
        	int mapSize = InitiatorGroupsLOVs.size();
        	
        	FormLOVPair[] pairs = new FormLOVPair[ mapSize ];
        	
        	for( String key : InitiatorGroupsLOVs.keySet() ) {
        		
        		pairs[i] = new FormLOVPair( key , InitiatorGroupsLOVs.get(key) );
        		
        		i++;
        		
        	}

            return pairs;
            
        } catch (Exception ex)
        {
            return new FormLOVPair[0];
        }
        
    }


	public HashMap<String, String> getInitiatorGroupsLOVs() {
		return InitiatorGroupsLOVs;
	}


	public void setInitiatorGroupsLOVs(HashMap<String, String> initiatorGroupsLOVs) {
		InitiatorGroupsLOVs = initiatorGroupsLOVs;
	}

}
