package com.vip.student.my.bean;

import com.vip.student.network.BaseRequestBean;

/**
 * Created by Administrator on 2015/12/11.
 */
public class ModifyCodeRequestBean extends BaseRequestBean {

    /**
     * OldPassword : sample string 1
     * NewPassword : sample string 2
     */

    private DataEntity Data;

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public DataEntity getData() {
        return Data;
    }

    public static class DataEntity {
        private String OldPassword;
        private String NewPassword;

        public void setOldPassword(String OldPassword) {
            this.OldPassword = OldPassword;
        }

        public void setNewPassword(String NewPassword) {
            this.NewPassword = NewPassword;
        }

        public String getOldPassword() {
            return OldPassword;
        }

        public String getNewPassword() {
            return NewPassword;
        }
    }
}
