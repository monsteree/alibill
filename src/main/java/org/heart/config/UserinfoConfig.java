package org.heart.config;

import org.heart.dao.BillUserinfoDao;
import org.heart.dto.BillUserinfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class UserinfoConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserinfoConfig.class);

    private static final Map<String, BillUserinfoDTO> userinfos = new HashMap<>();

    @Autowired
    private BillUserinfoDao billUserinfoDao;

//    @PostConstruct
//    public void init(){
//        billUserinfoDao.queryAll().forEach(a -> {
//            userinfos.put(a.getMailUsername(), a);
//            LOGGER.info("用户{ }init",a.getMailUsername());
//        });
//    }

    /**
     * 查询用户信息
     * @param mailUsername
     * @return
     */
    public static BillUserinfoDTO searchUserinfo(String mailUsername){
        return userinfos.get(mailUsername);
    }

}
