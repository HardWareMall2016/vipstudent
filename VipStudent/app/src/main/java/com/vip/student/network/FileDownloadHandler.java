package com.vip.student.network;

import java.io.File;

/**
 * Created by WuYue on 2015/12/3.
 */
public abstract class FileDownloadHandler implements FileDownloadCallback {

    public void onRequestFinished(){};

    @Override
    public void onDownloadFailed(String errorMsg) {
        onRequestFinished();
    }

    @Override
    public void onTimeout() {
        onRequestFinished();
    }

    @Override
    public void onNoNetwork() {
        onRequestFinished();
    }

    @Override
    public void onCanceled() {
        onRequestFinished();
    }

    @Override
    public void onProgress(long bytesWritten, long totalSize) {

    }

    @Override
    public void onDownloadSuccess(File downFile) {
        onRequestFinished();
    }
}
