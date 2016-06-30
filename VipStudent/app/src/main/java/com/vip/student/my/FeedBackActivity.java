package com.vip.student.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.vip.student.R;
import com.vip.student.base.BaseActivity;
import com.vip.student.my.bean.FeedbackRequestBean;
import com.vip.student.my.bean.FeedbackResponseBean;
import com.vip.student.network.ApiUrls;
import com.vip.student.network.HttpClientUtils;
import com.vip.student.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

/**
 * Created by Administrator on 2015/12/23.
 */
public class FeedBackActivity extends BaseActivity implements View.OnClickListener {
    private final static String TAG = "FeedBackActivity";

    private EditText mFeedback;
    private Button mFinish;

    private RequestHandle mSubmitRequest;

    private final String USER_ID = "32aa97ec-0f4c-49a7-b595-b65967cd8fad";
    private final String APP_ID = "23d837b3-41fb-426f-b214-922b00a0e242";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }

    @Override
    protected void onDestroy() {
        releaseRequest();
        super.onDestroy();
    }

    private void initView() {
        mFeedback = (EditText) findViewById(R.id.feedback_content);
        mFinish = (Button) findViewById(R.id.btn_feedback_finish);
        mFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_feedback_finish:
                if (TextUtils.isEmpty(mFeedback.getText().toString())) {
                    Utils.toast(R.string.mine_setting_feedback_empty);
                } else {
                    submitFeedback();
                }
                break;
        }
    }


    private void submitFeedback() {
        if (isSubmitRequestProcessing()) {
            return;
        }
        //Header
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("platform", "Android");

        FeedbackRequestBean requestBean = new FeedbackRequestBean();
        requestBean.setContent(mFeedback.getText().toString());
        requestBean.setAppid(APP_ID);
        requestBean.setSubsystem("android");
        requestBean.setChecktime(getchecktime());
        requestBean.setCheckValue(getCheckValue());
        String jsonStr = JSON.toJSONString(requestBean);
        HttpEntity entity = new StringEntity(jsonStr, "utf-8");


        AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    String content = new String(responseBody);
                    Log.i(TAG, "onFailure responseBody = " + content);
                }

                Log.i(TAG, "onFailure statusCode = " + statusCode);
                if (statusCode == 0) {
                    Utils.toast("请求超时");
                } else {
                    Utils.toast("服务器未知错误");
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.i(TAG, "onSuccess statusCode = " + statusCode);
                Log.i(TAG, "onSuccess responseBody = " + content);
                if (FeedBackActivity.this == null) {
                    return;
                }

                FeedbackResponseBean bean = null;
                try {
                    bean = JSON.parseObject(content, FeedbackResponseBean.class);
                    if (bean != null) {
                        if (bean.getRetcode() == 0) {
                            finish();
                            Utils.toast(R.string.mine_setting_feedback_submit_success);
                        } else {
                            Utils.toast(bean.getRetmsg());
                        }
                    }
                } catch (JSONException ex) {
                    Log.e("Utils", "fromJson error : " + ex.getMessage());
                }
            }
        };

        mSubmitRequest = HttpClientUtils.post(ApiUrls.TEACHER_FEEDBACK, headers, entity, "application/json", responseHandler);
    }

    private String getCheckValue() {
        String checkValue = USER_ID + APP_ID + getchecktime();
        return Utils.md5(checkValue).toUpperCase();
    }

    private String getchecktime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String dateStr = dateFormat.format(System.currentTimeMillis());
        return dateStr;
    }

    public boolean isSubmitRequestProcessing() {
        if (mSubmitRequest != null && !mSubmitRequest.isFinished()) {
            return true;
        }
        return false;
    }

    public void releaseRequest() {
        if (mSubmitRequest != null && !mSubmitRequest.isFinished()) {
            mSubmitRequest.cancel(true);
        }
    }

}
