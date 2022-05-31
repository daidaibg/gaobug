package com.imantou.exception.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
    /**
     * SHA256加密
     *
     * @param plaintext 明文
     * @param salt      盐
     * @return 加密值
     */
    public static String sha256(String plaintext, String salt) {
        return sha256(salt + plaintext);
    }

    /**
     * SHA256加密
     *
     * @param plaintext 明文
     * @return 加密值
     */
    public static String sha256(String plaintext) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(plaintext.getBytes(StandardCharsets.UTF_8));
            return byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes bytes
     * @return 加密值
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
