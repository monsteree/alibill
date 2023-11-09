package org.heart.service;

public interface ZipService {


    /**
     * 解压zip包带密码
     *
     * @param source   原始文件路径
     * @param dest     解压路径
     * @param password 解压文件密码(可以为空)
     */
    String unZip(String source, String dest, String password);

    /**
     * 解压zip包带密码
     *
     * @param source 原始文件路径
     * @param dest   解压路径
     */
    void unZip(String source, String dest);

}
