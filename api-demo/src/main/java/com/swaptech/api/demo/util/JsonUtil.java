package com.swaptech.api.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.swaptech.api.demo.pojo.ApiResult;

import java.util.List;
import java.util.Optional;

/**
 * JsonUtil
 *
 * @author SwapTech
 * @version 1.0, 2022/12/11 10:49
 * @since 1.0.0
 */
public final class JsonUtil {

    private static final ObjectMapper MAPPER;

    static {
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        jsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        MAPPER = jsonObjectMapper;
    }

    private JsonUtil() {
    }

    /**
     * serialize
     *
     * @param object object
     * @param <T>    T class
     * @return json
     */
    public static <T> Optional<String> serialize(T object) {
        if (null == object) {
            return Optional.empty();
        }
        try {
            return Optional.of(MAPPER.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * deserialize
     *
     * @param jsonStr jsonStr
     * @param <T>     T class
     * @return T
     */
    public static <T> Optional<T> deserialize(String jsonStr, Class<T> cls) {
        if (null == jsonStr || "".equals(jsonStr.trim())) {
            return Optional.empty();
        }
        try {
            return Optional.of(MAPPER.readValue(jsonStr, cls));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * deserialize
     *
     * @param jsonStr jsonStr
     * @param <T>     T class
     * @return T
     */
    public static <T> Optional<ApiResult<T>> deserializeApiResult(String jsonStr, Class<T> cls) {
        if (null == jsonStr || "".equals(jsonStr.trim())) {
            return Optional.empty();
        }
        JavaType type = MAPPER.getTypeFactory().constructParametricType(ApiResult.class, cls);
        try {
            return Optional.of(MAPPER.readValue(jsonStr, type));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * deserialize
     *
     * @param jsonStr jsonStr
     * @param <T>     T class
     * @return T
     */
    public static <T> Optional<ApiResult<List<T>>> deserializeApiResultList(String jsonStr, Class<T> cls) {
        if (null == jsonStr || "".equals(jsonStr.trim())) {
            return Optional.empty();
        }
        JavaType inner = MAPPER.getTypeFactory().constructParametricType(List.class, cls);
        JavaType type = MAPPER.getTypeFactory().constructParametricType(ApiResult.class, inner);
        try {
            return Optional.of(MAPPER.readValue(jsonStr, type));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
