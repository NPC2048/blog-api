package com.liangyuelong.blog.test.string;

import com.liangyuelong.blog.test.elapsedtime.ElapsedTime;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

public class StringTest {

    public static int null2Int(String st) {
        if (st != null) {
            try {
                return Integer.parseInt(st);
            } catch (Exception ignored) {
            }
        }
        return 0;
    }

    public static int null2Int(Object s) {
        if (s != null) {
            try {
                return Integer.parseInt(s.toString());
            } catch (Exception ignored) {
            }
        }
        return 0;
    }

    @Test
    public void test() {
        int length = 110000000;
        ElapsedTime.elapsedTimePrint(() -> {
            null2Int("1000");
        }, length);
        System.out.println("begin");
        ElapsedTime.elapsedTimePrint(() -> {
            null2Int("1000");
        }, length);
        ElapsedTime.elapsedTimePrint(() -> {
            null2Int((Object) "1000");
        }, length);
        ElapsedTime.elapsedTimePrint(() -> {
            null2Int("1000");
        }, length);
        ElapsedTime.elapsedTimePrint(() -> {
            null2Int((Object) "1000");
        }, length);
        ElapsedTime.elapsedTimePrint(() -> {
            null2Int("1000");
        }, length);
        ElapsedTime.elapsedTimePrint(() -> {
            null2Int((Object) "1000");
        }, length);
    }

    @Test
    public void formatTest() {
        String template = "<html>{0}</html>";
        String value = "fuck";
        String result = String.format(template, value);
        System.out.println(result);
        result = MessageFormat.format(template, value);
        System.out.println(result);
    }

}
