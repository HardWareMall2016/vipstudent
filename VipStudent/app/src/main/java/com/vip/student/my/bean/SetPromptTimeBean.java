package com.vip.student.my.bean;

import com.vip.student.network.BaseResponseBean;

/**
 * Created by Administrator on 2016/1/4.
 */
public class SetPromptTimeBean extends BaseResponseBean {
    /**
     * Data :
     */

    private String Data;

    public void setData(String Data) {
        this.Data = Data;
    }

    public String getData() {
        return Data;
    }
}
