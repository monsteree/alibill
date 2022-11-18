package org.heart.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginInfoConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInfoConfig.class);

    public String mailUsername;

    public String password;

    public String aLiBillPassword;

    public String getMailUsername() {
        return mailUsername;
    }

    public LoginInfoConfig setMailUsername(String mailUsername) {
        this.mailUsername = mailUsername;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginInfoConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getaLiBillPassword() {
        return aLiBillPassword;
    }

    public LoginInfoConfig setaLiBillPassword(String aLiBillPassword) {
        this.aLiBillPassword = aLiBillPassword;
        return this;
    }
}
