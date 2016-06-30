package com.vip.student.my.bean;

import com.vip.student.network.BaseResponseBean;

/**
 * Created by Administrator on 2016/1/4.
 */
public class PromptTimeBean extends BaseResponseBean {

    /**
     * id : 1
     * guid : 1111
     * createdate : 2015-12-14 15:37:32
     * updatedate : 2015-12-30 10:02:15
     * type : mobile
     * state : 1,2
     */

    private DataEntity Data;

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public DataEntity getData() {
        return Data;
    }

    public static class DataEntity {
        private int id;
        private String guid;
        private String createdate;
        private String updatedate;
        private String type;
        private String state;

        public void setId(int id) {
            this.id = id;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public void setUpdatedate(String updatedate) {
            this.updatedate = updatedate;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getId() {
            return id;
        }

        public String getGuid() {
            return guid;
        }

        public String getCreatedate() {
            return createdate;
        }

        public String getUpdatedate() {
            return updatedate;
        }

        public String getType() {
            return type;
        }

        public String getState() {
            return state;
        }
    }
}
