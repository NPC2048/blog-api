package com.liangyuelong.blog.test.regex;

import com.liangyuelong.blog.common.util.LetterUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 正则表达式验证
 *
 * @author yuelong.liang
 */
@Slf4j
public class RegexpTest {

    private char[] ch;

    @BeforeEach
    public void init() {
        log.info("init random ch pool");
        char[] ch = Arrays.copyOf(LetterUtils.ALL_LETTERS, LetterUtils.ALL_LETTERS.length);
        // 增加 - _ $ # %
        char[] newCh = new char[]{'@', '-', '_', '#', '%'};
        this.ch = ArrayUtils.addAll(ch, newCh);
        log.info(String.valueOf(ch));
    }

    @Test
    public void testValidUsername() {
        String regexp = "[\\w-]+";
        Pattern pattern = Pattern.compile(regexp);
        log.info("regexp: " + regexp);
        String username = "npc2048@153.co";
        log.info(username + " matches: " + pattern.matcher(username).matches());
        for (int i = 0; i < 25; i++) {
            username = RandomStringUtils.random(16, ch);
            log.info(username + " matches: " + pattern.matcher(username).matches());
        }

    }


}
