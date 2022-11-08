package org.heart.service.impl;

import org.heart.service.MailService;
import org.heart.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    @Value("${AttachPath}")
    private String AttachPath;

    @Override
    public String getFrom(MimeMessage mimeMessage) throws Exception {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
        String from = address[0].getAddress();
        if (from == null) {
            from = "";
            LOGGER.info("无法知道发送者.");
        }
        String personal = address[0].getPersonal();

        if (personal == null) {
            personal = "";
            LOGGER.info("无法知道发送者的姓名.");
        }

        String fromAddr = null;
        if (personal != null || from != null) {
            fromAddr = personal + "<" + from + ">";
            LOGGER.info("发送者是：{}", fromAddr);
        }
        return fromAddr;
    }

    /**
     * 　*　获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同
     * 　*　"to"----收件人　"cc"---抄送人地址　"bcc"---密送人地址
     */
    @Override
    public String getMailAddress(MimeMessage mimeMessage, String type) throws Exception {
        String mailAddr = "";
        String addType = type.toUpperCase();

        InternetAddress[] address = null;
        if (addType.equals("TO") || addType.equals("CC") || addType.equals("BCC")) {

            if (addType.equals("TO")) {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
            } else if (addType.equals("CC")) {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
            } else {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
            }

            if (address != null) {
                for (int i = 0; i < address.length; i++) {
                    String emailAddr = address[i].getAddress();
                    if (emailAddr == null) {
                        emailAddr = "";
                    } else {
                        LOGGER.info("转换之前的emailAddr: {}", emailAddr);
                        emailAddr = MimeUtility.decodeText(emailAddr);
                        LOGGER.info("转换之后的emailAddr: ", emailAddr);
                    }
                    String personal = address[i].getPersonal();
                    if (personal == null) {
                        personal = "";
                    } else {
                        LOGGER.info("转换之前的personal: {}", personal);
                        personal = MimeUtility.decodeText(personal);
                        LOGGER.info("转换之后的personal: {}", personal);
                    }
                    String compositeto = personal + "<" + emailAddr + ">";
                    LOGGER.info("完整的邮件地址：{}", compositeto);
                    mailAddr += "," + compositeto;
                }
                mailAddr = mailAddr.substring(1);
            }
        } else {
            throw new Exception("错误的电子邮件类型!");
        }
        return mailAddr;
    }

    @Override
    public String getSubject(MimeMessage mimeMessage) throws Exception {
        String subject = "";
        try {
            LOGGER.info("转换前的subject：{}", mimeMessage.getSubject());
            subject = MimeUtility.decodeText(mimeMessage.getSubject());
            LOGGER.info("转换后的subject: {}", mimeMessage.getSubject());
            if (subject == null) {
                subject = "";
            }
        } catch (Exception exce) {
            exce.printStackTrace();
        }
        return subject;
    }

    @Override
    public String getSentDate(MimeMessage mimeMessage) throws Exception {
        Date sentDate = mimeMessage.getSentDate();
        LOGGER.info("发送日期 原始类型: {}", DateUtil.DATE_14);
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.DATE_14);
        String strSentDate = format.format(sentDate);
        LOGGER.info("发送日期 可读类型: {}", strSentDate);
        return strSentDate;
    }

    @Override
    public String getBodyText(StringBuffer bodyText) throws Exception {
        return bodyText.toString();
    }

    @Override
    public void getMailContent(Part part, StringBuffer bodyText) throws Exception {
        String contentType = part.getContentType();
        // 获得邮件的MimeType类型
        LOGGER.info("邮件的MimeType类型: {}", contentType);
        int nameIndex = contentType.indexOf("name");

        boolean conName = false;

        if (nameIndex != -1) {
            conName = true;
        }

        if (part.isMimeType("text/plain") && conName == false) {
            // text/plain 类型
            bodyText.append((String) part.getContent());
        } else if (part.isMimeType("text/html") && conName == false) {
            // text/html 类型
            bodyText.append((String) part.getContent());
        }
//        else if (part.isMimeType("multipart/*"))
//        {
//            // multipart/*
//            Multipart multipart = (Multipart) part.getContent();
//            int counts = multipart.getCount();
//            for (int i = 0; i < counts; i++)
//            {
//                getMailContent(multipart.getBodyPart(i));
//            }
//        }
//        else if (part.isMimeType("message/rfc822"))
//        {
//            // message/rfc822
//            getMailContent((Part) part.getContent());
//        }
//        else
//        {
//
//        }
    }

    @Override
    public String getMessageId(MimeMessage mimeMessage) throws Exception {
        String messageID = mimeMessage.getMessageID();
        LOGGER.info("邮件ID: {}", messageID);
        return messageID;
    }

    @Override
    public Boolean isContainAttach(Part part) throws Exception {
        boolean attachFlag = false;
        // String contentType = part.getContentType();
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mPart = mp.getBodyPart(i);
                String disposition = mPart.getDisposition();
                if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))
                    attachFlag = true;
                else if (mPart.isMimeType("multipart/*")) {
                    attachFlag = isContainAttach((Part) mPart);
                } else {
                    String conType = mPart.getContentType();

                    if (conType.toLowerCase().indexOf("application") != -1)
                        attachFlag = true;
                    if (conType.toLowerCase().indexOf("name") != -1)
                        attachFlag = true;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            attachFlag = isContainAttach((Part) part.getContent());
        }
        return attachFlag;
    }

    @Override
    public String saveAttachment(Part part) throws Exception {
        String fileName;
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mPart = mp.getBodyPart(i);
                String disposition = mPart.getDisposition();
                if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
                    fileName = mPart.getFileName();
                    if (fileName.toLowerCase().indexOf("gb2312") != -1) {
                        fileName = MimeUtility.decodeText(fileName);
                    }
                    return saveFile(fileName, mPart.getInputStream());

                } else if (mPart.isMimeType("multipart/*")) {
                    saveAttachment(mPart);
                } else {
                    fileName = mPart.getFileName();
                    if ((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
                        fileName = MimeUtility.decodeText(fileName);
                        return saveFile(fileName, mPart.getInputStream());
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachment((Part) part.getContent());
        }
        return null;
    }

    @Override
    public String saveFile(String fileName, InputStream in) throws Exception {
        String storeDir = AttachPath;
        File directory = new File(storeDir);
        if(!directory.exists()){
            Files.createDirectory(Paths.get(storeDir));
        }
        File storeFile = new File(storeDir + File.separator + fileName);
        LOGGER.info("附件的保存地址:　{}", storeFile.toString());
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(storeFile));
            BufferedInputStream bis = new BufferedInputStream(in)) {
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        }catch (Exception e){
            throw new Exception("文件保存失败!");
        }
        return storeFile.toString();
    }

}
