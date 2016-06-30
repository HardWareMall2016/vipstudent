package com.vip.student.my;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.loopj.android.http.DataAsyncHttpResponseHandler;
import com.vip.student.R;
import com.vip.student.base.BaseActivity;
import com.vip.student.my.bean.ChangeLessonBean;
import com.vip.student.my.bean.ChangeListBean;
import com.vip.student.my.bean.ChangeListRequestBean;
import com.vip.student.my.bean.GetChangeLessonRequestBean;
import com.vip.student.my.bean.SubmitBean;
import com.vip.student.my.bean.SubmitRequestBean;
import com.vip.student.network.ApiUrls;
import com.vip.student.network.BaseHttpRequestHandler;
import com.vip.student.persistobject.CourseTimeModelBean;
import com.vip.student.persistobject.UserInfo;
import com.vip.student.utils.Utils;
import com.vip.student.widget.wheel.ArrayWheelAdapter;
import com.vip.student.widget.wheel.WheelView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/17.
 */


public class MyChangeActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mChangeLesson ;
    private TextView mChangeLessonName ;
    private TextView mChangeTimeName ;
    private LinearLayout mChangeLessonTime ;
    private Button mSubmit ;
    private EditText mChangLessonReason ;
    private EditText mChangLessonMemo ;
    // pop menu
    private PopupWindow mPopupWindow;
    private LayoutInflater mInflater;

    private View mExamTimePopMenuContent;
    private WheelView mWVExamTimes ;

    //private String LessonChangeStatus;
    private int potion = -1;
    private int potion_time = -1 ;

    private List<List<ChangeListRequestBean.DataEntity>> mLessonGroup=new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeclass);
        mInflater = getLayoutInflater();
        initView();
        GetChangeLesson();
        intiPopMenu();
        queryList();
    }

    private void initView() {
        mChangeLesson = (LinearLayout)findViewById(R.id.change_lesson);
        mChangeLesson.setOnClickListener(this);
        mChangeLessonName = (TextView)findViewById(R.id.tv_change_lesson_name);
        mChangeLessonTime = (LinearLayout)findViewById(R.id.change_lesson_time);
        mChangeLessonTime.setOnClickListener(this);
        mChangeTimeName = (TextView)findViewById(R.id.tv_change_time_name);
        mSubmit = (Button)findViewById(R.id.btn_submit);
        mSubmit.setOnClickListener(this);
        mChangLessonReason = (EditText)findViewById(R.id.edit_changlesson_reason);
        mChangLessonMemo = (EditText)findViewById(R.id.edit_changlesson_memo);
    }

    private void intiPopMenu() {
        /* 设置显示menu布局 view子VIEW */
        // View sub_view = inflater.inflate(R.layout.pop_memu_height, null);
		/* 第一个参数弹出显示view 后两个是窗口大小 */
        mPopupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
		/* 设置背景显示 */
        // mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.agold_menu_bg));
        int bgColor=getResources().getColor(R.color.main_background);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_lesson:
                hideKeyboard();
                if(mLessonGroup.size() != 0){
                    showChooseLessonMenu();
                }else{
                    Utils.toast("没有所需要调整的课程");
                }

                break;
            case R.id.change_lesson_time:
                hideKeyboard();
                if(mLessonGroup.size() != 0){
                    if(potion < 0){
                        Utils.toast("首先选择所需调课的课程");
                    }else{
                        showChooseLessonTimeMenu();
                    }
                }else{
                    Utils.toast("没有所需要调整的时间");
                }

                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    public void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager)
                this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
    }

    private void submit() {
        if (TextUtils.isEmpty(mChangeLessonName.getText().toString())) {
            Utils.toast("请选择调整的课程");
            return;
        }
        if (TextUtils.isEmpty(mChangeTimeName.getText().toString())) {
            Utils.toast("请选择调课的时间");
            return;
        }
        if (TextUtils.isEmpty(mChangLessonReason.getText().toString())) {
            Utils.toast("请输调课原因");
            return;
        }
        if(TextUtils.isEmpty(mChangLessonMemo.getText().toString())){
            Utils.toast("调课时间备注");
            return;
        }
        SubmitRequestBean request = new SubmitRequestBean();
        SubmitRequestBean.DataEntity data = new SubmitRequestBean.DataEntity();
        data.setStudentID(UserInfo.getCurrentUser().getUserId());
        data.setLessonID(mLessonGroup.get(potion).get(potion_time).getLessonId());
        data.setChangeReason(mChangLessonReason.getText().toString());
        data.setChangedDateMemo(mChangLessonMemo.getText().toString());
        request.setData(data);
        showRotateProgressDialog(getString(R.string.change_lesson_submiting), true);
        startRequest(ApiUrls.CHANGELESSONREQUEST, request, new BaseHttpRequestHandler() {
            @Override
            public void onRequestFinished() {
                closeRotateProgressDialog();
            }

            @Override
            public void onRequestFailed(String errorMsg) {
                super.onRequestFailed(errorMsg);
                Utils.toast(errorMsg);
            }

            @Override
            public void onRequestFailedNoNetwork() {
                super.onRequestFailedNoNetwork();
                Utils.toast(R.string.network_disconnect);
            }

            @Override
            public void onTimeout() {
                super.onTimeout();
                Utils.toast(R.string.network_timeout);
            }

            @Override
            public void onRequestSucceeded(String content) {
                super.onRequestSucceeded(content);
                SubmitBean bean = Utils.parseJsonTostError(content, SubmitBean.class);
                if(bean != null){
                    if(bean.getCode() == 0){
                        Utils.toast(getString(R.string.change_lesson_submit_success));
                        finish();
                    }
                }
            }
        });
    }

    //选择时间
    private void showChooseLessonTimeMenu() {
        mExamTimePopMenuContent = mInflater.inflate(R.layout.pop_memu_choose_time, null);
        mPopupWindow.setContentView(this.mExamTimePopMenuContent);
        View btnCancel=mExamTimePopMenuContent.findViewById(R.id.exam_time_cancel_time);
        btnCancel.setOnClickListener(mOnExamTimeClickListener);
        View btnFinish=mExamTimePopMenuContent.findViewById(R.id.exam_time_finish_time);
        btnFinish.setOnClickListener(mOnExamTimeClickListener);
        mWVExamTimes=(WheelView)mExamTimePopMenuContent.findViewById(R.id.time_wheel_time);
        mWVExamTimes.setCyclic(false);

        refreshPopmenuTimeContent();
        showPopMenu();
    }


    private void showChooseLessonMenu() {
        mExamTimePopMenuContent = mInflater.inflate(R.layout.pop_memu_choose_lesson, null);
        mPopupWindow.setContentView(this.mExamTimePopMenuContent);
        View btnCancel=mExamTimePopMenuContent.findViewById(R.id.exam_time_cancel);
        btnCancel.setOnClickListener(mOnExamTimeClickListener);
        View btnFinish=mExamTimePopMenuContent.findViewById(R.id.exam_time_finish);
        btnFinish.setOnClickListener(mOnExamTimeClickListener);
        mWVExamTimes=(WheelView)mExamTimePopMenuContent.findViewById(R.id.time_wheel);
        mWVExamTimes.setCyclic(false);

        refreshPopmenuContent();
        showPopMenu();
    }

    private void GetChangeLesson() {
        ChangeLessonBean request = new ChangeLessonBean();
        showRotateProgressDialog(getString(R.string.get_change_lesson), true);
        startRequest(ApiUrls.GETCHANGELESSON, request, new BaseHttpRequestHandler() {
            @Override
            public void onRequestFinished() {
                closeRotateProgressDialog();
            }
            @Override
            public void onRequestFailed(String errorMsg) {
                super.onRequestFailed(errorMsg);
                Utils.toast(errorMsg);
            }

            @Override
            public void onRequestFailedNoNetwork() {
                super.onRequestFailedNoNetwork();
                Utils.toast(R.string.network_disconnect);
            }

            @Override
            public void onTimeout() {
                super.onTimeout();
                Utils.toast(R.string.network_timeout);
            }

            @Override
            public void onRequestSucceeded(String content) {
                super.onRequestSucceeded(content);
                GetChangeLessonRequestBean bean = Utils.parseJsonTostError(content, GetChangeLessonRequestBean.class);
                if(bean != null){
                        int i = bean.getData().size();
                        if(i == 0){
                            mSubmit.setEnabled(true);
                        }else{
                            for(int j = 0;j < i;j++){
                                String LessonChangeStatus = bean.getData().get(j).getLessonChangeStatus();
                                if(TextUtils.isEmpty(LessonChangeStatus) || LessonChangeStatus.equals("0") || LessonChangeStatus.equals("3")){
                                    Utils.toast("正在调课，不能连续调课");
                                    mSubmit.setEnabled(false);
                                    mSubmit.setBackgroundColor(Color.parseColor("#808080"));
                                }else{
                                    mSubmit.setEnabled(true);
                                }
                            }
                        }
                }
            }

        });
    }


    //获取学生课表
    private void queryList() {
        ChangeListBean request = new ChangeListBean();
        ChangeListBean.DataEntity data = new ChangeListBean.DataEntity();

        //时间字符串可能为空
        if(UserInfo.getCurrentUser().getFirstLessonTime()>0){
            long firstLessontime = UserInfo.getCurrentUser().getFirstLessonTime();
            //long firt_times = Utils.parseLocalTimeToServer(firstLessontime);
            String startdate = Utils.formatToServerTimeStr(firstLessontime);
            data.setStartDate(startdate);
        }
        if(UserInfo.getCurrentUser().getLasterLessonTime() > 0){
            long LasterLessonTime = UserInfo.getCurrentUser().getLasterLessonTime();
            String enddate = Utils.formatToServerTimeStr(LasterLessonTime);
            data.setEndDate(enddate);
        }
        data.setStudentId(UserInfo.getCurrentUser().getUserId());
        request.setData(data);
        startRequest(ApiUrls.GETSTUDEENTLESSON, request, new BaseHttpRequestHandler() {
            @Override
            public void onRequestFinished() {
                closeRotateProgressDialog();
            }

            @Override
            public void onRequestFailed(String errorMsg) {
                super.onRequestFailed(errorMsg);
                Utils.toast(errorMsg);
            }

            @Override
            public void onRequestFailedNoNetwork() {
                super.onRequestFailedNoNetwork();
                Utils.toast(R.string.network_disconnect);
            }

            @Override
            public void onTimeout() {
                super.onTimeout();
                Utils.toast(R.string.network_timeout);
            }

            @Override
            public void onRequestSucceeded(String content) {
                super.onRequestSucceeded(content);
                ChangeListRequestBean bean = Utils.parseJsonTostError(content, ChangeListRequestBean.class);
                if (bean != null) {
                    HashMap<String, List<ChangeListRequestBean.DataEntity>> map = new HashMap<>();
                    //只能调24小时后的课程
                    Calendar defCalendar=Calendar.getInstance();
                    defCalendar.add(Calendar.DAY_OF_MONTH, 1);
                    //如果TeacherLevel 内容:231 vip 总监,可以调3小时后的课程
                    Calendar vipCalendar=Calendar.getInstance();
                    vipCalendar.add(Calendar.HOUR_OF_DAY, 3);

                    List<CourseTimeModelBean> courseTimeModels=UserInfo.getCurrentUser().getCourseTimeModels();

                    for (ChangeListRequestBean.DataEntity item : bean.getData()) {

                        Boolean isVipTeacher=false;
                        if(courseTimeModels!=null){
                            for (CourseTimeModelBean courseTimeModel:courseTimeModels){
                                if(item.getCourseTypeCode().equals(courseTimeModel.getCourseTypeCode())&&"231".equals(courseTimeModel.getTeacherLevel())){
                                        isVipTeacher=true;
                                    break;
                                }
                            }
                        }

                        if(isVipTeacher){
                            //如果TeacherLevel 内容:231 vip 总监,可以调3小时后的课程
                            if(Utils.parseServerTime(item.getStartTime()) < vipCalendar.getTimeInMillis()){
                                continue;
                            }
                        }else {
                            //过滤只显示24小时后的课程
                            if(Utils.parseServerTime(item.getStartTime()) < defCalendar.getTimeInMillis()){
                                continue;
                            }
                        }


                        String key = item.getCourseTypeCode() + item.getCourseSubTypeCode();
                        List<ChangeListRequestBean.DataEntity> lessons = map.get(key);
                        if (lessons == null) {
                            lessons = new LinkedList<>();
                            lessons.add(item);
                            map.put(key, lessons);
                        } else {
                            lessons.add(item);
                        }
                    }
                    mLessonGroup.clear();
                    Iterator<Map.Entry<String, List<ChangeListRequestBean.DataEntity>>> it = map.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, List<ChangeListRequestBean.DataEntity>> entry = it.next();
                        //对时间日期进行排序
                        List<ChangeListRequestBean.DataEntity> lessons = entry.getValue();
                        Collections.sort(lessons, new Comparator<ChangeListRequestBean.DataEntity>(){
                            @Override
                            public int compare(ChangeListRequestBean.DataEntity t1, ChangeListRequestBean.DataEntity t2) {
                                //转化为时间戳（long类型）
                                long m1 = Utils.parseServerTime(t1.getStartTime());
                                long m2 = Utils.parseServerTime(t2.getStartTime());
                                int result = 0;
                                if(m1>m2)
                                {
                                    result = 1;
                                }
                                if(m1<m2)
                                {
                                    result=-1;
                                }
                                return result;
                            }
                        });
                        mLessonGroup.add(lessons);
                    }
                }
            }
        });
    }

    private void refreshPopmenuContent() {
        String[] values = new String[mLessonGroup.size()];
        for (int i = 0; i < mLessonGroup.size(); i++) {
            values[i] = mLessonGroup.get(i).get(0).getCourseTypeName()+"-"+mLessonGroup.get(i).get(0).getCourseSubTypeName();
        }
        ArrayWheelAdapter<String> adapter=new ArrayWheelAdapter<>(values);
        mWVExamTimes.setAdapter(adapter);
        mWVExamTimes.setCurrentItem(0);
    }

    private void refreshPopmenuTimeContent(){
        List<ChangeListRequestBean.DataEntity> lessons = mLessonGroup.get(potion);
        String[] values = new String[lessons.size()];
        for (int i = 0; i < lessons.size(); i++) {
             values[i] = Utils.getFullFormateTimeStr(Utils.parseServerTime(lessons.get(i).getStartTime()));
        }
        ArrayWheelAdapter<String> adapter=new ArrayWheelAdapter<>(values);
        mWVExamTimes.setAdapter(adapter);
        mWVExamTimes.setCurrentItem(0);
    }


    private View.OnClickListener mOnExamTimeClickListener=new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.exam_time_cancel:
                    closePopWin();
                    break;
                case R.id.exam_time_finish://每一次点击完成的时候将时间设为空，potion_time设为默认-1
                    mChangeTimeName.setText("");
                    potion_time = -1;
                    potion = mWVExamTimes.getCurrentItem();
                    mChangeLessonName.setText(mLessonGroup.get(potion).get(0).getCourseTypeName() + "-" + mLessonGroup.get(potion).get(0).getCourseSubTypeName());

                    closePopWin();
                    break;
                case R.id.exam_time_cancel_time:
                    closePopWin();
                    break;
                case R.id.exam_time_finish_time:
                    potion_time = mWVExamTimes.getCurrentItem();
                    mChangeTimeName.setText(mLessonGroup.get(potion).get(potion_time).getStartTime());
                    closePopWin();
                    break;
            }
        }
    };

    // 关闭PopUpWindow
    public boolean closePopWin() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return true;
        }
        return false;
    }

    private void showPopMenu() {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
			/* 最重要的一步：弹出显示 在指定的位置(parent) 最后两个参数 是相对于 x / y 轴的坐标 */
            mPopupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            backgroundAlpha(0.7f);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener(){
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                   // BaseHttpRequest.releaseRequest(mRequestHandle);
                }
            });
        }
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        this.getWindow().setAttributes(lp);
    }
}
