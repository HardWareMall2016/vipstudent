package com.vip.student.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vip.student.R;
import com.vip.student.base.BaseActivity;
import com.vip.student.utils.Utils;

/**
 * Created by Administrator on 2015/12/16.
 */
public class RecommendActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mBeiKao ;
    private LinearLayout mTuoFo ;
    private LinearLayout mYaSi ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        initView();
    }

    private void initView() {
        mBeiKao = (LinearLayout)findViewById(R.id.recommend_zhan_beikao);
        mTuoFo = (LinearLayout)findViewById(R.id.recommend_zhan_tuofu);
        mYaSi = (LinearLayout)findViewById(R.id.recommend_zhan_yasi);

        mBeiKao.setOnClickListener(this);
        mTuoFo.setOnClickListener(this);
        mYaSi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recommend_zhan_beikao:
                try {
                    Uri uri = Uri.parse("market://search?q=pname:com.zhan.kykp");
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                } catch (Exception e) {
                    Utils.toast(R.string.us_error);
                }
                break;
            case R.id.recommend_zhan_tuofu:
                try {
                    Uri uri = Uri.parse("market://search?q=pname:com.zhan.toefltom");
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                } catch (Exception e) {
                    Utils.toast(R.string.us_error);
                }
                break;
            case R.id.recommend_zhan_yasi:
                try {
                    Uri uri = Uri.parse("market://search?q=pname:com.zhan.ieltstiku");
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                } catch (Exception e) {
                    Utils.toast(R.string.us_error);
                }
                break;
        }
    }
}
