package com.swaptech.api.demo;

import com.swaptech.api.demo.biz.Account;
import org.junit.jupiter.api.Test;

/**
 * AccountTest
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
class AccountTest {

    /**
     * query my balance
     */
    @Test
    void balance_invoke() throws Exception {
        Account.balance();
    }

    /**
     * query my balance
     */
    @Test
    void bills_invoke() throws Exception {
        Account.bills(365);
    }
}
