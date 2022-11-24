package org.heart.dto;

/**
 * 账户信息
 */
public class BillUserinfoDTO {
    /**
     * 邮箱
     */
    private String mailUsername;
    /**
     * 邮箱授权码
     */
    private String password;
    /**
     * 支付宝账单zip密码
     */
    private String aliBillPassword;
    /**
     * 创建时间
     */
    private String createTime;

    public String getMailUsername() {
        return mailUsername;
    }

    public BillUserinfoDTO setMailUsername(String mailUsername) {
        this.mailUsername = mailUsername;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public BillUserinfoDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAliBillPassword() {
        return aliBillPassword;
    }

    public BillUserinfoDTO setAliBillPassword(String aliBillPassword) {
        this.aliBillPassword = aliBillPassword;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public BillUserinfoDTO setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }
}
