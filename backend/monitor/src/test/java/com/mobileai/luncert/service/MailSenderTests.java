package com.mobileai.luncert.service;

import com.mobileai.luncert.model.core.EMail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailSenderTests {

    @Autowired
    MailSender mailSender;

    @Test
    public void testSendHtml() throws Exception {
        EMail email = EMail.builder().target("1242005392@qq.com")
            .subject("Verification Code")
            .content("Your code is: <h2>" + "123SDA" + "</h2>")
            .build();
        mailSender.sendHTMLMail(email);
    }
    
    @Test
    public void testSendText() throws Exception {
        EMail email = EMail.builder().target("1242005392@qq.com")
            .subject("Verification Code")
            .content("Your code is: " + "123SDA")
            .build();
        mailSender.sendTextMail(email);
    }

}