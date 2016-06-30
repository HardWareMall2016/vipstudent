package com.vip.student;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.vip.student.base.App;
import com.vip.student.base.BaseActivity;
import com.vip.student.base.BaseFragment;
import com.vip.student.base.EnvConfig;
import com.vip.student.login.LoginActivity;
import com.vip.student.login.UserInfoHelp;
import com.vip.student.login.bean.UserInfoBean;
import com.vip.student.message.MessageFragment;
import com.vip.student.my.MyFragment;
import com.vip.student.network.ApiUrls;
import com.vip.student.network.BaseHttpRequestHandler;
import com.vip.student.network.BaseRequestBean;
import com.vip.student.network.HttpClientUtils;
import com.vip.student.persistobject.UserInfo;
import com.vip.student.timetable.TimeTableFragment;
import com.vip.student.utils.ShareUtil;
import com.vip.student.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    public final static String EXT_KEY_SHOW_PAGE="show_page";

    public final static String TAG_HOME_PAGE="HomePage";
    public final static String TAG_MESSAGEE="Message";
    public final static String TAG_USER_CENTER="UserCenter";

    //Views
    private TextView mTVHomePage;
    private TextView mTVMessage;
    private TextView mTVMy;

    private List<Page> mPageList=new ArrayList<Page>();
    private Page mCurPage;

    //
    private RequestHandle mRequestUserInfo;
    private RequestHandle mUpgradeHandle;
    /*  Type */
    private class Page{
        String TAG;
        BaseFragment PageFragment;
        int FocusIconResId;
        int UnFocusIconResId;
        TextView BottomTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!checkIsLogin()){
            return;
        }

        showActionbar(false);
        //showBackIcon(false);
        setContentView(R.layout.activity_main);
        initView();
        initPages();
        checkUpgrade();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        if(checkIsLogin()){
            refreshUserInfo();
            setJPushAlias(getJPushAlias());
        }
    }

    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseUpgradeRequest();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(!checkIsLogin()){
            return;
        }

        String showPage=intent.getStringExtra(EXT_KEY_SHOW_PAGE);
        if(TextUtils.isEmpty(showPage)){
            showPage=TAG_HOME_PAGE;
        }
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            showPage(showPage);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_page:
                showPage(TAG_HOME_PAGE);
                break;
            case R.id.message:
                showPage(TAG_MESSAGEE);
                break;
            case R.id.my:
                showPage(TAG_USER_CENTER);
                break;
        }
    }

    private void initView(){
        mTVHomePage=(TextView)findViewById(R.id.home_page);
        mTVMessage=(TextView)findViewById(R.id.message);
        mTVMy=(TextView)findViewById(R.id.my);

        mTVHomePage.setOnClickListener(this);
        mTVMessage.setOnClickListener(this);
        mTVMy.setOnClickListener(this);
    }

    private void initPages(){
        mPageList.clear();
        //课表
        Page page=new Page();
        page.TAG=TAG_HOME_PAGE;
        page.PageFragment= new TimeTableFragment();
        page.FocusIconResId=R.mipmap.icon_toolbar_timetable_selected;
        page.UnFocusIconResId=R.mipmap.icon_toolbar_timetable_unselected;
        page.BottomTitle=mTVHomePage;
        mPageList.add(page);

        //消息
        page=new Page();
        page.TAG=TAG_MESSAGEE;
        page.PageFragment=new MessageFragment();
        page.FocusIconResId=R.mipmap.icon_toolbar_message_selected;
        page.UnFocusIconResId=R.mipmap.icon_toolbar_message_unselected;
        page.BottomTitle=mTVMessage;
        mPageList.add(page);

        //我的
        page=new Page();
        page.TAG=TAG_USER_CENTER;
        page.PageFragment= new MyFragment();
        page.FocusIconResId=R.mipmap.icon_toolbar_my_selected;
        page.UnFocusIconResId=R.mipmap.icon_toolbar_my_unselected;
        page.BottomTitle=mTVMy;
        mPageList.add(page);

        String showPage=getIntent().getStringExtra(EXT_KEY_SHOW_PAGE);
        if(TextUtils.isEmpty(showPage)){
            showPage=TAG_HOME_PAGE;
        }
        showPage(showPage);
    }

    private void showPage(String tag){
        for(Page page : mPageList){
            if(page.TAG.equals(tag)){
                if(mCurPage!=page) {
                    //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.content, page.PageFragment);
                    Utils.ShowFragment(MainActivity.this,R.id.content,page.PageFragment);
//                    transaction.commit();

                    page.BottomTitle.setCompoundDrawablesWithIntrinsicBounds(0, page.FocusIconResId, 0, 0);
                    page.BottomTitle.setTextColor(getResources().getColor(R.color.blue));
                    onPageChange(mCurPage, page);
                }
                mCurPage =page;
            }else{
                page.BottomTitle.setCompoundDrawablesWithIntrinsicBounds(0, page.UnFocusIconResId, 0, 0);
                page.BottomTitle.setTextColor(getResources().getColor(R.color.text_color_content));
            }
        }
    }

    private boolean checkIsLogin() {
        if(UserInfo.getCurrentUser()==null||!UserInfo.getCurrentUser().isLogined()){
            setJPushAlias("");
            Intent loginIntent=new Intent(this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
            finish();
            return false;
        }
        return true;
    }

    private  Page getCurrentPage(){
        return mCurPage;
    }

    private void onPageChange(Page oldPage,Page newPage){

    }

    /**
     * 更新用户信息
     */
    private void refreshUserInfo() {
        if(isRequestProcessing(mRequestUserInfo)){
            return;
        }
        BaseRequestBean requestBean = new BaseRequestBean();
        mRequestUserInfo=startRequest(ApiUrls.GET_USERINFO, requestBean, new BaseHttpRequestHandler() {
            @Override
            public void onRequestSucceeded(String content) {
                super.onRequestSucceeded(content);
                UserInfo userInfo=UserInfo.getCurrentUser();
                UserInfoBean bean = Utils.parseJson(content, UserInfoBean.class);
                if(bean!=null&&bean.getCode()==0&&userInfo!=null){
                    UserInfoHelp.updateUserInfo(bean, userInfo);
                }
            }
        });
    }

    private String getJPushAlias(){
        UserInfo userInfo=UserInfo.getCurrentUser();
        String jPushAlias=userInfo.getUserId().replace("-","_");
        return jPushAlias;
    }

    private void setJPushAlias(final String  jPushAlias) {
        //极光推送注册别名
        Log.i("MainActivity", "jPushAlias = "+jPushAlias);
        //用户存在并且现在保存的别名和用户ID不是同一个
        if(!jPushAlias.equals(ShareUtil.getStringValue(ShareUtil.JPUSH_ALIAS))){
            JPushInterface.setAlias(App.ctx, jPushAlias, new TagAliasCallback() {
                @Override
                public void gotResult(int responseCode, String alias, Set<String> tags) {
                    switch (responseCode) {
                        case 0:
                            Log.i("MainActivity", "alias success");
                            ShareUtil.setValue(ShareUtil.JPUSH_ALIAS, jPushAlias);
                            break;
                        case 6002:
                            Log.i("MainActivity", "Failed to set alias and tags due to timeout. Try again after 60s.");
                            break;
                        default:
                            Log.e("MainActivity", "Failed with errorCode = " + responseCode);
                            break;
                    }
                }
            });
        }
    }

    private void checkUpgrade(){
        if(mUpgradeHandle!=null&&!mUpgradeHandle.isFinished()){
            return;
        }
        mUpgradeHandle= HttpClientUtils.get(EnvConfig.UPGRADE_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String content = new String(bytes);
                Log.e("MainActivity", "Upgrade info : " + content);
                Gson gson = new Gson();
                try {
                    final UpgradeBean bean = gson.fromJson(content, UpgradeBean.class);
                    if(bean!=null&&!TextUtils.isEmpty(bean.getUrl())&&needUpgrade(bean.getVersion())){

                        AlertDialog.Builder dlgBuilder=new AlertDialog.Builder(MainActivity.this);
                        dlgBuilder.setTitle(R.string.new_version).
                                setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Utils.installApp(bean.getUrl());
                                        dialog.dismiss();
                                    }
                                });

                        dlgBuilder.setMessage(bean.getIntroduce());

                        if(bean.isForcedUpdate()){
                            dlgBuilder.setCancelable(false);
                        }else{
                            dlgBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        }
                        dlgBuilder.show();
                    }
                } catch (JsonSyntaxException exp) {
                    Log.e("MainActivity", "fromJson error : " + exp.getMessage());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }

            private String getCurVersion() {
                try {
                    PackageManager manager = App.ctx.getPackageManager();
                    PackageInfo info = manager.getPackageInfo(App.ctx.getPackageName(), 0);
                    return info.versionName;
                } catch (Exception e) {
                    return "1.0.0";
                }
            }

            private boolean needUpgrade(String newVersion){
                if(TextUtils.isEmpty(newVersion)){
                    return false;
                }
                String[] arrNewVer=newVersion.split("\\.");
                if(arrNewVer.length!=3){
                    return false;
                }
                boolean needUpgrade=false;
                String[] curVer=getCurVersion().split("\\.");
                for(int i=0;i<arrNewVer.length;i++){
                    int newVerSeg= Integer.parseInt(arrNewVer[i]);
                    int curVerSeg= Integer.parseInt(curVer[i]);
                    if(newVerSeg>curVerSeg){
                        needUpgrade=true;
                        break;
                    }
                }
                return needUpgrade;
            }
        });
    }

    private void releaseUpgradeRequest(){
        if(mUpgradeHandle!=null&&!mUpgradeHandle.isFinished()){
            mUpgradeHandle.cancel(true);
        }
    }

    public class UpgradeBean{
        private boolean update;
        private boolean forcedUpdate;
        private String version;
        private String url;
        private String introduce;

        public void setUpdate(boolean update) {
            this.update = update;
        }

        public void setForcedUpdate(boolean forcedUpdate) {
            this.forcedUpdate = forcedUpdate;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public boolean isUpdate() {
            return update;
        }

        public boolean isForcedUpdate() {
            return forcedUpdate;
        }

        public String getVersion() {
            return version;
        }

        public String getUrl() {
            return url;
        }

        public String getIntroduce() {
            return introduce;
        }
    }

}
