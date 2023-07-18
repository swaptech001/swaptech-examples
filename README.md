# SwapTech Public API Java

This is a lightweight example that works to
the [SwapTech public API doc](https://doc.swaptech.net/swaptech-api/swagger-ui/index.html#)

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

## Installation

Run `mvn install` where `pom.xml` is located to install the dependency.

We advice you to test the project in **Intelij Idea**.

## RESTful APIs

https://doc.swaptech.net/swaptech-api/swagger-ui/index.html

## Testnet

As configed in `Constants.java`, The default `baseUrl` is http://sandbox.swaptech.net. When click，you will get the
response as below:

```json
{
  "code": 0,
  "data": "Gateway Pong",
  "msg": "OK"
}
```

## Run Example

The examples are located under **src/test/java/com/swaptech/api/demo**. Before running the examples, set up your **
API_KEY** and **SECRET_KEY** in `Constants.java`. This configuration file is only used for examples, you should
reconfigure the API_KEY and SECRET_KEY when testing the example.

You can find one runnable example in Main.class. And below is a simple example：

**Step1:** Add material

Run the test of `src/test/java/com/swaptech/api/demo/MaterialDemo#addMaterial_url` as below:

```java
@Test
void addMaterial_url()throws Exception{
    addMaterial(null,"https://xxx_material_url");
    }

// success response like below
// {
//     "code": 0,
//     "data": {
//         "materialId": "3592304431574140853",
//         "clientRequestId": "6772270416567622298"
//     },
//     "msg": "OK"
// }
```

**Step2:** Add user face

Run the test of `src/test/java/com/swaptech/api/demo/FaceDemo#syncFaceByFileUrl` as below:

```java
@Test
void syncFaceByFileUrl()throws Exception{
    syncFace(null,"https://xxx_face_jpg_url");
    }

// success response like below
// {
//     "code": 0,
//     "data": {
//         "clientRequestId": "4137367033967684617",
//         "expiredAt": "1672801139066",
//         "faces": [
//             {
//                 "faceId": "3592305361434560445",
//                 "url": "https://xxx_face_0_url"
//             },
//             {
//                 "faceId": "3592305361434560446",
//                 "url": "https://xxx_face_1_url"
//             }
//         ]
//     },
//     "msg": "OK"
// }
```

**Step3**: Create Task and query

Run the test of `src/test/java/com/swaptech/api/demo/FaceDemo#syncFaceByFileUrl` as below:

```java
@Test
void createTask()throws Exception{
    StartTaskRequest request=StartTaskRequest.builder()
    // materialId from step1 success response
    .materialId(3592304431574140853L)
    .facePairs(Arrays.asList(
    FacePair.builder()
    .index(0L)
    // faceId from step2 success response
    .faceId(3592305361434560445L)
    .faceUrl("")
    .build(),
    FacePair.builder()
    // faceId from step2 success response, if more then one face in user's image
    .index(1L)
    .faceId(3592305361434560446L)
    .faceUrl("")
    .build()
    ))
    .ttl(Constants.DEFAULT_TTL)
    .priority(TaskPriorityEnum.HIGH)
    .clientRequestId(RandomUtils.nextLong(0L,Long.MAX_VALUE))
    .callbackUrl(Constants.CALLBACK_URL)
    .build();
    RequestDTO dto=RequestDTO.builder()
    .method(HttpMethod.POST)
    .uri(PATH_CREATE_TASK)
    .obj(request)
    .build();
    ApiRequestUtil.post(dto);
    }

// success response
// {"code":0,"data":{"taskId":"3592315806795024378","clientRequestId":"8835073177283944201"},"msg":"OK"}
```

Run the test of `src/test/java/com/swaptech/api/demo/FaceDemo#queryTask` to query the last result as below:

```java
@Test
void queryTask()throws Exception{
    // taskId from step3
    queryTask(3592315806795024378L);
    }

// success response
// {
// 	"code": 0,
// 	"data": {
// 		"id": "3592315806795024378",
// 		"materialId": "3592315632848848880",
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
// 		"expiredAt": "1672806008269",
// 		"clientRequestId": "8835073177283944201",
// 		"errorCode": null,
// 		"failReason": null
// 	},
// 	"msg": "OK"
// }
```

## Contributing

Contributions are welcome.
If you've found a bug within this project, please open an issue to discuss what you would like to change.

## Disclaimer

SwapTech is a technology provider and is committed to keeping customer information confidential. However, it is the responsibility of the user to use the technology in a lawful manner. SwapTech does not take responsibility for the actions of the user. It is advisable for users to customize a disclaimer based on local laws and seek legal advice if necessary.
