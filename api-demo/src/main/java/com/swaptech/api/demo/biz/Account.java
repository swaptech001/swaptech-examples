package com.swaptech.api.demo.biz;

import com.swaptech.api.demo.pojo.ApiResult;
import com.swaptech.api.demo.pojo.BalanceResponse;
import com.swaptech.api.demo.pojo.BillsResponse;
import com.swaptech.api.demo.pojo.HttpMethod;
import com.swaptech.api.demo.pojo.RequestDTO;
import com.swaptech.api.demo.util.ApiRequestUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

import static com.swaptech.api.demo.pojo.Constants.PATH_BALANCE;
import static com.swaptech.api.demo.pojo.Constants.PATH_BILLS;

/**
 * Account invoke
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
@Slf4j
public class Account {

    private static final String BILL_QUERY_STRING_FORMAT = "startTs=%s&endTs=%s";

    private Account() {}

    /**
     * query my balance
     */
    public static ApiResult<BalanceResponse> balance() throws Exception {
        RequestDTO requestDTO = RequestDTO.builder()
            .method(HttpMethod.GET)
            .uri(PATH_BALANCE)
            .build();

        return ApiRequestUtil.get(requestDTO, BalanceResponse.class)
            .orElseThrow(() -> new RuntimeException("request error with request " + requestDTO));
    }

    /**
     * query my balance
     *
     * @param daysAgo days ago
     */
    public static ApiResult<List<BillsResponse>> bills(Integer daysAgo) throws Exception {
        long millis = System.currentTimeMillis();
        long dayStart = millis - Duration.ofDays(daysAgo).toMillis();
        log.info("day start: {}", dayStart);
        String queryStr = String.format(BILL_QUERY_STRING_FORMAT, dayStart, millis);

        RequestDTO requestDTO = RequestDTO.builder()
            .method(HttpMethod.GET)
            .uri(PATH_BILLS)
            .queryString(queryStr)
            .headerTs(millis + "")
            .build();

        return ApiRequestUtil.getList(requestDTO, BillsResponse.class)
            .orElseThrow(() -> new RuntimeException("request error with request " + requestDTO));
    }
}
