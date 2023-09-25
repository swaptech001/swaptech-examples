# SwapTech Public API Curl

This is a lightweight example that works to the [SwapTech public API doc](https://doc.swaptech.net/swaptech-api/swagger-ui/index.html#).

- Supported APIs:
    - Account
        - `/v1/accounts/me/balance`
        - `/v1/accounts/me/bills`
    - File Upload
        - `/v1/files/-/signatures/amazons`
        - `/v1/files/-/signatures/aliyu`
    - Material
        - `/v1/materials/*`
    - Face
        - `/v1/faces*`
    - Task
        - `/v1/tasks/*`
- Test cases and examples

## RESTful APIs

Requests and responses have an `application/json` `Content-Type` and follow standard HTTP response status codes for success and failures.

Request bodies should have content type `application/json` and be valid JSON. 

```shell
curl -X POST "http://sandbox.swaptech.net/v1/<API_PATH>" \
-H "X_API_KEY: <ACCESS_KEY>" \
-H "X_API_SIGN: <SIGNATURE>" \
-H "X_API_TS: <TIMESTAMP>" \
-H "Content-Type: application/json"
```

When in the sandbox environment，we will disable `X_API_SIGN` check as default but `X_API_KEY` is a **must** , that means the correct `X_API_KEY` value  is sufficient to test in our sandbox environment.

When in the production environment, if you request strict checking of the **X_API_SIGN** for your account's request, we can enable this check. Your API request should then ensure a valid **`X_API_SIGH` **. For instructions on creating a valid `X_API_SIGN`, see https://doc.swaptech.net/swaptech-api/swagger-ui/index.html.
## Testnet

The default `baseUrl` is http://sandbox.swaptech.net. When click，you will get the response as below:

```json
{
  "code": 0,
  "data": "Gateway Pong",
  "msg": "OK"
}
```

## Create Task Example

### FileUrl to create task 

If your material image and face image are stored in your server, and you can only get **fileUrl**, then you can finish a task as described below:

#### Add material by fileUrl 

Run the `curl` request as shown below, and the **materialId** will be returned  in successfully response.

```shell
curl -X POST "http://sandbox.swaptech.net/v1/materials/" \
-H "X_API_KEY: <ACCESS_KEY>" \
-H "X_API_SIGN: <SIGNATURE>" \
-H "X_API_TS: <TIMESTAMP>" \
-H "Content-Type: application/json" \
-d '{"payload":"{\"fileId\":null,\"fileUrl\":\"https://xxx_material_url\",\"ttl\":3600000,\"callbackUrl\":\"https://xxx_callback_url\",\"clientRequestId\":\"7196238646601185968\"}"}'

// success response like below
// {
//     "code": 0,
//     "data": {
//         "materialId": "3640404518200473504",
//         "clientRequestId": "7196238646601185968"
//     },
//     "msg": "OK"
// }
```

#### Create Task and query

Run the `curl` request as shown below to query the task result:

```shell
curl -X POST "http://sandbox.swaptech.net/v1/tasks/" \
-H "X_API_KEY: <ACCESS_KEY>" \
-H "X_API_SIGN: <SIGNATURE>" \
-H "X_API_TS: <TIMESTAMP>" \
-H "Content-Type: application/json" \
-d '{"payload":"{\"materialId\":\"3640404518200473504\",\"facePairs\":[{\"index\":0,\"faceId\":null,\"faceUrl\":\"https://xxx_face_jpg_url\"}],\"ttl\":3600000,\"priority\":\"HIGH\",\"clientRequestId\":\"8005869950778481107\",\"callbackUrl\":\"https://webhook.site/d2e0ed34-49f1-4baf-b175-4b28a904a4d5\"}"}'

// success response
// {"code":0,"data":{"taskId":"3640404814553216937","clientRequestId":"8005869950778481107"},"msg":"OK"}
```

Run the `curl` request as below to query the last result as below,  the `TASK_ID` are from the success response above.

```shell
curl -X POST "http://sandbox.swaptech.net/v1/tasks/<TASK_ID>" \
-H "X_API_KEY: <ACCESS_KEY>" \
-H "X_API_SIGN: <SIGNATURE>" \
-H "X_API_TS: <TIMESTAMP>" \
-H "Content-Type: application/json"

// success response
// {
// 	"code": 0,
// 	"data": {
// 		"id": "3640404814553216937",
// 		"materialId": "3640404518200473504",
// 		"materialType": "PHOTO",
// 		"status": "SUCCEED",
// 		"facePairs": [{
// 			"index": 0,
// 			"sourceUrl": "https://xxx_source_url_0"
// 		}, {
// 			"index": 1,
// 			"sourceUrl": "https://xxx_source_url_2"
// 		}],
// 		"fileUrl": "https://xxx_file_url",
// 		"coverPhotoUrl": "https://xxx_cover_photo_url",
// 		"coverGifUrl": "https://xxx_cover_gif_url",
// 		"expiredAt": "1695199195106",
// 		"clientRequestId": "8005869950778481107",
// 		"errorCode": null,
// 		"failReason": null
// 	},
// 	"msg": "OK"
// }
```

### Local file to create task

If your material image and face image are stored locally,  you can finish the task as described below:

#### Get materialId

To obtain a **materialId** from a local image, you need to follow these 3 steps:

1. Obtain the **AWS S3** or **Aliyun OSS** signature (which includes a **fileId**)  to upload the material image.
2. Upload material image to **AWS S3** or **Aliyun OSS** using the **signature**.
3. Add the material using the **fileId** (included in the signature).

**Step1:** Obtain the **AWS S3** or **Aliyun OSS** signature

The queryString below **`extension=png`** means you want to upload a **png** image; you can replace it with **jpg**、**gif**、**mp4**, depending on  your file extension type.

* Obtain **AWS S3** signature

  ```shell
  curl -X GET "http://sandbox.swaptech.net/v1/files/-/signatures/amazons3?extension=png&fileType=MATERIAL" \
  -H "X_API_KEY: <ACCESS_KEY>" \
  -H "X_API_SIGN: <SIGNATURE>" \
  -H "X_API_TS: <TIMESTAMP>"
  
  // success response
  //    {
  //        "success": true,
  //        "errorCode": 0,
  //        "data": {
  //        		"fileId": "3640423433236445222",
  //            "url": "https://s3.ap-east-1.amazonaws.com/api-sandbox-temporary",
  //            "key": "US/20230920/fHYPiBgzWm/97df6953-e6bb-4de6-a6c8-6f14ae24cc83.png",
  //            "policy": "eyJleHBpcmF0aW9uIjoiMjAyMy0wOS0yMFQxODoxNDoyMloiLCJjb25kaXRpb25zIjpbeyJidWNrZXQiOiJhcGktc2FuZGJveC10ZW1wb3JhcnkifSx7ImtleSI6IlVTLzIwMjMwOTIwL2ZIWVBpQmd6V20vOTdkZjY5NTMtZTZiYi00ZGU2LWE2YzgtNmYxNGFlMjRjYzgzLnBuZyJ9LHsieC1hbXotYWxnb3JpdGhtIjoiQVdTNC1ITUFDLVNIQTI1NiJ9LHsieC1hbXotY3JlZGVudGlhbCI6IkFLSUFWQVBPUkdER0tIM0VIWFZULzIwMjMwOTIwL2FwLWVhc3QtMS9zMy9hd3M0X3JlcXVlc3QifSx7IngtYW16LWRhdGUiOiIyMDIzMDkyMFQxODA0MjJaIn0sWyJlcSIsIiR4LWFtei1tZXRhLXVzZXJJZCIsIjEiXSxbImVxIiwiJHgtYW16LW1ldGEtcGFyYW1ldGVycyIsImV5SmpiR2xsYm5SZmNtVnhkV1Z6ZEY5cFpDSTZJak0yTkRBME1qTTBNek15TXpZME5EVXlNakVpZlE9PSJdLFsiZXEiLCIkeC1hbXotbWV0YS1maWxlVHlwZSIsIk1BVEVSSUFMIl0sWyJlcSIsIiR4LWFtei1tZXRhLWJ1Y2tldCIsImFwaS1zYW5kYm94LXRlbXBvcmFyeSJdLFsic3RhcnRzLXdpdGgiLCIkQ29udGVudC1UeXBlIiwiaW1hZ2UvcG5nIl0sWyJjb250ZW50LWxlbmd0aC1yYW5nZSIsMCw0MTk0MzA0XV19",
  //            "expire": "1695204862125",
  //            "xamzAlgorithm": "AWS4-HMAC-SHA256",
  //            "xamzCredential": "AKIAVAPORGDGKH3EHXVT/20230920/ap-east-1/s3/aws4_request",
  //            "xamzDate": "20230920T180422Z",
  //            "xamzSignature": "8f6d7db1ac98852e652402803abe5546a62fb3280cc18308a9f4d1c5c199da9b",
  //            "xamzMetaUserid": "1",
  //            "xamzMetaBucket": "api-sandbox-temporary",
  //            "xamzMetaFiletype": "MATERIAL",
  //            "xamzMetaParameters": "eyJjbGllbnRfcmVxdWVzdF9pZCI6IjM2NDA0MjM0MzMyMzY0NDUyMjEifQ=="
  //    },
  //        "errorMsg": "OK"
  //    }
  ```

  

* Obtain **Aliyun OSS** signature

  ```shell
  curl -X GET "http://sandbox.swaptech.net/v1/files/-/signatures/aliyun?extension=png&fileType=MATERIAL" \
  -H "X_API_KEY: <ACCESS_KEY>" \
  -H "X_API_SIGN: <SIGNATURE>" \
  -H "X_API_TS: <TIMESTAMP>"
  
  // success response
  //    {
  //        "success": true,
  //        "errorCode": 0,
  //        "data": {
  //        		"fileId": "3640429106888243326",
  //            "accessId": "LTAI4G3p41pfESSvEH554mov",
  //            "host": "https://api-sandbox-temporary-hk.oss-accelerate.aliyuncs.com",
  //            "policy": "eyJleHBpcmF0aW9uIjoiMjAyMy0wOS0yMFQxMjoyODoyNC4zNjBaIiwiY29uZGl0aW9ucyI6W1sic3RhcnRzLXdpdGgiLCIkQ29udGVudC1UeXBlIiwiaW1hZ2UvcG5nIl0sWyJjb250ZW50LWxlbmd0aC1yYW5nZSIsMCw0MTk0MzA0XSx7ImtleSI6Ijc4NWFjODhlLTQxMmMtNDA5OC04MDVlLTJhODhmZGRlMTE2ZC5wbmcifSx7Ingtb3NzLWZvcmJpZC1vdmVyd3JpdGUiOiJ0cnVlIn0seyJ4OnR5cGUiOiJNQVRFUklBTCJ9LHsieDp1c2VyIjoiMSJ9LHsieDpyZWdpb24iOiJvc3MtY24taG9uZ2tvbmcifV19",
  //            "signature": "VwbWSISMAluOVGxRi8F8qvN6snc=",
  //            "callback": null,
  //            "expire": 1695212904,
  //            "filename": "785ac88e-412c-4098-805e-2a88fdde116d.png",
  //            "fileType": "MATERIAL",
  //            "userId": "1",
  //            "region": "oss-cn-hongkong",
  //            "bizId": null
  //    },
  //        "errorMsg": "OK"
  //    }
  ```

  

**Step2:** Upload material image with signature

The format below **`Content-Type=image/png`** means you want to upload a **png** image, you can replace it with **image/jpeg**、**image/gif**、**image/mp4** according to your file extension type.

**Notice**:

1. When you want to upload a **jpg** image, **Content-Type=image/jpeg** instead of **image/jpg**.
2. **file=<font color = red>@</font>path/to/your/file.jpg**. If you lost the **@**,  you will failed to upload your image without any error message.

* Upload material image to AWS S3

  ```shell
  curl -X POST "https://s3.ap-east-1.amazonaws.com/api-sandbox-temporary" \
      -H "Content-Type: multipart/form-data" \
      -F "key=US/20230920/fHYPiBgzWm/97df6953-e6bb-4de6-a6c8-6f14ae24cc83.png" \
      -F "Content-Type=image/png" \
      -F "X-Amz-Credential=AKIAVAPORGDGKH3EHXVT/20230920/ap-east-1/s3/aws4_request" \
      -F "X-Amz-Algorithm=AWS4-HMAC-SHA256" \
      -F "X-Amz-Date=20230920T180422Z" \
      -F "X-Amz-Meta-Userid=1" \
      -F "X-Amz-Meta-Bucket=api-sandbox-temporary" \
      -F "X-Amz-Meta-Filetype=MATERIAL" \
      -F "X-Amz-Meta-Parameters=eyJjbGllbnRfcmVxdWVzdF9pZCI6IjM2NDA0MjM0MzMyMzY0NDUyMjEifQ==" \
      -F "Policy=eyJleHBpcmF0aW9uIjoiMjAyMy0wOS0yMFQxODoxNDoyMloiLCJjb25kaXRpb25zIjpbeyJidWNrZXQiOiJhcGktc2FuZGJveC10ZW1wb3JhcnkifSx7ImtleSI6IlVTLzIwMjMwOTIwL2ZIWVBpQmd6V20vOTdkZjY5NTMtZTZiYi00ZGU2LWE2YzgtNmYxNGFlMjRjYzgzLnBuZyJ9LHsieC1hbXotYWxnb3JpdGhtIjoiQVdTNC1ITUFDLVNIQTI1NiJ9LHsieC1hbXotY3JlZGVudGlhbCI6IkFLSUFWQVBPUkdER0tIM0VIWFZULzIwMjMwOTIwL2FwLWVhc3QtMS9zMy9hd3M0X3JlcXVlc3QifSx7IngtYW16LWRhdGUiOiIyMDIzMDkyMFQxODA0MjJaIn0sWyJlcSIsIiR4LWFtei1tZXRhLXVzZXJJZCIsIjEiXSxbImVxIiwiJHgtYW16LW1ldGEtcGFyYW1ldGVycyIsImV5SmpiR2xsYm5SZmNtVnhkV1Z6ZEY5cFpDSTZJak0yTkRBME1qTTBNek15TXpZME5EVXlNakVpZlE9PSJdLFsiZXEiLCIkeC1hbXotbWV0YS1maWxlVHlwZSIsIk1BVEVSSUFMIl0sWyJlcSIsIiR4LWFtei1tZXRhLWJ1Y2tldCIsImFwaS1zYW5kYm94LXRlbXBvcmFyeSJdLFsic3RhcnRzLXdpdGgiLCIkQ29udGVudC1UeXBlIiwiaW1hZ2UvcG5nIl0sWyJjb250ZW50LWxlbmd0aC1yYW5nZSIsMCw0MTk0MzA0XV19" \
      -F "X-Amz-Signature=8f6d7db1ac98852e652402803abe5546a62fb3280cc18308a9f4d1c5c199da9b" \
      -F "file=@path/to/your/file.jpg"
  ```

* Upload material image to Aliyun OSS

  ```shell
  curl -X POST "https://api-sandbox-temporary-hk.oss-accelerate.aliyuncs.com" \
    -H "Content-Type: multipart/form-data" \
    -F "key=785ac88e-412c-4098-805e-2a88fdde116d.png" \
    -F "Content-Type=image/png" \
    -F "OSSAccessKeyId=LTAI4G3p41pfESSvEH554mov" \
    -F "x:user=1" \
    -F "x:region=oss-cn-hongkong" \
    -F "x:type=MATERIAL" \
    -F "x-oss-forbid-overwrite=true" \
    -F "policy=eyJleHBpcmF0aW9uIjoiMjAyMy0wOS0yMFQxMjoyODoyNC4zNjBaIiwiY29uZGl0aW9ucyI6W1sic3RhcnRzLXdpdGgiLCIkQ29udGVudC1UeXBlIiwiaW1hZ2UvcG5nIl0sWyJjb250ZW50LWxlbmd0aC1yYW5nZSIsMCw0MTk0MzA0XSx7ImtleSI6Ijc4NWFjODhlLTQxMmMtNDA5OC04MDVlLTJhODhmZGRlMTE2ZC5wbmcifSx7Ingtb3NzLWZvcmJpZC1vdmVyd3JpdGUiOiJ0cnVlIn0seyJ4OnR5cGUiOiJNQVRFUklBTCJ9LHsieDp1c2VyIjoiMSJ9LHsieDpyZWdpb24iOiJvc3MtY24taG9uZ2tvbmcifV19" \
    -F "signature=VwbWSISMAluOVGxRi8F8qvN6snc=" \
    -F "file=@path/to/your/material.png"
  ```

  

**Step3:** Add material image using material **fileId**

After successfully uploading the material image to **AWS S3** or **Aliyun OSS**, we can use the **material fileId** (contains in the signature) to add material, and the **materialId** will be returned in the success response.

```shell
curl -X POST "http://sandbox.swaptech.net/v1/materials/" \
-H "X_API_KEY: <ACCESS_KEY>" \
-H "X_API_SIGN: <SIGNATURE>" \
-H "X_API_TS: <TIMESTAMP>" \
-H "Content-Type: application/json" \
-d '{"payload":"{\"fileId\":\"3640539977174022455\",\"fileUrl\":null,\"ttl\":3600000,\"callbackUrl\":\"https://xxx_callback_url\",\"clientRequestId\":\"7196238646601185968\"}"}'

// success response
//    {
//        "code": 0,
//        "data": {
//        "materialId": "3640549434692008274",
//            "clientRequestId": "7196238646601185968"
//    },
//        "msg": "OK"
//    }
```



#### Get  FaceId

Get FaceId is the same as getting **materialId**.

**Step1:** Obtain the signature to upload face image.

When obtaining the signature to upload a material image, the queryString is **`fileType=MATERIAL`**. When obtaining the signature to upload a face image, the queryString is **fileType=FACE**.

The queryString below **`extension=png`** indicates that  you want to upload **png** image. You can replace it with **jpg**、**gif**、**mp4** according to your file extension type.

Take **AWS S3** for example:
```shell
curl -X GET "http://sandbox.swaptech.net/v1/files/-/signatures/amazons3?extension=png&fileType=FACE" \
-H "X_API_KEY: <ACCESS_KEY>" \
-H "X_API_SIGN: <SIGNATURE>" \
-H "X_API_TS: <TIMESTAMP>"

// success response
//    {
//        "success": true,
//        "errorCode": 0,
//        "data": {
//        		"fileId": "3640546580686240071",
//            "url": "https://s3.ap-east-1.amazonaws.com/api-sandbox-temporary",
//            "key": "US/20230920/IQzOsAxnBz/7ec1a7c5-a0a7-4085-bee6-428926cd0492.png",
//            "policy": "eyJleHBpcmF0aW9uIjoiMjAyMy0wOS0yMFQxOTo0NToxMloiLCJjb25kaXRpb25zIjpbeyJidWNrZXQiOiJhcGktc2FuZGJveC10ZW1wb3JhcnkifSx7ImtleSI6IlVTLzIwMjMwOTIwL0lRek9zQXhuQnovN2VjMWE3YzUtYTBhNy00MDg1LWJlZTYtNDI4OTI2Y2QwNDkyLnBuZyJ9LHsieC1hbXotYWxnb3JpdGhtIjoiQVdTNC1ITUFDLVNIQTI1NiJ9LHsieC1hbXotY3JlZGVudGlhbCI6IkFLSUFWQVBPUkdER0tIM0VIWFZULzIwMjMwOTIwL2FwLWVhc3QtMS9zMy9hd3M0X3JlcXVlc3QifSx7IngtYW16LWRhdGUiOiIyMDIzMDkyMFQxOTM1MTJaIn0sWyJlcSIsIiR4LWFtei1tZXRhLXVzZXJJZCIsIjEiXSxbImVxIiwiJHgtYW16LW1ldGEtcGFyYW1ldGVycyIsImV5SmpiR2xsYm5SZmNtVnhkV1Z6ZEY5cFpDSTZJak0yTkRBME16VXhNemN3TWpJek1qY3dNVEFpZlE9PSJdLFsiZXEiLCIkeC1hbXotbWV0YS1maWxlVHlwZSIsIkZBQ0UiXSxbImVxIiwiJHgtYW16LW1ldGEtYnVja2V0IiwiYXBpLXNhbmRib3gtdGVtcG9yYXJ5Il0sWyJzdGFydHMtd2l0aCIsIiRDb250ZW50LVR5cGUiLCJpbWFnZS9wbmciXSxbImNvbnRlbnQtbGVuZ3RoLXJhbmdlIiwwLDQxOTQzMDRdXX0=",
//            "expire": "1695210312358",
//            "xamzAlgorithm": "AWS4-HMAC-SHA256",
//            "xamzCredential": "AKIAVAPORGDGKH3EHXVT/20230920/ap-east-1/s3/aws4_request",
//            "xamzDate": "20230920T193512Z",
//            "xamzSignature": "e6f121cdca4ad9a0262150f8d8333bda59e0280e3116a5879adc2cc3b1bb996c",
//            "xamzMetaUserid": "1",
//            "xamzMetaBucket": "api-sandbox-temporary",
//            "xamzMetaFiletype": "FACE",
//            "xamzMetaParameters": "eyJjbGllbnRfcmVxdWVzdF9pZCI6IjM2NDA0MzUxMzcwMjIzMjcwMTAifQ=="
//    },
//        "errorMsg": "OK"
//    }
```



**Step2**: Upload user face image.

This step is the same as uploading a material image. The only difference is **`X-Amz-Meta-Filetype=FACE`**.
Take **AWS S3** for example:

```shell
curl -X POST "https://s3.ap-east-1.amazonaws.com/api-sandbox-temporary" \
    -H "Content-Type: multipart/form-data" \
    -F "key=US/20230920/IQzOsAxnBz/7ec1a7c5-a0a7-4085-bee6-428926cd0492.png" \
    -F "Content-Type=image/png" \
    -F "X-Amz-Credential=AKIAVAPORGDGKH3EHXVT/20230920/ap-east-1/s3/aws4_request" \
    -F "X-Amz-Algorithm=AWS4-HMAC-SHA256" \
    -F "X-Amz-Date=20230920T193512Z" \
    -F "X-Amz-Meta-Userid=1" \
    -F "X-Amz-Meta-Bucket=api-sandbox-temporary" \
    -F "X-Amz-Meta-Filetype=FACE" \
    -F "X-Amz-Meta-Parameters=eyJjbGllbnRfcmVxdWVzdF9pZCI6IjM2NDA0MzUxMzcwMjIzMjcwMTAifQ==" \
    -F "Policy=eyJleHBpcmF0aW9uIjoiMjAyMy0wOS0yMFQxOTo0NToxMloiLCJjb25kaXRpb25zIjpbeyJidWNrZXQiOiJhcGktc2FuZGJveC10ZW1wb3JhcnkifSx7ImtleSI6IlVTLzIwMjMwOTIwL0lRek9zQXhuQnovN2VjMWE3YzUtYTBhNy00MDg1LWJlZTYtNDI4OTI2Y2QwNDkyLnBuZyJ9LHsieC1hbXotYWxnb3JpdGhtIjoiQVdTNC1ITUFDLVNIQTI1NiJ9LHsieC1hbXotY3JlZGVudGlhbCI6IkFLSUFWQVBPUkdER0tIM0VIWFZULzIwMjMwOTIwL2FwLWVhc3QtMS9zMy9hd3M0X3JlcXVlc3QifSx7IngtYW16LWRhdGUiOiIyMDIzMDkyMFQxOTM1MTJaIn0sWyJlcSIsIiR4LWFtei1tZXRhLXVzZXJJZCIsIjEiXSxbImVxIiwiJHgtYW16LW1ldGEtcGFyYW1ldGVycyIsImV5SmpiR2xsYm5SZmNtVnhkV1Z6ZEY5cFpDSTZJak0yTkRBME16VXhNemN3TWpJek1qY3dNVEFpZlE9PSJdLFsiZXEiLCIkeC1hbXotbWV0YS1maWxlVHlwZSIsIkZBQ0UiXSxbImVxIiwiJHgtYW16LW1ldGEtYnVja2V0IiwiYXBpLXNhbmRib3gtdGVtcG9yYXJ5Il0sWyJzdGFydHMtd2l0aCIsIiRDb250ZW50LVR5cGUiLCJpbWFnZS9wbmciXSxbImNvbnRlbnQtbGVuZ3RoLXJhbmdlIiwwLDQxOTQzMDRdXX0=" \
    -F "X-Amz-Signature=e6f121cdca4ad9a0262150f8d8333bda59e0280e3116a5879adc2cc3b1bb996c" \
    -F "file=@path/to/your/file.jpg"
```



**Step3:** Add face image using the **fileId**

After successfully uploading the user face image to **AWS S3** or **Aliyun OSS**, we can use the **user face fileId** (contains in signature) to add user face, and the **faceId** will be returned in the success response.

```shell
curl -X POST "http://sandbox.swaptech.net/v1/faces/" \
-H "X_API_KEY: <ACCESS_KEY>" \
-H "X_API_SIGN: <SIGNATURE>" \
-H "X_API_TS: <TIMESTAMP>" \
-H "Content-Type: application/json" \
-d '{"payload":"{\"fileId\":\"3640546580686240071\",\"fileUrl\":null,\"ttl\":3600000,\"clientRequestId\":\"8656519754205426425\"}"}'

// success response
//    {
//        "code": 0,
//        "data": {
//        "clientRequestId": "6232788007327727191",
//            "expiredAt": "1695269156150",
//            "faces": [{
//            "faceId": "3640555061099166057",
//                "url": "https://api-sandbox-temporary-hk.oss-cn-hongkong.aliyuncs.com/ae2625c9-e2ee-4e84-8222-5162f000cc9e.jpg?Expires=1695269156&OSSAccessKeyId=LTAI4G3p41pfESSvEH554mov&Signature=tfJa95ckDxfVWoC7ziLiVgbSOTM%3D"
//        }]
//    },
//        "msg": "OK"
//    }
```



#### Create Task and query

After you finish the above step, the **materialId** and the **faceId** can be used to create a task.

```shell
curl -X POST "http://sandbox.swaptech.net/v1/tasks/" \
-H "X_API_KEY: <ACCESS_KEY>" \
-H "X_API_SIGN: <SIGNATURE>" \
-H "X_API_TS: <TIMESTAMP>" \
-H "Content-Type: application/json" \
-d '{"payload":"{\"materialId\":\"3640555902912756074\",\"facePairs\":[{\"index\":0,\"faceId\":\"3640555061099166057\",\"faceUrl\":null}],\"ttl\":3600000,\"priority\":\"HIGH\",\"clientRequestId\":\"8005869950778481107\",\"callbackUrl\":\"https://webhook.site/d2e0ed34-49f1-4baf-b175-4b28a904a4d5\"}"}'

// success response
// {"code":0,"data":{"taskId":"3640404814553216937","clientRequestId":"8005869950778481107"},"msg":"OK"}
```

The task query `curl` is the same as described before.

## Contributing

Contributions are welcome.
If you've found a bug within this project, please open an issue to discuss what you would like to change.

## Disclaimer

SwapTech is a technology provider and is committed to keeping customer information confidential. However, it is the responsibility of the user to use the technology in a lawful manner. SwapTech does not take responsibility for the actions of the user. It is advisable for users to customize a disclaimer based on local laws and seek legal advice if necessary.
