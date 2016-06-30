package com.vip.student.my.bean;

import com.vip.student.network.BaseRequestBean;

/**
 * Created by Administrator on 2015/12/18.
 */
public class SubmitRequestBean extends BaseRequestBean {

    /**
     * StudentID : 135
     * LessonID : 123
     * ChangeReason : test2
     * ChangedDateMemo : test3 2015-7-7
     */

    private DataEntity Data;

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public DataEntity getData() {
        return Data;
    }

    public static class DataEntity {
        private String StudentID;
        private String LessonID;
        private String ChangeReason;
        private String ChangedDateMemo;

        public void setStudentID(String StudentID) {
            this.StudentID = StudentID;
        }

        public void setLessonID(String LessonID) {
            this.LessonID = LessonID;
        }

        public void setChangeReason(String ChangeReason) {
            this.ChangeReason = ChangeReason;
        }

        public void setChangedDateMemo(String ChangedDateMemo) {
            this.ChangedDateMemo = ChangedDateMemo;
        }

        public String getStudentID() {
            return StudentID;
        }

        public String getLessonID() {
            return LessonID;
        }

        public String getChangeReason() {
            return ChangeReason;
        }

        public String getChangedDateMemo() {
            return ChangedDateMemo;
        }
    }
}
