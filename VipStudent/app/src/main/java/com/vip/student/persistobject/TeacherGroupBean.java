package com.vip.student.persistobject;

import java.util.List;

/**
 * Created by WuYue on 2015/12/17.
 */
public class TeacherGroupBean {

    private List<TeacherGroupEntity> TeacherGroup;

    public List<TeacherGroupEntity> getTeacherGroup() {
        return TeacherGroup;
    }

    public void setTeacherGroup(List<TeacherGroupEntity> teacherGroup) {
        TeacherGroup = teacherGroup;
    }

    public static class TeacherGroupEntity {
        /**
         * CourseProductName : 托福
         * CourseTypeCode : 0
         * Teachers : [{"CourseSubTypeCode":"0","CourseSubTypeName":"听力","TeacherQQ":"2880678345","TeacherEName":"Flora.Le","TeacherName":"Flora.Le","RemaiLessonCnt":6,"TotalLessonCnt":6},{"CourseSubTypeCode":"1","CourseSubTypeName":"口语","TeacherQQ":"2880998544","TeacherEName":"Stephen.Peng","TeacherName":"Stephen.Peng","RemaiLessonCnt":10,"TotalLessonCnt":10},{"CourseSubTypeCode":"2","CourseSubTypeName":"阅读","TeacherQQ":"2880697753","TeacherEName":"Icey.Gao","TeacherName":"Icey.Gao","RemaiLessonCnt":10,"TotalLessonCnt":10},{"CourseSubTypeCode":"3","CourseSubTypeName":"写作","TeacherQQ":"2880667086","TeacherEName":"April","TeacherName":"April","RemaiLessonCnt":10,"TotalLessonCnt":10}]
         */

        private String CourseProductName;
        private String CourseTypeCode;
        private List<TeachersEntity> Teachers;

        public void setCourseProductName(String CourseProductName) {
            this.CourseProductName = CourseProductName;
        }

        public void setCourseTypeCode(String CourseTypeCode) {
            this.CourseTypeCode = CourseTypeCode;
        }

        public void setTeachers(List<TeachersEntity> Teachers) {
            this.Teachers = Teachers;
        }

        public String getCourseProductName() {
            return CourseProductName;
        }

        public String getCourseTypeCode() {
            return CourseTypeCode;
        }

        public List<TeachersEntity> getTeachers() {
            return Teachers;
        }

        public static class TeachersEntity {
            /**
             * CourseSubTypeCode : 0
             * CourseSubTypeName : 听力
             * TeacherQQ : 2880678345
             * TeacherEName : Flora.Le
             * TeacherName : Flora.Le
             * RemaiLessonCnt : 6
             * TotalLessonCnt : 6
             */

            private String CourseSubTypeCode;
            private String CourseSubTypeName;
            private String TeacherQQ;
            private String TeacherEName;
            private String TeacherName;
            private int RemaiLessonCnt;
            private int TotalLessonCnt;

            public void setCourseSubTypeCode(String CourseSubTypeCode) {
                this.CourseSubTypeCode = CourseSubTypeCode;
            }

            public void setCourseSubTypeName(String CourseSubTypeName) {
                this.CourseSubTypeName = CourseSubTypeName;
            }

            public void setTeacherQQ(String TeacherQQ) {
                this.TeacherQQ = TeacherQQ;
            }

            public void setTeacherEName(String TeacherEName) {
                this.TeacherEName = TeacherEName;
            }

            public void setTeacherName(String TeacherName) {
                this.TeacherName = TeacherName;
            }

            public void setRemaiLessonCnt(int RemaiLessonCnt) {
                this.RemaiLessonCnt = RemaiLessonCnt;
            }

            public void setTotalLessonCnt(int TotalLessonCnt) {
                this.TotalLessonCnt = TotalLessonCnt;
            }

            public String getCourseSubTypeCode() {
                return CourseSubTypeCode;
            }

            public String getCourseSubTypeName() {
                return CourseSubTypeName;
            }

            public String getTeacherQQ() {
                return TeacherQQ;
            }

            public String getTeacherEName() {
                return TeacherEName;
            }

            public String getTeacherName() {
                return TeacherName;
            }

            public int getRemaiLessonCnt() {
                return RemaiLessonCnt;
            }

            public int getTotalLessonCnt() {
                return TotalLessonCnt;
            }
        }
    }

}
