package com.gaobug.store;

import com.gaobug.store.autoconfigure.OssProperties;
import com.gaobug.store.model.OssMark;
import com.gaobug.store.model.OssMessage;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public interface FileStorage {

    /**
     * 初始化
     *
     * @param ossProperties 配置
     * @return 当前对象
     */
    FileStorage init(OssProperties ossProperties);


    OssMessage upload(byte[] data);

    /**
     * 文件上传
     */
    OssMessage upload(InputStream inputStream);

    /**
     * 文件删除
     *
     * @param pathName 文件露肩
     */
    void delete(String pathName);

    /**
     * 获取域名地址
     */
    String getEndpointLink();

    /**
     * 生成日期文件路径
     *
     * @return
     */
    default String getPath() {
        String uuid = UUID.randomUUID().toString();
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "/" + uuid;
    }

    /**
     * 采用服务端签名后直传方案
     *
     * @return 签名
     * @author VampireAchao
     * @link https://help.aliyun.com/document_detail/31927.html
     */
    OssMark getMark();

}
