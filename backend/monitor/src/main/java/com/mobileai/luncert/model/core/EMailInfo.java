package com.mobileai.luncert.model.core;

import java.util.Properties;

import lombok.Getter;

@Getter
public class EMailInfo {
    
    public static enum EMailType {
        TENCENT,
        NETEASE
    }

    private static final String[] SMTP_SERVER_ADDR = {"smtp.qq.com", "smtp.164.com"};

    private static final int[] SMTP_SERVER_PORT = {465, 25};

    private String mailServerHost;

    private int mailServerPort;

    private final String sourceAddress = "lijingweisee@qq.com";

    private String targetAddress;
    
    private boolean validate = true;

    private final String userName = "2725115515";
    
    private final String password = "sanlcutywmeldghf";

    private String subject;

    private String content;

    // 邮件附件的文件名    
    private String[] attachFileNames;
    
    public EMailInfo(EMail email) throws Exception {
        String target = email.getTarget();
        EMailType type;
        if (target.endsWith("qq.com")) {
            type = EMailType.TENCENT;
        } else if (target.endsWith("163.com")) {
            type = EMailType.NETEASE;
        } else {
            throw new Exception("unrecogonized email type: " + target);
        }
        mailServerHost = SMTP_SERVER_ADDR[type.ordinal()];
        mailServerPort =  SMTP_SERVER_PORT[type.ordinal()];

        targetAddress = target;
        subject = email.getSubject();
        content = email.getContent();
    }

    /**   
        * 获得邮件会话属性   
        */    
    public Properties getProperties(){
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", mailServerHost);
        props.setProperty("mail.smtp.port", String.valueOf(mailServerPort));
        props.setProperty("mail.smtp.auth", validate ? "true" : "false");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
        props.setProperty("mail.smtp.socketFactory.fallback", "false");  
        props.setProperty("mail.smtp.socketFactory.port", String.valueOf(mailServerPort));  
        return props;
    }

}