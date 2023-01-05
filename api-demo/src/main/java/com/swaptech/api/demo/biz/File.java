package com.swaptech.api.demo.biz;

import com.swaptech.api.demo.pojo.AlibabaCloudSignatureResponse;
import com.swaptech.api.demo.pojo.ApiResult;
import com.swaptech.api.demo.pojo.AwsSignatureResponse;
import com.swaptech.api.demo.pojo.HttpMethod;
import com.swaptech.api.demo.pojo.RequestDTO;
import com.swaptech.api.demo.util.ApiRequestUtil;

import static com.swaptech.api.demo.pojo.Constants.PATH_ALIBABA_CLOUD;
import static com.swaptech.api.demo.pojo.Constants.PATH_AMAZON;

/**
 * FileDemo
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:48
 * @since 1.0.0
 */
public class File {

    private static final String QUERY_TEMPLATE = "extension=%s&fileType=%s";

    private File() {}

    /**
     * get alibaba cloud signature
     *
     * @param extension jpg, png or others
     * @param fileType  file type FACE or MATERIAL
     * @throws Exception
     */
    public static ApiResult<AlibabaCloudSignatureResponse> getAlibabaCloudSignature(String extension, String fileType) throws Exception {
        return getFileSignature(extension, fileType, PATH_ALIBABA_CLOUD, AlibabaCloudSignatureResponse.class);
    }

    /**
     * get aws s3 signature
     *
     * @param extension jpg, png or others
     * @param fileType  file type FACE or MATERIAL
     * @throws Exception
     */
    public static ApiResult<AwsSignatureResponse> getAWSs3Signature(String extension, String fileType) throws Exception {
        return getFileSignature(extension, fileType, PATH_AMAZON, AwsSignatureResponse.class);
    }

    /**
     * get file signature
     *
     * @param uri      PATH
     * @param fileType file type FACE or MATERIAL
     */
    private static <T> ApiResult<T> getFileSignature(String extension, String fileType, String uri, Class<T> clazz) throws Exception {
        String queryString = buildSignatureQueryString(extension, fileType);
        RequestDTO requestDTO = RequestDTO.builder()
            .method(HttpMethod.GET)
            .uri(uri)
            .queryString(queryString)
            .build();

        return ApiRequestUtil.get(requestDTO, clazz)
            .orElseThrow(() -> new RuntimeException("request error with request " + requestDTO));
    }

    private static String buildSignatureQueryString(String extension, String fileType) {
        return String.format(QUERY_TEMPLATE, extension, fileType);
    }
}
