package org.heart.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.lang3.StringUtils;
import org.heart.config.UserinfoConfig;
import org.heart.dao.BillTradeDao;
import org.heart.dto.BillTradeDTO;
import org.heart.dto.BillUserinfoDTO;
import org.heart.service.MailService;
import org.heart.service.BusinessService;
import org.heart.service.ZipService;
import org.heart.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BusinessServiceImpl implements BusinessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessServiceImpl.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private ZipService zipService;

    @Value("${AttachPath}")
    private String AttachPath;

    @Autowired
    private UserinfoConfig userinfoConfig;

    @Autowired
    private BatchSupport batchSupport;

    @Override
    public void getMailInfo(Message[] message, String mailUsername) {
        try {
            for (int i = 0; i < message.length; i++) {
                this.alipayBusiness(mailService.getFrom((MimeMessage) message[i]), message[i], mailUsername);
            }
        } catch (Exception e) {
            LOGGER.error("BusinessServiceImpl.getMailInfo error", e);
        }

    }

    @Override
    public void alipayBusiness(String sendName, Part part, String mailUsername) {
        try {
            //获取支付宝账单
            if (sendName.indexOf("支付宝提醒") != -1) {
                //查看是否有附件
                if (mailService.isContainAttach(part)) {
                    //保存附件
                    String storeFile = mailService.saveAttachment(part);
                    if (null == storeFile) {
                        return;
                    }
                    BillUserinfoDTO billUserinfoDTO = userinfoConfig.searchUserinfo(mailUsername);
                    //解压附件
                    String csvUrl = zipService.unZip(storeFile, AttachPath + File.separator + "bill", billUserinfoDTO.getAliBillPassword());
                    if(StringUtils.isBlank(csvUrl)){
                        return;
                    }
                    //解析csv
                    this.getCsvDataManual(csvUrl, mailUsername);
                }
            }
        } catch (Exception e) {
            LOGGER.error("BusinessServiceImpl.alipayBusiness error", e);
        }
    }

    private void getCsvDataManual(String csvUrl, String mailUsername){
        try {
            List<String> lines = Files.readAllLines(Paths.get(csvUrl),Charset.forName("GBK"));
            List<BillTradeDTO> billTradeDTOList = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                if(i == 4){
                    LOGGER.info("{}", lines.get(i));
                }
                if(i >= 25){
                    String[] str = lines.get(i).split(",");
                    BillTradeDTO billTradeDTO = new BillTradeDTO();
                    billTradeDTO.setTradeTime(DateUtil.convert(str[0],DateUtil.DATE_19, DateUtil.DATE_14));
                    billTradeDTO.setTradeType(str[1]);
                    billTradeDTO.setMerchantName(str[2]);
                    billTradeDTO.setTradeAccount(str[3]);
                    billTradeDTO.setProductDescription(str[4]);
                    billTradeDTO.setIncomeExpenses(str[5]);
                    Double amount = Double.valueOf(str[6]) * 100;
                    billTradeDTO.setAmount(amount.longValue());
                    billTradeDTO.setPaymentMethod(str[7]);
                    billTradeDTO.setTradeStatus(str[8]);
                    billTradeDTO.setTradeOrderId(str[9].trim());
                    billTradeDTO.setMerchantOrderId(str[10].trim());
                    billTradeDTO.setUserId(mailUsername);
                    billTradeDTO.setInvalidState("1");
                    billTradeDTO.setCreateTime(DateUtil.getChar14());
                    billTradeDTOList.add(billTradeDTO);
                }
            }
            batchSupport.batchExecute(BillTradeDao.BATCH_MERGE, billTradeDTOList);
        }catch (Exception e){
            LOGGER.error("CSV文件读取异常", e);
        }
    }
}
