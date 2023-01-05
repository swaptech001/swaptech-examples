package com.swaptech.api.demo.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
public final class HttpUtil {
    private static final OkHttpClient httpClient = new OkHttpClient();

    private HttpUtil() {
    }

    /**
     * Send a get request
     *
     * @param url     Url as string
     * @param headers Optional map with headers
     * @return response   Response as string
     */
    public static String get(String url, Map<String, String> headers) {
        return fetch("GET", url, StringUtils.EMPTY, headers);
    }

    /**
     * Post a json string
     *
     * @param url     Url as string
     * @param jsonStr a json string
     * @param headers Optional map with headers
     * @return response   Response as string
     */
    public static String postJson(String url, String jsonStr, Map<String, String> headers) {
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return fetch("POST", url, jsonStr, headers);
    }

    /**
     * Post a form string
     *
     * @param url     Url as string
     * @param body    map with body
     * @param headers Optional map with headers
     * @return response   Response as string
     */
    public static String postForm(String url, Map<String, String> body, Map<String, String> headers, File file) {
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        return fetch("POST", url, body, headers, file);
    }

    /**
     * Send a delete request
     *
     * @param url     Url as string
     * @param headers Optional map with headers
     * @return response   Response as string
     */
    public static String delete(String url, Map<String, String> headers) {
        return fetch("DELETE", url, "", headers);
    }

    /**
     * Send a request
     *
     * @param url     Url as string
     * @param body    Request body as string
     * @param headers Optional map with headers
     * @return response   Response as string
     */
    private static String fetch(String method, String url, String body, Map<String, String> headers) {
        final Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers.size() > 0) {
            headers.forEach(requestBuilder::header);
        }
        if (!StringUtils.isEmpty(body)) {
            final RequestBody requestBody = RequestBody.create(body.getBytes());
            requestBuilder.method(method, requestBody);
        } else {
            requestBuilder.method(method, null);
        }
        try {
            return httpClient.newCall(requestBuilder.build()).execute().body().string();
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * Send a request
     *
     * @param url     Url as string
     * @param body    Request body as string
     * @param headers Optional map with headers
     * @return response   Response as string
     */
    private static String fetch(String method, String url, Map<String, String> body, Map<String, String> headers, File file) {
        final Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers.size() > 0) {
            headers.forEach(requestBuilder::header);
        }
        if (null != body) {
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
            body.forEach(bodyBuilder::addFormDataPart);
            if (null != file) {
                bodyBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image"), file));
            }
            requestBuilder.method(method, bodyBuilder.build());
        }
        try {
            return httpClient.newCall(requestBuilder.build()).execute().body().string();
        } catch (IOException e) {
            log.error("#fetch error", e);
            return "";
        }
    }
}