package com.swaptech.api.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * alibaba signature response
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlibabaCloudSignatureResponse {
    private String fileId;
    private String accessId;
    private String host;
    private String policy;
    private String signature;
    private String callback;
    private String expire;
    private String filename;
    private String fileType;
    private String userId;
    private String region;
    private String bizId;
}
