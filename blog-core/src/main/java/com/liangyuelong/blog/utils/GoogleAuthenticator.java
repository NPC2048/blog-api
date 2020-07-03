package com.liangyuelong.blog.utils;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

/**
 * 谷歌验证工具类
 *
 * @author yuelong.liang
 */
public class GoogleAuthenticator {

    /**
     * 时间戳偏移量
     */
    private static final int TIME_EXCURSION = 3;

    /**
     * 随机生成一个密钥
     */
    public static String createSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes).toLowerCase();
    }

    /**
     * 校验方法
     *
     * @param secretKey 密钥
     * @param code      用户输入的 TOTP 验证码
     */
    public static boolean verify(String secretKey, String code) {
        long time = System.currentTimeMillis() / 1000 / 30;
        for (int i = -TIME_EXCURSION; i <= TIME_EXCURSION; i++) {
            String totp = getTOTP(secretKey, time + i);
            if (code.equals(totp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据密钥获取验证码
     * 返回字符串是因为验证码有可能以 0 开头
     *
     * @param secretKey 密钥
     * @param time      第几个 30 秒 System.currentTimeMillis() / 1000 / 30
     */
    public static String getTOTP(String secretKey, long time) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey.toUpperCase());
        String hexKey = Hex.encodeHexString(bytes);
        String hexTime = Long.toHexString(time);
        return TOTP.generateTOTP(hexKey, hexTime, "6");
    }

}
