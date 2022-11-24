package org.heart.dto;

/**
 *http返回参数
 */
public class ResponseDTO {

    //客户端错误
    public static final int CLIENT_ERROR_CODE = 420;
    public static final int SERVER_NO_RESP = 520;
    public static final int SUCCESS = 200;

    //客户端错误
    public static final String CLIENT_ERROR = "client_error";
    //反序列失败
    public static final String RESP_DESERIALIZE_FAIL = "resp_deserialize_fail";
    //序列化失败
    public static final String REQ_SERIALIZE_FAIL = "req_serialize_to_string_fail";
    public static final String RESP_SERIALIZE_FAIL = "resp_serialize_to_string_fail";
    //空的请求数据
    public static final String EMPTY_REQ_DATA = "empty_req_data";
    //发送请求失败
    public static final String SEND_REQ_FAIL = "send_req_fail";

    private int code;
    private String msg;
    private String errorMsg;
    private String data;

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    public int getCode() {
        return code;
    }

    public ResponseDTO setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseDTO setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public ResponseDTO setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public String getData() {
        return data;
    }

    public ResponseDTO setData(String data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
