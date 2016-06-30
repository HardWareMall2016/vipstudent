package com.vip.student.base;

import android.app.Dialog;
import android.support.v4.app.Fragment;

import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.vip.student.network.BaseRequestBean;
import com.vip.student.network.FileDownloadCallback;
import com.vip.student.network.HttpRequestCallback;
import com.vip.student.network.HttpRequestUtils;
import com.vip.student.utils.DialogUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2015/12/4.
 */
public class BaseFragment extends Fragment {

    private Dialog mProgressDlg;
    private List<RequestHandle> mRequestHandleList = new LinkedList<RequestHandle>();

    @Override
    public void onDestroyView() {
        /*for (RequestHandle request : mRequestHandleList) {
            HttpRequestUtils.releaseRequest(request);
        }*/
        releaseAllRequest();
        closeRotateProgressDialog();
        super.onDestroyView();
    }

    protected void releaseAllRequest(){
        for (RequestHandle request : mRequestHandleList) {
            HttpRequestUtils.releaseRequest(request);
        }
        mRequestHandleList.clear();
    }


    /***
     * 显示进度对话框
     *
     * @param msg
     * @param cancelable
     */
    public void showRotateProgressDialog(String msg, boolean cancelable) {
        mProgressDlg = DialogUtils.getRotateProgressDialog(getActivity(), msg);
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
     *
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
     *
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
     *
     * @param request
     */
    public void releaseRequest(RequestHandle request) {
        HttpRequestUtils.releaseRequest(request);
    }

    /***
     * 检查网络请求是否还在处理中
     *
     * @param request
     * @return
     */
    public boolean isRequestProcessing(RequestHandle request) {
        if (request != null && !request.isFinished()) {
            return true;
        }
        return false;
    }
}
