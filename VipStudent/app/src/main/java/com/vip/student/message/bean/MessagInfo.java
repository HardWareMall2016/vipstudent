package com.vip.student.message.bean;

/**
 * Created by WuYue on 2015/12/16.
 */
public class MessagInfo {
    private long MessageID;
    private int MessageTypeCode;
    private String MessageTypeName;
    private String Title;
    private String Content;
    //private String BusId;
    private long CreateTime;
    private int Status;

    public void setMessageID(long MessageID) {
        this.MessageID = MessageID;
    }

    public void setMessageTypeCode(int MessageTypeCode) {
        this.MessageTypeCode = MessageTypeCode;
    }

    public void setMessageTypeName(String MessageTypeName) {
        this.MessageTypeName = MessageTypeName;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public void setCreateTime(long CreateTime) {
        this.CreateTime = CreateTime;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public long getMessageID() {
        return MessageID;
    }

    public int getMessageTypeCode() {
        return MessageTypeCode;
    }

    public String getMessageTypeName() {
        return MessageTypeName;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public int getStatus() {
        return Status;
    }
}
