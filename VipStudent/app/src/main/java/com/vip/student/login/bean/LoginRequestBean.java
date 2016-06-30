package com.vip.student.login.bean;

import com.vip.student.network.BaseRequestBean;

/**
 * Created by WuYue on 2015/12/10.
 */
public class LoginRequestBean extends BaseRequestBean {

    /**
     * Data : {"UserName":"sample string 1","PassWorde":"sample string 2"}
     */

    private DataEntity Data;

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public DataEntity getData() {
        return Data;
    }

    public static class DataEntity {
        /**
         * UserName : sample string 1
         * PassWorde : sample string 2
         */

        private String UserName;
        private String PassWorde;

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public void setPassWorde(String PassWorde) {
            this.PassWorde = PassWorde;
        }

        public String getUserName() {
            return UserName;
        }

        public String getPassWorde() {
            return PassWorde;
        }
    }
}
