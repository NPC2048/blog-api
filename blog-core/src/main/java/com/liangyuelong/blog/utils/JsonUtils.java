package com.liangyuelong.blog.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.liangyuelong.blog.common.constant.DateFormatConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author yuelong.liang
 */
@Slf4j
public class JsonUtils {

    private static int maxSize = 16;

    private static final ConcurrentMap<String, ObjectMapper> CACHE = new ConcurrentHashMap<>(maxSize);

    private static final ObjectMapper DEFAULT_OM = customObjectMapper(DateFormatConstants.DEFAULT);

    public static String toJson(Object value, String pattern) {
        try {
            return toJsonThrow(value, pattern);
        } catch (JsonProcessingException e) {
            log.info(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public static String toJson(Object value) {
        try {
            return toJsonThrow(value);
        } catch (JsonProcessingException e) {
            log.info(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public static void write(OutputStream output, Object value) {
        try {
            writeThrow(output, value);
        } catch (IOException e) {
            log.info(ExceptionUtils.getStackTrace(e));
        }
    }

    public static void write(Writer writer, Object value) {
        try {
            writeThrow(writer, value);
        } catch (IOException e) {
            log.info(ExceptionUtils.getStackTrace(e));
        }
    }

    public static <T> T parse(String json, Class<T> tClass) {
        try {
            parseThrow(json, tClass);
        } catch (JsonProcessingException e) {
            log.info(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public static <T> T parse(String json, String pattern, Class<T> tClass) {
        try {
            parseThrow(json, pattern, tClass);
        } catch (JsonProcessingException e) {
            log.info(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public static <T> T parse(String json, TypeReference<T> typeReference) {
        try {
            return parseThrow(json, typeReference);
        } catch (JsonProcessingException e) {
            log.info(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public static <T> T parse(String json, String pattern, TypeReference<T> typeReference) {
        try {
            return parseThrow(json, pattern, typeReference);
        } catch (JsonProcessingException e) {
            log.info(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public static String toJsonThrow(Object value) throws JsonProcessingException {
        return DEFAULT_OM.writeValueAsString(value);
    }

    public static String toJsonThrow(Object value, String pattern) throws JsonProcessingException {
        return getNullObjectMapper(pattern).writeValueAsString(value);
    }

    public static void writeThrow(OutputStream output, Object value) throws IOException {
        DEFAULT_OM.writeValue(output, value);
    }

    public static void writeThrow(Writer writer, Object value) throws IOException {
        DEFAULT_OM.writeValue(writer, value);
    }

    public static <T> T parseThrow(String json, Class<T> tClass) throws JsonProcessingException {
        return DEFAULT_OM.readValue(json, tClass);
    }

    public static <T> T parseThrow(String json, String pattern, Class<T> tClass) throws JsonProcessingException {
        return getNullObjectMapper(pattern).readValue(json, tClass);
    }

    public static <T> T parseThrow(String json, String pattern, TypeReference<T> typeReference) throws JsonProcessingException {
        return getNullObjectMapper(pattern).readValue(json, typeReference);
    }

    public static <T> T parseThrow(String json, TypeReference<T> typeReference) throws JsonProcessingException {
        return DEFAULT_OM.readValue(json, typeReference);
    }

    private static ObjectMapper customObjectMapper(String pattern) {
        FastDateFormat fastDateFormat = FastDateFormat.getInstance(pattern);
        DateFormat dateFormat = new DateFormat() {
            @Override
            public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
                return fastDateFormat.format(date, toAppendTo, fieldPosition);
            }

            @Override
            public Date parse(String source, ParsePosition pos) {
                return fastDateFormat.parse(source, pos);
            }
        };
        return Jackson2ObjectMapperBuilder.json().dateFormat(dateFormat)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).build();
    }

    private static ObjectMapper getNullObjectMapper(String pattern) {
        return pattern == null ? DEFAULT_OM : getObjectMapper(pattern);
    }

    /**
     * 获取 ObjectMapper
     * 先根据 pattern 从 CACHE 获取，如果没有，则根据 pattern 生成 ObjectMapper,
     * 存入 CACHE 后返回
     *
     * @param pattern 日期格式化
     * @return ObjectMapper
     */
    private static ObjectMapper getObjectMapper(String pattern) {
        ObjectMapper om = CACHE.get(pattern);
        if (om == null) {
            om = customObjectMapper(pattern);
            if (CACHE.size() >= maxSize) {
                CACHE.clear();
            }
            CACHE.put(pattern, om);
        }
        return om;
    }
}
