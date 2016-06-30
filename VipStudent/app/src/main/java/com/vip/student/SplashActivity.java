package com.vip.student;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.vip.student.base.ActionBarActivity;

/**
 * Created by WuYue on 2015/12/31.
 */
public class SplashActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        showActionbar(false);

        ImageView img = new ImageView(this);
        img.setImageResource(R.mipmap.splash_page);

        img.setScaleType(ImageView.ScaleType.FIT_XY);
        setContentView(img);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Create an Intent that will start the Main Activity.
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2 * 1000);
    }
}
