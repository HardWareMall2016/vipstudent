package com.vip.student.timetable;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vip.student.R;
import com.vip.student.base.BaseFragment;
import com.vip.student.my.bean.ChangeListBean;
import com.vip.student.my.bean.ChangeListRequestBean;
import com.vip.student.network.ApiUrls;
import com.vip.student.network.BaseHttpRequestHandler;
import com.vip.student.persistobject.UserInfo;
import com.vip.student.utils.CacheUtility;
import com.vip.student.utils.ToastUtils;
import com.vip.student.utils.Utils;
import com.vip.student.widget.WrapContentHeightViewPager;
import com.vip.student.widget.wheel.ArrayWheelAdapter;
import com.vip.student.widget.wheel.WheelView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by qs on 2015/12/4.
 */
public class TimeTableFragment extends BaseFragment implements View.OnClickListener {
    public final String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private WrapContentHeightViewPager pager;
    private ListView mlistView;
    private TextView tvmonth;
    private LinearLayout mViewCalendarContent;
    private LinearLayout mViewMainContent;
    private ImageView mViewArrow;

    // pop menu
    private PopupWindow mPopupWindow;
    private LayoutInflater mInflater;
    private View mExamTimePopMenuContent;
    private WheelView mWVExamTimes;
    private LinearLayout titleeeee;
    private View no_schedule;

    private final LayoutTransition transitioner = new LayoutTransition();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = getActivity().getLayoutInflater();

        View view = View.inflate(getActivity(), R.layout.fragment_timetable, null);

        tvmonth = (TextView) view.findViewById(R.id.Month_choose);
        tvmonth.setOnClickListener(this);
        tvmonth.setVisibility(View.GONE);

        intiPopMenu();
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        String s = calendar.getTime().toString();

        pager = (WrapContentHeightViewPager) view.findViewById(R.id.viewPager);
        pager.setOffscreenPageLimit(2);


        mViewMainContent = (LinearLayout) view.findViewById(R.id.main_content);
        mViewCalendarContent = (LinearLayout) view.findViewById(R.id.calendar_content);
        mViewCalendarContent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mViewCalendarContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            mViewCalendarContent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "translationY",
                                -mViewCalendarContent.getHeight(), 0).
                                setDuration(transitioner.getDuration(LayoutTransition.APPEARING));
                        transitioner.setAnimator(LayoutTransition.APPEARING, animIn);

                        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "translationY", 0,
                                -mViewCalendarContent.getHeight()).
                                setDuration(transitioner.getDuration(LayoutTransition.DISAPPEARING));
                        transitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
                        mViewMainContent.setLayoutTransition(transitioner);
                        transitioner.setStartDelay(LayoutTransition.APPEARING, 0);
                        transitioner.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0);
                    }

                });
        mViewArrow = (ImageView) view.findViewById(R.id.arrow);
        mViewArrow.setOnClickListener(this);

        //title eeee
        titleeeee = (LinearLayout) view.findViewById(R.id.title_eeee);
        for (String string : weekDays) {
            TextView textView = new TextView(view.getContext());
            textView.setText(string);
            textView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            textView.setLayoutParams(layoutParams);
            titleeeee.addView(textView);
        }

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ((MyFragmentPagerAdapter) pager.getAdapter()).setDestoryItem(false);
                Date startDate=((MyFragmentPagerAdapter) pager.getAdapter()).getStartDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.MONTH, position);

                final SimpleDateFormat sFullDateFormat = new SimpleDateFormat("MM月");
                String monthStr = sFullDateFormat.format(calendar.getTime());
                tvmonth.setText(monthStr);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        String mouths = String.format("%02d", Calendar.getInstance().get(Calendar.MONTH) + 1);
        tvmonth.setText(mouths + "月");

        mlistView = (ListView) view.findViewById(R.id.ListView);
        no_schedule = view.findViewById(R.id.no_schedule_layout);
        no_schedule.setClickable(true);

        showCalendarAndList(calendar);

        queryList();

        return view;
    }

    private void showList(List<ChangeListRequestBean.DataEntity> data) {
        mlistView.setAdapter(new TimeTableListAdapter(getActivity(), this, data));
    }

    private void intiPopMenu() {
         /* 设置显示menu布局 view子VIEW */
        // View sub_view = inflater.inflate(R.layout.pop_memu_height, null);
        /* 第一个参数弹出显示view 后两个是窗口大小 */
        mPopupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        /* 设置背景显示 */
        // mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.agold_menu_bg));
        int bgColor = getResources().getColor(R.color.main_background);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(bgColor));
        /* 设置触摸外面时消失 */
        mPopupWindow.setOutsideTouchable(true);
        /* 设置系统动画 */
        mPopupWindow.setAnimationStyle(R.style.pop_menu_animation);
        mPopupWindow.update();
        mPopupWindow.setTouchable(true);
        /* 设置点击menu以外其他地方以及返回键退出 */
        mPopupWindow.setFocusable(true);
    }


    private void showCalendarAndList(Calendar calendar) {
        ChangeListBean request=generateRequestBean();
        //获取缓存
        List<ChangeListRequestBean.DataEntity> data=CacheUtility.findCacheData(ApiUrls.GETSTUDEENTLESSON, request, ChangeListRequestBean.DataEntity.class);

        if(data==null||data.size()==0){
            showRotateProgressDialog(getString(R.string.timetable_loading), true);
            no_schedule.setVisibility(View.VISIBLE);
            no_schedule.setFocusable(true);
            tvmonth.setVisibility(View.GONE);
        }else{
            tvmonth.setVisibility(View.VISIBLE);
            //显示日历
            showCalendar(data);
            //显示ListView
            showList(data);
        }
    }

    private void showCalendar(List<ChangeListRequestBean.DataEntity> data) {
        if (pager == null) return;

        Calendar calendarNow = Calendar.getInstance();
        Date nowDate = calendarNow.getTime();

        ChangeListRequestBean.DataEntity firstData = data.get(0);
        ChangeListRequestBean.DataEntity lastData = data.get(data.size() - 1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = nowDate;
        Date endDate = nowDate;
        try {
            startDate = dateFormat.parse(firstData.getStartTime());
        } catch (ParseException e) {
        }
        try {
            endDate = dateFormat.parse(lastData.getEndTime());
        } catch (ParseException e) {
        }//结束时间小于开始时间，交换下
        if (endDate.before(startDate)) {
            long startTimeInMins = startDate.getTime();
            long endTimeInMins = endDate.getTime();
            startDate.setTime(endTimeInMins);
            endDate.setTime(startTimeInMins);
        }

        pager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), startDate, endDate, TimeTableListAdapter.shortListByTime(data), new CellClick() {
            @Override
            public boolean onCellClicked(View v, Date date) {
                if (date == null) return false;
                ListAdapter mlistViewAdapter = mlistView.getAdapter();
                if (mlistViewAdapter != null) {
                    List<ChangeListRequestBean.DataEntity> list = ((TimeTableListAdapter) mlistView.getAdapter()).getList();
                    ArrayList<String> strings = new ArrayList<>();
                    for (ChangeListRequestBean.DataEntity entity : list) {
                        Date itemdate = new Date(Utils.parseServerTime(entity.getStartTime()));
                        if (itemdate.getYear() == date.getYear() && itemdate.getMonth() == date.getMonth() && itemdate.getDate() == date.getDate()) {
                            strings.add(entity.getCourseSubTypeName());
                        }
                    }
                    showPopup(v, strings);
                    int datePosition = ((TimeTableListAdapter) mlistViewAdapter).getDatePosition(date);
                    if (datePosition >= 0) {
                        if (strings.size() <= 2) {
                            mlistView.setSelection(datePosition);
                        } else {
                            mlistView.setSelection(datePosition);
                        }
                    }
                }
                return false;
            }
        }));
        selectNearMonth(startDate);
    }

    private void showPopup(final View view, final List<String> strings) {
        if (strings.size() > 2)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ViewGroup popContent = (ViewGroup) View.inflate(view.getContext(), R.layout.pop_cellview, null);
                    TextView childAt = (TextView) popContent.getChildAt(0);
                    TextView childAt1 = (TextView) popContent.getChildAt(1);
                    childAt.setText(strings.get(2));
                    if (strings.size() > 3) {
                        childAt1.setVisibility(View.VISIBLE);
                        childAt1.setText(strings.get(3));
                    }
                    PopupWindow popupWindow = new PopupWindow(popContent, view.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    int bgColor = getResources().getColor(R.color.transparent);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(bgColor));
                    /* 设置触摸外面时消失 */
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.showAsDropDown(view, 0, 0);
                }
            }, 200);

    }

    /***
     * 选择离当前时间最近的月份
     * @param startDate
     */
    private void selectNearMonth(Date startDate){
        Calendar calendar = Calendar.getInstance();
        int curYear= calendar.get(Calendar.YEAR);
        int curMonth=calendar.get(Calendar.MONTH);
        int distance=Integer.MAX_VALUE;
        int nearPosition=0;
        for(int i=0;i<pager.getAdapter().getCount();i++){
            calendar.setTime(startDate);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, i);

            int tempYear=calendar.get(Calendar.YEAR);
            int tempMonth=calendar.get(Calendar.MONTH);

            int monthNums=Math.abs((tempYear-curYear)*12+tempMonth-curMonth);
            if(monthNums<distance){
                distance=monthNums;
                nearPosition=i;
            }
        }

        pager.setCurrentItem(nearPosition);

        calendar.setTime(startDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, nearPosition);

        final SimpleDateFormat sFullDateFormat = new SimpleDateFormat("MM月");
        String monthStr = sFullDateFormat.format(calendar.getTime());
        tvmonth.setText(monthStr);
    }

    private ChangeListBean generateRequestBean(){
        ChangeListBean request = new ChangeListBean();
        ChangeListBean.DataEntity data = new ChangeListBean.DataEntity();

        //时间字符串可能为空
        if (UserInfo.getCurrentUser().getFirstLessonTime() > 0) {
            long firstLessontime = UserInfo.getCurrentUser().getFirstLessonTime();
            String startdate = Utils.formatToServerTimeStr(firstLessontime);
            data.setStartDate(startdate);
        }
        if (UserInfo.getCurrentUser().getLasterLessonTime() > 0) {
            long LasterLessonTime = UserInfo.getCurrentUser().getLasterLessonTime();
            String enddate = Utils.formatToServerTimeStr(LasterLessonTime);
            data.setEndDate(enddate);
        }
        data.setStudentId(UserInfo.getCurrentUser().getUserId());
        request.setData(data);

        return request;
    }

    //获取学生课表
    private void queryList() {
        final ChangeListBean request=generateRequestBean();
        startRequest(ApiUrls.GETSTUDEENTLESSON, request, new BaseHttpRequestHandler() {
            @Override
            public void onRequestFinished() {
                closeRotateProgressDialog();
            }

            @Override
            public void onRequestFailed(String errorMsg) {
                super.onRequestFailed(errorMsg);
                Utils.toast(errorMsg);
                /*no_schedule.setVisibility(View.VISIBLE);
                no_schedule.setFocusable(true);*/

            }

            @Override
            public void onRequestFailedNoNetwork() {
                super.onRequestFailedNoNetwork();
                Utils.toast(R.string.network_disconnect);
                /*no_schedule.setVisibility(View.VISIBLE);
                no_schedule.setFocusable(true);*/

            }

            @Override
            public void onTimeout() {
                super.onTimeout();
                Utils.toast(R.string.network_timeout);
               /* no_schedule.setVisibility(View.VISIBLE);
                no_schedule.setFocusable(true);*/

            }

            @Override
            public void onRequestSucceeded(String content) {
                super.onRequestSucceeded(content);
                if (UserInfo.getCurrentUser() == null) {
                    return;
                }
                ChangeListRequestBean responseBean = Utils.parseJsonTostError(content, ChangeListRequestBean.class);
                if (responseBean != null) {
                    showList(responseBean.getData());

                    if(responseBean.getData()!=null&&responseBean.getData().size()>0){
                        showCalendar(responseBean.getData());
                    }

                    if (responseBean.getData()==null||responseBean.getData().size() == 0) {
                        no_schedule.setVisibility(View.VISIBLE);
                        no_schedule.setFocusable(true);
                        tvmonth.setVisibility(View.GONE);
                    } else {
                        no_schedule.setVisibility(View.GONE);
                        no_schedule.setFocusable(false);
                        tvmonth.setVisibility(View.VISIBLE);
                    }

                    //缓存起来
                    CacheUtility.putCacheDataList(ApiUrls.GETSTUDEENTLESSON, request, responseBean.getData(), ChangeListRequestBean.DataEntity.class);
                } else {
                    no_schedule.setVisibility(View.VISIBLE);
                    no_schedule.setFocusable(true);
                    tvmonth.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onStart() {
        if (pager != null) {
            pager.postInvalidate();
        }
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Month_choose:
                showChooseTimeMenu();
                break;
            case R.id.arrow:
                toggle();
                break;
        }
    }

    private void toggle() {
        if (mViewCalendarContent.getVisibility() == View.VISIBLE) {
            mViewCalendarContent.setVisibility(View.GONE);
            mViewArrow.setImageResource(R.drawable.arrow_down_selector);
        } else {
            mViewCalendarContent.setVisibility(View.VISIBLE);
            mViewArrow.setImageResource(R.drawable.arrow_up_selector);
        }
    }

    private void showChooseTimeMenu() {
        if(pager==null&&pager.getAdapter()==null&&pager.getAdapter().getCount()==0){
            return;
        }

        mExamTimePopMenuContent = mInflater.inflate(R.layout.pop_memu_choose_time, null);
        mPopupWindow.setContentView(this.mExamTimePopMenuContent);
        TextView exam_time_set = (TextView) mExamTimePopMenuContent.findViewById(R.id.exam_time_set);
        exam_time_set.setText("请选择月份");
        View btnCancel = mExamTimePopMenuContent.findViewById(R.id.exam_time_cancel_time);
        btnCancel.setOnClickListener(mOnExamTimeClickListener);
        View btnFinish = mExamTimePopMenuContent.findViewById(R.id.exam_time_finish_time);
        btnFinish.setOnClickListener(mOnExamTimeClickListener);
        mWVExamTimes = (WheelView) mExamTimePopMenuContent.findViewById(R.id.time_wheel_time);
        mWVExamTimes.setCyclic(false);

        refreshPopmenuTimeContent();
        showPopMenu();
    }

    private View.OnClickListener mOnExamTimeClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.exam_time_cancel_time:
                    closePopWin();
                    break;
                case R.id.exam_time_finish_time:
                    int potion_time = mWVExamTimes.getCurrentItem();
                    pager.setCurrentItem(potion_time);
                    closePopWin();
                    break;
            }
        }
    };

    private void refreshPopmenuTimeContent() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM");
        MyFragmentPagerAdapter pageAdapter=(MyFragmentPagerAdapter) pager.getAdapter();
        Date startDate=pageAdapter.getStartDate();
        String[] values = new String[pageAdapter.getCount()];
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < pageAdapter.getCount(); i++) {
            calendar.setTime(startDate);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, i);
            values[i] = dataFormat.format(calendar.getTime());
        }
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<>(values);
        mWVExamTimes.setAdapter(adapter);
        mWVExamTimes.setCurrentItem(0);
    }

    // 关闭PopUpWindow
    public boolean closePopWin() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (pager.getAdapter() != null) {
                ((MyFragmentPagerAdapter) pager.getAdapter()).setDestoryItem(true);
            }
            releaseAllRequest();
            closeRotateProgressDialog();
        } else {
            queryList();
        }
    }

    private void showPopMenu() {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            /* 最重要的一步：弹出显示 在指定的位置(parent) 最后两个参数 是相对于 x / y 轴的坐标 */
            mPopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            backgroundAlpha(0.7f);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //待评价状态实时更新
        if (requestCode == 12345 && resultCode == Activity.RESULT_OK) {
            String stringExtra = data.getStringExtra(TeacherAppraiseActivity.EXT_KEY_LESSONID);
            TimeTableListAdapter adapter = (TimeTableListAdapter) mlistView.getAdapter();
            adapter.setAppraise(stringExtra);
            Log.e("result", stringExtra);
        }
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }


}
