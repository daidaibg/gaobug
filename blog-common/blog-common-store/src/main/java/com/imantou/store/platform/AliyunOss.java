///*
// * 爱组搭 http://imantou.com 低代码组件化开发平台
// * ------------------------------------------
// * 受知识产权保护，请勿删除版权申明
// */
//package com.imantou.store.platform;
//
//import com.imantou.store.IFileStorage;
//import com.imantou.store.autoconfigure.OssProperties;
//import com.imantou.store.model.OssMark;
//import com.imantou.store.model.OssMessage;
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import com.aliyun.oss.common.utils.BinaryUtil;
//import com.aliyun.oss.model.MatchMode;
//import com.aliyun.oss.model.PolicyConditions;
//import com.aliyun.oss.model.PutObjectRequest;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//
///**
// * aliyun oss 存储
// * <p>
// * 尊重知识产权，CV 请保留版权，爱组搭 http://imantou.com 出品
// *
// * @author izyao
// * @since 2022-03-22
// */
//public class AliyunOss implements IFileStorage {
//
//    private OSS OSSClient;
//
//    private OssProperties properties;
//
//    private String host;
//
//    @Override
//    public AliyunOss init(OssProperties ossProperties) {
//        try {
//            properties = ossProperties;
//            OSSClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKey(), ossProperties.getSecretKey());
//            OSSClient.createBucket(ossProperties.getBucketName());
//            host = "https://" + properties.getBucketName() + "." + properties.getEndpoint();
//        } catch (Exception e) {
//            throw new RuntimeException("阿里云配置出错,请检查配置！");
//        }
//        return this;
//    }
//
//    @Override
//    public OssMessage upload(byte[] data) {
//        return this.upload(new ByteArrayInputStream(data));
//    }
//
//    @Override
//    public OssMessage upload(InputStream inputStream) {
//        String path = getPath();
//        OSSClient.putObject(new PutObjectRequest(properties.getBucketName(), path, inputStream));
//        return OssMessage.builder().filename(path).url(getEndpointLink() + "/" + path).build();
//    }
//
//    @Override
//    public String getEndpointLink() {
//        String endpoint = properties.getEndpoint();
//        StringBuilder uri = new StringBuilder(endpoint);
//        if (endpoint.startsWith("http://")) {
//            uri.insert(7, properties.getBucketName() + ".");
//        } else if (endpoint.startsWith("https://")) {
//            uri.insert(8, properties.getBucketName() + ".");
//        } else {
//            throw new RuntimeException("Endpoint配置错误");
//        }
//        return uri.toString();
//    }
//
//    @Override
//    public OssMark getMark() {
//        long expireTime = 30;
//        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
//        String dir = getPath();
//        Date expiration = new Date(expireEndTime);
//        // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
//        PolicyConditions policyConds = new PolicyConditions();
//        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
//        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
//
//        String postPolicy = OSSClient.generatePostPolicy(expiration, policyConds);
//        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
//        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
//        String postSignature = OSSClient.calculatePostSignature(postPolicy);
//        return OssMark.builder()
//                .accessid(properties.getAccessKey())
//                .policy(encodedPolicy)
//                .signature(postSignature)
//                .dir(dir)
//                .host(host)
//                .expire(String.valueOf(expireEndTime / 1000))
//                .build();
//    }
//
//    @Override
//    public void delete(String pathName) {
//        pathName = pathName.replace(getEndpointLink() + "/", "");
//        try {
//            OSSClient.deleteObject(properties.getBucketName(), pathName);
//        } catch (Exception e) {
//            throw new RuntimeException("删除失败,请检查相关配置");
//        }
//    }
//}
