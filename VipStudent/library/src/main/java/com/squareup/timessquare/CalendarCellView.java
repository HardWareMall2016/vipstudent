// Copyright 2013 Square, Inc.

package com.squareup.timessquare;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.timessquare.MonthCellDescriptor.RangeState;

public class CalendarCellView extends LinearLayout {
    private static final int[] STATE_SELECTABLE = {
            R.attr.tsquare_state_selectable
    };
    private static final int[] STATE_CURRENT_MONTH = {
            R.attr.tsquare_state_current_month
    };
    private static final int[] STATE_TODAY = {
            R.attr.tsquare_state_today
    };
    private static final int[] STATE_HIGHLIGHTED = {
            R.attr.tsquare_state_highlighted
    };
    private static final int[] STATE_RANGE_FIRST = {
            R.attr.tsquare_state_range_first
    };
    private static final int[] STATE_RANGE_MIDDLE = {
            R.attr.tsquare_state_range_middle
    };
    private static final int[] STATE_RANGE_LAST = {
            R.attr.tsquare_state_range_last
    };

    private boolean isSelectable = false;
    private boolean isCurrentMonth = false;
    private boolean isToday = false;
    private boolean isHighlighted = false;
    private RangeState rangeState = RangeState.NONE;

    @SuppressWarnings("UnusedDeclaration") //
    public CalendarCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSelectable(boolean isSelectable) {
        this.isSelectable = isSelectable;
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setSelected(hasFocus);
            }
        });
    }


    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (!isToday && selected) {
            setBackgroundColor(Color.parseColor("#d2eeff"));
        } else if (!isToday && !selected) {
            setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
//        if (!isToday && pressed) {
//            setBackgroundColor(Color.parseColor("#d2eeff"));
//        } else if (!isToday && !pressed) {
//            setBackgroundColor(getResources().getColor(android.R.color.white));
//        }
    }

    public void setCurrentMonth(boolean isCurrentMonth) {
        this.isCurrentMonth = isCurrentMonth;
        if (!isCurrentMonth) {
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).setVisibility(INVISIBLE);
            }
        }
        refreshDrawableState();
    }

    public void setToday(boolean isToday) {
        if (isToday) {
            setBackgroundColor(Color.parseColor("#ffffcd"));
        }
        this.isToday = isToday;
        refreshDrawableState();
    }


    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
        refreshDrawableState();
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public boolean isToday() {
        return isToday;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

}
