package com.vip.student.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vip.student.R;
import com.vip.student.base.BaseActivity;
import com.vip.student.persistobject.UserInfo;

/**
 * Created by Administrator on 2015/12/11.
 */
public class MinePersonActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_person);
        initView();
    }

    private void initView() {
        //昵称
        View itemView = findViewById(R.id.setting_person_nickname);
        TextView itemTitle1 = getItemTitle(itemView);
        TextView itemTitles = getItemTitles(itemView);
        itemTitle1.setText(R.string.my_setting_person_nickname);
        itemTitles.setText(UserInfo.getCurrentUser().getStudentName());
        //手机号
        itemView = findViewById(R.id.setting_person_phone);
        itemTitle1 = getItemTitle(itemView);
        itemTitles = getItemTitles(itemView);
        itemTitle1.setText(R.string.my_setting_person_phone);
        itemTitles.setText(UserInfo.getCurrentUser().getMobilePhone());
        //QQ
        itemView = findViewById(R.id.setting_person_qq);
        itemTitle1 = getItemTitle(itemView);
        itemTitles = getItemTitles(itemView);
        itemTitle1.setText(R.string.my_setting_person_qq);
        itemTitles.setText(UserInfo.getCurrentUser().getStudentQQ());
        //修改密码
        findViewById(R.id.setting_person_modify_code).setOnClickListener(this);

    }

    private TextView getItemTitles(View itemView) {
        return (TextView) itemView.findViewById(R.id.titles);
    }

    private TextView getItemTitle(View itemView) {
        return (TextView) itemView.findViewById(R.id.person_title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_person_modify_code:
                startActivity(new Intent(getApplicationContext(),ModifyCodeActivity.class));
                break;
        }
    }
}
