/**
 * Copyright (c) 2009-2012 Cloupia, Inc. All rights reserved.
 */
package com.cloupia.feature.nimble.pojo;

public class EmailMessageRequest
{
    private String[] toAddrs     = new String[0];
    private String[] ccAddrs     = new String[0];
    private String[] bccAddrs    = new String[0];
    private String   subject;
    private String   fromAddress;
    private String   messageBody;
    private String   contentType = "text/plain";

    public EmailMessageRequest()
    {
    }

    public String[] getToAddrs()
    {
        return toAddrs;
    }

    public void setToAddr(String addr)
    {
        toAddrs = new String[1];
        toAddrs[0] = addr;
    }

    public void setToAddrs(String[] toAddrs)
    {
        this.toAddrs = toAddrs;
    }

    public String[] getCcAddrs()
    {
        return ccAddrs;
    }

    public void setCcAddr(String addr)
    {
        ccAddrs = new String[1];
        ccAddrs[0] = addr;
    }

    public void setCcAddrs(String[] ccAddrs)
    {
        this.ccAddrs = ccAddrs;
    }

    public String[] getBccAddrs()
    {
        return bccAddrs;
    }

    public void setBccAddr(String addr)
    {
        bccAddrs = new String[1];
        bccAddrs[0] = addr;
    }

    public void setBccAddrs(String[] bccAddrs)
    {
        this.bccAddrs = bccAddrs;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getFromAddress()
    {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress)
    {
        this.fromAddress = fromAddress;
    }

    public String getMessageBody()
    {
        return messageBody;
    }

    public void setMessageBody(String messageBody)
    {
        this.messageBody = messageBody;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getContentType()
    {
        return contentType;
    }
}
