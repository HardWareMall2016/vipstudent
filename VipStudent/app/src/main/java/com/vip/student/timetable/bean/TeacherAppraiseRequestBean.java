package com.vip.student.timetable.bean;

import com.vip.student.network.BaseRequestBean;

/**
 * Created by Administrator on 2015/12/21.
 */
public class TeacherAppraiseRequestBean extends BaseRequestBean {

    private DataEntity Data;

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public DataEntity getData() {
        return Data;
    }

    public static class DataEntity {
        private String ComplainState;//投诉状态
        private String NoFinishReason;//为上完课的原因
        private String IsFinishClass;//课是否上完
        private String LessonId;
        private String NetworkQuality; //总体
        private String PreparLesson;//态度
        private String Communication;//逻辑
        private String TeachingAbility; //针对
        private String AppraiseDetail;//评论

        public void setComplainState(String ComplainState) {
            this.ComplainState = ComplainState;
        }

        public void setNoFinishReason(String NoFinishReason) {
            this.NoFinishReason = NoFinishReason;
        }

        public void setIsFinishClass(String IsFinishClass) {
            this.IsFinishClass = IsFinishClass;
        }

        public void setLessonId(String LessonId) {
            this.LessonId = LessonId;
        }

        public void setNetworkQuality(String NetworkQuality) {
            this.NetworkQuality = NetworkQuality;
        }

        public void setPreparLesson(String PreparLesson) {
            this.PreparLesson = PreparLesson;
        }

        public void setCommunication(String Communication) {
            this.Communication = Communication;
        }

        public void setTeachingAbility(String TeachingAbility) {
            this.TeachingAbility = TeachingAbility;
        }

        public void setAppraiseDetail(String AppraiseDetail) {
            this.AppraiseDetail = AppraiseDetail;
        }

        public String getComplainState() {
            return ComplainState;
        }

        public String getNoFinishReason() {
            return NoFinishReason;
        }

        public String getIsFinishClass() {
            return IsFinishClass;
        }

        public String getLessonId() {
            return LessonId;
        }

        public String getNetworkQuality() {
            return NetworkQuality;
        }

        public String getPreparLesson() {
            return PreparLesson;
        }

        public String getCommunication() {
            return Communication;
        }

        public String getTeachingAbility() {
            return TeachingAbility;
        }

        public String getAppraiseDetail() {
            return AppraiseDetail;
        }
    }
}
