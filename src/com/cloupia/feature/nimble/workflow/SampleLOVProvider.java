package com.cloupia.feature.nimble.workflow;

import com.cloupia.model.cIM.FormLOVPair;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.ReportFieldFormatterIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;

public class SampleLOVProvider implements LOVProviderIf
{
    public static final String NAME = "sampleInputTypeLOV";

    public SampleLOVProvider()
    {
    }

    @Override
    public FormLOVPair[] getLOVs(WizardSession session)
    {
        try
        {
        	FormLOVPair[] pairs = new FormLOVPair[5];

            for (int i = 0; i < pairs.length; i++) {
                pairs[i] = new FormLOVPair("SampleInput"+i,
                        ""+i);
            }

            return pairs;
        } catch (Exception ex)
        {
            return new FormLOVPair[0];
        }
    }

   
}

