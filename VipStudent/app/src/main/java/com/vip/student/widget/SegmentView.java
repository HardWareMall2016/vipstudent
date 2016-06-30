package com.vip.student.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vip.student.R;

/**
 * Created by Administrator on 2015/12/10.
 */
public class SegmentView extends LinearLayout {
    private TextView mCourseRate;
    private TextView mStudyAdviser;

    public SegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public SegmentView(Context context) {
        super(context);
        init();
    }
    private void init() {
        mCourseRate = new TextView(getContext());
        mStudyAdviser = new TextView(getContext());
        mCourseRate.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        mStudyAdviser.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        mCourseRate.setText(R.string.mine_course_rate);
        mStudyAdviser.setText(R.string.mine_study_adviser);

        mCourseRate.setTextColor(Color.rgb(32, 166, 251));
        mStudyAdviser.setTextColor(Color.rgb(255, 255, 255));

        mCourseRate.setGravity(Gravity.CENTER);
        mStudyAdviser.setGravity(Gravity.CENTER);
        mCourseRate.setPadding(20, 14, 20, 14);
        mStudyAdviser.setPadding(20, 14, 20, 14);
        mCourseRate.setId(R.id.tv_kechengnjindu);
        mStudyAdviser.setId(R.id.tv_xuexiguwen);
        setSegmentTextSize(14);
//        mCourseRate.setBackgroundResource(R.drawable.seg_left);
//        mStudyAdviser.setBackgroundResource(R.drawable.seg_right);
        mCourseRate.setSelected(true);
        this.removeAllViews();
        this.addView(mCourseRate);
        this.addView(mStudyAdviser);
        this.invalidate();
    }

    /**
     * 设置字体大小 单位dip
     */
    public void setSegmentTextSize(int dp) {
        mCourseRate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        mStudyAdviser.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    private static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 设置文字
     */
    public void setSegmentText(CharSequence text,int position) {
        if (position == 0) {
            mCourseRate.setText(text);
        }
        if (position == 1) {
            mStudyAdviser.setText(text);
        }
    }
}
