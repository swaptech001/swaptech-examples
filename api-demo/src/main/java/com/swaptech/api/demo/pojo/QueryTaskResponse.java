package com.swaptech.api.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * query task response
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryTaskResponse {
    private String id;
    private String materialId;
    private String clientRequestId;
    private String materialType;
    private String status;
    private List<FaceItem> facePairs;
    private String fileUrl;
    private String coverPhotoUrl;
    private String coverGifUrl;
    private String expiredAt;
    private String errorCode;
    private String failReason;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FaceItem {
        private Integer index;
        private String sourceUrl;
        private String targetUrl;
    }
}
