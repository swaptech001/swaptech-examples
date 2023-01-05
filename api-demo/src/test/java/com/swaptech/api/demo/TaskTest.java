package com.swaptech.api.demo;

import com.swaptech.api.demo.biz.Task;
import com.swaptech.api.demo.pojo.FacePair;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

/**
 * TaskTest
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
class TaskTest {

    @Test
    void createTask_invoke() throws Exception {
        List<FacePair> facePairs = Collections.singletonList(
            FacePair.builder()
                .index(0L)
                .faceId(null)
                .faceUrl("")
                .build()
        );
        Task.createTask(1L, facePairs);
    }

    @Test
    void queryTask_invoke() throws Exception {
        Task.queryTask(1L);
    }
}
