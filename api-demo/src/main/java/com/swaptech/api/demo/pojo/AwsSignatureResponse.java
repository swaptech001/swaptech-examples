package com.swaptech.api.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * aws signature response
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwsSignatureResponse {
    private String fileId;
    private String url;
    private String key;
    private String policy;
    private String expire;
    private String xamzAlgorithm;
    private String xamzCredential;
    private String xamzDate;
    private String xamzSignature;
    private String xamzMetaUserid;
    private String xamzMetaBucket;
    private String xamzMetaFiletype;
    private String xamzMetaParameters;
}
