package com.vip.student.my.bean;

import com.vip.student.network.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
public class GetChangeLessonRequestBean extends BaseResponseBean {

    /**
     * CourseTypeCode : test0
     * CourseTypeName : test1
     * CourseSubTypeCode : test2
     * CourseSubTypeName : test3
     * OldLessonTime : 2015-08-24 17:09:56
     * LessonChangeStatus : test5
     * NewLessonTime : 2015-08-24 17:09:56
     * ChangeReason : test7
     * CreateTime : 2015-08-24 17:09:56
     */

    private List<DataEntity> Data;

    public void setData(List<DataEntity> Data) {
        this.Data = Data;
    }

    public List<DataEntity> getData() {
        return Data;
    }

    public static class DataEntity {
        private String CourseTypeCode;
        private String CourseTypeName;
        private String CourseSubTypeCode;
        private String CourseSubTypeName;
        private String OldLessonTime;
        private String LessonChangeStatus;
        private String NewLessonTime;
        private String ChangeReason;
        private String CreateTime;

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

        public void setOldLessonTime(String OldLessonTime) {
            this.OldLessonTime = OldLessonTime;
        }

        public void setLessonChangeStatus(String LessonChangeStatus) {
            this.LessonChangeStatus = LessonChangeStatus;
        }

        public void setNewLessonTime(String NewLessonTime) {
            this.NewLessonTime = NewLessonTime;
        }

        public void setChangeReason(String ChangeReason) {
            this.ChangeReason = ChangeReason;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
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

        public String getOldLessonTime() {
            return OldLessonTime;
        }

        public String getLessonChangeStatus() {
            return LessonChangeStatus;
        }

        public String getNewLessonTime() {
            return NewLessonTime;
        }

        public String getChangeReason() {
            return ChangeReason;
        }

        public String getCreateTime() {
            return CreateTime;
        }
    }
}
