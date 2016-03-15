/**
 * Copyright (c) 2009-2012 Cloupia, Inc. All rights reserved.
 */
package com.cloupia.feature.nimble.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.cloupia.feature.nimble.pojo.EmailMessageRequest;
import com.cloupia.model.cIM.MailSettings;

/**
 * This class demonstrate the logic to manage sending mail, also implemented all required methods to send mail
 *
 */
public class MailManager
{
    static Logger logger = Logger.getLogger(MailManager.class);

   

    /**
     * 
     * This method use to send email to specified recipient, this method called in sendEmail method
     * 
     * @param settings
     *           This parameter contains mail setup information
     * @param emailReq
     *            This parameter contains required information to send mail
     * @return true/false
     *            This method returns true on successful mail sent otherwise returns false.
     *   
     * @throws Exception
     */
    private static boolean sendEmailBuiltIn(MailSettings settings, EmailMessageRequest emailReq) throws Exception
    {
        logger.info("Sending mail through built-in mailer...");

        String fromAddress = "";
        String subject = "";
        String messageText = "";
        int toAddrs = 0;
        int ccAddrs = 0;
        int bccAddrs = 0;

        boolean debug = false;
        String property = System.getProperty("mail.debug");

        if ((property != null) && property.equalsIgnoreCase("true"))
        {
            debug = true;
        }

        // Setting SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", settings.getSmtpServer());

        if ((settings.getSmtpUser() != null) && (settings.getSmtpUser().length() > 0))
        {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.user", settings.getSmtpUser());
        } else
        {
            props.put("mail.smtp.auth", "false");
        }

        props.put("mail.smtp.port", settings.getSmtpPort());

        // create some properties and get the default Session

        // Session session = Session.getDefaultInstance(props, null);
        Session session = Session.getInstance(props, null);
        session.setDebug(debug);

        // Connecting to SMTP Server
        Transport tr = session.getTransport("smtp");
        tr.connect(settings.getSmtpServer(), settings.getSmtpUser(), settings.getSmtpPass());

        // create a message
        MimeMessage msg = new MimeMessage(session);

        // set the from address
        if (emailReq.getFromAddress() != null)
        {
            fromAddress = emailReq.getFromAddress();

            InternetAddress addressFrom = new InternetAddress(fromAddress);

            msg.setFrom(addressFrom);
        }

        if ((emailReq.getToAddrs() != null) && (emailReq.getToAddrs().length != 0))
        {
            toAddrs = emailReq.getToAddrs().length;

            InternetAddress[] addressTo = new InternetAddress[toAddrs];

            for (int i = 0; i < toAddrs; i++)
            {
                addressTo[i] = new InternetAddress(emailReq.getToAddrs()[i]);
            }

            msg.setRecipients(Message.RecipientType.TO, addressTo);
        }

        if ((emailReq.getCcAddrs() != null) && (emailReq.getCcAddrs().length != 0))
        {
            ccAddrs = emailReq.getCcAddrs().length;

            InternetAddress[] addressCc = new InternetAddress[ccAddrs];

            for (int i = 0; i < ccAddrs; i++)
            {
                addressCc[i] = new InternetAddress(emailReq.getCcAddrs()[i]);
            }

            msg.setRecipients(Message.RecipientType.CC, addressCc);
        }

        if ((emailReq.getBccAddrs() != null) && (emailReq.getBccAddrs().length != 0))
        {
            bccAddrs = emailReq.getBccAddrs().length;

            InternetAddress[] addressBcc = new InternetAddress[bccAddrs];

            for (int i = 0; i < bccAddrs; i++)
            {
                addressBcc[i] = new InternetAddress(emailReq.getBccAddrs()[i]);
            }

            msg.setRecipients(Message.RecipientType.BCC, addressBcc);
        }

        // Setting the Subject and Content Type
        if (emailReq.getSubject() != null)
        {
            subject = emailReq.getSubject();
            msg.setSubject(subject);
        }

        msg.setSentDate(new Date());

        if (emailReq.getMessageBody() != null)
        {
            messageText = emailReq.getMessageBody();

            String contentType = emailReq.getContentType();

            if ((contentType == null) || contentType.equals(""))
            {
                contentType = "text/plain";
            }

            msg.setContent(messageText, contentType);
        }

        // msg.setText(messageText,"text", "html");

        // Send email if one of To or cc or bcc addresses are specified
        if ((toAddrs != 0) || (ccAddrs != 0) || (bccAddrs != 0))
        {
            msg.saveChanges(); // don't forget this

            tr.sendMessage(msg, msg.getAllRecipients());

            tr.close();
        } else
        {
            throw new Exception("Nothing send, no To,CC,Bcc addresses specified ");
        }

        return true;
    }

    /**
     * This method use to call sendEmailBuiltIn method by passing appropriate parameters
     * 
     * @param label       
     * @param settings
     *           This parameters contains mail setup information
     * @param emailReq 
     *           This parameters is object of  EmailMessageRequest class which is used to store required information from user to send mail
     * @return This method returns true on successful mail sent
     * @throws Exception
     */
    public static boolean sendEmail(String label, MailSettings settings, EmailMessageRequest emailReq) throws Exception
    {
  
        return sendEmailBuiltIn(settings, emailReq);
    }

    /**
     * 
     * This method demonstrate logic for sending mail without using object of EmailMessageRequest class, this is overloaded method of above.
     * @param settings
     *           This parameter contains mail setup info
     * @param multiPart
     * @param fromAddress
     *          This parameter contains sender email address
     * @param subject
     *          This parameter contains subject of mail to be send 
     * @param toAddresses
     *          This parameter contains recipient email address
     * @return  It returns true on successful mail delivered otherwise returns false
     * 
     * @throws Exception
     */
    public static boolean sendEmailBuiltIn(MailSettings settings, MimeMultipart multiPart, String fromAddress,
            String subject, String toAddresses) throws Exception
    {
        logger.info("Sending multi-part mail through built-in mailer...");

        int toAddrs = 0;

        if (toAddresses == null)
        {
            toAddresses = "";
        }

        String[] toAddrList = toAddresses.split(",");

        boolean debug = false;
        String property = System.getProperty("mail.debug");

        if ((property != null) && property.equalsIgnoreCase("true"))
        {
            debug = true;
        }

        // Setting SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", settings.getSmtpServer());

        if ((settings.getSmtpUser() != null) && (settings.getSmtpUser().length() > 0))
        {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.user", settings.getSmtpUser());
        } else
        {
            props.put("mail.smtp.auth", "false");
        }

        props.put("mail.smtp.port", settings.getSmtpPort());

        // create some properties and get the default Session

        // Session session = Session.getDefaultInstance(props, null);
        Session session = Session.getInstance(props, null);
        session.setDebug(debug);

        // Connecting to SMTP Server
        Transport tr = session.getTransport("smtp");
        tr.connect(settings.getSmtpServer(), settings.getSmtpUser(), settings.getSmtpPass());

        // create a message
        MimeMessage msg = new MimeMessage(session);
        msg.setContent(multiPart);

        // set the from address
        if (fromAddress != null)
        {
            InternetAddress addressFrom = new InternetAddress(fromAddress);
            msg.setFrom(addressFrom);
        }

        if (toAddrList.length > 0)
        {
            toAddrs = toAddrList.length;

            InternetAddress[] addressTo = new InternetAddress[toAddrs];

            for (int i = 0; i < toAddrs; i++)
            {
                addressTo[i] = new InternetAddress(toAddrList[i].trim());
            }

            msg.setRecipients(Message.RecipientType.TO, addressTo);
        }

        // Setting the Subject and Content Type
        if (subject != null)
        {
            msg.setSubject(subject);
        }

        msg.setSentDate(new Date());

        if ((toAddrs != 0))
        {
            msg.saveChanges(); // don't forget this
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        } else
        {
            throw new Exception("Nothing send, no To addresses specified ");
        }

        return true;
    }
}
