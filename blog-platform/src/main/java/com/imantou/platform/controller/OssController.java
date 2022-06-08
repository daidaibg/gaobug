package com.imantou.platform.controller;


import com.imantou.response.exception.BusinessException;
import com.imantou.response.ResponseWrapped;
import com.imantou.platform.domain.SysOss;
import com.imantou.platform.service.SysOssService;
import com.imantou.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;


@RestController
@RequestMapping("/platform/oss")
@RefreshScope
public class OssController {

    @Value("${gaobug.url.website}")
    private String website;

    @Resource
    private SysOssService ossService;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public ResponseWrapped<Object> upload(MultipartFile file) {
        if (file == null) {
            throw new BusinessException("照片上传失败");
        }
        if (file.getSize() > 50 * 1024L * 1024L) {
            throw new BusinessException("上传图片不能超过50M");
        }
        String newFileName = getNewFileName(file);
        String path = "/opt/blog/image/" + newFileName;
        //保存文件
        FileUtils.uploadImg(file, path);
        //保存文件信息
        SysOss sysOosForSave = new SysOss();
        sysOosForSave.setUrl("/img/" + newFileName);
        sysOosForSave.setCreateDate(new Date());
        ossService.save(sysOosForSave);
        sysOosForSave.setUrl(website + sysOosForSave.getUrl());
        return ResponseWrapped.success(sysOosForSave);
    }

    public static String getNewFileName(MultipartFile file) {
        //原文件名
        String originalFileName = file.getOriginalFilename();
        //获取文件后缀
        String subName = originalFileName.substring(originalFileName.lastIndexOf("."));
        //新文件名
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "/" + UUID.randomUUID() + subName;
    }
}
