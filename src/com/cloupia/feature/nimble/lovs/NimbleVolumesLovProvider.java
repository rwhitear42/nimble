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

public class NimbleVolumesLovProvider implements LOVProviderIf {

	public static final String NAME = "nimbleVolumesLOV";
	
	private HashMap<String,String> volumesLOVs = new HashMap<>();
	
	// Constructor.
	public NimbleVolumesLovProvider( HashMap<String, String> volumesLOVs ) {
		
		this.volumesLOVs = volumesLOVs;	
		
	}
	
	
    @Override
    public FormLOVPair[] getLOVs(WizardSession session)
    {
        try
        {
        	
        	int i = 0;
        	
        	int mapSize = volumesLOVs.size();
        	
        	FormLOVPair[] pairs = new FormLOVPair[ mapSize ];
        	
        	for( String key : volumesLOVs.keySet() ) {
        		
        		pairs[i] = new FormLOVPair( key , volumesLOVs.get(key) );
        		
        		i++;
        		
        	}

            return pairs;
            
        } catch (Exception ex)
        {
            return new FormLOVPair[0];
        }
        
    }


	public HashMap<String, String> getVolumesLOVs() {
		return volumesLOVs;
	}


	public void setVolumesLOVs(HashMap<String, String> volumesLOVs) {
		this.volumesLOVs = volumesLOVs;
	}

}
