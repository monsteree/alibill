package org.heart.test;

import org.heart.Application;
import org.heart.dao.BillUserinfoDao;
import org.heart.dto.BillUserinfoDTO;
import org.heart.dto.ResponseDTO;
import org.heart.utils.JsonUtils;
import org.heart.utils.OkhttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = Application.class)
public class test {


    @Autowired
    private OkhttpUtil okhttpUtil;

    @Autowired
    private BillUserinfoDao billUserinfoDao;

    @Test
    public void test(){
        String url = "";
        String data = "";
        ResponseDTO responseDTO = okhttpUtil.post(data, url);
        System.out.println(JsonUtils.convertToJson(responseDTO));
    }

    @Test
    public void test1(){
        List<BillUserinfoDTO> list = billUserinfoDao.queryAll();
        System.out.println(JsonUtils.convertToJson(list));
    }
}
