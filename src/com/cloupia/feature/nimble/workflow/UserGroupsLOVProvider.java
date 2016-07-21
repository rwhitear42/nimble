/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.workflow;

import com.cloupia.model.cIM.FormLOVPair;
import com.cloupia.service.cIM.inframgr.forms.wizard.LOVProviderIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.ReportFieldFormatterIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;

public class UserGroupsLOVProvider implements LOVProviderIf, ReportFieldFormatterIf
{
    public static final String NAME = "sampleInputTypeLOV";

    public UserGroupsLOVProvider()
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

    @Override
    public String formatValue(Object value, Object parentObject)
    {
        if ((value != null) && value instanceof Number)
        {
            int groupId = ((Number) value).intValue();

            try
            {
              if(groupId > 0)
                {
                    return "SampleInput"+groupId;
                }
            } catch (Exception ex)
            {
            }
        }

        return String.valueOf(value);
    }
}

