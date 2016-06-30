package com.vip.student.login.bean;

import com.vip.student.network.BaseResponseBean;
import com.vip.student.persistobject.CourseTimeModelBean;
import com.vip.student.persistobject.TeacherGroupBean;

import java.util.List;

/**
 * Created by WuYue on 2015/12/10.
 */
public class UserInfoBean extends BaseResponseBean {

    /**
     * Data : {"CourseStatus":{"TotalCourseCnt":1,"NoCommentCount":2,"FinishCourseCnt":3,"TotalTestCnt":4,"FinishTestCnt":5,"TotalRealTopicCnt":6,"FinishRealTopicCnt":7},"StudentItem":{"StudentId":"sample string 1","StudentName":"sample string 2","StudentNickName":"sample string 3","StudentQQ":"sample string 4","StudentIconUrl":"sample string 5","Sex":"sample string 6","MobilePhone":"sample string 7","UserName":"sample string 8","Email":"sample string 9","Age":"sample string 10","Telephone":"sample string 11","StudentDays":12,"StudyConsultantName":"sample string 13","StudyConsultantEName":"sample string 14","StudyConsultantTel":"sample string 15","FirstLessonTime":"2015-12-11 09:51:16","LasterLessonTime":"2015-12-11 09:51:16","ScoreNextDate":"2015-12-11 09:51:16"},"TeacherGroup":[{"CourseTypeCode":"sample string 1","CourseProductName":"sample string 2","Teachers":[{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7}]},{"CourseTypeCode":"sample string 1","CourseProductName":"sample string 2","Teachers":[{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7}]},{"CourseTypeCode":"sample string 1","CourseProductName":"sample string 2","Teachers":[{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7}]}],"Token":"sample string 1","UserId":"sample string 2"}
     */

    private DataEntity Data;

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public DataEntity getData() {
        return Data;
    }

    public static class DataEntity {
        /**
         * CourseStatus : {"TotalCourseCnt":1,"NoCommentCount":2,"FinishCourseCnt":3,"TotalTestCnt":4,"FinishTestCnt":5,"TotalRealTopicCnt":6,"FinishRealTopicCnt":7}
         * StudentItem : {"StudentId":"sample string 1","StudentName":"sample string 2","StudentNickName":"sample string 3","StudentQQ":"sample string 4","StudentIconUrl":"sample string 5","Sex":"sample string 6","MobilePhone":"sample string 7","UserName":"sample string 8","Email":"sample string 9","Age":"sample string 10","Telephone":"sample string 11","StudentDays":12,"StudyConsultantName":"sample string 13","StudyConsultantEName":"sample string 14","StudyConsultantTel":"sample string 15","FirstLessonTime":"2015-12-11 09:51:16","LasterLessonTime":"2015-12-11 09:51:16","ScoreNextDate":"2015-12-11 09:51:16"}
         * TeacherGroup : [{"CourseTypeCode":"sample string 1","CourseProductName":"sample string 2","Teachers":[{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7}]},{"CourseTypeCode":"sample string 1","CourseProductName":"sample string 2","Teachers":[{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7}]},{"CourseTypeCode":"sample string 1","CourseProductName":"sample string 2","Teachers":[{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7}]}]
         * Token : sample string 1
         * UserId : sample string 2
         */

        private CourseStatusEntity CourseStatus;
        private StudentItemEntity StudentItem;
        private String Token;
        private String UserId;
        private List<TeacherGroupBean.TeacherGroupEntity> TeacherGroup;

        public void setCourseStatus(CourseStatusEntity CourseStatus) {
            this.CourseStatus = CourseStatus;
        }

        public void setStudentItem(StudentItemEntity StudentItem) {
            this.StudentItem = StudentItem;
        }

        public void setToken(String Token) {
            this.Token = Token;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public void setTeacherGroup(List<TeacherGroupBean.TeacherGroupEntity> TeacherGroup) {
            this.TeacherGroup = TeacherGroup;
        }

        public CourseStatusEntity getCourseStatus() {
            return CourseStatus;
        }

        public StudentItemEntity getStudentItem() {
            return StudentItem;
        }

        public String getToken() {
            return Token;
        }

        public String getUserId() {
            return UserId;
        }

        public List<TeacherGroupBean.TeacherGroupEntity> getTeacherGroup() {
            return TeacherGroup;
        }

        public static class CourseStatusEntity {
            /**
             * TotalCourseCnt : 1
             * NoCommentCount : 2
             * FinishCourseCnt : 3
             * TotalTestCnt : 4
             * FinishTestCnt : 5
             * TotalRealTopicCnt : 6
             * FinishRealTopicCnt : 7
             */

            private int TotalCourseCnt;
            private int NoCommentCount;
            private int FinishCourseCnt;
            private int TotalTestCnt;
            private int FinishTestCnt;
            private int TotalRealTopicCnt;
            private int FinishRealTopicCnt;

            private List<CourseTimeModelBean> CourseTimeModels;

            public void setTotalCourseCnt(int TotalCourseCnt) {
                this.TotalCourseCnt = TotalCourseCnt;
            }

            public void setNoCommentCount(int NoCommentCount) {
                this.NoCommentCount = NoCommentCount;
            }

            public void setFinishCourseCnt(int FinishCourseCnt) {
                this.FinishCourseCnt = FinishCourseCnt;
            }

            public void setTotalTestCnt(int TotalTestCnt) {
                this.TotalTestCnt = TotalTestCnt;
            }

            public void setFinishTestCnt(int FinishTestCnt) {
                this.FinishTestCnt = FinishTestCnt;
            }

            public void setTotalRealTopicCnt(int TotalRealTopicCnt) {
                this.TotalRealTopicCnt = TotalRealTopicCnt;
            }

            public void setFinishRealTopicCnt(int FinishRealTopicCnt) {
                this.FinishRealTopicCnt = FinishRealTopicCnt;
            }

            public int getTotalCourseCnt() {
                return TotalCourseCnt;
            }

            public int getNoCommentCount() {
                return NoCommentCount;
            }

            public int getFinishCourseCnt() {
                return FinishCourseCnt;
            }

            public int getTotalTestCnt() {
                return TotalTestCnt;
            }

            public int getFinishTestCnt() {
                return FinishTestCnt;
            }

            public int getTotalRealTopicCnt() {
                return TotalRealTopicCnt;
            }

            public int getFinishRealTopicCnt() {
                return FinishRealTopicCnt;
            }

            public List<CourseTimeModelBean> getCourseTimeModels() {
                return CourseTimeModels;
            }

            public void setCourseTimeModels(List<CourseTimeModelBean> courseTimeModels) {
                CourseTimeModels = courseTimeModels;
            }
        }

        public static class StudentItemEntity {
            /**
             * StudentId : sample string 1
             * StudentName : sample string 2
             * StudentNickName : sample string 3
             * StudentQQ : sample string 4
             * StudentIconUrl : sample string 5
             * Sex : sample string 6
             * MobilePhone : sample string 7
             * UserName : sample string 8
             * Email : sample string 9
             * Age : sample string 10
             * Telephone : sample string 11
             * StudentDays : 12
             * StudyConsultantName : sample string 13
             * StudyConsultantEName : sample string 14
             * StudyConsultantTel : sample string 15
             * FirstLessonTime : 2015-12-11 09:51:16
             * LasterLessonTime : 2015-12-11 09:51:16
             * ScoreNextDate : 2015-12-11 09:51:16
             */

            private String StudentId;
            private String StudentName;
            private String StudentNickName;
            private String StudentQQ;
            private String StudentIconUrl;
            private String Sex;
            private String MobilePhone;
            private String UserName;
            private String Email;
            private String Age;
            private String Telephone;
            private int StudentDays;
            private String StudyConsultantName;
            private String StudyConsultantNameSex ;
            private String StudyConsultantEName;
            private String StudyConsultantTel;
            private String FirstLessonTime;
            private String LasterLessonTime;
            private String ScoreNextDate;

            public void setStudentId(String StudentId) {
                this.StudentId = StudentId;
            }

            public void setStudentName(String StudentName) {
                this.StudentName = StudentName;
            }

            public void setStudentNickName(String StudentNickName) {
                this.StudentNickName = StudentNickName;
            }

            public void setStudentQQ(String StudentQQ) {
                this.StudentQQ = StudentQQ;
            }

            public void setStudentIconUrl(String StudentIconUrl) {
                this.StudentIconUrl = StudentIconUrl;
            }

            public void setSex(String Sex) {
                this.Sex = Sex;
            }

            public void setMobilePhone(String MobilePhone) {
                this.MobilePhone = MobilePhone;
            }

            public void setUserName(String UserName) {
                this.UserName = UserName;
            }

            public void setEmail(String Email) {
                this.Email = Email;
            }

            public void setAge(String Age) {
                this.Age = Age;
            }

            public void setTelephone(String Telephone) {
                this.Telephone = Telephone;
            }

            public void setStudentDays(int StudentDays) {
                this.StudentDays = StudentDays;
            }

            public void setStudyConsultantName(String StudyConsultantName) {
                this.StudyConsultantName = StudyConsultantName;
            }

            public String getStudyConsultantNameSex() {
                return StudyConsultantNameSex;
            }

            public void setStudyConsultantNameSex(String studyConsultantNameSex) {
                StudyConsultantNameSex = studyConsultantNameSex;
            }

            public void setStudyConsultantEName(String StudyConsultantEName) {
                this.StudyConsultantEName = StudyConsultantEName;
            }

            public void setStudyConsultantTel(String StudyConsultantTel) {
                this.StudyConsultantTel = StudyConsultantTel;
            }

            public void setFirstLessonTime(String FirstLessonTime) {
                this.FirstLessonTime = FirstLessonTime;
            }

            public void setLasterLessonTime(String LasterLessonTime) {
                this.LasterLessonTime = LasterLessonTime;
            }

            public void setScoreNextDate(String ScoreNextDate) {
                this.ScoreNextDate = ScoreNextDate;
            }

            public String getStudentId() {
                return StudentId;
            }

            public String getStudentName() {
                return StudentName;
            }

            public String getStudentNickName() {
                return StudentNickName;
            }

            public String getStudentQQ() {
                return StudentQQ;
            }

            public String getStudentIconUrl() {
                return StudentIconUrl;
            }

            public String getSex() {
                return Sex;
            }

            public String getMobilePhone() {
                return MobilePhone;
            }

            public String getUserName() {
                return UserName;
            }

            public String getEmail() {
                return Email;
            }

            public String getAge() {
                return Age;
            }

            public String getTelephone() {
                return Telephone;
            }

            public int getStudentDays() {
                return StudentDays;
            }

            public String getStudyConsultantName() {
                return StudyConsultantName;
            }

            public String getStudyConsultantEName() {
                return StudyConsultantEName;
            }

            public String getStudyConsultantTel() {
                return StudyConsultantTel;
            }

            public String getFirstLessonTime() {
                return FirstLessonTime;
            }

            public String getLasterLessonTime() {
                return LasterLessonTime;
            }

            public String getScoreNextDate() {
                return ScoreNextDate;
            }
        }

        /*public static class TeacherGroupEntity {
            *//**
             * CourseTypeCode : sample string 1
             * CourseProductName : sample string 2
             * Teachers : [{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7},{"CourseSubTypeCode":"sample string 1","CourseSubTypeName":"sample string 2","TeacherName":"sample string 3","TeacherEName":"sample string 4","TeacherQQ":"sample string 5","TotalLessonCnt":6,"RemaiLessonCnt":7}]
             *//*

            private String CourseTypeCode;
            private String CourseProductName;
            private List<TeachersEntity> Teachers;

            public void setCourseTypeCode(String CourseTypeCode) {
                this.CourseTypeCode = CourseTypeCode;
            }

            public void setCourseProductName(String CourseProductName) {
                this.CourseProductName = CourseProductName;
            }

            public void setTeachers(List<TeachersEntity> Teachers) {
                this.Teachers = Teachers;
            }

            public String getCourseTypeCode() {
                return CourseTypeCode;
            }

            public String getCourseProductName() {
                return CourseProductName;
            }

            public List<TeachersEntity> getTeachers() {
                return Teachers;
            }

            public static class TeachersEntity {
                *//**
                 * CourseSubTypeCode : sample string 1
                 * CourseSubTypeName : sample string 2
                 * TeacherName : sample string 3
                 * TeacherEName : sample string 4
                 * TeacherQQ : sample string 5
                 * TotalLessonCnt : 6
                 * RemaiLessonCnt : 7
                 *//*

                private String CourseSubTypeCode;
                private String CourseSubTypeName;
                private String TeacherName;
                private String TeacherEName;
                private String TeacherQQ;
                private int TotalLessonCnt;
                private int RemaiLessonCnt;

                public void setCourseSubTypeCode(String CourseSubTypeCode) {
                    this.CourseSubTypeCode = CourseSubTypeCode;
                }

                public void setCourseSubTypeName(String CourseSubTypeName) {
                    this.CourseSubTypeName = CourseSubTypeName;
                }

                public void setTeacherName(String TeacherName) {
                    this.TeacherName = TeacherName;
                }

                public void setTeacherEName(String TeacherEName) {
                    this.TeacherEName = TeacherEName;
                }

                public void setTeacherQQ(String TeacherQQ) {
                    this.TeacherQQ = TeacherQQ;
                }

                public void setTotalLessonCnt(int TotalLessonCnt) {
                    this.TotalLessonCnt = TotalLessonCnt;
                }

                public void setRemaiLessonCnt(int RemaiLessonCnt) {
                    this.RemaiLessonCnt = RemaiLessonCnt;
                }

                public String getCourseSubTypeCode() {
                    return CourseSubTypeCode;
                }

                public String getCourseSubTypeName() {
                    return CourseSubTypeName;
                }

                public String getTeacherName() {
                    return TeacherName;
                }

                public String getTeacherEName() {
                    return TeacherEName;
                }

                public String getTeacherQQ() {
                    return TeacherQQ;
                }

                public int getTotalLessonCnt() {
                    return TotalLessonCnt;
                }

                public int getRemaiLessonCnt() {
                    return RemaiLessonCnt;
                }
            }
        }*/
    }
}
