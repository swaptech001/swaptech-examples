package com.swaptech.api.demo.pojo;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.swaptech.api.demo.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WrappedRequest
 *
 * @author SwapTech
 * @version 1.0, 2022/12/11 10:55
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming
public class WrappedRequest {
    private String payload;

    public static String warp(String payload) {
        WrappedRequest request = new WrappedRequest(payload);
        return JsonUtil.serialize(request).get();
    }
}

