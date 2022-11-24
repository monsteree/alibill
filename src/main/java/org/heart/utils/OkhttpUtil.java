package org.heart.utils;

import okhttp3.*;
import org.heart.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class OkhttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(OkhttpUtil.class);

    private static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8");
    

    private OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .build();

    public ResponseDTO post(String data, String url){
        LOGGER.info("http url[{}] ", url);
        LOGGER.info("http req [{}]", data);
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(FORM, data)).addHeader("Content-Type", FORM.toString())
                    .build();
            Response response = null;
            response = httpClient.newCall(request).execute();
            if(null == response){
                return responseDTO.setCode(ResponseDTO.SERVER_NO_RESP).setMsg(ResponseDTO.SEND_REQ_FAIL);
            }
            return copyResponse(response);
        }catch (Exception e){
            LOGGER.error("URL[{}] DATA[{}] post exception", url, data, e);
            return responseDTO.setCode(ResponseDTO.SERVER_NO_RESP).setMsg(ResponseDTO.SEND_REQ_FAIL);
        }

    }

    /**
     * 复制okhttp response 应答对象
     *
     * @param response okhttp response
     * @return 成功 code =200,data=应答序列化json string 失败code=420,data =null
     */
    private ResponseDTO copyResponse(Response response) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(response.code());
        try {
            String data = response.isSuccessful() ? response.body().string() : null;
            responseDTO.setData(data);
        } catch (IOException e) {
            LOGGER.error("http client resp serialize fail", e);
            responseDTO.setCode(ResponseDTO.CLIENT_ERROR_CODE);
            responseDTO.setMsg(ResponseDTO.RESP_SERIALIZE_FAIL);
            responseDTO.setErrorMsg(e.getMessage());
        }
        return responseDTO;
    }
}
