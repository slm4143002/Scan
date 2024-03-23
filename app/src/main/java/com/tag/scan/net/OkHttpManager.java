package com.tag.scan.net;

import com.google.gson.JsonObject;

/**
 * <http公共解析库>
 *
 */
public class OkHttpManager {

    /**
     * 异步Get请求 带tag(关闭页面则取消请求)
     *
     * @param url             请求url
     * @param activityName    请求activityName
     * @param requestResult 请求回调
     * @param clazz           Class<T>
     * @param params          请求参数
     * @param <T>             泛型模板
     */
    public <T> void requestAsyncGetEnqueue(String url, String activityName, RequestResult<T> requestResult, Class<T> clazz, Param... params) {
        OkHttpUtil.getInstance().requestAsyncGetEnqueue(url, activityName, requestResult, clazz, params);
    }

    /**
     * 异步POST请求
     *
     * @param url             请求url
     * @param requestResult 请求回调
     * @param clazz           Class<T>
     * @param jsonObject          请求参数
     * @param <T>             泛型模板
     */
    public <T> void requestAsyncPostJson(String url, RequestResult<T> requestResult, Class<T> clazz, JsonObject jsonObject) {
        OkHttpUtil.getInstance().requestAsyncPostJson(url, requestResult, clazz, jsonObject);
    }

    /**
     * 异步POST请求
     *
     * @param url             请求url
     * @param requestResult 请求回调
     * @param clazz           Class<T>
     * @param params          请求参数
     * @param <T>             泛型模板
     */
    public <T> void requestAsyncPost(String url, RequestResult<T> requestResult, Class<T> clazz, Param... params) {
        OkHttpUtil.getInstance().requestAsyncPost(url, requestResult, clazz, params);
    }

    /**
     * 取消当前页面正在请求的请求
     *
     * @param activity
     */
    public void cancelActivityRequest(String activity) {
        OkHttpUtil.getInstance().cancelActivityRequest(activity);
    }


}
