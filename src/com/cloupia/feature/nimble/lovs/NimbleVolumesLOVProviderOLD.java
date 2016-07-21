/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.lovs;

import com.cloupia.model.cIM.FormLOVPair;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;

public class NimbleVolumesLOVProviderOLD implements LOVProviderIf
{
    public static final String NAME = "nimbleVolumesLOV";

    public NimbleVolumesLOVProviderOLD()
    {
    }

    @Override
    public FormLOVPair[] getLOVs(WizardSession session)
    {
        try
        {
        	FormLOVPair[] pairs = new FormLOVPair[2];

			pairs[0] = new FormLOVPair("russVol01", "russVol01");
			pairs[1] = new FormLOVPair("russVol02", "russVol02");

            return pairs;
        } catch (Exception ex)
        {
            return new FormLOVPair[0];
        }
    }
 
}