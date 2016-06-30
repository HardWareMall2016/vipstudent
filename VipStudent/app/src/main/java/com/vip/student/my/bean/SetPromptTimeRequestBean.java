package com.vip.student.my.bean;

import com.vip.student.network.BaseRequestBean;

/**
 * Created by Administrator on 2016/1/4.
 */
public class SetPromptTimeRequestBean extends BaseRequestBean {
    /**
     * Data : sample string 2
     */

    private String Data;

    public void setData(String Data) {
        this.Data = Data;
    }

    public String getData() {
        return Data;
    }
}
