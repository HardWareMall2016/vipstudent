package com.vip.student.my.bean;

import com.vip.student.network.BaseResponseBean;

/**
 * Created by Administrator on 2015/12/18.
 */
public class SubmitBean extends BaseResponseBean {
    /**
     * Data : true
     */

    private boolean Data;

    public void setData(boolean Data) {
        this.Data = Data;
    }

    public boolean isData() {
        return Data;
    }
}
