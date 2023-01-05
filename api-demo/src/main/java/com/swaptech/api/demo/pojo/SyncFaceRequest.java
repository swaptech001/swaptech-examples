package com.swaptech.api.demo.pojo;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SyncFaceRequest
 *
 * @author SwapTech
 * @version 1.0, 2022/12/11 10:55
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming
public class SyncFaceRequest {
    private Long fileId;
    private String fileUrl;
    private Long ttl;
    private Long clientRequestId;
}
