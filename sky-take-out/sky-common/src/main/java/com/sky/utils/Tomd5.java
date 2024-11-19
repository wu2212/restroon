package com.sky.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Tomd5 {
    public String toMD5(String original) {
        try {
            // 创建 MessageDigest 实例，指定为 MD5 算法
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 对字符串进行加密处理
            md.update(original.getBytes());
            byte[] digest = md.digest();

            // 将加密后的字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

            // 返回 MD5 加密后的字符串
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
