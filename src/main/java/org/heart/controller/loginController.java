package org.heart.controller;


import org.heart.dao.BillUserinfoDao;
import org.heart.dto.BillUserinfoDTO;
import org.heart.service.BusinessService;
import org.heart.service.LoginService;
import org.heart.service.MailService;
import org.heart.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import java.util.List;

@RestController
public class loginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(loginController.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private BillUserinfoDao billUserinfoDao;

    @PostMapping("/loginController/login")
    public void login() {
        try {
            String mailUsername = "825919026@qq.com";
            Message[] message = loginService.login(mailUsername);
            LOGGER.info("邮件数量: {}", message.length);
            businessService.getMailInfo(message, mailUsername);
//            new GetMailInfoThread(message).start();
        } catch (Exception e) {
            LOGGER.error("system error", e);
        }
    }
}
