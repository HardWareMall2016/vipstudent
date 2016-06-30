package com.vip.student.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestHandle;
import com.vip.student.R;
import com.vip.student.base.BaseActivity;
import com.vip.student.my.bean.PromptTimeBean;
import com.vip.student.my.bean.SetPromptTimeBean;
import com.vip.student.my.bean.SetPromptTimeRequestBean;
import com.vip.student.network.ApiUrls;
import com.vip.student.network.BaseHttpRequestHandler;
import com.vip.student.network.BaseRequestBean;
import com.vip.student.utils.Utils;

/**
 * Created by Administrator on 2015/12/16.
 */
public class PromptTimeActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout prompt_time_24,prompt_time_dangri,prompt_time_1;
    private ImageView mDuiHao1,mDuiHao2,mDuiHao3;
    private boolean visibility_Flag_1 = false;
    private boolean visibility_Flag_2 = false;
    private boolean visibility_Flag_3 = false ;
    private int prompttime_1 = 0;
    private int prompttime_2 = 0;
    private int prompttime_3 = 0;
    private final static int bef_24=1;
    private final static int bef_12=1<<1;
    private final static int bef_1=1<<2;
    private RequestHandle mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promttime);
        initView();
        GetPromptTime();
    }

    private void GetPromptTime() {
        BaseRequestBean request = new BaseRequestBean();
        showRotateProgressDialog("获取设置提醒时间", true);
        startRequest(ApiUrls.GETPUSHSTATE, request, new BaseHttpRequestHandler() {
            @Override
            public void onRequestFinished() {
                closeRotateProgressDialog();
            }

            @Override
            public void onRequestFailed(String errorMsg) {
                super.onRequestFailed(errorMsg);
                Utils.toast(errorMsg);
            }

            @Override
            public void onRequestFailedNoNetwork() {
                super.onRequestFailedNoNetwork();
                Utils.toast(R.string.network_disconnect);
            }

            @Override
            public void onTimeout() {
                super.onTimeout();
                Utils.toast(R.string.network_timeout);
            }

            @Override
            public void onRequestSucceeded(String content) {
                super.onRequestSucceeded(content);
                PromptTimeBean bean = Utils.parseJsonTostError(content, PromptTimeBean.class);
                if (bean != null) {
                    if (bean.getData() != null && bean.getData().getState() != null) {
                        String state = bean.getData().getState();
                        if (state.length() == 1) {
                            if (state.equals("1")) {
                                prompttime_1 = Integer.parseInt(state);
                                mDuiHao1.setVisibility(View.VISIBLE);
                                visibility_Flag_1 = true;
                            } else if (state.equals("2")) {
                                prompttime_2 = Integer.parseInt(state);
                                mDuiHao2.setVisibility(View.VISIBLE);
                                visibility_Flag_2 = true;
                            } else if (state.equals("3")) {
                                prompttime_3 = Integer.parseInt(state);
                                mDuiHao3.setVisibility(View.VISIBLE);
                                visibility_Flag_3 = true;
                            }
                        } else {
                            String[] temp = null;
                            temp = state.split(",");
                            for (int i = 0; i < temp.length; i++) {
                                if (temp[i].equals("1")) {
                                    prompttime_1 = 1;
                                    mDuiHao1.setVisibility(View.VISIBLE);
                                    visibility_Flag_1 = true;
                                } else if (temp[i].equals("2")) {
                                    prompttime_2 = 2;
                                    mDuiHao2.setVisibility(View.VISIBLE);
                                    visibility_Flag_2 = true;
                                } else if (temp[i].equals("3")) {
                                    prompttime_3 = 3;
                                    mDuiHao3.setVisibility(View.VISIBLE);
                                    visibility_Flag_3 = true;
                                }
                            }
                        }
                    }/* else {
                        Utils.toast("没有返回值");
                    }*/
                }
            }
        });
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu) {
        menu.setText("完成");
    }
    @Override
    public void onActionBarMenuClick() {
        /*if(prompttime_1 == 0 && prompttime_2 == 0 && prompttime_3 == 0){
            Utils.toast("请选择提醒时间");
        }else{
            SetPromptTime();
        }*/
        SetPromptTime();
    }

    //拼接字符串
    private String mergeConfig() {
         String data="";
        if (visibility_Flag_1) {
            data+="1";
        }
        if (visibility_Flag_2) {
            if(TextUtils.isEmpty(data)){
                data+="2";
            }else{
                data+=",2";
            }
        }
        if (visibility_Flag_3) {
            if(TextUtils.isEmpty(data)){
                data+="3";
            }else{
                data+=",3";
            }
        }
        return data;
    }
    private void SetPromptTime() {
        //防止暴力点击
        if(this.isRequestProcessing(mRequest)){
            return;
        }
        showRotateProgressDialog("设置提醒时间", true);
        SetPromptTimeRequestBean request = new SetPromptTimeRequestBean();
        request.setData(mergeConfig());
        mRequest= startRequest(ApiUrls.SETPUSHSTATE, request, new BaseHttpRequestHandler() {
            @Override
            public void onRequestFinished() {
                closeRotateProgressDialog();
            }
            @Override
            public void onRequestFailed(String errorMsg) {
                super.onRequestFailed(errorMsg);
                Utils.toast(errorMsg);
            }
            @Override
            public void onRequestFailedNoNetwork() {
                super.onRequestFailedNoNetwork();
                Utils.toast(R.string.network_disconnect);
            }
            @Override
            public void onTimeout() {
                super.onTimeout();
                Utils.toast(R.string.network_timeout);
            }

            @Override
            public void onRequestSucceeded(String content) {
                super.onRequestSucceeded(content);
                SetPromptTimeBean bean = Utils.parseJsonTostError(content, SetPromptTimeBean.class);
                if(bean != null){
                    if(bean.getCode() == 0){
                        Utils.toast("提醒设置成功");
                        finish();
                    }
                }

            }
        });
    }

    private void initView() {
        prompt_time_24 = (RelativeLayout)findViewById(R.id.prompt_time_24);
        prompt_time_dangri = (RelativeLayout)findViewById(R.id.prompt_time_dangri);
        prompt_time_1 = (RelativeLayout)findViewById(R.id.prompt_time_1);
        prompt_time_dangri.setOnClickListener(this);
        prompt_time_24.setOnClickListener(this);
        prompt_time_1.setOnClickListener(this);
        mDuiHao1 = (ImageView)findViewById(R.id.prompt_time_duihao1);
        mDuiHao2 = (ImageView)findViewById(R.id.prompt_time_duihao2);
        mDuiHao3 = (ImageView)findViewById(R.id.prompt_time_duihao3);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.prompt_time_24:
                if(visibility_Flag_1){
                    mDuiHao1.setVisibility(View.INVISIBLE);
                    visibility_Flag_1 = false;
                    prompttime_1 = 0 ;
                } else {
                    mDuiHao1.setVisibility(view.VISIBLE);
                    visibility_Flag_1 =true;
                    prompttime_1 = 1 ;
                }

                break;
            case R.id.prompt_time_dangri:
                if(visibility_Flag_2){
                    mDuiHao2.setVisibility(View.INVISIBLE);
                    visibility_Flag_2 = false;
                    prompttime_2 = 0 ;
                } else {
                    mDuiHao2.setVisibility(view.VISIBLE);
                    visibility_Flag_2 =true;
                    prompttime_2 = 2 ;
                }

                break;
            case R.id.prompt_time_1:
                if(visibility_Flag_3){
                    mDuiHao3.setVisibility(View.INVISIBLE);
                    visibility_Flag_3 = false;
                    prompttime_3 = 0;
                } else {
                    mDuiHao3.setVisibility(view.VISIBLE);
                    visibility_Flag_3 =true;
                    prompttime_3 = 3 ;
                }
                break;
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result", "");
        PromptTimeActivity.this.setResult(RESULT_OK, intent);
        PromptTimeActivity.this.finish();
    }
}
