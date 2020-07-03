package com.liangyuelong.blog.test.string;

import com.liangyuelong.blog.test.elapsedtime.ElapsedTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class StringBuilderTest {

    private List<String> numberList = new LinkedList<>();

    private StringBuilder str = new StringBuilder();

    @BeforeEach
    public void init() {
        for (int i = 0; i < 10000000; i++) {
            str.append(i);
        }
    }

    @Test
    public void test() {
        System.out.println(str.substring(0, 100));
        ElapsedTime.elapsedTimePrint((i) -> {
            str.deleteCharAt(str.length() - 1).toString();
        }, 50);
        System.out.println(str.length());
    }

    @Test
    public void test2() {
        System.out.println(str.substring(0, 100));
        ElapsedTime.elapsedTimePrint((i) -> {
            str.substring(0, str.length() - 1);
        }, 50);
    }

    @Test
    public void testi() {
        String str = "2918\t2735\t2736\t2779\t2706\t2741\t3156\t3162\t2799\t2949\t2731\t2943";
        avg(str);
        System.out.println("=======================");
        str = "3084\t2754\t2833\t3208\t2827\t2856\t2806\t2745\t3133\t2758\t2913\t2854";
        avg(str);
    }

    public void avg(String arrStr) {
        String[] strArr = arrStr.split("\\s+");
        System.out.println(Arrays.toString(strArr));
        int[] arr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        Arrays.sort(arr);
        int sum = 0;
        for (int i = 1; i < arr.length - 1; i++) {
            sum += arr[i];
        }
        System.out.println("min: " + arr[0]);
        System.out.println("max: " + arr[arr.length - 1]);
        System.out.println("sum: " + sum);
        System.out.println("avg: " + sum / 10);
    }

}
