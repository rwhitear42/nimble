package com.cloupia.feature.nimble.lovs;

import java.util.HashMap;

import com.cloupia.model.cIM.FormLOVPair;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;

public class NimbleVolumeCollectionsLovProvider implements LOVProviderIf {

	public static final String NAME = "nimbleVolumeCollectionsLOV";
	
	private HashMap<String,String> volumeCollectionsLOVs = new HashMap<>();
	
	// Constructor.
	public NimbleVolumeCollectionsLovProvider( HashMap<String, String> volumeCollectionsLOVs ) {
		
		this.volumeCollectionsLOVs = volumeCollectionsLOVs;	
		
	}
	
	
    @Override
    public FormLOVPair[] getLOVs(WizardSession session)
    {
        try
        {
        	
        	int i = 0;
        	
        	int mapSize = volumeCollectionsLOVs.size();
        	
        	FormLOVPair[] pairs = new FormLOVPair[ mapSize ];
        	
        	for( String key : volumeCollectionsLOVs.keySet() ) {
        		
        		pairs[i] = new FormLOVPair( key , volumeCollectionsLOVs.get(key) );
        		
        		i++;
        		
        	}

            return pairs;
            
        } catch (Exception ex)
        {
            return new FormLOVPair[0];
        }
        
    }


	public HashMap<String, String> getVolumeCollectionsLOVs() {
		return volumeCollectionsLOVs;
	}


	public void setVolumeCollectionsLOVs(HashMap<String, String> volumeCollectionsLOVs) {
		this.volumeCollectionsLOVs = volumeCollectionsLOVs;
	}

}
