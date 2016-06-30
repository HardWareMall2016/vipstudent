package com.vip.student.db.dbobject;

/**
 * Created by WuYue on 2015/12/16.
 */
public class Message extends BaseData{
    public long MessageID;
    public int MessageTypeCode;
    public String MessageTypeName;
    public String Title;
    public String Content;
    public long CreateTime;
    public int Status;

    public final static int MSG_TYPE_SYS=0;//系统消息
    public final static int MSG_TYPE_LEAVE_MSG=1;//留言
    public final static int MSG_TYPE_NOTIFY=2;//调课申请
}
