package com.swaptech.api.demo.util;

import com.swaptech.api.demo.pojo.AlibabaCloudSignatureResponse;
import com.swaptech.api.demo.pojo.ApiResult;
import com.swaptech.api.demo.pojo.AwsSignatureResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * upload file util
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
@Slf4j
public final class UploadFileUtil {

    private UploadFileUtil() {}

    /**
     * upload aws file
     *
     * @param response    aws response
     * @param contentType contentType
     * @param path        file path
     * @param clazz       result class
     * @param <T>         result class
     * @return result
     * @throws Exception
     */
    public static <T> Optional<ApiResult<T>> post(AwsSignatureResponse response, String contentType, String path, Class<T> clazz) throws Exception {
        log.info("request: {}", response);

        Map<String, String> form = new HashMap<>(16);
        form.put("content-type", contentType);
        form.put("key", response.getKey());
        form.put("policy", response.getPolicy());
        form.put("x-amz-meta-bucket", response.getXamzMetaBucket());
        form.put("x-amz-meta-filetype", response.getXamzMetaFiletype());
        form.put("x-amz-meta-parameters", response.getXamzMetaParameters());
        form.put("x-amz-algorithm", response.getXamzAlgorithm());
        form.put("x-amz-date", response.getXamzDate());
        form.put("x-amz-signature", response.getXamzSignature());
        form.put("x-amz-meta-userid", response.getXamzMetaUserid());
        form.put("x-amz-credential", response.getXamzCredential());

        String url = response.getUrl();
        log.info("url: {}", url);
        log.info("body: {}", form);
        String result = HttpUtil.postForm(url, form, new HashMap<>(), new File(path));
        log.info("response: {}", result);
        return JsonUtil.deserializeApiResult(result, clazz);
    }


    /**
     * upload alibaba cloud file
     *
     * @param response    aws response
     * @param contentType contentType
     * @param path        file path
     * @param clazz       result class
     * @param <T>         result class
     * @return result
     * @throws Exception
     */
    public static <T> Optional<ApiResult<T>> post(AlibabaCloudSignatureResponse response, String contentType, String path, Class<T> clazz) throws Exception {
        log.info("request: {}", response);

        Map<String, String> form = new HashMap<>(16);
        form.put("content-type", contentType);
        form.put("key", response.getFilename());
        form.put("policy", response.getPolicy());
        form.put("OSSAccessKeyId", response.getAccessId());
        form.put("x:user", response.getUserId());
        form.put("x:region", response.getRegion());
        form.put("x:type", response.getFileType());
        form.put("x-oss-forbid-overwrite", "true");
        form.put("signature", response.getSignature());

        String url = response.getHost();
        log.info("url: {}", url);
        log.info("body: {}", form);
        String result = HttpUtil.postForm(url, form, new HashMap<>(), new File(path));
        log.info("response: {}", result);
        return JsonUtil.deserializeApiResult(result, clazz);
    }
}
