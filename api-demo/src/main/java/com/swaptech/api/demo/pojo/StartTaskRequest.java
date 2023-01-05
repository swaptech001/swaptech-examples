package com.swaptech.api.demo.pojo;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ApiStartTaskRequest
 *
 * @author SwapTech
 * @version 1.0, 2022/12/11 14:43
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming
public class StartTaskRequest {
    private Long materialId;
    private List<FacePair> facePairs;
    private Long ttl;
    private TaskPriorityEnum priority;
    private Long clientRequestId;
    private String callbackUrl;
}
