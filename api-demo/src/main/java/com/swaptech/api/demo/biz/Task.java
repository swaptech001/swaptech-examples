package com.swaptech.api.demo.biz;

import com.swaptech.api.demo.pojo.ApiResult;
import com.swaptech.api.demo.pojo.Constants;
import com.swaptech.api.demo.pojo.FacePair;
import com.swaptech.api.demo.pojo.HttpMethod;
import com.swaptech.api.demo.pojo.QueryTaskResponse;
import com.swaptech.api.demo.pojo.RequestDTO;
import com.swaptech.api.demo.pojo.StartTaskRequest;
import com.swaptech.api.demo.pojo.StartTaskResponse;
import com.swaptech.api.demo.pojo.TaskPriorityEnum;
import com.swaptech.api.demo.util.ApiRequestUtil;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

import static com.swaptech.api.demo.pojo.Constants.PATH_CREATE_TASK;
import static com.swaptech.api.demo.pojo.Constants.PATH_QUERY_TASK;

/**
 * TaskDemo
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
public class Task {

    private Task() {}

    /**
     * create task
     *
     * @throws Exception
     */
    public static ApiResult<StartTaskResponse> createTask(Long materialId, List<FacePair> facePairs) throws Exception {
        StartTaskRequest request = StartTaskRequest.builder()
            .materialId(materialId)
            .facePairs(facePairs)
            .ttl(Constants.DEFAULT_TTL)
            .priority(TaskPriorityEnum.HIGH)
            .clientRequestId(RandomUtils.nextLong(0L, Long.MAX_VALUE))
            .callbackUrl(Constants.CALLBACK_URL)
            .build();
        RequestDTO dto = RequestDTO.builder()
            .method(HttpMethod.POST)
            .uri(PATH_CREATE_TASK)
            .obj(request)
            .build();
        return ApiRequestUtil.post(dto, StartTaskResponse.class)
            .orElseThrow(() -> new RuntimeException("request error with request " + dto));
    }

    /**
     * query task
     *
     * @param taskId task id
     * @throws Exception
     */
    public static ApiResult<QueryTaskResponse> queryTask(Long taskId) throws Exception {
        RequestDTO dto = RequestDTO.builder()
            .method(HttpMethod.GET)
            .uri(String.format(PATH_QUERY_TASK, taskId))
            .build();
        return ApiRequestUtil.get(dto, QueryTaskResponse.class)
            .orElseThrow(() -> new RuntimeException("request error with request " + dto));
    }
}
