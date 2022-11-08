package org.heart.controller;


import org.heart.service.BusinessService;
import org.heart.service.LoginService;
import org.heart.service.MailService;
import org.heart.service.ZipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@RestController
public class loginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(loginController.class);





    @Autowired
    private MailService mailService;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/loginController/login")
    public void login() {
        try {

            Message[] message = loginService.login();
            LOGGER.info("邮件数量: {}", message.length);
            businessService.getMailInfo(message);
//            new GetMailInfoThread(message).start();
        } catch (Exception e) {
            LOGGER.error("system error", e);
        }
    }
}
