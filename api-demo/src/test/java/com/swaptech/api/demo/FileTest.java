package com.swaptech.api.demo;

import com.swaptech.api.demo.biz.File;
import org.junit.jupiter.api.Test;

/**
 * FileTest
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:48
 * @since 1.0.0
 */
class FileTest {

    @Test
    void getAlibabaCloudSignature_invoke() throws Exception {
        File.getAlibabaCloudSignature("jpg", "FACE");
    }

    @Test
    void getAWSs3Signature_invoke() throws Exception {
        File.getAWSs3Signature("jpg", "MATERIAL");
    }
}
