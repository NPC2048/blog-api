package com.liangyuelong.blog.utils;


import java.io.CharArrayWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

/**
 * 基于 java.net.URLEncoder
 * 与 apache 的 StandardCharsets 结合使用, 不用处理异常
 *
 * @author yuelong.liang
 */
public class UrlEncoder {

    private static BitSet dontNeedEncoding;

    private static final int CASE_DIFF = ('a' - 'A');

    private UrlEncoder() {
    }

    static {
        dontNeedEncoding = new BitSet(256);
        for (int i = 'a', z = 'z'; i <= z; i++) {
            dontNeedEncoding.set(i);
        }
        for (int i = 'A', z = 'Z'; i <= z; i++) {
            dontNeedEncoding.set(i);
        }
        for (int i = '0', n = '9'; i <= n; i++) {
            dontNeedEncoding.set(i);
        }
        dontNeedEncoding.set(' ');
        dontNeedEncoding.set('-');
        dontNeedEncoding.set('_');
        dontNeedEncoding.set('.');
        dontNeedEncoding.set('*');
    }

    public static String encode(String s) {
        return encode(s, StandardCharsets.UTF_8);
    }

    public static String encode(String content, Charset charset) {
        boolean needToChange = false;
        StringBuilder out = new StringBuilder(content.length());
        CharArrayWriter charArrayWriter = new CharArrayWriter();

        for (int i = 0; i < content.length(); ) {
            int c = content.charAt(i);
            if (dontNeedEncoding.get(c)) {
                if (c == ' ') {
                    c = '+';
                    needToChange = true;
                }
                out.append((char) c);
                i++;
            } else {
                // convert to external encoding before hex conversion
                do {
                    charArrayWriter.write(c);
                    if (c >= 0xD800 && c <= 0xDBFF) {
                        if ((i + 1) < content.length()) {
                            int d = content.charAt(i + 1);
                            if (d >= 0xDC00 && d <= 0xDFFF) {
                                charArrayWriter.write(d);
                                i++;
                            }
                        }
                    }
                    i++;
                } while (i < content.length() && !dontNeedEncoding.get((c = content.charAt(i))));

                charArrayWriter.flush();
                String str = new String(charArrayWriter.toCharArray());
                byte[] ba = str.getBytes(charset);
                for (byte b : ba) {
                    out.append('%');
                    char ch = Character.forDigit((b >> 4) & 0xF, 16);
                    // converting to use uppercase letter as part of
                    // the hex value if ch is a letter.
                    if (Character.isLetter(ch)) {
                        ch -= CASE_DIFF;
                    }
                    out.append(ch);
                    ch = Character.forDigit(b & 0xF, 16);
                    if (Character.isLetter(ch)) {
                        ch -= CASE_DIFF;
                    }
                    out.append(ch);
                }
                charArrayWriter.reset();
                needToChange = true;
            }
        }

        return (needToChange ? out.toString() : content);
    }

}
