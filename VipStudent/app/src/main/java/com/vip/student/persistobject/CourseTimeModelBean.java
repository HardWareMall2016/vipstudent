package com.vip.student.persistobject;

import java.io.Serializable;
/**
 * Created by WuYue on 2016/1/11.
 */
public class CourseTimeModelBean implements Serializable {
    /**
     * TeacherLevel : sample string 1
     * CourseTypeCode : sample string 2
     * CourseProductName : sample string 3
     * HandselLesson : 4
     * TotalCourseCnt : 5
     * FinishCourseCnt : 6
     */
    private String TeacherLevel;
    private String CourseTypeCode;
    private String CourseProductName;
    private int HandselLesson;
    private int TotalCourseCnt;
    private int FinishCourseCnt;

    public void setTeacherLevel(String TeacherLevel) {
        this.TeacherLevel = TeacherLevel;
    }

    public void setCourseTypeCode(String CourseTypeCode) {
        this.CourseTypeCode = CourseTypeCode;
    }

    public void setCourseProductName(String CourseProductName) {
        this.CourseProductName = CourseProductName;
    }

    public void setHandselLesson(int HandselLesson) {
        this.HandselLesson = HandselLesson;
    }

    public void setTotalCourseCnt(int TotalCourseCnt) {
        this.TotalCourseCnt = TotalCourseCnt;
    }

    public void setFinishCourseCnt(int FinishCourseCnt) {
        this.FinishCourseCnt = FinishCourseCnt;
    }

    public String getTeacherLevel() {
        return TeacherLevel;
    }

    public String getCourseTypeCode() {
        return CourseTypeCode;
    }

    public String getCourseProductName() {
        return CourseProductName;
    }

    public int getHandselLesson() {
        return HandselLesson;
    }

    public int getTotalCourseCnt() {
        return TotalCourseCnt;
    }

    public int getFinishCourseCnt() {
        return FinishCourseCnt;
    }
}
