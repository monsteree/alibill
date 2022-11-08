package org.heart.service;

import javax.mail.Message;
import javax.mail.Part;

public interface BusinessService {

    /**
     * 获取邮箱信息（30天）
     * @param message
     * @throws Exception
     */
    void getMailInfo(Message[] message);


    /**
     * 支付宝订单业务逻辑
     * @param sendName
     * @param part
     */
    void alipayBusiness(String sendName, Part part);
}
