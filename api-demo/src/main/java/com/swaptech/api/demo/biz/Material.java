package com.swaptech.api.demo.biz;

import com.swaptech.api.demo.pojo.AddMaterialResponse;
import com.swaptech.api.demo.pojo.ApiResult;
import com.swaptech.api.demo.pojo.Constants;
import com.swaptech.api.demo.pojo.HttpMethod;
import com.swaptech.api.demo.pojo.MaterialPreProcessRequest;
import com.swaptech.api.demo.pojo.QueryMaterialResponse;
import com.swaptech.api.demo.pojo.RequestDTO;
import com.swaptech.api.demo.util.ApiRequestUtil;
import org.apache.commons.lang3.RandomUtils;

import static com.swaptech.api.demo.pojo.Constants.PATH_ADD_MATERIAL;
import static com.swaptech.api.demo.pojo.Constants.PATH_DELETE_MATERIAL;
import static com.swaptech.api.demo.pojo.Constants.PATH_QUERY_MATERIAL;

/**
 * MaterialTest
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
public class Material {

    private Material() {}

    /**
     * add or download material
     *
     * @param fileId  file id
     * @param fileUrl file url
     * @throws Exception
     */
    public static ApiResult<AddMaterialResponse> addMaterial(Long fileId, String fileUrl) throws Exception {
        MaterialPreProcessRequest request = MaterialPreProcessRequest.builder()
            .fileId(fileId)
            .fileUrl(fileUrl)
            .callbackUrl(Constants.CALLBACK_URL)
            .ttl(Constants.DEFAULT_TTL)
            .clientRequestId(RandomUtils.nextLong(0L, Long.MAX_VALUE))
            .build();
        RequestDTO requestDTO = RequestDTO.builder()
            .method(HttpMethod.POST)
            .uri(PATH_ADD_MATERIAL)
            .obj(request)
            .build();

        return ApiRequestUtil.post(requestDTO, AddMaterialResponse.class)
            .orElseThrow(() -> new RuntimeException("request error with request " + request));
    }

    /**
     * query material
     *
     * @param materialId material id
     * @throws Exception
     */
    public static ApiResult<QueryMaterialResponse> queryMaterial(Long materialId) throws Exception {
        RequestDTO requestDTO = RequestDTO.builder()
            .method(HttpMethod.GET)
            .uri(String.format(PATH_QUERY_MATERIAL, materialId))
            .build();

        return ApiRequestUtil.get(requestDTO, QueryMaterialResponse.class)
            .orElseThrow(() -> new RuntimeException("request error with request " + requestDTO));
    }

    /**
     * delete material
     *
     * @param materialId material id
     * @throws Exception
     */
    public static void deleteMaterial(Long materialId) throws Exception {
        RequestDTO requestDTO = RequestDTO.builder()
            .method(HttpMethod.DELETE)
            .uri(String.format(PATH_DELETE_MATERIAL, materialId))
            .build();

        ApiRequestUtil.delete(requestDTO, String.class);
    }
}
