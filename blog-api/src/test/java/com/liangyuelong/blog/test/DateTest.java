package com.liangyuelong.blog.test;

import cn.hutool.Hutool;
import cn.hutool.crypto.SecureUtil;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Calendar;

public class DateTest {

    @Test
    public void test() throws ParseException {
        FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MARCH, 9);
        System.out.println(format.format(calendar.getTime()));
//        calendar.add(Calendar.DATE, 100);
        calendar.add(Calendar.DAY_OF_MONTH, 90);
        System.out.println(format.format(calendar.getTime()));
    }

}
