package com.swaptech.api.demo.pojo;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MaterialPreProcessRequest
 *
 * @author SwapTech
 * @version 1.0, 2022/12/11 14:01
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming
public class MaterialPreProcessRequest {
    private Long fileId;
    private String fileUrl;
    private Long ttl;
    private String callbackUrl;
    private Long clientRequestId;
}
