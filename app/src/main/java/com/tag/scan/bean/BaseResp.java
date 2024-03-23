package com.tag.scan.bean;

/**
 * <网络请求返回体>
 *
 */
public class BaseResp
{

    protected String status;

    protected String error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
//
//    /**
//     * 返回状态码
//     */
//    protected int code;
//
//    /**
//     * 返回信息描述
//     */
//    protected String msg;
//
//    protected String exp;
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public String getExp() {
//        return exp;
//    }
//
//    public void setExp(String exp) {
//        this.exp = exp;
//    }
}
