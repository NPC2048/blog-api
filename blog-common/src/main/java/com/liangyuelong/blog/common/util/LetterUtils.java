package com.liangyuelong.blog.common.util;

/**
 * 字母工具类
 *
 * @author yuelong.liang
 */
public class LetterUtils {

    public static final char[] ALL_LETTERS = new char[26 + 26 + 10];

    static {
        initAllLetters();
    }

    /**
     * 初始化所有字符串数组
     */
    private static void initAllLetters() {
        int index = 0;
        int size = 26;
        for (int i = 0; i < size; i++) {
            ALL_LETTERS[index++] = (char) (i + 65);
        }
        for (int i = 0; i < size; i++) {
            ALL_LETTERS[index++] = (char) (i + 97);
        }
        size = 10;
        for (int i = 0; i < size; i++) {
            ALL_LETTERS[index++] = (char) (i + 48);
        }
    }

    public static void reset() {
        initAllLetters();
    }

}
