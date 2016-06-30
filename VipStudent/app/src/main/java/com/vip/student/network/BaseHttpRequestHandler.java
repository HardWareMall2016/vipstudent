package com.vip.student.network;

/**
 * Created by WuYue on 2015/12/3.
 */
public abstract class BaseHttpRequestHandler implements HttpRequestCallback {

    public void onRequestFinished(){}

    @Override
    public void onRequestFailed(String errorMsg) {
        onRequestFinished();
    }

    @Override
    public void onRequestFailedNoNetwork() {
        onRequestFinished();
    }

    @Override
    public void onTimeout() {
        onRequestFinished();
    }

    @Override
    public void onRequestCanceled() {
        onRequestFinished();
    }

    @Override
    public void onRequestSucceeded(String content) {
        onRequestFinished();
    }
}
