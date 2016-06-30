package com.vip.student.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vip.student.MainActivity;
import com.vip.student.R;
import com.vip.student.base.BaseActivity;
import com.vip.student.my.bean.ModifyCodeBean;
import com.vip.student.my.bean.ModifyCodeRequestBean;
import com.vip.student.network.ApiUrls;
import com.vip.student.network.BaseHttpRequestHandler;
import com.vip.student.persistobject.UserInfo;
import com.vip.student.utils.Utils;

/**
 * Created by Administrator on 2015/12/11.
 */
public class ModifyCodeActivity extends BaseActivity implements View.OnClickListener {

    //view
    private EditText mCurrentCode ;
    private EditText mNewCode ;
    private EditText mAgainCode ;
    private Button mFinish;

    private String oldPass;
    private String mStrPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_code);
        initView();
    }

    private void initView() {
        mCurrentCode = (EditText)findViewById(R.id.modify_current_code);
        mNewCode = (EditText)findViewById(R.id.modify_new_password);
        mAgainCode = (EditText)findViewById(R.id.modify_again_password);
        mFinish = (Button)findViewById(R.id.btn_modify_code);
        mFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_modify_code:
                if (checkPassword()) {
                    changePwd();
                }
                break;
        }
    }

    private void changePwd() {
        ModifyCodeRequestBean request = new ModifyCodeRequestBean();
        ModifyCodeRequestBean.DataEntity  data = new ModifyCodeRequestBean.DataEntity();
        data.setOldPassword(Utils.md5(oldPass));
        data.setNewPassword(Utils.md5(mStrPassword));
        request.setData(data);
        request.setToken(UserInfo.getCurrentUser().getToken());
        request.setUserId(UserInfo.getCurrentUser().getUserId());
        showRotateProgressDialog(getString(R.string.chang_password), true);
        startRequest(ApiUrls.CHANGPASSWORD, request, new BaseHttpRequestHandler() {
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
                ModifyCodeBean bean = Utils.parseJsonTostError(content, ModifyCodeBean.class);
                if(bean != null) {
                    Utils.toast(R.string.chang_password_finish);
                    UserInfo.logout();
                    Intent homePageIntent = new Intent(ModifyCodeActivity.this, MainActivity.class);
                    homePageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homePageIntent);
                }
            }
        });
    }

    private boolean checkPassword() {
        oldPass = mCurrentCode.getText().toString().trim();
        mStrPassword = mNewCode.getText().toString();

        String rePwd = mAgainCode.getText().toString();

        if (TextUtils.isEmpty(mStrPassword)) {
            Utils.toast(R.string.pwd_empty);
            return false;
        }

        if (TextUtils.isEmpty(rePwd)) {
            Utils.toast(R.string.reinput_pwd);
            return false;
        }

        if (mStrPassword.length() < 6 || mStrPassword.length() > 16) {
            Utils.toast(R.string.pwd_length_error);
            return false;
        }
        if (TextUtils.isEmpty(oldPass)) {
            Utils.toast(R.string.reinput_pwd);
            return false;
        }

        if (oldPass.length() < 6 || oldPass.length() > 16) {
            Utils.toast(R.string.pwd_length_error);
            return false;
        }

        if (!mStrPassword.equals(rePwd)) {
            Utils.toast(R.string.re_pwd_not_equal);
            return false;
        }
        return true;
    }
}
