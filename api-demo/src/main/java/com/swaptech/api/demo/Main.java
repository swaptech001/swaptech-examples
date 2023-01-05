package com.swaptech.api.demo;

import com.swaptech.api.demo.biz.Face;
import com.swaptech.api.demo.biz.File;
import com.swaptech.api.demo.biz.Material;
import com.swaptech.api.demo.biz.Task;
import com.swaptech.api.demo.pojo.AddMaterialResponse;
import com.swaptech.api.demo.pojo.AwsSignatureResponse;
import com.swaptech.api.demo.pojo.FacePair;
import com.swaptech.api.demo.pojo.StartTaskResponse;
import com.swaptech.api.demo.pojo.SyncFaceResponse;
import com.swaptech.api.demo.util.UploadFileUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * business demo
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:49
 * @since 1.0.0
 */
@Slf4j
public final class Main {

    private static final String MATERIAL_URL = "https://pub-13542b0.s3.ap-east-1.amazonaws.com/api/m0.jpeg";
    private static final String FACE_URL = "https://pub-13542b0.s3.ap-east-1.amazonaws.com/api/f0.png";
    /** change to absolute path if not work */
    private static final String MATERIAL_PATH = Main.class.getClassLoader().getResource("material.jpeg").getPath();
    /** change to absolute path if not work */
    private static final String FACE_PATH = Main.class.getClassLoader().getResource("face.png").getPath();

    private Main() {}

    /**
     * the simplest and slowest way to face swap
     *
     * @throws Exception
     */
    public static void faceSwapSimplestWithUrl() throws Exception {
        // 1. upload material and wait for materialId in callback
        log.info("==================== add material start ====================");
        final AddMaterialResponse response = Material.addMaterial(null, MATERIAL_URL).getData();
        Long materialId = Long.parseLong(response.getMaterialId());
        // 1.1(optional) query material status, sleep 1 second to do other works
        TimeUnit.SECONDS.sleep(1);
        while ("PRE_PROCESSING".equals(Material.queryMaterial(materialId).getData().getStatus())) {
            log.info("==================== waiting material pre-processing ====================");
            TimeUnit.SECONDS.sleep(5);
        }
        log.info("==================== material pre-process complete ====================");

        // 2. upload face
        log.info("==================== upload face complete ====================");
        SyncFaceResponse syncFaceResponse = Face.syncFace(null, FACE_URL).getData();
        Long faceId = Long.parseLong(syncFaceResponse.getFaces().get(0).getFaceId());

        // 3. start task with face url and wait callback
        log.info("==================== start task ====================");
        StartTaskResponse startTaskResponse = Task.createTask(materialId, Collections.singletonList(FacePair.builder().index(0L).faceId(faceId).faceUrl(null).build())).getData();
        Long taskId = Long.parseLong(startTaskResponse.getTaskId());
        // 3.1(optional) query task status, sleep 1 second to do other works
        TimeUnit.SECONDS.sleep(1);
        while ("PROCESSING".equals(Task.queryTask(taskId).getData().getStatus())) {
            log.info("==================== waiting task processing ====================");
            TimeUnit.SECONDS.sleep(5);
        }
        log.info("==================== task process complete ====================");
    }

    /**
     * the fastest way to face swap with multiple use materialId and faceId to speed up
     *
     * @throws Exception
     */
    public static void faceSwapWithId() throws Exception {
        // 1.1 get signature and upload file by yourself
        log.info("==================== get signature ====================");
        AwsSignatureResponse materialFileResponse = File.getAWSs3Signature("jpg", "MATERIAL").getData();
        // 1.2 upload material file
        log.info("==================== upload material to aws or alibaba ====================");
        UploadFileUtil.post(materialFileResponse, "image/jpeg", MATERIAL_PATH, String.class);
        // 1.3 start material
        Long materialFilId = Long.parseLong(materialFileResponse.getFileId());
        log.info("==================== add material start ====================");
        AddMaterialResponse addMaterialResponse = Material.addMaterial(materialFilId, null).getData();
        Long materialId = Long.parseLong(addMaterialResponse.getMaterialId());
        // 1.3(optional) query material status, sleep 1 second to do other works
        TimeUnit.SECONDS.sleep(1);
        while ("PRE_PROCESSING".equals(Material.queryMaterial(materialId).getData().getStatus())) {
            log.info("==================== waiting material pre-processing ====================");
            TimeUnit.SECONDS.sleep(5);
        }
        log.info("==================== material pre-process complete ====================");

        // 2.1 get signature and upload file by yourself
        log.info("==================== get signature ====================");
        AwsSignatureResponse faceFileResponse = File.getAWSs3Signature("png", "FACE").getData();
        // 2.2 upload face and wait for faceId in callback
        log.info("==================== upload face to aws or alibaba ====================");
        UploadFileUtil.post(faceFileResponse, "image/png", FACE_PATH, String.class);
        Long faceFileId = Long.parseLong(faceFileResponse.getFileId());
        log.info("==================== sync face ====================");
        SyncFaceResponse syncFaceResponse = Face.syncFace(faceFileId, null).getData();
        Long faceId = Long.parseLong(syncFaceResponse.getFaces().get(0).getFaceId());

        // 3. start task with face url and wait callback
        log.info("==================== start task ====================");
        StartTaskResponse startTaskResponse = Task.createTask(materialId, Collections.singletonList(FacePair.builder().index(0L).faceId(faceId).faceUrl(null).build())).getData();
        Long taskId = Long.parseLong(startTaskResponse.getTaskId());
        // 3.1(optional) query task status, sleep 1 second to do other works
        TimeUnit.SECONDS.sleep(1);
        while ("PROCESSING".equals(Task.queryTask(taskId).getData().getStatus())) {
            log.info("==================== waiting task processing ====================");
            TimeUnit.SECONDS.sleep(5);
        }
        log.info("==================== task process complete ====================");
    }

    public static void main(String[] args) throws Exception {
        faceSwapSimplestWithUrl();
        faceSwapWithId();
    }
}
