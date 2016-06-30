package com.vip.student.my.bean;

import com.vip.student.network.BaseRequestBean;


/**
 * Created by Administrator on 2015/12/18.
 */
public class ChangeListBean extends BaseRequestBean {
    /**
     * StudentId : sample string 1
     * StartDate : 2015-12-18 11:48:01
     * EndDate : 2015-12-18 11:48:01
     */

    private DataEntity Data;

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public DataEntity getData() {
        return Data;
    }

    public static class DataEntity {
        private String StudentId;
        private String StartDate;
        private String EndDate;

        public void setStudentId(String StudentId) {
            this.StudentId = StudentId;
        }

        public void setStartDate(String StartDate) {
            this.StartDate = StartDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public String getStudentId() {
            return StudentId;
        }

        public String getStartDate() {
            return StartDate;
        }

        public String getEndDate() {
            return EndDate;
        }
    }
}
