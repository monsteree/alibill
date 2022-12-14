CREATE TABLE "TBL_BILL_TRADE"
(	"INCOME_EXPENSES" VARCHAR2(255),
     "TRADE_ACCOUNT" VARCHAR2(255),
     "PRODUCT_DESCRIPTION" VARCHAR2(255),
     "PAYMENT_METHOD" VARCHAR2(255),
     "AMOUNT" LONG,
     "TRADE_STATUS" VARCHAR2(255),
     "TRADE_TYPE" VARCHAR2(255),
     "TRADE_ORDERID" VARCHAR2(255) NOT NULL ENABLE,
     "MERCHANT_ORDERID" VARCHAR2(255),
     "TRADE_TIME" VARCHAR2(14),
     "CREATE_TIME" VARCHAR2(14),
     "USERID" VARCHAR2(255),
     "INVALID_STATE" VARCHAR2(255),
     PRIMARY KEY ("TRADE_ORDERID")
);



CREATE TABLE "TBL_BILL_USERINFO"
(	"MAIL_USERNAME" VARCHAR2(255) NOT NULL ENABLE,
     "PASSWORD" VARCHAR2(255),
     "ALI_BILL_PASSWORD" VARCHAR2(255),
     "CREATE_TIME" CHAR(14),
     PRIMARY KEY ("MAIL_USERNAME")
);


CREATE TABLE "TBL_BILL_INFO"
(	"BILL_NAME" VARCHAR2(255) NOT NULL ENABLE,
     "CREATE_TIME" CHAR(14),
     PRIMARY KEY ("BILL_NAME")
)

