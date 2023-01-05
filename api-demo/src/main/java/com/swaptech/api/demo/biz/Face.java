package com.swaptech.api.demo.biz;

import com.swaptech.api.demo.pojo.ApiResult;
import com.swaptech.api.demo.pojo.BatchDeleteFaceRequest;
import com.swaptech.api.demo.pojo.Constants;
import com.swaptech.api.demo.pojo.HttpMethod;
import com.swaptech.api.demo.pojo.RequestDTO;
import com.swaptech.api.demo.pojo.SyncFaceRequest;
import com.swaptech.api.demo.pojo.SyncFaceResponse;
import com.swaptech.api.demo.util.ApiRequestUtil;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

import static com.swaptech.api.demo.pojo.Constants.PATH_ADD_FACE;
import static com.swaptech.api.demo.pojo.Constants.PATH_BATCH_DELETE;
import static com.swaptech.api.demo.pojo.Constants.PATH_DELETE;

/**
 * Face
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:48
 * @since 1.0.0
 */
public class Face {

    private Face() {}

    /**
     * upload face
     *
     * @param fileId  face file id
     * @param faceUrl face url
     * @throws Exception
     */
    public static ApiResult<SyncFaceResponse> syncFace(Long fileId, String faceUrl) throws Exception {
        RequestDTO requestDTO = RequestDTO.builder()
            .method(HttpMethod.POST)
            .uri(PATH_ADD_FACE)
            .headerApiKey(Constants.ACCESS_KEY)
            .headerTs(System.currentTimeMillis() + "")
            .obj(SyncFaceRequest.builder()
                .fileId(fileId)
                .fileUrl(faceUrl)
                .ttl(Constants.DEFAULT_TTL)
                .clientRequestId(RandomUtils.nextLong(0L, Long.MAX_VALUE))
                .build())
            .build();

        return ApiRequestUtil.post(requestDTO, SyncFaceResponse.class)
            .orElseThrow(() -> new RuntimeException("request error with request " + requestDTO));
    }

    /**
     * delete face
     *
     * @param faceId face id
     * @throws Exception
     */
    public static void deleteFace(Long faceId) throws Exception {
        RequestDTO requestDTO = RequestDTO.builder()
            .method(HttpMethod.DELETE)
            .uri(String.format(PATH_DELETE, faceId))
            .build();

        ApiRequestUtil.delete(requestDTO, String.class);
    }

    /**
     * batch delete faces
     *
     * @param faceIds face ids
     * @throws Exception
     */
    public static void batchDelete(List<Long> faceIds) throws Exception {
        RequestDTO requestDTO = RequestDTO.builder()
            .method(HttpMethod.POST)
            .uri(PATH_BATCH_DELETE)
            .obj(BatchDeleteFaceRequest
                .builder()
                .faceIds(faceIds)
                .build())
            .build();

        ApiRequestUtil.post(requestDTO, String.class);
    }

}
