package com.vip.student.my;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vip.student.R;
import com.vip.student.base.BaseFragment;
import com.vip.student.persistobject.TeacherGroupBean;
import com.vip.student.persistobject.UserInfo;
import com.vip.student.utils.GridViewUtils;
import com.vip.student.view.ActionSheetDialog;
import com.vip.student.widget.MyGridView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class MyFragment extends BaseFragment implements View.OnClickListener {

    private TextView mCourseRate;
    private TextView mStudyAdviser;
    private TextView mSetting ;
    private TextView mStudentDays ;
    private TextView mTotalCourseCnt ;
    private TextView mRemindcourseCnt ;
    private ProgressBar mProgressBar ;
    private TextView mIntegral;
    private TextView mStudyConsultantName;
    private LinearLayout mLlcouser ;
    private LinearLayout mLlStudyAdviser ;
    private RelativeLayout mPhone ;
    private ListView mListview ;
    private TextView mChangeClass ;
    private ImageView mStudyConsultantAvouter ;

    private String phoneNumber ;
    private LayoutInflater mInflater;
    private boolean mShow = true;

    private MyListAdapter mAdapter ;
    private List<TeacherGroupBean.TeacherGroupEntity> mList = new ArrayList<TeacherGroupBean.TeacherGroupEntity>();

    private onSegmentViewClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.frag_layout_mine, null);
        mInflater= getActivity().getLayoutInflater();

        mCourseRate = (TextView) contentView.findViewById(R.id.tv_kechengnjindu);
        mStudyAdviser = (TextView) contentView.findViewById(R.id.tv_xuexiguwen);
        mLlcouser = (LinearLayout) contentView.findViewById(R.id.ll_couser);
        mLlStudyAdviser = (LinearLayout) contentView.findViewById(R.id.ll_StudyAdviser);
        mSetting = (TextView) contentView.findViewById(R.id.mine_setting);
        mStudentDays = (TextView) contentView.findViewById(R.id.mine_num);
        mStudentDays.setText(UserInfo.getCurrentUser().getStudentDays() + "");
        mTotalCourseCnt = (TextView) contentView.findViewById(R.id.totalcoursecnt);
        mTotalCourseCnt.setText(UserInfo.getCurrentUser().getTotalCourseCnt() + "");
        mRemindcourseCnt = (TextView) contentView.findViewById(R.id.remindcoursecnt);
        mProgressBar = (ProgressBar) contentView.findViewById(R.id.integral_progressbar);
        mIntegral = (TextView) contentView.findViewById(R.id.integral);
        mStudyConsultantName = (TextView) contentView.findViewById(R.id.study_consultant_name);
        mPhone = (RelativeLayout) contentView.findViewById(R.id.rl_mine_phone);
        mChangeClass = (TextView)contentView.findViewById(R.id.tv_change_class);
        mStudyConsultantAvouter = (ImageView)contentView.findViewById(R.id.avouter);
        if(UserInfo.getCurrentUser().getStudyConsultantNameSex() != null){
            if(UserInfo.getCurrentUser().getStudyConsultantNameSex().equals("男")){
                mStudyConsultantAvouter.setImageResource(R.mipmap.bg_default_head_portrait_man);
            }else{
                mStudyConsultantAvouter.setImageResource(R.mipmap.bg_default_head_portrait_woman);
            }
        }
        mList = UserInfo.getCurrentUser().getTeacherGroup().getTeacherGroup();
        mListview = (ListView) contentView.findViewById(R.id.mine_listview);
        mAdapter = new MyListAdapter();
        mListview.setAdapter(mAdapter);

        int remindcnt = UserInfo.getCurrentUser().getTotalCourseCnt() - UserInfo.getCurrentUser().getFinishCourseCnt();
        if(remindcnt < 0){
            mRemindcourseCnt.setText("0");
        }else{
            mRemindcourseCnt.setText(remindcnt + "");
        }
        int num1 = UserInfo.getCurrentUser().getFinishCourseCnt();
        int num2 = UserInfo.getCurrentUser().getTotalCourseCnt();

        if(remindcnt < 0){
            mIntegral.setText("100%");
            mProgressBar.setProgress(100);
        }else{
            mIntegral.setText(NumberFormat.getInstance().format(num1*100/ num2) + "%");
            mProgressBar.setProgress(num1*100/ num2);
        }
        mStudyConsultantName.setText(UserInfo.getCurrentUser().getStudyConsultantEName());
        phoneNumber = UserInfo.getCurrentUser().getStudyConsultantTel() + "";

        mCourseRate.setOnClickListener(this);
        mStudyAdviser.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        mPhone.setOnClickListener(this);
        mChangeClass.setOnClickListener(this);
        refreshViews();

        return contentView;
    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.mine_list_item, null);
                viewHolder.mine_listview_title = (TextView)convertView.findViewById(R.id.mine_listview_title);
                viewHolder.mGridView = (MyGridView)convertView.findViewById(R.id.mine_listview_gridview);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mine_listview_title.setText(mList.get(i).getCourseProductName());
            viewHolder.mGridView.setAdapter(new GridViewAdapter(getActivity(), mList.get(i).getTeachers()));
            // 计算GridView宽度, 设置默认为numColumns为2.
            GridViewUtils.updateGridViewLayoutParams(viewHolder.mGridView, 2);

            return convertView;
        }


    }

    private class GridViewAdapter extends BaseAdapter {

        private List<TeacherGroupBean.TeacherGroupEntity.TeachersEntity> gridViewList = new ArrayList<TeacherGroupBean.TeacherGroupEntity.TeachersEntity>();
        public GridViewAdapter(FragmentActivity activity, List<TeacherGroupBean.TeacherGroupEntity.TeachersEntity> teachers) {
              this.gridViewList = teachers;
        }

        @Override
        public int getCount() {
            return gridViewList.size();
        }

        @Override
        public Object getItem(int i) {
            return gridViewList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolders viewHolder;
            if(convertView == null){
                viewHolder = new ViewHolders();
                convertView = mInflater.inflate(R.layout.mine_gridview_item, null);
                viewHolder.gridview_name = (TextView)convertView.findViewById(R.id.gridview_name);
                viewHolder.TeacherQQ = (TextView)convertView.findViewById(R.id.gridview_qq);
                viewHolder.TotalLessonCnt = (TextView)convertView.findViewById(R.id.gridview_remaiLessonCnt);
                viewHolder.OverPlusLessonCnt = (TextView)convertView.findViewById(R.id.gridview_totalLessonCnt);
                viewHolder.Teacher = (TextView)convertView.findViewById(R.id.gridview_techer);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolders) convertView.getTag();
            }
            viewHolder.gridview_name.setText(gridViewList.get(i).getCourseSubTypeName()+"课程");
            viewHolder.TeacherQQ.setText(gridViewList.get(i).getTeacherQQ());
            viewHolder.TotalLessonCnt.setText(gridViewList.get(i).getTotalLessonCnt()+"");
            viewHolder.OverPlusLessonCnt.setText(gridViewList.get(i).getRemaiLessonCnt()+"");
            viewHolder.Teacher.setText(gridViewList.get(i).getTeacherName());
            if(i == 0){
                viewHolder.gridview_name.setBackgroundResource(R.mipmap.bg_blue_rounded);
            }else if(i == 1){
                viewHolder.gridview_name.setBackgroundResource(R.mipmap.bg_yellow_rounded);
            }else if(i == 2){
                viewHolder.gridview_name.setBackgroundResource(R.mipmap.bg_purple_rounded);
            }else if(i == 3){
                viewHolder.gridview_name.setBackgroundResource(R.mipmap.bg_cyan_rounded);
            }else{
                viewHolder.gridview_name.setBackgroundResource(R.mipmap.bg_blue_rounded);
            }

            return convertView;
        }
    }

    static class ViewHolders{
        TextView gridview_name ;//教程
        TextView Teacher ;//教师
        TextView TeacherQQ ; //QQ
        TextView TotalLessonCnt ;//已排课时
        TextView OverPlusLessonCnt ;//剩余课时
    }

    static class ViewHolder{
        TextView mine_listview_title ;
        MyGridView mGridView ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_kechengnjindu:
                if (mCourseRate.isSelected()) {
                    return;
                }
                mShow = true;
                if (listener != null) {
                    listener.onSegmentViewClick(mCourseRate, 0);
                }
                refreshViews();
                break;
            case R.id.tv_xuexiguwen:
                if (mStudyAdviser.isSelected()) {
                    return;
                }
                mShow = false;
                if (listener != null) {
                    listener.onSegmentViewClick(mStudyAdviser, 1);
                }
                refreshViews();
                break;
            case R.id.mine_setting:
                startActivity(new Intent(getActivity(),MySetting.class));
                break;
            case R.id.rl_mine_phone:
                new ActionSheetDialog(getActivity())
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem(phoneNumber, ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            public void onClick(int which) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                                startActivity(intent);//内部类
                            }
                        }).show();
                break;
            case R.id.tv_change_class:
                startActivity(new Intent(getActivity(),MyChangeActivity.class));
                break;
        }
    }

    private void refreshViews() {
        if(mShow) {
            mLlcouser.setVisibility(View.VISIBLE);
            mLlStudyAdviser.setVisibility(View.GONE);
            mCourseRate.setTextColor(Color.rgb(32, 166, 251));
            mStudyAdviser.setTextColor(Color.rgb(255, 255, 255));
            mCourseRate.setBackgroundResource(R.drawable.seg_left);
            mStudyAdviser.setBackgroundResource(R.drawable.seg_right);
            mCourseRate.setSelected(true);
            mStudyAdviser.setSelected(false);
        } else {
            mLlcouser.setVisibility(View.GONE);
            mLlStudyAdviser.setVisibility(View.VISIBLE);
            mCourseRate.setTextColor(Color.rgb(255, 255, 255));
            mStudyAdviser.setTextColor(Color.rgb(32, 166, 251));
            mCourseRate.setBackgroundResource(R.drawable.seg_left_one);
            mStudyAdviser.setBackgroundResource(R.drawable.seg_right_one);
            mStudyAdviser.setSelected(true);
            mCourseRate.setSelected(false);
        }
    }
    public static interface onSegmentViewClickListener {
        public void onSegmentViewClick(View v, int position);
    }

    public void setOnSegmentViewClickListener(onSegmentViewClickListener listener) {
        this.listener = listener;
    }
}
