package org.heart.service.impl;

import org.heart.config.UserinfoConfig;
import org.heart.dto.BillUserinfoDTO;
import org.heart.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserinfoConfig userinfoConfig;

    @Override
    public Message[] login(String mailUsername) {
        try {
            String host = "pop.qq.com";
            Properties p = new Properties();
            p.setProperty("mail.pop3.host", "pop.qq.com"); // 按需要更改
            p.setProperty("mail.pop3.port", "995");
            // SSL安全连接参数
            p.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            p.setProperty("mail.pop3.socketFactory.fallback", "true");
            p.setProperty("mail.pop3.socketFactory.port", "995");
            Session session = Session.getDefaultInstance(p, null);
            Store store = session.getStore("pop3");
            BillUserinfoDTO billUserinfoDTO = userinfoConfig.searchUserinfo(mailUsername);
            store.connect(host, mailUsername, billUserinfoDTO.getPassword());
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message[] message = folder.getMessages();
            return message;
        } catch (Exception e) {
            LOGGER.error("LoginServiceImpl.login error", e);
        }
        return null;
    }
}
