package com.vip.student.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.timessquare.MonthView;
import com.vip.student.base.Constant;

public class WrapContentHeightViewPager extends ViewPager {

    public static int constantHeight = 0;
    private boolean isCanScroll = true;

    /**
     * Constructor
     *
     * @param context the context
     */
    public WrapContentHeightViewPager(Context context) {
        super(context);
    }

    /**
     * Constructor
     *
     * @param context the context
     * @param attrs   the attribute set
     */
    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setScanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }
    @Override
    public void scrollTo(int x, int y) {
        if(isCanScroll)
            super.scrollTo(x, y);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        for (int i = 0; i < getChildCount(); i++) {
            if (Constant.ViewPager > 0) {
                break;
            }

            ViewGroup childAt = (ViewGroup) getChildAt(i);
            if (childAt == null) break;
            childAt.measure(0, 0);
            ListView childAt1 = (ListView) childAt.getChildAt(childAt.getChildCount() - 1);
            ViewGroup view = (ViewGroup) childAt1.getAdapter().getView(0, null, null);
            ViewGroup childAt3 = (ViewGroup) view.getChildAt(view.getChildCount() - 1);
            ViewGroup childAt4 = (ViewGroup) childAt3.getChildAt(childAt3.getChildCount() - 1);
            View childAt5 = childAt4.getChildAt(0);
            childAt5.measure(0, 0);
            int measuredHeight = childAt5.getMeasuredHeight();
            if (Constant.ViewPager == 0) {

                Constant.ViewPager = Constant.ViewPager < measuredHeight * 5 ? measuredHeight * 5 : Constant.ViewPager;
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Constant.ViewPager, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}