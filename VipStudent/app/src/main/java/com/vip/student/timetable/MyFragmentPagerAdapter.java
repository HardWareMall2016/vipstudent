package com.vip.student.timetable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.squareup.timessquare.CalendarPickerView;
import com.vip.student.my.bean.ChangeListRequestBean;
import com.vip.student.utils.Utils;

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter implements CalendarFragment.CalendarFrgmentClickListener {
    private int lastClickPosition = 0;

    public boolean isCanDestory = true;
    private Date mStartDate;
    private int mMonthsCount=0;
    private SparseArray<CalendarFragment> mCache=new SparseArray<>();
    private List<ChangeListRequestBean.DataEntity> mData;
    private CalendarPickerView.CellClickInterceptor mCellClickListener;

    /**
     * 从开始时间显示到结束时间
     * @param fm
     * @param begin
     * @param end
     */
    public MyFragmentPagerAdapter(FragmentManager fm, Date begin, Date end, List<ChangeListRequestBean.DataEntity> data, CalendarPickerView.CellClickInterceptor clickInterceptor) {
        super(fm);
        initList(begin, end, data, clickInterceptor);
    }

    public void initList(Date begin, Date end, List<ChangeListRequestBean.DataEntity> data, CalendarPickerView.CellClickInterceptor clickInterceptor) {
        mCache.clear();
        mData=data;
        mCellClickListener=clickInterceptor;
        mMonthsCount = Utils.getMonth(begin, end);
        mStartDate=begin;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setDestoryItem(boolean isCanDestory) {
        this.isCanDestory = isCanDestory;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }


    @Override
    public int getCount() {
        return mMonthsCount;
    }


    @Override
    public Fragment getItem(int position) {
        CalendarFragment fragment=mCache.get(position);
        if(fragment==null){
            fragment=createCalendarFragment(position);
            mCache.put(position,fragment);
        }
        fragment.setCalendarFrgmentClickListener(this);
        fragment.setCellClickInterceptor(mCellClickListener);
        return fragment;
    }

    private CalendarFragment createCalendarFragment(int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mStartDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, position);
        return CalendarFragment.newInstance(position, calendar.getTime(), mData);//, mCellClickListener
    }

    @Override
    public void click(int position) {
        lastClickPosition = position;
    }

}
