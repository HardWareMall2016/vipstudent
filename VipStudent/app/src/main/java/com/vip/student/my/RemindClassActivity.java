package com.vip.student.my;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;

import com.vip.student.R;
import com.vip.student.base.BaseActivity;
import com.vip.student.utils.ShareUtil;
import com.vip.student.utils.Utils;
import com.vip.student.widget.UISwitchButton;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2015/12/16.
 */
public class RemindClassActivity extends BaseActivity implements View.OnClickListener, OnCheckedChangeListener {
    //private UISwitchButton switch1;
    //private UISwitchButton switchButton1;
    //private UISwitchButton switchButton2;
    //private UISwitchButton switchButton3;
    //private LinearLayout ll_recommend_setting;
    private LinearLayout mPromptTime ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_setting);
        initView();
    }

    private void initView() {
        //switch1 = (UISwitchButton)findViewById(R.id.switch1);
        //switchButton1 = (UISwitchButton)findViewById(R.id.switchButton1);
        //switchButton2 = (UISwitchButton)findViewById(R.id.switchButton2);
        //switchButton3 = (UISwitchButton)findViewById(R.id.switchButton3);
        //ll_recommend_setting = (LinearLayout)findViewById(R.id.ll_recommend_setting);
        mPromptTime = (LinearLayout)findViewById(R.id.prompt_time);

        //refershButton();

        mPromptTime.setOnClickListener(this);

        //switch1.setOnCheckedChangeListener(this);
        //switchButton1.setOnCheckedChangeListener(this);
        //switchButton2.setOnCheckedChangeListener(this);
        //switchButton3.setOnCheckedChangeListener(this);
    }

    /*private void refershButton() {
        if(ShareUtil.getStringValue(ShareUtil.REMIND_SETTING, ShareUtil.VALUE_TURN_OFF).equals(ShareUtil.VALUE_TURN_OFF)){
            switch1.setChecked(false);
            showSwitchButton(false);
        }else{
            switch1.setChecked(true);
            showSwitchButton(true);
        }
        if(ShareUtil.getStringValue(ShareUtil.REMIND_SETTING_VOICE,ShareUtil.VALUE_TURN_OFF).equals(ShareUtil.VALUE_TURN_OFF)){
            switchButton1.setChecked(false);
        }else{
            switchButton1.setChecked(true);
        }
        if(ShareUtil.getStringValue(ShareUtil.REMIND_SETTING_POPUP,ShareUtil.VALUE_TURN_OFF).equals(ShareUtil.VALUE_TURN_OFF)){
            switchButton2.setChecked(false);
        }else{
            switchButton2.setChecked(true);
        }
        if(ShareUtil.getStringValue(ShareUtil.REMIND_SETTING_SHOCK,ShareUtil.VALUE_TURN_OFF).equals(ShareUtil.VALUE_TURN_OFF)){
            switchButton3.setChecked(false);
        }else{
            switchButton3.setChecked(true);
        }
    }*/


    /*private void showSwitchButton(boolean show) {
        ll_recommend_setting.setVisibility(show?View.VISIBLE:View.GONE);
    }*/

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.prompt_time:
                startActivity(new Intent(getApplicationContext(), PromptTimeActivity.class));
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        /*switch(compoundButton.getId()){
            case R.id.switch1:
                if (isChecked) {
                    ShareUtil.setValue(ShareUtil.REMIND_SETTING,ShareUtil.VALUE_TURN_ON);
                } else {
                    ShareUtil.setValue(ShareUtil.REMIND_SETTING, ShareUtil.VALUE_TURN_OFF);
                }
                break;
            case R.id.switchButton1:
                if(isChecked){
                    ShareUtil.setValue(ShareUtil.REMIND_SETTING_VOICE,ShareUtil.VALUE_TURN_ON);
                }else{
                    ShareUtil.setValue(ShareUtil.REMIND_SETTING_VOICE,ShareUtil.VALUE_TURN_OFF);
                }
                break;
            case R.id.switchButton2:
                if(isChecked){
                    ShareUtil.setValue(ShareUtil.REMIND_SETTING_POPUP,ShareUtil.VALUE_TURN_ON);
                }else {
                    ShareUtil.setValue(ShareUtil.REMIND_SETTING_POPUP,ShareUtil.VALUE_TURN_OFF);
                }
                break;
            case R.id.switchButton3:
                if(isChecked){
                    ShareUtil.setValue(ShareUtil.REMIND_SETTING_SHOCK,ShareUtil.VALUE_TURN_ON);
                }else{
                    ShareUtil.setValue(ShareUtil.REMIND_SETTING_SHOCK,ShareUtil.VALUE_TURN_OFF);
                }
                break;
        }
        refershButton();*/
    }
}
