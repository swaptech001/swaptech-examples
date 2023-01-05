package com.swaptech.api.demo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.swaptech.api.demo.pojo.ApiResult;
import com.swaptech.api.demo.pojo.Constants;
import com.swaptech.api.demo.pojo.RequestDTO;
import com.swaptech.api.demo.pojo.WrappedRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ApiRequestUtil
 *
 * @author SwapTech
 * @version 1.0, 2022/12/11 11:54
 * @since 1.0.0
 */
@Slf4j
public final class ApiRequestUtil {

    private ApiRequestUtil() {
    }

    public static <T> Optional<ApiResult<T>> get(RequestDTO dto, Class<T> clazz) throws Exception {
        fillParams(dto);
        String srcStr = SignUtil.assembleSignInputWithoutPayload(dto);
        log.info("srcStr: {}", srcStr);

        String sign = SignUtil.sign(srcStr, Constants.ACCESS_SECRET);
        dto.setHeaderSignature(sign);
        log.info("request: {}", dto);

        Map<String, String> headers = new HashMap<>(4);
        headers.put(Constants.HEADER_API_KEY, Constants.ACCESS_KEY);
        headers.put(Constants.HEADER_SIGN, sign);
        headers.put(Constants.HEADER_TS, dto.getHeaderTs());
        String url = Constants.BASE_URL + dto.getUri();
        if (null != dto.getQueryString() && dto.getQueryString().length() > 0) {
            url = url + "?" + dto.getQueryString();
        }
        log.info("url: {}", url);
        log.info("headers: {}", headers);
        String response = HttpUtil.get(url, headers);
        log.info("response: {}", response);
        return JsonUtil.deserializeApiResult(response, clazz);
    }

    public static <T> Optional<ApiResult<List<T>>> getList(RequestDTO dto, Class<T> clazz) throws Exception {
        fillParams(dto);
        String srcStr = SignUtil.assembleSignInputWithoutPayload(dto);
        log.info("srcStr: {}", srcStr);

        String sign = SignUtil.sign(srcStr, Constants.ACCESS_SECRET);
        dto.setHeaderSignature(sign);
        log.info("request: {}", dto);

        Map<String, String> headers = new HashMap<>(4);
        headers.put(Constants.HEADER_API_KEY, Constants.ACCESS_KEY);
        headers.put(Constants.HEADER_SIGN, sign);
        headers.put(Constants.HEADER_TS, dto.getHeaderTs());
        String url = Constants.BASE_URL + dto.getUri();
        if (null != dto.getQueryString() && dto.getQueryString().length() > 0) {
            url = url + "?" + dto.getQueryString();
        }
        log.info("url: {}", url);
        log.info("headers: {}", headers);
        String response = HttpUtil.get(url, headers);
        log.info("response: {}", response);
        return JsonUtil.deserializeApiResultList(response, clazz);
    }

    public static <T> Optional<ApiResult<T>> delete(RequestDTO dto, Class<T> clazz) throws Exception {
        fillParams(dto);
        String srcStr = SignUtil.assembleSignInputWithoutPayload(dto);
        String sign = SignUtil.sign(srcStr, Constants.ACCESS_SECRET);
        dto.setHeaderSignature(sign);
        log.info("srcStr: {}", srcStr);

        Map<String, String> headers = new HashMap<>(4);
        headers.put(Constants.HEADER_API_KEY, Constants.ACCESS_KEY);
        headers.put(Constants.HEADER_SIGN, sign);
        headers.put(Constants.HEADER_TS, dto.getHeaderTs());
        String url = Constants.BASE_URL + dto.getUri();
        log.info("url: {}", url);
        log.info("headers: {}", headers);
        String response = HttpUtil.delete(url, headers);
        log.info("response: {}", response);
        return JsonUtil.deserializeApiResult(response, clazz);
    }

    public static <T> Optional<ApiResult<T>> post(RequestDTO dto, Class<T> clazz) throws Exception {
        fillParams(dto);
        String body = WrappedRequest.warp(JsonUtil.serialize(dto.getObj()).get());
        String payload = extractPayload(body);
        dto.setPayload(payload);

        String srcStr = SignUtil.assembleSignInputWithPayload(dto);
        log.info("srcStr: {}", srcStr);
        String sign = SignUtil.sign(srcStr, Constants.ACCESS_SECRET);
        dto.setHeaderSignature(sign);
        log.info("request: {}", dto);

        Map<String, String> headers = new HashMap<>(4);
        headers.put(Constants.HEADER_API_KEY, Constants.ACCESS_KEY);
        headers.put(Constants.HEADER_SIGN, sign);
        headers.put(Constants.HEADER_TS, dto.getHeaderTs());
        headers.put(Constants.HEADER_CONTENT_TYPE, Constants.APPLICATION_JSON);
        String url = Constants.BASE_URL + dto.getUri();
        log.info("url: {}", url);
        log.info("headers: {}", headers);
        String response = HttpUtil.postJson(url, body, headers);
        log.info("response: {}", response);
        return JsonUtil.deserializeApiResult(response, clazz);
    }

    private static void fillParams(RequestDTO dto) {
        if (null == dto.getHeaderApiKey()) {
            dto.setHeaderApiKey(Constants.HEADER_API_KEY);
        }
        if (null == dto.getHeaderTs()) {
            dto.setHeaderTs(System.currentTimeMillis() + "");
        }
    }

    private static String extractPayload(String body) {
        JsonNode node = JsonUtil.deserialize(body, JsonNode.class).get();
        return node.at("/payload").asText();
    }
}
