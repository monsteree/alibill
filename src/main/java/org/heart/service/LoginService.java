package org.heart.service;

import javax.mail.Message;

public interface LoginService {

    /**
     * 登录
     * @return
     */
    Message[] login();
}
