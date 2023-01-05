package com.swaptech.api.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BatchDeleteFaceRequest
 *
 * @author SwapTech
 * @version 1.0, 2022/12/11 11:40
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchDeleteFaceRequest {
    /** face id list */
    private List<Long> faceIds;
}
