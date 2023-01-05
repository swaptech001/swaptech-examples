package com.swaptech.api.demo.util;

import com.swaptech.api.demo.pojo.RequestDTO;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * signature util
 *
 * @author SwapTech
 * @version 1.0, 2022/12/7 11:43
 * @since 1.0.0
 */
public final class SignUtil {
    private static final String ALGORITHM = "HmacSHA256";
    private static final String ENCODE = "UTF-8";
    private static final String PRE_PAYLOAD = "payload=";
    private static final char[] CHARS = "0123456789abcdef".toCharArray();

    public void doSign(RequestDTO dto) {

    }

    /**
     * signature
     *
     * @param input input
     * @param sk    secret key
     * @return signature
     */
    public static String sign(String input, String sk) {
        try {
            Mac sha256Hmac = Mac.getInstance(ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(sk.getBytes(ENCODE), ALGORITHM);
            sha256Hmac.init(secretKeySpec);
            return toHexString(sha256Hmac.doFinal(input.getBytes(ENCODE)));
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * assemble sign input without payload
     *
     * @param dto verify dto
     * @return signature
     */
    public static String assembleSignInputWithoutPayload(RequestDTO dto) {
        String method = dto.getMethod();
        String uri = dto.getUri();
        String headerTs = dto.getHeaderTs();
        String queryString = dto.getQueryString();
        // get's payload does not be signed
        return headerTs + method + uri + defaultString(queryString);
    }

    /**
     * assemble sign input with payload
     *
     * @param dto verify dto
     * @return signature
     */
    public static String assembleSignInputWithPayload(RequestDTO dto) {
        String method = dto.getMethod();
        String uri = dto.getUri();
        String payload = dto.getPayload();
        String headerTs = dto.getHeaderTs();
        String queryString = dto.getQueryString();
        return headerTs + method + uri + defaultString(queryString) + PRE_PAYLOAD + defaultString(payload);
    }

    private static String toHexString(byte[] bytes) {
        if (null == bytes || bytes.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int h = (b >> 4) & 0xF;
            int l = b & 0xF;
            sb.append(CHARS[h]);
            sb.append(CHARS[l]);
        }
        return sb.toString();
    }

    private static String defaultString(String str) {
        return null == str ? "" : str;
    }
}

