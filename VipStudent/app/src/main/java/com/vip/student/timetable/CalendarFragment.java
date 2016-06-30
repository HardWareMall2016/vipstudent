package com.vip.student.timetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.timessquare.CalendarPickerView;
import com.vip.student.R;
import com.vip.student.my.bean.ChangeListRequestBean;
import com.vip.student.utils.Utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private static final String TAG = "CalendarFragment";

    private static final String SETTING_DATE = "SETTINGDATE";
    private static final String DATA_CALENDAR = "DATA";
    private static final String POSITON = "position";

    private int mPosition;
    private List<ChangeListRequestBean.DataEntity> dataCalendars;
    private Date mDate;
    private CalendarPickerView.CellClickInterceptor cellClickInterceptor;
    private CalendarFrgmentClickListener calendarFrgmentClickListener;
    private CalendarPickerView calendarView;
    private Calendar nextcalendar;
    private Calendar lastCalendar;
    private View mRootView;
    private boolean isCellClicked;

    public static CalendarFragment newInstance(int position, Date date, List<ChangeListRequestBean.DataEntity> data) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putSerializable(POSITON,position);
        args.putSerializable(SETTING_DATE, date);
        args.putSerializable(DATA_CALENDAR, (Serializable) data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition= (int) getArguments().getSerializable(POSITON);
            mDate = (Date) getArguments().get(SETTING_DATE);
            dataCalendars = (List<ChangeListRequestBean.DataEntity>) getArguments().get(DATA_CALENDAR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        if(mRootView==null){
            mRootView = inflater.inflate(R.layout.fragment_calendar, container, false);
            initView();
        }
        return mRootView;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setCalendarFrgmentClickListener(CalendarFrgmentClickListener calendarFrgmentClickListener) {
        this.calendarFrgmentClickListener = calendarFrgmentClickListener;
    }

    public void setCellClickInterceptor(CalendarPickerView.CellClickInterceptor cellClickInterceptor) {
        this.cellClickInterceptor = cellClickInterceptor;
    }

    public void initView() {
        calendarView = (CalendarPickerView) mRootView.findViewById(R.id.calendar);
        if (calendarView.getCellClickInterceptor() != null)
            if (isCellClicked == false)
                return;

        calendarView.setOnFragmentClickListener(new CalendarPickerView.FragmentClickListener() {
            @Override
            public void onClick() {
                isCellClicked = true;
                if (calendarFrgmentClickListener != null) {
                    calendarFrgmentClickListener.click(mPosition);
                }
            }
        });

        isCellClicked = (false);
        lastCalendar = Calendar.getInstance();
        lastCalendar.setTime(mDate);
        lastCalendar.set(Calendar.DAY_OF_MONTH, 1);
        nextcalendar = Calendar.getInstance();
        nextcalendar.setTime(mDate);
        nextcalendar.add(Calendar.MONTH, 1);

        nextcalendar.add(Calendar.DAY_OF_MONTH, -1);
        nextcalendar.set(Calendar.HOUR_OF_DAY, 24);
        nextcalendar.set(Calendar.MINUTE, 0);
        nextcalendar.set(Calendar.SECOND, 0);
        nextcalendar.set(Calendar.MILLISECOND, 100);

        calendarView.setDecorators(Collections.EMPTY_LIST);
        //设置点击事件
        calendarView.setCellClickInterceptor(cellClickInterceptor);
        if (dataCalendars == null) {
            CalendarPickerView.FluentInitializer fluentInitializer = calendarView.init(lastCalendar.getTime(), nextcalendar.getTime(), Locale.ENGLISH) //
                    .inMode(CalendarPickerView.SelectionMode.SINGLE);//
            Calendar current = Calendar.getInstance();
            if (current.after(lastCalendar) && current.before(nextcalendar))
                fluentInitializer.withSelectedDates(Arrays.asList(new Date()));
        } else {
            try {
                CalendarPickerView.FluentInitializer fluentInitializer = calendarView.init(lastCalendar.getTime(), nextcalendar.getTime(), Locale.ENGLISH, toMap(dataCalendars)) //
                        .inMode(CalendarPickerView.SelectionMode.SINGLE);//
                Calendar current = Calendar.getInstance();
                if (current.after(lastCalendar) && current.before(nextcalendar))
                    fluentInitializer.withSelectedDates(Arrays.asList(new Date()));
            } catch (Exception e) {

            }

        }
    }

    /**
     * 转换map传输
     * <date,"1,2,3,4">
     *
     * @param dataCalendars
     */
    private HashMap<Date, String> toMap(List<ChangeListRequestBean.DataEntity> dataCalendars) throws ParseException {
        HashMap<Date, String> dateStringHashMap = new HashMap<>();
        for (ChangeListRequestBean.DataEntity dataEntity : dataCalendars) {
            //精简到月份12 月22 日  yyyyMMdd
            Date key = new Date(Utils.parseServerTime(dataEntity.getStartTime()));
            String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(key);
            key = new SimpleDateFormat("yyyyMMdd").parse(yyyyMMdd);

            String s = dateStringHashMap.get(key);
            if (s != null) {
                s += "," + dataEntity.getCourseSubTypeName();
                dateStringHashMap.put(key, s);
            } else {
                s = dataEntity.getCourseSubTypeName();
                dateStringHashMap.put(key, s);
            }

        }
        return dateStringHashMap;
    }

    public interface CalendarFrgmentClickListener {
        void click(int position);
    }

}
