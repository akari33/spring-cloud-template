package com.temp.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * @Author: Administrator
 * @Date: 2023/10/10/9:46
 */
@Slf4j
public class JsonUtil {


    private static ObjectMapper objectMapperLongToString = new ObjectMapper();
    private static ObjectMapper objectMapper = new ObjectMapper();


    static {
        SimpleModule simpleModule = new SimpleModule();
        // long -> String
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // Long -> String
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapperLongToString.registerModule(simpleModule);
    }

    /**
     * ===========================以下是从JSON中获取对象====================================
     */
    public static <T> T parseObject(String jsonString, Class<T> object) {
        T t = null;
        try {
            t = objectMapper.readValue(jsonString, object);
        } catch (JsonProcessingException e) {
            log.error("JsonString转为自定义对象失败：{}", e.getMessage());
        }
        return t;
    }

    public static <T> T parseObject(File file, Class<T> object) {
        T t = null;
        try {
            t = objectMapper.readValue(file, object);
        } catch (IOException e) {
            log.error("从文件中读取json字符串转为自定义对象失败：{}", e.getMessage());
        }
        return t;
    }

    //将json数组字符串转为指定对象List列表或者Map集合
    public static <T> T parseJSONArray(String jsonArray, TypeReference<T> reference) {
        T t = null;
        try {
            t = objectMapper.readValue(jsonArray, reference);
        } catch (JsonProcessingException e) {
            log.error("JSONArray转为List列表或者Map集合失败：{}", e.getMessage());
        }
        return t;
    }


    /**
     * =================================以下是将对象转为JSON=====================================
     */
    public static String toJSONString(Object object) {
        //FAIL_ON_EMPTY_BEANS
        //序列化时，如果对象为空，不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Object转JSONString失败：{}", e.getMessage());
        }
        return jsonString;
    }

    public static String toJSONAndLongString(Object object) {
        //FAIL_ON_EMPTY_BEANS
        //序列化时，如果对象为空，不抛异常
        objectMapperLongToString.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonString = null;
        try {
            jsonString = objectMapperLongToString.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Object转JSONString失败：{}", e.getMessage());
        }
        return jsonString;
    }

    public static byte[] toByteArray(Object object) {
        byte[] bytes = null;
        try {
            bytes = objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("Object转ByteArray失败：{}", e.getMessage());
        }
        return bytes;
    }

    public static void objectToFile(File file, Object object) {
        try {
            objectMapper.writeValue(file, object);
        } catch (JsonProcessingException e) {
            log.error("Object写入文件失败：{}", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * =============================以下是与JsonNode相关的=======================================
     */
    //JsonNode和JSONObject一样，都是JSON树形模型，只不过在jackson中，存在的是JsonNode
    public static JsonNode parseJSONObject(String jsonString) {
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            log.error("JSONString转为JsonNode失败：{}", e.getMessage());
        }
        return jsonNode;
    }

    public static JsonNode parseJSONObject(Object object) {
        JsonNode jsonNode = objectMapper.valueToTree(object);
        return jsonNode;
    }

    public static String toJSONString(JsonNode jsonNode) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            log.error("JsonNode转JSONString失败：{}", e.getMessage());
        }
        return jsonString;
    }


    /**
     * object to map
     */
    public static <T> T parseObjectToMap(Object object, Class<T> objectClass) {
        T t = null;
        try {
            String jsonString = objectMapper.writeValueAsString(object);
            t = objectMapper.readValue(jsonString, objectClass);
        } catch (JsonProcessingException e) {
            log.error("Object转Map失败：{}", e.getMessage());
        }
        return t;
    }
}
