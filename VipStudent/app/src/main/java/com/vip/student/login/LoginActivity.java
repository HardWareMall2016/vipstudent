package com.vip.student.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vip.student.MainActivity;
import com.vip.student.R;
import com.vip.student.base.BaseActivity;
import com.vip.student.login.bean.LoginRequestBean;
import com.vip.student.login.bean.UserInfoBean;
import com.vip.student.network.ApiUrls;
import com.vip.student.network.BaseHttpRequestHandler;
import com.vip.student.persistobject.UserInfo;
import com.vip.student.utils.Utils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    //Widgets
    private EditText mEtStudentNO;
    private EditText mEtPwd;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBackIcon(false);
        setContentView(R.layout.activity_login);

        mEtStudentNO = (EditText) findViewById(R.id.student_number);
        mEtPwd = (EditText) findViewById(R.id.password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!checkInput()) {
            return;
        }
        String studentPwd = mEtStudentNO.getText().toString();
        String pwd = mEtPwd.getText().toString();

        LoginRequestBean request = new LoginRequestBean();
        LoginRequestBean.DataEntity data = new LoginRequestBean.DataEntity();
        data.setUserName(studentPwd);
        data.setPassWorde(Utils.md5(pwd));
        request.setData(data);

        showRotateProgressDialog(getString(R.string.login_logining), true);
        startRequest(ApiUrls.USER_LOGIN, request, new BaseHttpRequestHandler() {
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
                UserInfoBean bean = Utils.parseJsonTostError(content, UserInfoBean.class);
                if (bean != null) {
                    UserInfo user = new UserInfo();
                    user.setToken(bean.getData().getToken());
                    user.setUserId(bean.getData().getUserId());
                    UserInfoHelp.updateUserInfo(bean, user);

                    Intent homePageIntent = new Intent(LoginActivity.this, MainActivity.class);
                    homePageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homePageIntent);

                    finish();
                    Utils.toast(R.string.login_success);
                }
            }
        });
    }

    private boolean checkInput() {
        String studentPwd = mEtStudentNO.getText().toString();
        if (TextUtils.isEmpty(studentPwd)) {
            Utils.toast(R.string.login_input_student_no);
            return false;
        }

        String pwd = mEtPwd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            Utils.toast(R.string.login_input_pwd);
            return false;
        }
        return true;
    }
}
