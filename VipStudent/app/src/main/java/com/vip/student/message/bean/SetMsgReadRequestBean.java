package com.vip.student.message.bean;

import com.vip.student.network.BaseRequestBean;

import java.util.List;

/**
 * Created by WuYue on 2015/12/14.
 */
public class SetMsgReadRequestBean extends BaseRequestBean {

    /**
     * Data : [1,2,3]
     */

    private List<Integer> Data;

    public void setData(List<Integer> Data) {
        this.Data = Data;
    }

    public List<Integer> getData() {
        return Data;
    }
}
