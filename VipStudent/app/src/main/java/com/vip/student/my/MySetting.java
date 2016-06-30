package com.vip.student.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vip.student.MainActivity;
import com.vip.student.R;
import com.vip.student.base.BaseActivity;
import com.vip.student.db.MessageDB;
import com.vip.student.login.LoginActivity;
import com.vip.student.persistobject.UserInfo;
import com.vip.student.utils.CacheUtility;
import com.vip.student.utils.Utils;

/**
 * Created by Administrator on 2015/12/11.
 */
public class MySetting extends BaseActivity implements View.OnClickListener {

    private RelativeLayout mQuite ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        //个人信息
        View itemView = findViewById(R.id.setting_person);
        ImageView itemIcon = getItemIcon(itemView);
        TextView itemTitle = getItemTitle(itemView);
        itemIcon.setImageResource(R.mipmap.setting_person);
        itemTitle.setText(R.string.my_setting_person);
        itemView.setOnClickListener(this);
        //上课提醒设置
        itemView = findViewById(R.id.setting_warn);
        //itemView.setVisibility(View.GONE);
        itemIcon = getItemIcon(itemView);
        itemTitle = getItemTitle(itemView);
        itemIcon.setImageResource(R.mipmap.setting_warn);
        itemTitle.setText(R.string.my_setting_warn);
        itemView.setOnClickListener(this);
        //意见反馈
        itemView = findViewById(R.id.setting_feedback);
        itemIcon = getItemIcon(itemView);
        itemTitle = getItemTitle(itemView);
        itemIcon.setImageResource(R.mipmap.setting_feedback);
        itemTitle.setText(R.string.my_setting_feedback);
        itemView.setOnClickListener(this);
        //备考APP推荐
        itemView = findViewById(R.id.setting_recommend);
        itemIcon = getItemIcon(itemView);
        itemTitle = getItemTitle(itemView);
        itemIcon.setImageResource(R.mipmap.setting_recommend);
        itemTitle.setText(R.string.my_setting_recommend);
        itemView.setOnClickListener(this);
        //清除缓存
        itemView = findViewById(R.id.clear_cache);
        itemIcon = getItemIcon(itemView);
        itemTitle = getItemTitle(itemView);
        itemIcon.setImageResource(R.drawable.setting_cahce);
        itemTitle.setText(R.string.my_setting_clear_cahce);
        itemView.setOnClickListener(this);

        mQuite = (RelativeLayout)findViewById(R.id.my_setting_quit);
        mQuite.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_person:
                startActivity(new Intent(getApplicationContext(),MinePersonActivity.class));
                break;
            case R.id.setting_warn:
                startActivity(new Intent(getApplicationContext(),RemindClassActivity.class));
                break;
            case R.id.setting_feedback:
                startActivity(new Intent(getApplicationContext(),FeedBackActivity.class));
                break;
            case R.id.setting_recommend:
                startActivity(new Intent(getApplicationContext(),RecommendActivity.class));
                break;
            case R.id.clear_cache:
                CacheUtility.clearAllCacheData();
                MessageDB messageDB=new MessageDB(this);
                messageDB.deleteAll();
                Utils.toast(R.string.my_setting_clear_cache_finish);
                break;
            case R.id.my_setting_quit:
                UserInfo.logout();
                Utils.toast(R.string.my_setting_quit);
                Intent homePageIntent = new Intent(this, MainActivity.class);
                homePageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homePageIntent);
                break;
        }
    }


    private ImageView getItemIcon(View itemView) {
        return (ImageView) itemView.findViewById(R.id.left_img);
    }

    private TextView getItemTitle(View itemView) {
        return (TextView) itemView.findViewById(R.id.title);
    }

}
