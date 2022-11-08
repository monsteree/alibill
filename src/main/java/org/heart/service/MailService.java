package org.heart.service;

import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;

public interface MailService {

    /**
     * 获得发件人的地址和姓名
     * @param mimeMessage
     * @return
     * @throws Exception
     */
    String getFrom(MimeMessage mimeMessage) throws Exception;

    /**
     * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同
     * @param mimeMessage
     * @param type
     * @return
     * @throws Exception
     */
    String getMailAddress(MimeMessage mimeMessage, String type) throws Exception;

    /**
     * 获得邮件主题
     * @param mimeMessage
     * @return
     * @throws Exception
     */
    String getSubject(MimeMessage mimeMessage)throws Exception;

    /**
     * 获得邮件发送日期
     * @param mimeMessage
     * @return
     * @throws Exception
     */
    String getSentDate(MimeMessage mimeMessage)throws Exception;

    /**
     * 获得邮件正文内容
     * @param bodyText
     * @return
     * @throws Exception
     */
    String getBodyText(StringBuffer bodyText)throws Exception;

    /**
     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
     * 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析 　　
     * @param part
     * @param bodyText
     * @throws Exception
     */
    void getMailContent(Part part, StringBuffer bodyText)throws Exception;

    /**
     * 获得此邮件的Message-ID
     * @return
     * @throws Exception
     */
    String getMessageId(MimeMessage mimeMessage) throws Exception;

    /**
     * 判断此邮件是否包含附件
     * @return
     * @throws Exception
     */
    Boolean isContainAttach(Part part) throws Exception;

    /**
     *保存附件
     * @throws Exception
     */
    String saveAttachment(Part part)throws Exception;

    /**
     * 真正的保存附件到指定目录里
     * @param fileName
     * @param in
     * @throws Exception
     */
    String saveFile(String fileName, InputStream in) throws Exception;



}
