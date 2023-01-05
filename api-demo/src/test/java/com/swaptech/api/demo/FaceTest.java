package com.swaptech.api.demo;

import com.swaptech.api.demo.biz.Face;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * FaceTest
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:48
 * @since 1.0.0
 */
class FaceTest {

    @Test
    void syncFace_fileId() throws Exception {
        // oss 3588092005656651164
        Face.syncFace(3588126801195547972L, null);
    }

    @Test
    void syncFace_fileUrl() throws Exception {
        Face.syncFace(null, "https://www.google.com/url?sa=i&url=https%3A%2F%2Fmobile.twitter.com%2Fladygaga&psig=AOvVaw2fOadkNp4e92HDjf21q3YI&ust=1672887012516000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCOCog_PzrPwCFQAAAAAdAAAAABAE");
    }

    @Test
    void deleteFace_invoke() throws Exception {
        Face.deleteFace(1L);
    }

    @Test
    void batchDelete_invoke() throws Exception {
        Face.batchDelete(Arrays.asList(1L, 2L));
    }
}
