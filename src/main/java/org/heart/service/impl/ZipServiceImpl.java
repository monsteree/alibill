package org.heart.service.impl;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.heart.controller.loginController;
import org.heart.service.ZipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZipServiceImpl implements ZipService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipServiceImpl.class);

    @Override
    public void unZip(String source, String dest, String password) {
        File zipFile = new File(source);
        try {
            // 首先创建ZipFile指向磁盘上的.zip文件
            ZipFile zFile = new ZipFile(zipFile);

            zFile.setFileNameCharset("UTF-8");
            // 解压目录
            File destDir = new File(dest);
            if (!destDir.exists()) {
                // 目标目录不存在时，创建该文件夹
                destDir.mkdirs();
            }
            if (zFile.isEncrypted()) {
                // 设置密码
                zFile.setPassword(password.toCharArray());
            }
            // 将文件抽出到解压目录(解压)
            zFile.extractAll(dest);
            List<FileHeader> headerList = zFile.getFileHeaders();
            List<File> extractedFileList = new ArrayList<>();
            headerList.forEach(a -> {
                if (!a.isDirectory()) {
                    extractedFileList.add(new File(destDir, a.getFileName()));
                }
            });
            File[] extractedFiles = new File[extractedFileList.size()];
            extractedFileList.toArray(extractedFiles);
            extractedFileList.forEach(a -> {
                LOGGER.info("{}文件解压成功!", a.getAbsolutePath());
            });
        } catch (ZipException e) {
            LOGGER.error("ZipServiceImpl.unZip error", e);
        }
    }

    @Override
    public void unZip(String source, String dest) {
        this.unZip(source, dest, "");
    }
}
