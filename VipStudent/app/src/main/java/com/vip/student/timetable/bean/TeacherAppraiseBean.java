package com.vip.student.timetable.bean;

import com.vip.student.network.BaseResponseBean;

/**
 * Created by Administrator on 2015/12/21.
 */
public class TeacherAppraiseBean extends BaseResponseBean {
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
