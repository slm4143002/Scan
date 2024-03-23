package com.tag.scan.net;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * 网络请求
 *
 */
public class OkHttpUtil {

    Handler handler = new Handler() {

    };

    private final String TAG = OkHttpUtil.class.getSimpleName();

    private static OkHttpUtil manager;

    private OkHttpClient mOkHttpClient;

    public final int TIMEOUT = 90;

    public final int WRITE_TIMEOUT = 70;

    public final int READ_TIMEOUT = 70;

    /**
     * 请求url集合
     */
    private HashMap<String, Set<String>> requestMap;

    public OkHttpUtil() {
        requestMap = new HashMap<String, Set<String>>();
        mOkHttpClient = new OkHttpClient();

        mOkHttpClient.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
    }

    public static OkHttpUtil getInstance() {
        if (manager == null) {
            synchronized (OkHttpUtil.class) {
                if (manager == null) {
                    return new OkHttpUtil();
                }
            }
        }
        return manager;
    }

    /**
     * Get请求
     *
     */
    public <T> void requestAsyncGetEnqueue(String url, String activityName, RequestResult<T> requestResult, Class<T> clazz, Param... params) {
        String constructUrl = constructUrl(url, params);
        Request request = new Request.Builder()
                .get()
                .url(constructUrl)
                .build();
        mOkHttpClient.newCall(request).enqueue(new RequestCallBack(requestResult, clazz));
    }

    /**
     * POST请求
     *
     */
    public <T> void requestAsyncPostJson(String url, RequestResult<T> requestResult, Class<T> clazz, JsonObject jsonObject) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, String.valueOf(jsonObject));
        Request request = new Request.Builder().post(body).url(url).build();
        mOkHttpClient.newCall(request).enqueue(new RequestCallBack(requestResult, clazz));
    }

    /**
     * POST请求
     *
     */
    public <T> void requestAsyncPost(String url, RequestResult<T> requestResult, Class<T> clazz, Param... params) {
        JsonObject jsonObject = new JsonObject();
        for (Param param :
                params) {
            jsonObject.addProperty(param.key, param.value);
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, String.valueOf(jsonObject));
        Request request = new Request.Builder().post(body).url(url).build();

        mOkHttpClient.newCall(request).enqueue(new RequestCallBack(requestResult, clazz));
    }

    /**
     * 取消当前页面正在请求的请求
     *
     * @param activityName
     */
    public void cancelActivityRequest(String activityName) {
        try {
            if (requestMap.containsKey(activityName)) {
                Set<String> urlSet = requestMap.get(activityName);
                for (String url : urlSet) {
                    mOkHttpClient.getDispatcher().cancel(url);
                }
                requestMap.remove(activityName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 构造get请求的url
     *
     */
    private String constructUrl(String url, Param... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (params.length != 0) {
            sb.append("?");
        } else {
            return sb.toString();
        }

        for (Param param :
                params) {
            sb.append(param.key + "=" + param.value + "&");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    class RequestCallBack<T> implements Callback {

        private RequestResult<T> mRequestResult;

        private Class<T> clazz;

        private String notifyMsg = "";

        private String activityName;

        public RequestCallBack(RequestResult<T> mRequestResult, Class<T> clazz) {
            this.mRequestResult = mRequestResult;
            this.clazz = clazz;
        }

        public RequestCallBack(RequestResult<T> mRequestResult, Class<T> clazz, String activityName) {
            this.mRequestResult = mRequestResult;
            this.clazz = clazz;
            this.activityName = activityName;
        }

        @Override
        public void onFailure(Request request, IOException e) {
            Log.e(TAG, request.toString() + e.toString());
            notifyMsg = NET_ERROR;
            postErrorMsg();
        }

        @Override
        public void onResponse(Response response) throws IOException {
            if (response.isSuccessful()) {
                String result = response.body().string();
                JsonObject result1 = new Gson().fromJson(result, JsonObject.class);
                JsonObject json = new Gson().fromJson(result1.get("result"), JsonObject.class);
                if ("success".equals(json.get("status").getAsString())) {
                    if (null != json.get("data")) {
                        JsonObject jsonbean = new Gson().fromJson(json.get("data"), JsonObject.class);
                        Log.i(TAG, result);
                        final T res = GsonHelper.toType(jsonbean.toString(), clazz);
                        postSucessMsg(res);
                    }
                } else {
                    JsonObject error = new Gson().fromJson(json.get("error"), JsonObject.class);
                    notifyMsg = error.get("message").getAsString();
                    postErrorMsg();
                }
            } else {
                notifyMsg = NETWORK_ERROR;
                postErrorMsg();
            }
        }

        /**
         * 主线程发送错误消息
         */
        private void postErrorMsg() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mRequestResult.onCompleted();
                    mRequestResult.onFailure(notifyMsg);
                }
            });
        }

        /**
         * 主线程发送正确消息
         */
        private void postSucessMsg(final T res) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mRequestResult.onCompleted();
                    mRequestResult.onSuccessful(res);
                }
            });
        }

    }

    public static String NET_ERROR = "未发现网络";
    public static String NETWORK_ERROR = "请确认网络连接是否正常";
    public void destory() {
        manager = null;
    }
}
