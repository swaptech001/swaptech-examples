package com.swaptech.api.demo.pojo;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FacePair
 *
 * @author SwapTech
 * @version 1.0, 2022/12/11 14:43
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming
public class FacePair {
    private Long index;
    private Long faceId;
    private String faceUrl;
}
