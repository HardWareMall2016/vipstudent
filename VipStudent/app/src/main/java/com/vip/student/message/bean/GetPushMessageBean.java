package com.vip.student.message.bean;

import com.vip.student.network.BaseResponseBean;

import java.util.List;

/**
 * Created by WuYue on 2016/1/19.
 */
public class GetPushMessageBean extends BaseResponseBean {

    /**
     * id : 1
     * UserGuid : sample string 2
     * MessageTypeCode : sample string 3
     * MessageTypeName : sample string 4
     * Title : sample string 5
     * Content : sample string 6
     * CreateTime : 2016-01-19 10:30:13
     * NeedSendTime : 2016-01-19 10:30:13
     * Status : sample string 7
     * Type : sample string 8
     */

    private List<DataEntity> Data;

    public void setData(List<DataEntity> Data) {
        this.Data = Data;
    }

    public List<DataEntity> getData() {
        return Data;
    }

    public static class DataEntity {
        private int id;
        private String UserGuid;
        private String MessageTypeCode;
        private String MessageTypeName;
        private String Title;
        private String Content;
        private String CreateTime;
        private String NeedSendTime;
        private String Status;
        private String Type;

        public void setId(int id) {
            this.id = id;
        }

        public void setUserGuid(String UserGuid) {
            this.UserGuid = UserGuid;
        }

        public void setMessageTypeCode(String MessageTypeCode) {
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

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public void setNeedSendTime(String NeedSendTime) {
            this.NeedSendTime = NeedSendTime;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public int getId() {
            return id;
        }

        public String getUserGuid() {
            return UserGuid;
        }

        public String getMessageTypeCode() {
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

        public String getCreateTime() {
            return CreateTime;
        }

        public String getNeedSendTime() {
            return NeedSendTime;
        }

        public String getStatus() {
            return Status;
        }

        public String getType() {
            return Type;
        }
    }
}
