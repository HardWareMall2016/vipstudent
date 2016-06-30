package com.vip.student.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vip.student.R;
import com.vip.student.base.BaseActivity;
import com.vip.student.network.ApiUrls;
import com.vip.student.network.BaseHttpRequestHandler;
import com.vip.student.timetable.bean.TeacherAppraiseBean;
import com.vip.student.timetable.bean.TeacherAppraiseRequestBean;
import com.vip.student.utils.Utils;
import com.vip.student.widget.RatingBar;

/**
 * Created by Administrator on 2015/12/21.
 */
public class TeacherAppraiseActivity extends BaseActivity implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    public static final String EXT_KEY_DATA = "teacherAppraiseDate";
    public static final String EXT_KEY_TIME = "teacherAppraiseTime";
    public static final String EXT_KEY_LESSON = "teacherAppraiseLesson";
    public static final String EXT_KEY_TEACHER = "teacherAppraiseTeacher";
    public static final String EXT_KEY_LESSONID = "lessonId";

    private RatingBar mPrepareLessonBar;
    private RatingBar mCommunicateBar;
    private RatingBar mGiveLessonBar;
    private RatingBar messageBar;

    private EditText mSuggestion;
    private EditText mETOtherReason;
    private Button mSubmit;
    private RadioGroup mRadioGroup;
    private RadioGroup mRadioGroup_no;
    private RadioButton mYesButton;
    private RadioButton mNoButton;
    private RadioButton mLateButton;
    private RadioButton mNolessonButton;
    private RadioButton mChangeClassButton;
    private RadioButton mOtherButton;

    private LinearLayout mLinearLayoutNoReson;
    private LinearLayout mLinearLayoutApraise;

    private TextView mData;
    private TextView mTime;
    private TextView mLesson;
    private TextView mTeacher;

    private String PrepareLessonBar = "5";
    private String CommunicatBar = "5";
    private String GiveLessonBar = "5";
    private String MessageBar = "5";

    private boolean isSuccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_appraise);

        initView();
    }

    private void initView() {
        mLinearLayoutApraise = (LinearLayout) findViewById(R.id.teacher_appraise_yindao);
        mPrepareLessonBar = (RatingBar) findViewById(R.id.ratingbar_prepare_lesson);
        mPrepareLessonBar.setOnRatingBarChangeListener(this);
        mCommunicateBar = (RatingBar) findViewById(R.id.ratingbar_communicate);
        mCommunicateBar.setOnRatingBarChangeListener(this);
        mGiveLessonBar = (RatingBar) findViewById(R.id.ratingbar_give_lesson);
        mGiveLessonBar.setOnRatingBarChangeListener(this);
        messageBar = (RatingBar) findViewById(R.id.ratingbar_message);
        messageBar.setOnRatingBarChangeListener(this);
        mSuggestion = (EditText) findViewById(R.id.teacher_appraise_suggestion);
        mSubmit = (Button) findViewById(R.id.teacher_appraise_submit);
        mSubmit.setOnClickListener(this);

        mData = (TextView) findViewById(R.id.teacher_appraise_data);

        mData.setText(getIntent().getStringExtra(EXT_KEY_DATA));
        mTime = (TextView) findViewById(R.id.teacher_appraise_time);
        mTime.setText(getIntent().getStringExtra(EXT_KEY_TIME));
        mLesson = (TextView) findViewById(R.id.teacher_appraise_lesson);
        mLesson.setText(getIntent().getStringExtra(EXT_KEY_LESSON));
        mTeacher = (TextView) findViewById(R.id.teacher_appraise_teacher);
        mTeacher.setText(getIntent().getStringExtra(EXT_KEY_TEACHER));


        mLinearLayoutNoReson = (LinearLayout) findViewById(R.id.teacher_appraise_no_reson);
        mYesButton = (RadioButton) findViewById(R.id.teacher_appraise_yes);
        mNoButton = (RadioButton) findViewById(R.id.teacher_appraise_no);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup_yes_no);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.teacher_appraise_yes) {
                    mLinearLayoutNoReson.setVisibility(View.GONE);
                } else {
                    mLinearLayoutNoReson.setVisibility(View.VISIBLE);
                }
            }
        });

        mETOtherReason = (EditText) findViewById(R.id.et_otherreason);
        mLateButton = (RadioButton) findViewById(R.id.reason_late);
        mNolessonButton = (RadioButton) findViewById(R.id.reason_nolesson);
        mChangeClassButton = (RadioButton) findViewById(R.id.reason_changeclass);
        mOtherButton = (RadioButton) findViewById(R.id.reason_other);
        mRadioGroup_no = (RadioGroup) findViewById(R.id.radioGroup_no);
        mRadioGroup_no.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.reason_other) {
                    mETOtherReason.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.reason_late) {
                    mETOtherReason.setVisibility(View.GONE);
                    mETOtherReason.setText("");
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.reason_nolesson) {
                    mETOtherReason.setVisibility(View.GONE);
                    mETOtherReason.setText("");
                } else {
                    mETOtherReason.setVisibility(View.GONE);
                    mETOtherReason.setText("");
                }
            }
        });
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean byUser) {
        if (byUser) {
            switch (ratingBar.getId()) {
                case R.id.ratingbar_prepare_lesson:
                    mPrepareLessonBar.setRating(rating);
                    PrepareLessonBar = (int) rating + "";
                    if (PrepareLessonBar.equals("5")) {
                        mLinearLayoutApraise.setVisibility(View.GONE);
                        mCommunicateBar.setRating(5);
                        CommunicatBar = "5";
                        mGiveLessonBar.setRating(5);
                        GiveLessonBar = "5";
                        messageBar.setRating(5);
                        MessageBar = "5";
                    } else {
                        mLinearLayoutApraise.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.ratingbar_communicate:
                    mCommunicateBar.setRating(rating);
                    CommunicatBar = (int) rating + "";
                    break;
                case R.id.ratingbar_give_lesson:
                    mGiveLessonBar.setRating(rating);
                    GiveLessonBar = (int) rating + "";
                    break;
                case R.id.ratingbar_message:
                    messageBar.setRating(rating);
                    MessageBar = (int) rating + "";
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.teacher_appraise_submit:
                if (!verify()) {
                    return;
                }
                submit();
                break;
        }
    }

    private void submit() {
        String noFinishReason = "";
        if (mRadioGroup.getCheckedRadioButtonId() == R.id.teacher_appraise_no) {
            switch (mRadioGroup_no.getCheckedRadioButtonId()) {
                case R.id.reason_late:
                case R.id.reason_nolesson:
                case R.id.reason_changeclass:
                    RadioButton reasonRb = (RadioButton) findViewById(mRadioGroup_no.getCheckedRadioButtonId());
                    noFinishReason = reasonRb.getText().toString();
                    break;
                case R.id.reason_other:
                    noFinishReason = mETOtherReason.getText().toString();
                    break;
            }
        }

        TeacherAppraiseRequestBean request = new TeacherAppraiseRequestBean();
        final TeacherAppraiseRequestBean.DataEntity data = new TeacherAppraiseRequestBean.DataEntity();
        data.setLessonId(getIntent().getStringExtra(EXT_KEY_LESSONID));
        data.setIsFinishClass(String.valueOf((mRadioGroup.getCheckedRadioButtonId() == R.id.teacher_appraise_yes)));
        data.setNoFinishReason(noFinishReason);
        data.setNetworkQuality((int) mPrepareLessonBar.getRating() + "");
        data.setPreparLesson((int) mCommunicateBar.getRating() + "");
        data.setCommunication((int) mGiveLessonBar.getRating() + "");
        data.setTeachingAbility((int) messageBar.getRating() + "");
        data.setAppraiseDetail(mSuggestion.getText().toString());
        request.setData(data);
        showRotateProgressDialog(getString(R.string.change_lesson_submiting), true);
        startRequest(ApiUrls.SETTEACHERAPPRAISER, request, new BaseHttpRequestHandler() {
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
                TeacherAppraiseBean bean = Utils.parseJsonTostError(content, TeacherAppraiseBean.class);
                if (bean != null) {
                    if (bean.isData()) {
                        Utils.toast(R.string.teacher_appraise_thanks);
                        isSuccess = true;
        if (isSuccess) {
            Intent intent = new Intent();
            intent.putExtra(EXT_KEY_LESSONID, getIntent().getStringExtra(EXT_KEY_LESSONID));
            setResult(RESULT_OK,intent);
        }
                        finish();
                    }
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean verify() {
        //课程完成:否
        if (mRadioGroup.getCheckedRadioButtonId() == R.id.teacher_appraise_no) {
            switch (mRadioGroup_no.getCheckedRadioButtonId()) {
                case R.id.reason_late:
                case R.id.reason_nolesson:
                case R.id.reason_changeclass:
                    break;
                case R.id.reason_other:
                    if (TextUtils.isEmpty(mETOtherReason.getText().toString())) {
                        Utils.toast(R.string.no_teacher_appraise_other_reason);
                        return false;
                    }
                    break;
                default:
                    Utils.toast(R.string.no_teacher_appraise_no_reason);
                    return false;
            }
        }
        return true;
    }
}
