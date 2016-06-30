package com.vip.student.network;

import com.vip.student.persistobject.UserInfo;

/**
 * Created by WuYue on 2015/12/10.
 */
public class BaseRequestBean {

    public BaseRequestBean(){
        if(UserInfo.getCurrentUser()!=null){
            setUserId(UserInfo.getCurrentUser().getUserId());
            setToken(UserInfo.getCurrentUser().getToken());
        }
    }

    /**
     * Token :
     * Data : {}
     * UserId :
     */

    private String Token;
    private String UserId;

    public void setToken(String Token) {
        this.Token = Token;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getToken() {
        return Token;
    }

    public String getUserId() {
        return UserId;
    }
}
