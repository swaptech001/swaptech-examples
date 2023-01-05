package com.swaptech.api.demo;

import com.swaptech.api.demo.pojo.Constants;
import com.swaptech.api.demo.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * Index test
 *
 * @author SwapTech
 * @version 1.0 2022/12/15 20:54
 * @since 1.0.0
 */
@Slf4j
class IndexTest {
    @Test
    void index_ping() {
        log.info(HttpUtil.get(Constants.BASE_URL, Collections.emptyMap()));
    }
}
