package com.liangyuelong.blog.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 基于 java.net.URLDecoder
 * 与 apache 的 StandardCharsets 结合使用, 不用处理异常
 *
 * @author yuelong.liang
 */
public class UrlDecoder {

    private UrlDecoder() {

    }

    public static String decode(String content) {
        return decode(content, StandardCharsets.UTF_8);
    }

    public static String decode(String content, Charset charset) {
        boolean needToChange = false;
        int numChars = content.length();
        StringBuilder sb = new StringBuilder(numChars > 500 ? numChars / 2 : numChars);
        int i = 0;

        char c;
        byte[] bytes = null;
        while (i < numChars) {
            c = content.charAt(i);
            switch (c) {
                case '+':
                    sb.append(' ');
                    i++;
                    needToChange = true;
                    break;
                case '%':
                    try {
                        // (numChars-i)/3 is an upper bound for the number
                        // of remaining bytes
                        if (bytes == null) {
                            bytes = new byte[(numChars - i) / 3];
                        }
                        int pos = 0;

                        while (((i + 2) < numChars) &&
                                (c == '%')) {
                            int v = Integer.parseInt(content.substring(i + 1, i + 3), 16);
                            if (v < 0) {
                                throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
                            }
                            bytes[pos++] = (byte) v;
                            i += 3;
                            if (i < numChars) {
                                c = content.charAt(i);
                            }
                        }

                        if ((i < numChars) && (c == '%')) {
                            throw new IllegalArgumentException(
                                    "URLDecoder: Incomplete trailing escape (%) pattern");
                        }

                        sb.append(new String(bytes, 0, pos, charset));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(
                                "URLDecoder: Illegal hex characters in escape (%) pattern - "
                                        + e.getMessage());
                    }
                    needToChange = true;
                    break;
                default:
                    sb.append(c);
                    i++;
                    break;
            }
        }
        return (needToChange ? sb.toString() : content);
    }

}
