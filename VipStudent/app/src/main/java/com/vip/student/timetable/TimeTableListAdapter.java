package com.vip.student.timetable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vip.student.R;
import com.vip.student.my.bean.ChangeListRequestBean;
import com.vip.student.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Qs on 15/12/23.
 */
public class TimeTableListAdapter extends BaseAdapter implements View.OnClickListener {
    SimpleDateFormat sim = new SimpleDateFormat("MM 月 dd 日");
    SimpleDateFormat sim_HH_mm = new SimpleDateFormat("HH:mm");

    private Context context;

    private Fragment fragment;

    private List<ChangeListRequestBean.DataEntity> list = new ArrayList();

    public TimeTableListAdapter(Context context, Fragment fragment, List<ChangeListRequestBean.DataEntity> data) {
        this.context = context;
        this.list = data;
        this.fragment = fragment;
        shortListByTime(this.list);
    }

    public TimeTableListAdapter(Context context, List<ChangeListRequestBean.DataEntity> list) {
        this.context = context;
        this.list = list;
        shortListByTime(this.list);
    }
    public void setAppraise(String lessonid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLessonId().equals(lessonid)) {
                list.get(i).setIsAppraise(1);
                notifyDataSetChanged();
                return;
            }
        }
    }

    public List<ChangeListRequestBean.DataEntity> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_timetable, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        convertView.setId(position);
//        viewHolder.btn.setTag(position);
//        viewHolder.btn.setOnClickListener(this);
        ChangeListRequestBean.DataEntity dataEntity = list.get(position);

        Date startDate = new Date(Utils.parseServerTime(dataEntity.getStartTime()));
        Date endDate = new Date(Utils.parseServerTime(dataEntity.getEndTime()));

        viewHolder.textView1.setText(sim.format(startDate) + " " + Utils.getWeekOfDate(startDate));
        viewHolder.textView2.setText("时间 : " + sim_HH_mm.format(startDate) + "-" + sim_HH_mm.format(endDate));
        viewHolder.textView3.setText("教师 : " + dataEntity.getTeacherName());


        String courseSubTypeName = dataEntity.getCourseSubTypeName();
        if (courseSubTypeName.equals("口语")) {
            viewHolder.img.setImageResource(R.mipmap.timetable_kouyu);
        } else if (courseSubTypeName.equals("数学")) {
            viewHolder.img.setImageResource(R.mipmap.timetable_shuxue);
        } else if (courseSubTypeName.equals("写作")) {
            viewHolder.img.setImageResource(R.mipmap.timetable_xiezuo);
        } else if (courseSubTypeName.equals("听力")) {
            viewHolder.img.setImageResource(R.mipmap.timetable_tingli);
        } else if (courseSubTypeName.equals("阅读")) {
            viewHolder.img.setImageResource(R.mipmap.timetable_yuedu);

        } else {
            viewHolder.img.setImageResource(R.mipmap.timetable_kexue);
        }
//        viewHolder.img.se
        ViewParent parent1 = viewHolder.img.getParent().getParent();
        if (position % 2 == 0) {
            if (parent1 != null) {
                View view = (View) parent1;
                view.setBackgroundColor(Color.parseColor("#4db8fc"));
            }
//            viewHolder.img.setBackgroundColor(Color.parseColor("#4db8fc"));
            viewHolder.layoutcontent.setBackgroundColor(convertView.getResources().getColor(android.R.color.white));
        } else {
            if (parent1 != null) {
                View view = (View) parent1;
                view.setBackgroundColor(Color.parseColor("#1b9def"));
            }
//            viewHolder.img.setBackgroundColor(Color.parseColor("#1b9def"));
            viewHolder.layoutcontent.setBackgroundColor(Color.parseColor("#e3f4ff"));
        }

        final View finalConvertView = convertView;
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalConvertView.performClick();
            }
        });
        if (dataEntity.getIsAppraise() == 1) {
            viewHolder.btn.setText("已评价");
            viewHolder.btn.setTextColor(Color.parseColor("#aaaaaa"));
            viewHolder.btn.setSelected(false);
            viewHolder.btn.setEnabled(false);
            convertView.setOnClickListener(null);
            viewHolder.btn.setClickable(false);

        } else {
            if (new Date().before(endDate)) {
                convertView.setOnClickListener(null);
                viewHolder.btn.setClickable(false);
                viewHolder.btn.setEnabled(true);
                viewHolder.btn.setSelected(false);
                viewHolder.btn.setTextColor(Color.parseColor("#ff0099cc"));
                viewHolder.btn.setText("待上课");
            }else{
                viewHolder.btn.setText("待评价");
                convertView.setOnClickListener(this);
                viewHolder.btn.setTextColor(Color.parseColor("#ff4c4c"));
                viewHolder.btn.setEnabled(true);
                viewHolder.btn.setClickable(true);
                viewHolder.btn.setSelected(true);
            }
        }

        String s = dataEntity.getCourseTypeName() + dataEntity.getCourseSubTypeName();
        viewHolder.type_title.setText(s);

        return convertView;
    }

    public static List shortListByTime(List<ChangeListRequestBean.DataEntity> list) {
        if (list == null) return null;
        Collections.sort(list, new Comparator<ChangeListRequestBean.DataEntity>() {
            @Override
            public int compare(ChangeListRequestBean.DataEntity lhs, ChangeListRequestBean.DataEntity rhs) {
                return compare(Utils.parseServerTime(lhs.getStartTime()), Utils.parseServerTime(rhs.getStartTime()));
            }

            public int compare(long lhs, long rhs) {
                return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
            }
        });
        return list;
    }

    /**
     * 传入日期,返回当前日期的第一条position
     *
     * @param date
     * @return 无匹配返回-1; else 直接跳转条目
     */
    public int getDatePosition(Date date) {
        if (date != null && list != null)
            for (int i = 0; i < list.size(); i++) {
                ChangeListRequestBean.DataEntity entity = list.get(i);
                Date itemdate = new Date(Utils.parseServerTime(entity.getStartTime()));
                if (itemdate.getYear() == date.getYear() && itemdate.getMonth() == date.getMonth() && itemdate.getDate() == date.getDate()) {
                    return i;
                }
            }
        return -1;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), TeacherAppraiseActivity.class);
        ChangeListRequestBean.DataEntity dataEntity = list.get(v.getId());
        intent.putExtra(TeacherAppraiseActivity.EXT_KEY_DATA, dataEntity.getStartTime().split(" ")[0]);
        intent.putExtra(TeacherAppraiseActivity.EXT_KEY_LESSONID, dataEntity.getLessonId());
        intent.putExtra(TeacherAppraiseActivity.EXT_KEY_LESSON, dataEntity.getCourseTypeName() + dataEntity.getCourseSubTypeName());
        intent.putExtra(TeacherAppraiseActivity.EXT_KEY_TEACHER, dataEntity.getTeacherName());
        Date startDate = new Date(Utils.parseServerTime(dataEntity.getStartTime()));
        Date endDate = new Date(Utils.parseServerTime(dataEntity.getEndTime()));
        intent.putExtra(TeacherAppraiseActivity.EXT_KEY_TIME, sim_HH_mm.format(startDate) + "-" + sim_HH_mm.format(endDate));
//        v.getContext().startActivity(intent);
        fragment.startActivityForResult(intent, 12345);
    }

    public class ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView type_title;

        public ImageView img;

        public Button btn;

        public View layoutcontent;

        public ViewHolder(View view) {
            layoutcontent = view.findViewById(R.id.Layout_content);
            textView1 = (TextView) view.findViewById(R.id.textView1);
            textView2 = (TextView) view.findViewById(R.id.textView2);
            textView3 = (TextView) view.findViewById(R.id.textView3);
            type_title = (TextView) view.findViewById(R.id.Type_Title);
            btn = (Button) view.findViewById(R.id.pingjia);
            img = (ImageView) view.findViewById(R.id.item_img);
        }
    }
}
