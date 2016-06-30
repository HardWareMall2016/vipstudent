package com.vip.student.my.bean;

import com.vip.student.network.BaseResponseBean;
import com.vip.student.utils.Utils;

import org.aisen.orm.annotation.PrimaryKey;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/18.
 */
public class ChangeListRequestBean extends BaseResponseBean {
    /**
     * LessonId : sample string 1
     * TeacherId : sample string 2
     * TeacherName : sample string 3
     * StudentGuid : sample string 4
     * StudentName : sample string 5
     * StudentSex : sample string 6
     * TeacherEName : sample string 7
     * TeacherQQ : sample string 8
     * StartTime : 2015-12-18 10:46:44
     * EndTime : 2015-12-18 10:46:44
     * CourseTypeCode : sample string 11
     * CourseTypeName : sample string 12
     * CourseSubTypeCode : sample string 13
     * CourseSubTypeName : sample string 14
     * CourseOnlineUrl : sample string 15
     * CourseProcess : 16
     * IsOnline : 17
     * IsAppraise : 18
     * ResourseDownLoadUrl : sample string 19
     * IsChangeLesson : 20
     */

    private List<DataEntity> Data;

    public void setData(List<DataEntity> Data) {
        this.Data = Data;
    }

    public List<DataEntity> getData() {
        return Data;
    }

    public static class DataEntity implements Serializable{
        @PrimaryKey(column = "LessonId")
        private String LessonId;
        private String TeacherId;
        private String TeacherName;
        private String StudentGuid;
        private String StudentName;
        private String StudentSex;
        private String TeacherEName;
        private String TeacherQQ;
        private String StartTime;
        private String EndTime;
        private String CourseTypeCode;
        private String CourseTypeName;
        private String CourseSubTypeCode;
        private String CourseSubTypeName;
        private String CourseOnlineUrl;
        private int CourseProcess;
        private int IsOnline;
        private int IsAppraise;
        private String ResourseDownLoadUrl;
        private int IsChangeLesson;


        public void setLessonId(String LessonId) {
            this.LessonId = LessonId;
        }

        public void setTeacherId(String TeacherId) {
            this.TeacherId = TeacherId;
        }

        public void setTeacherName(String TeacherName) {
            this.TeacherName = TeacherName;
        }

        public void setStudentGuid(String StudentGuid) {
            this.StudentGuid = StudentGuid;
        }

        public void setStudentName(String StudentName) {
            this.StudentName = StudentName;
        }

        public void setStudentSex(String StudentSex) {
            this.StudentSex = StudentSex;
        }

        public void setTeacherEName(String TeacherEName) {
            this.TeacherEName = TeacherEName;
        }

        public void setTeacherQQ(String TeacherQQ) {
            this.TeacherQQ = TeacherQQ;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public void setCourseTypeCode(String CourseTypeCode) {
            this.CourseTypeCode = CourseTypeCode;
        }

        public void setCourseTypeName(String CourseTypeName) {
            this.CourseTypeName = CourseTypeName;
        }

        public void setCourseSubTypeCode(String CourseSubTypeCode) {
            this.CourseSubTypeCode = CourseSubTypeCode;
        }

        public void setCourseSubTypeName(String CourseSubTypeName) {
            this.CourseSubTypeName = CourseSubTypeName;
        }

        public void setCourseOnlineUrl(String CourseOnlineUrl) {
            this.CourseOnlineUrl = CourseOnlineUrl;
        }

        public void setCourseProcess(int CourseProcess) {
            this.CourseProcess = CourseProcess;
        }

        public void setIsOnline(int IsOnline) {
            this.IsOnline = IsOnline;
        }

        public void setIsAppraise(int IsAppraise) {
            this.IsAppraise = IsAppraise;
        }

        public void setResourseDownLoadUrl(String ResourseDownLoadUrl) {
            this.ResourseDownLoadUrl = ResourseDownLoadUrl;
        }

        public void setIsChangeLesson(int IsChangeLesson) {
            this.IsChangeLesson = IsChangeLesson;
        }

        public String getLessonId() {
            return LessonId;
        }

        public String getTeacherId() {
            return TeacherId;
        }

        public String getTeacherName() {
            return TeacherName;
        }

        public String getStudentGuid() {
            return StudentGuid;
        }

        public String getStudentName() {
            return StudentName;
        }

        public String getStudentSex() {
            return StudentSex;
        }

        public String getTeacherEName() {
            return TeacherEName;
        }

        public String getTeacherQQ() {
            return TeacherQQ;
        }

        public String getStartTime() {
            return StartTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public String getCourseTypeCode() {
            return CourseTypeCode;
        }

        public String getCourseTypeName() {
            return CourseTypeName;
        }

        public String getCourseSubTypeCode() {
            return CourseSubTypeCode;
        }

        public String getCourseSubTypeName() {
            return CourseSubTypeName;
        }

        public String getCourseOnlineUrl() {
            return CourseOnlineUrl;
        }

        public int getCourseProcess() {
            return CourseProcess;
        }

        public int getIsOnline() {
            return IsOnline;
        }

        public int getIsAppraise() {
            return IsAppraise;
        }

        public String getResourseDownLoadUrl() {
            return ResourseDownLoadUrl;
        }

        public int getIsChangeLesson() {
            return IsChangeLesson;
        }
    }
}
