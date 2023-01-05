package com.swaptech.api.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * query material response
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryMaterialResponse {
    private String materialId;
    private String fileUrl;
    private String materialType;
    private String status;
    private String coverPhotoUrl;
    private String coverGifUrl;
    private String duration;
    private String expiredAt;
    private List<FaceItem> parsedFaces;
    private String errorCode;
    private String failReason;
    private String clientRequestId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FaceItem {
        private Integer index;
        private String url;
    }
}
