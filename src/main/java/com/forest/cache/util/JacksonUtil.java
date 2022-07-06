package com.forest.cache.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: Jackson工具类封装
 *
 * @author senlin
 * @date 2020/7/24
 */
public class JacksonUtil {

    private static final ObjectMapper deserializationMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new AfterburnerModule());

    private static final ObjectMapper serializationMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(new AfterburnerModule());

    /**
     * json 转Object
     */
    public static <T> T getObjectFromJson(String json, Class<T> type) {
        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return deserializationMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("JSON转Object出错", e);
        }
    }

    /**
     * json 转Object
     */
    public static <T> T getFromJson(String json, TypeReference<T> type) {
        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return deserializationMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("JSON转Object出错", e);
        }
    }

    /**
     * json 转 List<T>
     */
    public static <T> List<T> getListFromJson(String json, Class<T> itemType) {
        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            JavaType listType = deserializationMapper.getTypeFactory()
                    .constructCollectionType(ArrayList.class, itemType);
            return deserializationMapper.readValue(json, listType);
        } catch (Exception e) {
            throw new RuntimeException("JSON转Object出错", e);
        }

    }

    /**
     * object转json
     */
    public static String toJsonString(Object obj) {
        try {
            return serializationMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Object转JSON出错", e);
        }
    }


}
