package com.swaptech.api.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * api DTO
 *
 * @author SwapTech
 * @version 1.0, 2022/12/2 17:47
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDTO {
    /** request method */
    private String method;
    /** request uri */
    private String uri;
    /** queryString */
    private String queryString;
    /** json */
    private String payload;
    /** header apiKey */
    private String headerApiKey;
    /** header signature */
    private String headerSignature;
    /** headerTs */
    private String headerTs;
    /** request payload */
    private Object obj;
}
