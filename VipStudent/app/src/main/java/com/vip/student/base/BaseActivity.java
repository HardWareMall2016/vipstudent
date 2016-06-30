package com.vip.student.base;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import com.loopj.android.http.RequestHandle;
import com.vip.student.network.BaseRequestBean;
import com.vip.student.network.FileDownloadCallback;
import com.vip.student.network.HttpRequestCallback;
import com.vip.student.network.HttpRequestUtils;
import com.vip.student.utils.DialogUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2015/12/2.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private Dialog mProgressDlg;
    private List<RequestHandle> mRequestHandleList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }

    /**
     * 保持屏幕常亮，需要在 onCreate中调用
     */
    protected void setKeepScreenOn(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onDestroy() {
        for (RequestHandle request : mRequestHandleList) {
            HttpRequestUtils.releaseRequest(request);
        }
        closeRotateProgressDialog();
        super.onDestroy();
    }


    /***
     * 显示进度对话框
     * @param msg
     * @param cancelable
     */
    public void showRotateProgressDialog(String msg, boolean cancelable) {
        mProgressDlg = DialogUtils.getRotateProgressDialog(this, msg);
        mProgressDlg.setCancelable(cancelable);
        mProgressDlg.show();
    }

    /***
     * 关闭显示进度对话框
     */
    public void closeRotateProgressDialog() {
        if (mProgressDlg != null) {
            mProgressDlg.dismiss();
            mProgressDlg = null;
        }
    }

    /***
     * 网络请求
     * @param apiUrl
     * @param requestBean
     * @param requestCallback
     * @return
     */
    public RequestHandle startRequest(String apiUrl, BaseRequestBean requestBean, HttpRequestCallback requestCallback) {
        RequestHandle handle = HttpRequestUtils.startRequest(apiUrl, requestBean, requestCallback);
        if (handle != null) {
            mRequestHandleList.add(handle);
        }
        return handle;
    }

    /***
     * 下载文件
     * @param serviceFileUrl
     * @param saveFilePath
     * @param callback
     * @return
     */
    public RequestHandle downloadFile(String serviceFileUrl, String saveFilePath, FileDownloadCallback callback) {
        RequestHandle handle = HttpRequestUtils.downloadFile(serviceFileUrl, saveFilePath, callback);
        if (handle != null) {
            mRequestHandleList.add(handle);
        }
        return handle;
    }

    /***
     * 释放网络请求
     * @param request
     */
    public void releaseRequest(RequestHandle request) {
        HttpRequestUtils.releaseRequest(request);
    }

    /***
     * 检查网络请求是否还在处理中
     * @param request
     * @return
     */
    public boolean isRequestProcessing(RequestHandle request) {
        if (request != null&&!request.isFinished()) {
            return true;
        }
        return false;
    }

}
