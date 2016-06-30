package com.vip.student.message.bean;

import com.vip.student.network.BaseResponseBean;

import java.util.List;

/**
 * Created by WuYue on 2015/12/11.
 */
public class MsgListResponseBean extends BaseResponseBean {

    /**
     * Data : [{"MessageID":"sample string 1","MessageTypeCode":"sample string 2","MessageTypeName":"sample string 3","Title":"sample string 4","Content":"sample string 5","BusId":"sample string 6","CreateTime":"2015-12-11 16:05:20","Status":"sample string 8"},{"MessageID":"sample string 1","MessageTypeCode":"sample string 2","MessageTypeName":"sample string 3","Title":"sample string 4","Content":"sample string 5","BusId":"sample string 6","CreateTime":"2015-12-11 16:05:20","Status":"sample string 8"},{"MessageID":"sample string 1","MessageTypeCode":"sample string 2","MessageTypeName":"sample string 3","Title":"sample string 4","Content":"sample string 5","BusId":"sample string 6","CreateTime":"2015-12-11 16:05:20","Status":"sample string 8"}]
     */

    private List<DataEntity> Data;

    public void setData(List<DataEntity> Data) {
        this.Data = Data;
    }

    public List<DataEntity> getData() {
        return Data;
    }

    public static class DataEntity {
        /**
         * MessageID : sample string 1
         * MessageTypeCode : sample string 2
         * MessageTypeName : sample string 3
         * Title : sample string 4
         * Content : sample string 5
         * BusId : sample string 6
         * CreateTime : 2015-12-11 16:05:20
         * Status : sample string 8
         */

        private String MessageID;
        private String MessageTypeCode;
        private String MessageTypeName;
        private String Title;
        private String Content;
        private String BusId;
        private String CreateTime;
        private String Status;

        public void setMessageID(String MessageID) {
            this.MessageID = MessageID;
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

        public void setBusId(String BusId) {
            this.BusId = BusId;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getMessageID() {
            return MessageID;
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

        public String getBusId() {
            return BusId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public String getStatus() {
            return Status;
        }
    }
}
