package com.vip.student.my.bean;

import com.vip.student.network.BaseRequestBean;

/**
 * Created by Administrator on 2016/1/18.
 */
public class ChangeLessonBean extends BaseRequestBean {

    /**
     * Data :
     */

    private String Data = "";

    public void setData(String Data) {
        this.Data = Data;
    }

    public String getData() {
        return Data;
    }
}
