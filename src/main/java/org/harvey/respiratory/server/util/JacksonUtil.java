package org.harvey.respiratory.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json工具类
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-08 12:12
 */
public class JacksonUtil {
    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final PrintStream OUT = System.out;

    /**
     * 对所有调用次方法之后的转化都生效, 不会报错了
     */
    public static void ignoreUnknownFieldInJson() {
        // 对于Json中的未知字段选择忽略
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对所有调用次方法之后的转化都生效, 继续报错
     */
    public static void emphasisUnknownFieldInJson() {
        // 对于Json中的未知字段选择重视
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    public static String toJsonStr(Object bean) {
        try {
            return MAPPER.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJsonStr(HashMap<String, Object> map) {
        //map<String,String>转json
        try {
            return MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toBean(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T[] toBeanArray(String arrayJson, Class<T[]> type) {
        try {
            return MAPPER.readValue(arrayJson, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> toBeanList(String listJson, Class<T> type) {
        try {
            return MAPPER.readValue(listJson, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> toMap(String json) {
        try {
            return MAPPER.readValue(json, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> toStringMap(String json) {
        try {
            return MAPPER.readValue(json, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Map<?, ?> toMap(Object bean) {
        return MAPPER.convertValue(bean, Map.class);
    }

    public static <T> T toBean(Map<String, Object> map, Class<T> type) {
        return MAPPER.convertValue(map, type);
    }

    public static String pretty(Object bean) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printPretty(Object bean) {
        OUT.println(pretty(bean));
    }
}
