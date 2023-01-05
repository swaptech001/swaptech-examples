package com.swaptech.api.demo;

import com.swaptech.api.demo.biz.Material;
import org.junit.jupiter.api.Test;

/**
 * MaterialTest
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
class MaterialTest {

    @Test
    void addMaterial_url() throws Exception {
        Material.addMaterial(null, "https://fs-private-hk.oss-cn-hongkong.aliyuncs.com/0a9c27f9-91f6-419f-bc16-86265646d4fd.jpeg?Expires=1671442336&OSSAccessKeyId=LTAI4G3p41pfESSvEH554mov&Signature=9ToAisdGO%2BLyGa8BFKSuNEWo%2BQA%3D");
        Material.addMaterial(null, "https://www.faceplay.me/ds/videos/home/home_12_1_m.mp4");
    }

    @Test
    void addMaterial_fileId() throws Exception {
        Material.addMaterial(3588086027062175122L, null);
    }

    @Test
    void queryMaterial_invoke() throws Exception {
        Material.queryMaterial(3588096319812648238L);
    }

    @Test
    void delete_invoke() throws Exception {
        Material.deleteMaterial(3592382960756181645L);
    }
}
