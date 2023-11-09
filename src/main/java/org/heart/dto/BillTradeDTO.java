package org.heart.dto;

/**
 * @author ：zfl
 * @description：TODO
 * @date ：2023/11/07 17:13
 */
public class BillTradeDTO {

    /**
     * 收支状态
     */
    private String incomeExpenses;
    /**
     * 对方账号
     */
    private String tradeAccount;
    /**
     * 商品说明
     */
    private String productDescription;
    /**
     * 收/付款方式
     */
    private String paymentMethod;
    /**
     * 金额 分
     */
    private Long amount;
    /**
     * 交易状态
     */
    private String tradeStatus;
    /**
     * 交易分类
     */
    private String tradeType;
    /**
     * 交易订单号
     */
    private String tradeOrderId;
    /**
     * 商家订单号
     */
    private String merchantOrderId;
    /**
     * 交易时间
     */
    private String tradeTime;
    /**
     * 插入时间
     */
    private String createTime;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 失效状态
     * 0-失效
     * 1-正常
     */
    private String invalidState;

    /**
     * 交易对方
     */
    private String merchantName;

    public String getMerchantName() {
        return merchantName;
    }

    public BillTradeDTO setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public String getIncomeExpenses() {
        return incomeExpenses;
    }

    public BillTradeDTO setIncomeExpenses(String incomeExpenses) {
        this.incomeExpenses = incomeExpenses;
        return this;
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public BillTradeDTO setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
        return this;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public BillTradeDTO setProductDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public BillTradeDTO setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public BillTradeDTO setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public BillTradeDTO setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
        return this;
    }

    public String getTradeType() {
        return tradeType;
    }

    public BillTradeDTO setTradeType(String tradeType) {
        this.tradeType = tradeType;
        return this;
    }

    public String getTradeOrderId() {
        return tradeOrderId;
    }

    public BillTradeDTO setTradeOrderId(String tradeOrderId) {
        this.tradeOrderId = tradeOrderId;
        return this;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public BillTradeDTO setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
        return this;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public BillTradeDTO setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public BillTradeDTO setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public BillTradeDTO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getInvalidState() {
        return invalidState;
    }

    public BillTradeDTO setInvalidState(String invalidState) {
        this.invalidState = invalidState;
        return this;
    }
}
