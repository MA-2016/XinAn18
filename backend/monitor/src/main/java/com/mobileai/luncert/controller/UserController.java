package com.mobileai.luncert.controller;

import java.security.NoSuchAlgorithmException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import com.mobileai.luncert.model.core.EMail;
import com.mobileai.luncert.model.mysql.entity.User;
import com.mobileai.luncert.service.MailSender;
import com.mobileai.luncert.service.UserManagement;
import com.mobileai.luncert.utils.CipherHelper;
import com.mobileai.luncert.utils.ResponseHelper;
import com.mobileai.luncert.utils.mulsession.MulSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserManagement userManagement;

    @Autowired
    private MailSender mailSender;

    @GetMapping("/validate")
    public String validate(MulSession session, HttpServletResponse rep,
        String account, String password, String checkCode) throws Exception {
        User user = userManagement.validate(account, password);
        if (user != null) {
            session.setPair("validated", true);
            session.setPair("isAdmin", user.isAdmin());
            return ResponseHelper.wrap(200, null, null);
        } else {
            return ResponseHelper.wrap(201, "validate failed", null);
        }
    }

    @GetMapping("/sendMailCode")
    public String sendMailCode(MulSession mulSession, String account) throws Exception {
        String mailCheckCode = genMailCheckCode();

        EMail email = EMail.builder()
            .target(account)
            .subject("Verification Code")
            .content("Your code is: " + mailCheckCode)
            .build();
        try {
            mailSender.sendHTMLMail(email);
            mulSession.setPair("checkCode", mailCheckCode);
            return ResponseHelper.wrap(200, "we have send an e-mail to your mailbox", null);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.warn("failed to send check mail" + e.toString());
            return ResponseHelper.wrap(201, "failed to send check mail", null); 
        }
    }

    private String genMailCheckCode() {
        return CipherHelper.getUUID(8);
    }

    @GetMapping("/regis")
    public String regis(
        MulSession mulSession,
        String account,
        String password,
        String checkCode) throws NoSuchAlgorithmException {
        if (userManagement.avaliable(account)) {
            userManagement.regis(account, password);
            return ResponseHelper.wrap(200, null, null);
            // String mcc = mulSession.getValue("checkCode", String.class);
            // if (mcc == null) {
            //     return ResponseHelper.wrap(201, "illegal check code", null);
            // } else if (mcc.equals(checkCode)) {
            //     userManagement.regis(account, password);
            //     return ResponseHelper.wrap(200, null, null);
            // }
            // else {
            //     return ResponseHelper.wrap(202, "incorrect check code", null);
            // }
        } else {
            return ResponseHelper.wrap(203, "account registed", null);
        }
    }

}