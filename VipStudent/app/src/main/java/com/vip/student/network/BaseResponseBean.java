package com.vip.student.network;

public class BaseResponseBean {
    /**
     * Code : 0
     * Message :
     */
    private int Code;
    private String Message;

    public void setCode(int Code) {
        this.Code = Code;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getCode() {
        return Code;
    }

    public String getMessage() {
        return Message;
    }
}
