package com.liangyuelong.blog.utils;

import org.apache.commons.lang3.RandomUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 验证码生成工具类
 *
 * @author yuelong.liang
 */
public class VerifyCodeUtils {

    public static String drawRandomText(int width, int height, BufferedImage verifyImg) {
        Graphics2D graphics = (Graphics2D) verifyImg.getGraphics();
        //设置画笔颜色-验证码背景色
        graphics.setColor(Color.WHITE);
        //填充背景
        graphics.fillRect(0, 0, width, height);
        graphics.setFont(new Font("微软雅黑", Font.BOLD, 40));

        //数字和字母的组合
        String baseNumLetter = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
        StringBuilder sBuffer = new StringBuilder();

        //旋转原点的 x 坐标
        int x = 10;
        String ch;
        for (int i = 0; i < 4; i++) {
            graphics.setColor(randomColor());
            //设置字体旋转角度, 角度小于90度
            int degree = RandomUtils.nextInt() % 90;
            int dot = RandomUtils.nextInt(0, baseNumLetter.length());
            ch = baseNumLetter.charAt(dot) + "";
            sBuffer.append(ch);
            //正向旋转
            graphics.rotate(degree * Math.PI / 180, x, 45);
            graphics.drawString(ch, x, 45);
            //反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, 45);
            x += 48;
        }
        //画干扰线
        for (int i = 0; i < 6; i++) {
            // 设置随机颜色
            graphics.setColor(randomColor());
            // 随机画线
            graphics.drawLine(RandomUtils.nextInt(0, width), RandomUtils.nextInt(0, height),
                    RandomUtils.nextInt(0, width), RandomUtils.nextInt(0, height));
        }

        //添加噪点
        for (int i = 0; i < 30; i++) {
            int x1 = RandomUtils.nextInt(0, width);
            int y1 = RandomUtils.nextInt(0, height);
            graphics.setColor(randomColor());
            graphics.fillRect(x1, y1, 2, 2);
        }

        return sBuffer.toString();
    }

    /**
     * 升级生成颜色
     *
     * @return Color
     */
    public static Color randomColor() {
        return new Color(RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256), RandomUtils.nextInt(0, 256));
    }

}
