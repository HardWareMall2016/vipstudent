package com.vip.student.timetable;

import android.view.View;

import com.squareup.timessquare.CalendarPickerView;

import java.io.Serializable;
import java.util.Date;

public abstract class CellClick implements CalendarPickerView.CellClickInterceptor, Serializable {

        @Override
        public abstract boolean onCellClicked(View v, Date date) ;

    }