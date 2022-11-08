package org.heart.service.impl;

import org.heart.service.MailService;
import org.heart.service.BusinessService;
import org.heart.service.ZipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class BusinessServiceImpl implements BusinessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessServiceImpl.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private ZipService zipService;

    @Value("${aliBillPassword}")
    private String aliBillPassword;

    @Value("${AttachPath}")
    private String AttachPath;

    @Override
    public void getMailInfo(Message[] message) {
        try {
            for (int i = 0; i < message.length; i++) {
                this.alipayBusiness(mailService.getFrom((MimeMessage) message[i]), message[i]);
            }
        } catch (Exception e) {
            LOGGER.error("BusinessServiceImpl.getMailInfo error", e);
        }

    }

    @Override
    public void alipayBusiness(String sendName, Part part) {
        try {
            //获取支付宝账单
            if (sendName.indexOf("支付宝提醒") != -1) {
                //查看是否有附件
                if (mailService.isContainAttach(part)) {
                    //保存附件
                    String storeFile = mailService.saveAttachment(part);
                    if (null == storeFile) {
                        return;
                    }
                    //解压附件
                    zipService.unZip(storeFile, AttachPath + File.separator + "bill", aliBillPassword);




                }
            }
        } catch (Exception e) {
            LOGGER.error("BusinessServiceImpl.alipayBusiness error", e);
        }

    }
}
