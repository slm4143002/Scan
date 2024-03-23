package com.tag.scan.net;

public interface RequestResult<T> {

    public void onSuccessful(T entity);

    public void onFailure(String errorMsg);

    public void onCompleted();
}
