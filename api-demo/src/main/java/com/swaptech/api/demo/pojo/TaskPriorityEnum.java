package com.swaptech.api.demo.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ApiTaskPriorityEnum
 *
 * @author SwapTech
 * @version 1.0, 2022/12/11 14:44
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public enum TaskPriorityEnum {
    /** normal */
    NORMAL((byte) 0),
    /** high */
    HIGH((byte) 1),
    /** immediate */
    IMMEDIATE((byte) 2);

    private final byte value;
}
