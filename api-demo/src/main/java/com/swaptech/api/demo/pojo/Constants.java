package com.swaptech.api.demo.pojo;

/**
 * Constants
 *
 * @author SwapTech
 * @version 1.0, 2022/12/9 15:50
 * @since 1.0.0
 */
public final class Constants {

    /** sever */
    public static final String BASE_URL = "http://sandbox.swaptech.net";
    /** modify to your own callback url */
    public static final String CALLBACK_URL = "your own callback url";

    /** ak: xxx */
    public static final String ACCESS_KEY = "your access key";
    /** sk: xxx */
    public static final String ACCESS_SECRET = "your secret key";
    public static final String PATH_BALANCE = "/v1/accounts/me/balance";

    public static final String HEADER_API_KEY = "X_API_KEY";
    public static final String HEADER_SIGN = "X_API_SIGN";
    public static final String HEADER_TS = "X_API_TS";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";

    public static final Long DEFAULT_TTL = 60 * 60 * 1000L;
    public static final String PATH_BILLS = "/v1/accounts/me/bills";
    public static final String PATH_ADD_FACE = "/v1/faces";
    public static final String PATH_DELETE = "/v1/faces/%s";
    public static final String PATH_BATCH_DELETE = "/v1/faces/delete";
    public static final String PATH_AMAZON = "/v1/files/-/signatures/amazons3";
    public static final String PATH_ALIBABA_CLOUD = "/v1/files/-/signatures/aliyun";
    public static final String PATH_ADD_MATERIAL = "/v1/materials/";
    public static final String PATH_QUERY_MATERIAL = "/v1/materials/%s";
    public static final String PATH_DELETE_MATERIAL = "/v1/materials/%s";
    public static final String PATH_CREATE_TASK = "/v1/tasks";
    public static final String PATH_QUERY_TASK = "/v1/tasks/%s";

    private Constants() {}
}
