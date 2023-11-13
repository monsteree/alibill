package org.heart.dto;

/**
 * @author ：zfl
 * @description：TODO
 * @date ：2023/11/10 17:20
 */
public class BillInfoDTO {

    private String billName;

    private String createTime;

    public String getBillName() {
        return billName;
    }

    public BillInfoDTO setBillName(String billName) {
        this.billName = billName;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public BillInfoDTO setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }
}
