package com.vip.student.persistobject;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Wuyue on 2015/10/14.
 */
public class UserInfo extends BasePersistObject {
    private String Token;
    private String UserId;
    private String StudentName;
    private String StudentQQ;
    private String MobilePhone;
    /*private String FirstLessonTime;
      private String LasterLessonTime;
      private String ScoreNextDate;*/

    private long FirstLessonTime;
    private long LasterLessonTime;
    private long ScoreNextDate;
    //学习顾问名
    private String StudyConsultantName;
    //学习顾问英文名称
    private String StudyConsultantEName;
    //学习顾问电话号码
    private String StudyConsultantTel;
    //课程天数
    private int StudentDays;
    //总课时数
    private int TotalCourseCnt;
    //完成课时数
    private int FinishCourseCnt;
    //教师联系方式
    private String TeacherGroupJsonStr;
    //课程状态
    private List<CourseTimeModelBean> courseTimeModels;
    private String StudyConsultantNameSex ;
    public String getStudyConsultantNameSex() {
        return StudyConsultantNameSex;
    }

    public void setStudyConsultantNameSex(String studyConsultantNameSex) {
        StudyConsultantNameSex = studyConsultantNameSex;
    }


    private boolean isLogined=false;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentQQ() {
        return StudentQQ;
    }

    public void setStudentQQ(String studentQQ) {
        StudentQQ = studentQQ;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

    public String getStudyConsultantName() {
        return StudyConsultantName;
    }

    public void setStudyConsultantName(String studyConsultantName) {
        StudyConsultantName = studyConsultantName;
    }

    public String getStudyConsultantEName() {
        return StudyConsultantEName;
    }

    public void setStudyConsultantEName(String studyConsultantEName) {
        StudyConsultantEName = studyConsultantEName;
    }

    public String getStudyConsultantTel() {
        return StudyConsultantTel;
    }

    public void setStudyConsultantTel(String studyConsultantTel) {
        StudyConsultantTel = studyConsultantTel;
    }

    public int getStudentDays() {
        return StudentDays;
    }

    public void setStudentDays(int studentDays) {
        StudentDays = studentDays;
    }

    public int getTotalCourseCnt() {
        return TotalCourseCnt;
    }

    public void setTotalCourseCnt(int totalCourseCnt) {
        TotalCourseCnt = totalCourseCnt;
    }

    public int getFinishCourseCnt() {
        return FinishCourseCnt;
    }

    public void setFinishCourseCnt(int finishCourseCnt) {
        FinishCourseCnt = finishCourseCnt;
    }

    public String getTeacherGroupJsonStr() {
        return TeacherGroupJsonStr;
    }

    public void setTeacherGroupJsonStr(String teacherGroupJsonStr) {
        TeacherGroupJsonStr = teacherGroupJsonStr;
    }

    public List<CourseTimeModelBean> getCourseTimeModels() {
        return courseTimeModels;
    }

    public void setCourseTimeModels(List<CourseTimeModelBean> courseTimeModels) {
        this.courseTimeModels = courseTimeModels;
    }

    public long getFirstLessonTime() {
        return FirstLessonTime;
    }

    public void setFirstLessonTime(long firstLessonTime) {
        FirstLessonTime = firstLessonTime;
    }

    public long getLasterLessonTime() {
        return LasterLessonTime;
    }

    public void setLasterLessonTime(long lasterLessonTime) {
        LasterLessonTime = lasterLessonTime;
    }

    public long getScoreNextDate() {
        return ScoreNextDate;
    }

    public void setScoreNextDate(long scoreNextDate) {
        ScoreNextDate = scoreNextDate;
    }

    public boolean isLogined() {
        return isLogined;
    }

    public void setIsLogined(boolean isLogined) {
        this.isLogined = isLogined;
    }

    /***
     * 获取教师信息
     * @return
     */
    public TeacherGroupBean getTeacherGroup(){
        if(TextUtils.isEmpty(TeacherGroupJsonStr)){
            return null;
        }
        Gson gson=new Gson();
        TeacherGroupBean teacherGroup= gson.fromJson(TeacherGroupJsonStr, TeacherGroupBean.class);
        return teacherGroup;
    }

    private static UserInfo sUserInfo=null;

    public static UserInfo getCurrentUser() {
        if(sUserInfo==null){
            sUserInfo=getPersisObject(UserInfo.class);
        }
        return sUserInfo;
    }

    public static void saveLoginUserInfo(UserInfo user) {
        //如果登录的不是同一个用户就清除相关数据
        if(getCurrentUser()==null||!getCurrentUser().getUserId().equals(user.getUserId())){
            PushMessageInfo.clearPushMessageInfo();
        }
        persisObject(user);
        sUserInfo=user;
    }

    public static void logout() {
        if(sUserInfo!=null){
            sUserInfo.setIsLogined(false);
            persisObject(sUserInfo);
        }
        //PushMessageInfo.clearPushMessageInfo();
        /*deletePersistObject(UserInfo.class);
        sUserInfo=null;*/
    }
}
