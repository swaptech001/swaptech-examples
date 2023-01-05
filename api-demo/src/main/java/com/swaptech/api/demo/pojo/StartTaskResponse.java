package com.swaptech.api.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * start task response
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartTaskResponse {
    private String taskId;
    private String clientRequestId;
}
