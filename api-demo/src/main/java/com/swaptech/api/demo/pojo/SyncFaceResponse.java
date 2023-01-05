package com.swaptech.api.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * sync face response
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncFaceResponse {
    private String expiredAt;
    private List<FaceItem> faces;
    private String clientRequestId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FaceItem {
        private String faceId;
        private String url;
    }
}
