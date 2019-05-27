package com.mobileai.luncert.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mobileai.luncert.model.core.EMail;
import com.mobileai.luncert.model.mysql.entity.User;
import com.mobileai.luncert.service.MailSender;
import com.mobileai.luncert.service.UserManagement;
import com.mobileai.luncert.utils.ResourceHelper;
import com.mobileai.luncert.utils.SecurityTransport;
import com.mobileai.luncert.utils.CipherHelper.Cipher;
import com.mobileai.luncert.utils.CipherHelper.SM2Utils;
import com.mobileai.luncert.utils.CipherHelper.Util;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.json.JSONObject;


@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class);

    private static final int CHECK_CODE_IMAGE_WIDTH = 120;

    private static final int CHECK_CODE_IMAGE_HEIGHT = 40;

    @Autowired
    private UserManagement userManagement;

    @Autowired
    private MailSender mailSender;

    private String genPass(String account, String password) throws NoSuchAlgorithmException {
        StringBuilder builder = new StringBuilder();
        builder.append(account).append(password).append(new Date());
        return Cipher.genKeyFromString(builder.toString());
    }

    private String genMailId() throws NoSuchAlgorithmException {
        StringBuilder builder = new StringBuilder();
        builder.append(new Date().toString()).append(this.hashCode());
        return Cipher.genKeyFromString(builder.toString());
    }

    private Color randomColor(int fc, int bc) {
        Random random = new Random();
        fc = (fc > 255) ? 255 : fc;
        bc = (bc > 255) ? 255 : bc;
        return new Color(fc + random.nextInt(bc - fc), fc + random.nextInt(bc - fc), fc + random.nextInt(bc - fc));
    }

    private String insertIdIntoMail(String mailHtml, String identifier, String account) {
        int i = mailHtml.indexOf("<body>") + 6;
        StringBuilder builder = new StringBuilder();
        builder.append(mailHtml.substring(0, i))
                .append("<input id='activateIdentifier' type='hidden' value='")
                .append(identifier)
                .append("' />")
                .append("<input id='account' type='hidden' value='")
                .append(account)
                .append("' />")
                .append(mailHtml.substring(i));
        return  builder.toString();
    }

    /**
     * 与客户端交换SM2公钥
     * @throws IOException
     */

    @RequestMapping(value = "exchange")
    public String exchange(HttpSession session, String puk) throws IOException {
        String localPrk = new String(Base64.encode(Util.hexToByte(Cipher.getUUID(64))));
        String localPuk = new String(Base64.encode(Util.hexToByte(Cipher.getUUID(130))));

        SecurityTransport st = new SecurityTransport(puk, localPrk, Cipher.getUUID(48));
        session.setAttribute("st", st);
        
        return new String(Base64.encode(SM2Utils.encrypt(Base64.decode(puk.getBytes()), localPuk.getBytes())));
    }

    /**
     * @return sm4key
     */
    @RequestMapping(value = "requireKey")
    public String requireKey(HttpSession session) throws IOException {
        SecurityTransport st = (SecurityTransport)session.getAttribute("st");
        return st.parseSM2(st.getSM4Key());
    }

    /**
     * @return User if validate successfully
     */
    @RequestMapping(value = "validate")
    public String validate(HttpSession session, HttpServletResponse rep, String data) throws Exception {
        SecurityTransport st = (SecurityTransport)session.getAttribute("st");
        Map<String, String> fields = st.load(data);
        
        User user = userManagement.validate(fields.get("account"), fields.get("password"));

        if (user == null) return st.response(201, "validate failed", null);
        else if (!user.getIsActive()) return st.response(202, "inactivated account", null);
        else {
            Cookie cookie = new Cookie("usrpass", genPass(fields.get("account"), fields.get("password")));
            cookie.setPath("/");
            rep.addCookie(cookie);
            session.setAttribute("usrpass", cookie.getValue());
            session.setAttribute("isAdmin", user.getIsAdmin());
            return st.response(200, null, JSONObject.fromObject(user));
        }
    }

    /**
     * return image of checkCode
     */
    @RequestMapping(value = "getCheckCode")
    public void getCheckCode(HttpSession session, HttpServletResponse rep) throws IOException {
        Random random = new Random();

        // disable cache
        rep.setHeader("Pragma", "No-cache");
        rep.setHeader("Cache-Control", "no-cache");
        rep.setDateHeader("Expires", 0);

        BufferedImage image = new BufferedImage(CHECK_CODE_IMAGE_WIDTH, CHECK_CODE_IMAGE_HEIGHT, 1);
        
        Graphics graphics = image.getGraphics();
        graphics.setColor(randomColor(200, 250));
        graphics.setFont(new Font("Open sans", 0, 28));
        graphics.fillRect(0, 0, CHECK_CODE_IMAGE_WIDTH, CHECK_CODE_IMAGE_HEIGHT);

        for (int i = 0; i < 40; i++) {
            graphics.setColor(randomColor(130, 200));
            int x = random.nextInt(CHECK_CODE_IMAGE_WIDTH);
            int y = random.nextInt(CHECK_CODE_IMAGE_HEIGHT);
            graphics.drawLine(x, y, x + random.nextInt(30), y + random.nextInt(30));
        }

        String code = "";
        Graphics2D gstr = (Graphics2D)graphics;
        for (int i = 0; i < 4; i++) {
            String c = String.valueOf((char)(random.nextInt(26) + 65));
            if (random.nextBoolean()) c = c.toLowerCase();
            
            code += c;
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

            double degree = (random.nextDouble() - 0.5) * Math.PI / 2;
            gstr.rotate(degree,  30 * i + 15, 15);
            gstr.drawString(c, 30 * i, 30);
            gstr.rotate(- degree,  30 * i + 15, 15);
        }

        graphics.dispose();
        session.setAttribute("checkCode", code);

        ImageIO.write(image, "JPEG", rep.getOutputStream());
        rep.getOutputStream().flush();
    }

    /**
     * 注册一个未激活帐号，会被保存到mysql中
     */
    @RequestMapping(value = "regis")
    public String regis(HttpSession session, String data) throws IOException, NoSuchAlgorithmException {
        SecurityTransport st = (SecurityTransport)session.getAttribute("st");

        Map<String, String> fields = st.load(data);

        String account = fields.get("account");
        
        if (session.getAttribute("checkCode").toString().compareTo(fields.get("checkCode")) == 0) {
            if (userManagement.regis(account, fields.get("password"))) {
                String activateIdentifier = genMailId();
                userManagement.setActivateIdentifier(activateIdentifier, account);

                EMail mailInfo = new EMail(account);
                mailInfo.setValidate(true);
                mailInfo.setTargetAddress(account);
                mailInfo.setSubject("帐号激活");
                mailInfo.setContent(insertIdIntoMail(ResourceHelper.getResource("templates/checkMail.html"), activateIdentifier, account));
                try {
                    mailSender.sendHTML(mailInfo);
                    return st.response(200, "we have send an e-mail to your mailbox", null);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    logger.warn("failed to send check mail" + e.toString());
                    return st.response(201, "failed to send check mail", null); 
                }
            } else return st.response(202, "account registed", null);
        } else return st.response(203, "incorrect check code", null);
    }

    @RequestMapping(value = "activate")
    public String activate(HttpSession session, String activateIdentifier, String account) throws IOException {
        SecurityTransport st = (SecurityTransport)session.getAttribute("st");
        if (userManagement.activate(activateIdentifier, account)) return st.response(200, "activate successfully", null);
        else return st.response(201, "activate failed", null);
    }

}