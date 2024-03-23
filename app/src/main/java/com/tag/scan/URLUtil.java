package com.tag.scan;

/**
 * <网络请求url地址>
 * <功能详细描述>
 *
 */
public class URLUtil {

    /**
     * 服务器地址
     */
//    public static final String SERVER = "http://192.168.1.106:8099/";
    public static final String SERVER = "http://192.168.0.3:8090/";

    /**
     * 服务器地址
     */
//    public static String SERVER_URL =  "http://192.168.1.106:8099/";
    public static String SERVER_URL =  "http://192.168.0.3:8090/";

    /**
     * 筹备电子卡查询
     */
    public static final String PREPARE_SEARCH = "card/batchnumber";

    /**
     * 筹备电子卡生成
     */
    public static final String PREPARE_CREATE = "card/preparatory-detail";

    /**
     * 组装卡查询
     */
    public static final String ASSEMBLE_SEARCH = "card/assemble-batchnumber";

    /**
     * 组装卡生成
     */
    public static final String ASSEMBLE_CREATE = "card/assemble-detail";

    /**
     * 接地上传
     */
    public static final String GROUND_CONNECTION_UPLOAD = "card/ground-connection";

    /**
     * 耐压上传
     */
    public static final String WITHSTAND_VOLTAGE_UPLOAD = "card/withstand-voltage";

    /**
     * UT上传
     */
    public static final String UT_UPLOAD = "card/ut";

    /**
     * 批量完成
     */
    public static final String BATCH_COMPLETION_FINISH = "";

    /**
     * 电子卡清除
     */
    public static final String CARD_CLEARING = "card/clear-card";

}
