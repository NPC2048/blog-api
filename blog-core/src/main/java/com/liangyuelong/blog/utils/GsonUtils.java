package com.liangyuelong.blog.utils;

import com.google.gson.*;
import com.liangyuelong.blog.common.constant.DateFormatConstants;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gson 工具类
 *
 * @author yuelong.liang
 */
public class GsonUtils {

    private GsonUtils() {
    }

    private static GsonBuilder gsonBuilder = customGsonBuilder(DateFormatConstants.DEFAULT);

    private static Gson gson = gsonBuilder.create();

    private static Map<String, Gson> customGsons = new ConcurrentHashMap<>();

    private static int maxSize = 32;

    public static GsonBuilder customGsonBuilder(String defaultDateFormat) {
        return createDefaultTypeAdapter(new GsonBuilder(), defaultDateFormat).enableComplexMapKeySerialization();
    }

    private static Gson getFormatGson(String dateFormat) {
        Gson gson = customGsons.get(dateFormat);
        if (gson == null) {
            clearGsonMap();
            gson = gsonBuilder.setDateFormat(dateFormat).create();
            customGsons.put(dateFormat, gson);
        }
        return gson;
    }

    private static void clearGsonMap() {
        if (customGsons.size() > maxSize) {
            customGsons.clear();
        }
    }

    public static String objectToJson(Object obj) {
        return gson.toJson(obj);
    }

    public static String objectToJson4Null(Object obj) {
        Gson gson = customGsons.get("gsonNull");
        if (gson == null) {
            clearGsonMap();
            gson = customGsonBuilder(DateFormatConstants.DEFAULT).serializeNulls().create();
            customGsons.put("gsonNull", gson);
        }
        return gson.toJson(obj);
    }

    public static String objectToJson(Object obj, String dateFormat) {
        return getFormatGson(dateFormat).toJson(obj);
    }

    public static String objectToJson4Null(Object obj, String dateFormat) {
        String key = "gsonNull" + dateFormat;
        Gson gson = customGsons.get(key);
        if (gson != null) {
            return gson.toJson(obj);
        }
        clearGsonMap();
        gson = customGsonBuilder(dateFormat).serializeNulls().create();
        customGsons.put(key, gson);
        return gson.toJson(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> c) {
        return gson.fromJson(json, c);
    }

    public static <T> T jsonToObject(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> T jsonToObject(String json, Class<T> c, String dateFormat) {
        return getFormatGson(dateFormat).fromJson(json, c);
    }

    public static <T> T jsonToObject(String json, Type type, String dateFormat) {
        return getFormatGson(dateFormat).fromJson(json, type);
    }

    public static JsonElement objectToJsonTree(Object src) {
        return gson.toJsonTree(src);
    }

    public static JsonElement objectToJsonTree(Object src, Type typeOfSrc) {
        return gson.toJsonTree(src, typeOfSrc);
    }

    public static <T> T jsonToObject(JsonElement ele, Class<T> classOfT) {
        return gson.fromJson(ele, classOfT);
    }

    public static <T> T jsonToObject(JsonElement json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static GsonBuilder createDefaultTypeAdapter(final GsonBuilder gs, String dateFormat) {
        gs.registerTypeAdapter(Double.class, (JsonDeserializer<Double>) (json, typeOfT, context) -> {
            final String date = json.getAsJsonPrimitive().getAsString();
            try {
                if ("".equals(date)) {
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return Double.valueOf(date);
        });
        gs.registerTypeAdapter(Integer.class, (JsonDeserializer<Integer>) (json, typeOfT, context) -> {
            final String date = json.getAsJsonPrimitive().getAsString();
            try {
                if ("".equals(date)) {
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return Integer.valueOf(date);
        });
        gs.registerTypeAdapter(Float.class, (JsonDeserializer<Float>) (json, typeOfT, context) -> {
            final String date = json.getAsJsonPrimitive().getAsString();
            try {
                if ("".equals(date)) {
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return Float.valueOf(date);
        });
        gs.registerTypeAdapter(Short.class, (JsonDeserializer<Short>) (json, typeOfT, context) -> {
            final String date = json.getAsJsonPrimitive().getAsString();
            try {
                if ("".equals(date)) {
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return Short.valueOf(date);
        });
        gs.registerTypeAdapter(Long.class, (JsonDeserializer<Long>) (json, typeOfT, context) -> {
            final String date = json.getAsJsonPrimitive().getAsString();
            try {
                if ("".equals(date)) {
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return Long.valueOf(date);
        });
        gs.registerTypeAdapter(Date.class, new DateTypeAdatper(dateFormat));
        return gs;
    }

    public static class DateTypeAdatper implements JsonSerializer<Date>, JsonDeserializer<Date> {

        private String dateFormat;

        DateTypeAdatper(String dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            String date = json.getAsJsonPrimitive().getAsString();
            if ("".equals(date)) {
                return null;
            }
            Date td = null;
            if (date.length() != dateFormat.length()) {
                td = parse(DateFormat.getDateInstance(), date);
            }
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            if (td != null) {
                date = sdf.format(td);
            }
            return parse(sdf, date);
        }

        public Date parse(DateFormat df, String date) {
            try {
                return df.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(DateFormatUtils.format(src, dateFormat));
        }

    }

    public static int getMaxSize() {
        return maxSize;
    }

    public static void setMaxSize(int maxSize) {
        GsonUtils.maxSize = maxSize;
    }
}
