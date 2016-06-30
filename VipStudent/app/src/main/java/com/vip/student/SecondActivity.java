package com.vip.student;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.vip.student.base.BaseActivity;
import com.vip.student.view.ActionSheetDialog;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView test = new TextView(this);
        test.setText(R.string.hello_world);
        test.setTextColor(Color.BLACK);
        setContentView(test);
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu) {
        menu.setText("menu");
    }

    @Override
    public void onActionBarMenuClick() {
        //Utils.toast("点击menu");

        new ActionSheetDialog(this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("Item 1", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    public void onClick(int which) {

                    }
                }).addSheetItem("Item 2", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            public void onClick(int which) {

            }
        }).show();

    }
}
